package de.schmiereck.hex2d;

public class PartStep {
    private PartEvent partEvent;
    private long probability;
    private long addProbability = 0L;
    private long subProbability = 0L;
    private final int[] dirProbability = new int[Cell.Dir.values().length];
    private final int[] rotProbability = new int[Cell.Dir.values().length];
    private Cell.Dir nextDir;
    private Cell.Dir rotDir;

    public PartStep(final PartEvent partEvent, final long probability) {
        this.partEvent = partEvent;
        this.probability = probability;
        //np this.nextDir = Cell.Dir.NP;
        this.nextDir = Cell.Dir.AP;
        this.rotDir = Cell.Dir.AP;
    }

    public PartEvent getPartEvent() {
        return this.partEvent;
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

    public void setRot(final Cell.Dir dir, final int rotProbability) {
        this.rotProbability[dir.ordinal()] = rotProbability;
    }

    public int getRot(final Cell.Dir dir) {
        return this.rotProbability[dir.ordinal()];
    }

    public Cell.Dir getNextDir() {
        return this.nextDir;
    }

    public void setNextDir(final Cell.Dir nextDir) {
        this.nextDir = nextDir;
    }

    public Cell.Dir getRotDir() {
        return this.rotDir;
    }

    public void setRotDir(final Cell.Dir rotDir) {
        this.rotDir = rotDir;
    }

    public long getAddProbability() {
        return this.addProbability;
    }

    public void setAddProbability(final long addProbability) {
        this.addProbability = addProbability;
    }

    public void addAddProbability(final long addProbability) {
        this.addProbability += addProbability;
    }
}
