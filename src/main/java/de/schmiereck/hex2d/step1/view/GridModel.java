package de.schmiereck.hex2d.step1.view;

import javafx.scene.shape.Circle;

public class GridModel {
    private int nodeCountX;
    private int nodeCountY;
    private GridCellModel[][] gridCellArr;

    public void init(final int nodeCountX, final int nodeCountY) {
        this.nodeCountX = nodeCountX;
        this.nodeCountY = nodeCountY;
        this.gridCellArr = new GridCellModel[nodeCountX][nodeCountY];
    }

    public void setShape(final int posX, final int posY, final Circle shape) {
        this.gridCellArr[posX][posY].setShape(shape);
    }

    public GridCellModel getGridCellModel(final int posX, final int posY) {
        return this.gridCellArr[posX][posY];
    }

    public void setGridCellModel(final int posX, final int posY, final GridCellModel gridCellModel) {
        this.gridCellArr[posX][posY] = gridCellModel;
    }

    public int getNodeCountX() {
        return this.nodeCountX;
    }

    public int getNodeCountY() {
        return this.nodeCountY;
    }
}
