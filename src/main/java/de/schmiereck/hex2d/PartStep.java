package de.schmiereck.hex2d;

import de.schmiereck.hex2d.math.Num;

public class PartStep {
    private PartStep parentPartStep;
    private final Cell.Dir dir;
    private long probability;
    private final int[] dirProbability = new int[Cell.Dir.values().length];
    private final Num probNum;

    public PartStep(final PartStep parentPartStep, final Cell.Dir dir, final long probability, final Num probNum) {
        this.parentPartStep = parentPartStep;
        this.dir = dir;
        this.probability = probability;
        this.probNum = probNum;
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

    public void addProbability(final int probability) {
        this.probability += probability;
    }

    public Num getProbNum() {
        return this.probNum;
    }
}
