package de.schmiereck.hex2d;

import java.util.List;

public class GridNode {
    private final int posX;
    private final int posY;

    private final Cell[] cellArr;

    public GridNode(final int posX, final int posY) {
        this.posX = posX;
        this.posY = posY;
        this.cellArr = new Cell[2];
        this.cellArr[0] = new Cell();
        this.cellArr[1] = new Cell();
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public List<PartStep> getPartStepList(final int cellArrPos) {
        return this.cellArr[cellArrPos].getPartStepList();
    }

    public void addPartStep(final int cellArrPos, final PartStep partStep) {
        this.cellArr[cellArrPos].addPartStep(partStep);
    }
}
