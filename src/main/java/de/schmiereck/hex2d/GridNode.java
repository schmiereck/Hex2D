package de.schmiereck.hex2d;

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

    public void setProb(final int cellArrPos, final Cell.Dir dir, final int probability) {
        this.cellArr[cellArrPos].setProb(dir, probability);
    }

    public int getProb(final int cellArrPos, final Cell.Dir dir) {
        return this.cellArr[cellArrPos].getProb(dir);
    }

    public void setProbability(final int cellArrPos, final long probability) {
        this.cellArr[cellArrPos].setProbability(probability);
    }

    public void addProbability(final int cellArrPos, final long probability) {
        this.cellArr[cellArrPos].addProbability(probability);
    }

    public long getProbability(final int cellArrPos) {
        return this.cellArr[cellArrPos].getProbability();
    }

    public void setProbabilityDenominator(final int cellArrPos, final long probabilityDenominator) {
        this.cellArr[cellArrPos].setProbabilityDenominator(probabilityDenominator);
    }

    public long getProbabilityDenominator(final int cellArrPos) {
        return this.cellArr[cellArrPos].getProbabilityDenominator();
    }

}
