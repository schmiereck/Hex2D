package de.schmiereck.hex2d.step2.view;

import de.schmiereck.hex2d.step2.service.HexGrid;
import de.schmiereck.hex2d.step2.service.HexGridService;
import de.schmiereck.hex2d.step2.service.PartStep;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
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
        double minProb = Double.POSITIVE_INFINITY;
        double maxProb = 0.0D;
        for (int posY = 0; posY < this.gridModel.getNodeCountY(); posY++) {
            for (int posX = 0; posX < this.gridModel.getNodeCountX(); posX++) {
                final double gridNodeProbability = this.hexGridService.retrieveActGridNodeProbability(posX, posY);
                if (gridNodeProbability > 0.0D) {
                    if (gridNodeProbability < minProb) minProb = gridNodeProbability;
                    if (gridNodeProbability > maxProb) maxProb = gridNodeProbability;
                }
            }
        }

        final double minRadius = 0.5D;
        final double maxRadius = Math.max(minRadius, this.StepX * 0.45D);

        // Vorhandene Eigentime-Linien entfernen
        for (int posY = 0; posY < this.gridModel.getNodeCountY(); posY++) {
            for (int posX = 0; posX < this.gridModel.getNodeCountX(); posX++) {
                final GridCellModel gridCellModel = this.gridModel.getGridCellModel(posX, posY);
                if (!gridCellModel.getEigentimeLineList().isEmpty()) {
                    this.mainPane.getChildren().removeAll(gridCellModel.getEigentimeLineList());
                    gridCellModel.clearEigentimeLines();
                }
            }
        }

        // 2. Durchlauf: Darstellung Wahrscheinlichkeiten + Eigentime-Linienketten
        for (int posY = 0; posY < this.gridModel.getNodeCountY(); posY++) {
            for (int posX = 0; posX < this.gridModel.getNodeCountX(); posX++) {
                final GridCellModel gridCellModel = this.gridModel.getGridCellModel(posX, posY);
                final Circle gridNodeCircle = gridCellModel.getShape();
                final double gridNodeProbability = this.hexGridService.retrieveActGridNodeProbability(posX, posY);

                if ((gridNodeProbability > 0.0D) && (maxProb > 0.0D) && (minProb != Double.POSITIVE_INFINITY)) {
                    final double radius = this.scaleProbability(gridNodeProbability, minProb, maxProb, minRadius, maxRadius);
                    gridNodeCircle.setRadius(radius);
                    gridNodeCircle.setFill(Color.YELLOW);

                    // Eigentime-Linien zeichnen (eine Kette pro PartStep)
                    final List<PartStep> partSteps = this.hexGridService.retrieveActPartSteps(posX, posY);
                    this.drawEigentimeChains(gridCellModel, partSteps, radius);
                } else {
                    gridNodeCircle.setRadius(minRadius);
                    gridNodeCircle.setFill(Color.DARKGRAY);
                }
            }
        }
    }

    private void drawEigentimeChains(final GridCellModel gridCellModel, final List<PartStep> partStepList,
                                     final double baseRadius) {
        if (partStepList == null || partStepList.isEmpty()) return;

        final double centerX = gridCellModel.getScreenPosX();
        final double centerY = gridCellModel.getScreenPosY();
        final double maxLen = this.StepX * 0.1D; // maximale Gesamtlänge einer Kette
        //final int segmentsPerChain = 4; // feste Segmentanzahl je PartStep

        double x0 = centerX;
        double y0 = centerY;
        double currentAngle0 = 0.0D;

        for (final PartStep partStep : partStepList) {
            if (partStep.getProbability() <= 64 * 4 * 4) continue; // zu kleine Wahrscheinlichkeit

            //final double prob = Math.max(0.0D, (double) partStep.getProbability());
            //final double lenFactor = prob / (double) HexGridService.PROBABILITY; // [0..1]
            //final double totalChainLen = Math.max(baseRadius * 0.6D, maxLen * lenFactor); // minimale sichtbare Länge
            //final double totalChainLen = maxLen; // minimale sichtbare Länge
            //final double segmentLen = totalChainLen / segmentsPerChain;
            final double segmentLen = maxLen;

            final double angleRad = HexGridService.calcAngleRadFromEigentime(partStep.getEigentime());
            // Rotationsinkrement: kleiner Zusatzwinkel für Kettenoptik
            //final double deltaAngle = Math.toRadians(12.0D); // 12° je Segment

            final double currentAngle1 = currentAngle0 + angleRad;

            final double x1 = x0 + (Math.cos(currentAngle1) * segmentLen);
            final double y1 = y0 + (Math.sin(currentAngle1) * segmentLen);

            final Line line = new Line(x0, y0, x1, y1);
            line.setStrokeWidth(1.0D);
            line.setStroke(calcStrokeColor(partStep));
            line.setOpacity(0.85D);

            // Linien nach den Kreisen zeichnen -> oben liegend
            this.mainPane.getChildren().add(line);
            gridCellModel.getEigentimeLineList().add(line);

            // Nächstes Segment startet am Ende und rotiert weiter
            x0 = x1;
            y0 = y1;
            currentAngle0 = currentAngle1;
        }
    }

    private Color calcStrokeColor(final PartStep partStep) {
        final long impulse = partStep.getImpulse();
        if (impulse > 0) return Color.LIMEGREEN;
        if (impulse < 0) return Color.ORANGERED;
        return Color.DEEPSKYBLUE;
    }

    // Neue Hilfsmethode: Skalierung in [minR, maxR]
    private double scaleProbability(final double value,
                                    final double minProb, final double maxProb,
                                    final double minR, final double maxR) {
        if ((value <= 0.0D) || (minProb <= 0.0D) || (maxProb <= 0.0D)) return minR;

        final double lv = (value);
        final double lmax = (maxProb);

        double t = (lv) / (lmax);
        if (t < 0.0D) t = 0.0D;
        if (t > 1.0D) t = 1.0D;

        return minR + (t * (maxR - minR));
    }

    // Neue Hilfsmethode: Logarithmische Skalierung in [minR, maxR]
    private double scaleProbabilityLog(final double value,
                                       final double minProb, final double maxProb,
                                       final double minR, final double maxR) {
        if ((value <= 0.0D) || (minProb <= 0.0D) || (maxProb <= 0.0D)) return minR;
        if (minProb == maxProb) return (minR + maxR) / 2.0D;

        final double lv = Math.log(value);
        final double lmin = Math.log(minProb);
        final double lmax = Math.log(maxProb);

        double t = (lv - lmin) / (lmax - lmin);
        if (t < 0.0D) t = 0.0D;
        if (t > 1.0D) t = 1.0D;

        return minR + (t * (maxR - minR));
    }
}