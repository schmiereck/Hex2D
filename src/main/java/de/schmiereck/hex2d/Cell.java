package de.schmiereck.hex2d;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    public enum Dir {
        NP, AP, BP, CP, AN, BN, CN
    }

    private List<PartStep> partStepList = new ArrayList<>();

    public List<PartStep> getPartStepList() {
        return this.partStepList;
    }

    public void addPartStep(final PartStep partStep) {
        this.partStepList.add(partStep);
    }
}
