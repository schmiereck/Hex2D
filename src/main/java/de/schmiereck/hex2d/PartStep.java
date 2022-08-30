package de.schmiereck.hex2d;

public class PartStep {
    private PartStep parentPartStep;
    private final Cell.Dir dir;
    private final long probability;
    private final int dirProbability[] = new int[Cell.Dir.values().length];

    private boolean calcuated = false;
    private double calcuatedProbability;

    public PartStep(final PartStep parentPartStep, final Cell.Dir dir, final long probability) {
        this.parentPartStep = parentPartStep;
        this.dir = dir;
        this.probability = probability;
    }

    public PartStep getParentPartStep() {
        return parentPartStep;
    }

    public Cell.Dir getDir() {
        return this.dir;
    }

    public long getProbability() {
        return this.probability;
    }

    public void setProb(final Cell.Dir dir, final int probability) {
        this.dirProbability[dir.ordinal()] = probability;
    }

    public int getProb(final Cell.Dir dir) {
        return this.dirProbability[dir.ordinal()];
    }

    public boolean getCalcuated() {
        return this.calcuated;
    }

    public void setCalcuated(final boolean calcuated) {
        this.calcuated = calcuated;
        if (this.calcuated)
            this.parentPartStep = null;
    }

    public double getCalcuatedProbability() {
        return this.calcuatedProbability;
    }

    public void setCalcuatedProbability(final double calcuatedProbability) {
        this.calcuatedProbability = calcuatedProbability;
    }
}
