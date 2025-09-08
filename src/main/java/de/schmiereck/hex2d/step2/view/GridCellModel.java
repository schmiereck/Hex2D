package de.schmiereck.hex2d.step2.view;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

public class GridCellModel {
    private final double screenPosX;
    private final double screenPosY;
    private Circle shape;
    // Liste der aktuell dargestellten Eigentime-Linien (wird in jedem updateView neu erzeugt)
    private final List<Line> eigentimeLineList = new ArrayList<>();

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

    public List<Line> getEigentimeLineList() {
        return this.eigentimeLineList;
    }

    public void clearEigentimeLines() {
        this.eigentimeLineList.clear();
    }
}
