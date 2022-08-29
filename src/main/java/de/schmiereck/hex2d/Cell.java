package de.schmiereck.hex2d;

public class Cell {
    public enum Dir {
        NP, AP, BP, CP, AN, BN, CN
    }
    private int dirProbability[] = new int[Dir.values().length];

    /**
     * numerator
     */
    private long probability;

    public void setProb(final Cell.Dir dir, final int probability) {
        this.dirProbability[dir.ordinal()] = probability;
    }

    public int getProb(final Cell.Dir dir) {
        return this.dirProbability[dir.ordinal()];
    }

    public long getProbability() {
        return this.probability;
    }

    public void setProbability(final long probability) {
        this.probability = probability;
    }

    public void addProbability(final long probability) {
        this.probability += probability;
    }

}
