package de.schmiereck.hex2d;

public class PartStep {
    private PartStep parentPartStep;
    private final Cell.Dir dir;
    private long probability;
    private final int[] dirProbability = new int[Cell.Dir.values().length];
    private final long[] probCntArr = new long[HexGridService.PROBABILITY + 1];

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

    public long[] getProbCntArr() {
        return this.probCntArr;
    }

    public void addProbability(final int probability) {
        this.probability += probability;
    }
}
