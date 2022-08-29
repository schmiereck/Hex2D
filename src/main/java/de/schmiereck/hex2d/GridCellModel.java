package de.schmiereck.hex2d;

import javafx.scene.shape.Circle;

public class GridCellModel {
    private final double screenPosX;
    private final double screenPosY;
    private Circle shape;

    public GridCellModel(final double screenPosX, final double screenPosY) {
        this.screenPosX = screenPosX;
        this.screenPosY = screenPosY;
    }

    public double getScreenPosX() {
        return this.screenPosX;
    }

    public double getScreenPosY() {
        return this.screenPosY;
    }

    public Circle getShape() {
        return shape;
    }

    public void setShape(final Circle shape) {
        this.shape = shape;
    }
}
