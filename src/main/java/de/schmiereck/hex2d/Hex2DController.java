package de.schmiereck.hex2d;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Hex2DController implements Initializable
{
    @FXML
    private Label counterText;

    @FXML
    private Pane mainPane;

    @Autowired
    private HexGridService hexGridService;

    private GridModel gridModel = new GridModel();

    @FXML
    public void initialize() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.mainPane.setStyle("-fx-background-color: black;");

        this.hexGridService.initialize(2, 1);

        final HexGrid hexGrid = this.hexGridService.getHexGrid();

        this.gridModel.init(hexGrid.getNodeCountX(), hexGrid.getNodeCountY());

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

    @FXML
    protected void onNextButtonClick() {
        this.counterText.setText("Step: ");

        this.hexGridService.calcNext();

        this.updateView();
    }

    private void updateView() {
        for (int posY = 0; posY < this.gridModel.getNodeCountY(); posY++) {
            for (int posX = 0; posX < this.gridModel.getNodeCountX(); posX++) {
                final GridCellModel gridCellModel = this.gridModel.getGridCellModel(posX, posY);
                final Circle gridNodeCircle = gridCellModel.getShape();
                final double gridNodeProbability = this.hexGridService.retrieveActGridNodeProbability(posX, posY);
                if (gridNodeProbability > 0) {
                    gridNodeCircle.setRadius((gridNodeProbability * 9.0D) / HexGridService.PROBABILITY);
                    gridNodeCircle.setFill(Color.YELLOW);
                } else {
                    gridNodeCircle.setRadius(1.0D);
                    gridNodeCircle.setFill(Color.DARKGRAY);
                }
            }
        }
    }
}