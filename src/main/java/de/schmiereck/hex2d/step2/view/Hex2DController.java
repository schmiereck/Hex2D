package de.schmiereck.hex2d.step2.view;

import de.schmiereck.hex2d.step2.service.HexGrid;
import de.schmiereck.hex2d.step2.service.HexGridService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class Hex2DController implements Initializable
{
    @FXML
    private Label counterText;

    @FXML
    private BorderPane mainBoderPane;

    @FXML
    private Pane mainPane;

    @Autowired
    private HexGridService hexGridService;

    public double StepX;
    public double StepHalfX;
    public double StepY;

    private GridModel gridModel = new GridModel();

    @FXML
    public void initialize() {
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        this.mainPane.setStyle("-fx-background-color: black;");

        //this.hexGridService.initialize(2, 1);
        this.hexGridService.initialize(4, 2);

        final HexGrid hexGrid = this.hexGridService.getHexGrid();

        final double width = this.mainPane.getWidth();
        final double height = this.mainPane.getHeight();

        this.gridModel.init(hexGrid.getNodeCountX(), hexGrid.getNodeCountY());

        this.StepX = 32.0D;
        //this.StepX = ((double) width) / hexGrid.getNodeCountX();
        this.StepHalfX = StepX / 2.0D;
        this.StepY = Math.sqrt(Math.pow(StepX, 2.0D) - Math.pow(StepHalfX, 2.0D));

        this.initGridModel(this.gridModel);

        for (int posY = 0; posY < this.gridModel.getNodeCountY(); posY++) {
            for (int posX = 0; posX < this.gridModel.getNodeCountX(); posX++) {
                final GridCellModel gridCellModel = this.gridModel.getGridCellModel(posX, posY);
                final Circle gridNodeCircle = new Circle(1.0D, Color.DARKGRAY);
                gridNodeCircle.setCenterX(gridCellModel.getScreenPosX());
                gridNodeCircle.setCenterY(gridCellModel.getScreenPosY());
                //gridNodeCircle.relocate(gridNode.getScreenPosX(), gridNode.getScreenPosY());
                this.mainPane.getChildren().add(gridNodeCircle);

                this.gridModel.setShape(posX, posY, gridNodeCircle);
            }
        }

        this.updateView();
    }

    private void initGridModel(final GridModel gridModel) {
        for (int posY = 0; posY < gridModel.getNodeCountY(); posY++) {
            for (int posX = 0; posX < gridModel.getNodeCountX(); posX++) {
                final double screenPosX = this.calcScreenPosX(posX, posY);
                final double screenPosY = this.calcScreenPosY(posX, posY);
                gridModel.setGridCellModel(posX, posY, new GridCellModel(screenPosX, screenPosY));
            }
        }
    }

    private double calcScreenPosX(final int posX, final int posY) {
        return (posX * StepX) + ((posY % 2) * StepHalfX);
    }

    private double calcScreenPosY(final int posX, final int posY) {
        return (posY * StepY);
    }


    @FXML
    protected void onNextButtonClick() {
        this.hexGridService.calcNext();

        this.updateView();
    }

    private void updateView() {
        this.counterText.setText(String.format("Step: %d (Part-Steps: %,d)", this.hexGridService.retrieveStepCount(), this.hexGridService.retrievePartStepCount()));

        // 1. Durchlauf: min/max positive Wahrscheinlichkeiten bestimmen
        double minPos = Double.POSITIVE_INFINITY;
        double maxPos = 0.0D;
        for (int posY = 0; posY < this.gridModel.getNodeCountY(); posY++) {
            for (int posX = 0; posX < this.gridModel.getNodeCountX(); posX++) {
                final double gridNodeProbability = this.hexGridService.retrieveActGridNodeProbability(posX, posY);
                if (gridNodeProbability > 0.0D) {
                    if (gridNodeProbability < minPos) minPos = gridNodeProbability;
                    if (gridNodeProbability > maxPos) maxPos = gridNodeProbability;
                }
            }
        }

        final double minRadius = 0.5D;
        final double maxRadius = Math.max(minRadius, this.StepX * 0.45D);

        // 2. Durchlauf: logarithmisch skalierte Darstellung
        for (int posY = 0; posY < this.gridModel.getNodeCountY(); posY++) {
            for (int posX = 0; posX < this.gridModel.getNodeCountX(); posX++) {
                final GridCellModel gridCellModel = this.gridModel.getGridCellModel(posX, posY);
                final Circle gridNodeCircle = gridCellModel.getShape();
                final double gridNodeProbability = this.hexGridService.retrieveActGridNodeProbability(posX, posY);

                if ((gridNodeProbability > 0.0D) && (maxPos > 0.0D) && (minPos != Double.POSITIVE_INFINITY)) {
                    final double radius = scaleProbabilityLog(gridNodeProbability, minPos, maxPos, minRadius, maxRadius);
                    gridNodeCircle.setRadius(radius);
                    gridNodeCircle.setFill(Color.YELLOW);
                } else {
                    gridNodeCircle.setRadius(minRadius);
                    gridNodeCircle.setFill(Color.DARKGRAY);
                }
            }
        }
    }

    // Neue Hilfsmethode: Logarithmische Skalierung in [minR, maxR]
    private double scaleProbabilityLog(final double value, final double minPos, final double maxPos,
                                       final double minR, final double maxR) {
        if ((value <= 0.0D) || (minPos <= 0.0D) || (maxPos <= 0.0D)) return minR;
        if (minPos == maxPos) return (minR + maxR) / 2.0D;

        final double lv = Math.log(value);
        final double lmin = Math.log(minPos);
        final double lmax = Math.log(maxPos);

        double t = (lv - lmin) / (lmax - lmin);
        if (t < 0.0D) t = 0.0D;
        if (t > 1.0D) t = 1.0D;

        return minR + (t * (maxR - minR));
    }
}