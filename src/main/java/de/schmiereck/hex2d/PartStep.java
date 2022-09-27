package de.schmiereck.hex2d;

public class PartStep {
    private PartStep parentPartStep;
    private long probability;
    private long subProbability;
    private final int[] dirProbability = new int[Cell.Dir.values().length];
    private Cell.Dir nextDir;

    public PartStep(final PartStep parentPartStep, final long probability) {
        this.parentPartStep = parentPartStep;
        this.probability = probability;
        this.nextDir = Cell.Dir.NP;
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

    public Cell.Dir getNextDir() {
        return this.nextDir;
    }

    public void setNextDir(final Cell.Dir nextDir) {
        this.nextDir = nextDir;
    }
}
