package de.schmiereck.hex2d.step1.service;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    //      bn  cp    A
    //       \ /
    //   an---A---ap     A
    //       / \
    //      cn  bp    A
    public enum Dir {
        //!np NP,
        AP, BP, CP, AN, BN, CN
    }
    //public static Dir[] NDir = {
    //        AP, BP, CP, AN, BN, CN
    //};

    private List<PartStep> partStepList = new ArrayList<>();

    public List<PartStep> getPartStepList() {
        return this.partStepList;
    }

    public void addPartStep(final PartStep partStep) {
        this.partStepList.add(partStep);
    }
}
