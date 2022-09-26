package de.schmiereck.hex2d;

import de.schmiereck.hex2d.math.Num;

public class PartStep {
    private PartStep parentPartStep;
    private long probability;
    private long subProbability;
    private final int[] dirProbability = new int[Cell.Dir.values().length];

    public PartStep(final PartStep parentPartStep, final long probability) {
        this.parentPartStep = parentPartStep;
        this.probability = probability;
    }

    public PartStep getParentPartStep() {
        return parentPartStep;
    }

    public long getProbability() {
        return this.probability;
    }

    public void addProbability(final long probability) {
        this.probability += probability;
    }

    public long getSubProbability() {
        return this.subProbability;
    }

    public void setSubProbability(final long subProbability) {
        this.subProbability = subProbability;
    }

    public void addSubProbability(final long subProbability) {
        this.subProbability += subProbability;
    }

    public void setProb(final Cell.Dir dir, final int probability) {
        this.dirProbability[dir.ordinal()] = probability;
    }

    public int getProb(final Cell.Dir dir) {
        return this.dirProbability[dir.ordinal()];
    }
}
