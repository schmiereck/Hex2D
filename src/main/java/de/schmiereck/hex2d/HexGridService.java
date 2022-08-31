package de.schmiereck.hex2d;

import static de.schmiereck.hex2d.utils.DirUtils.calcAxisByDirNumber;
import static de.schmiereck.hex2d.utils.DirUtils.calcDirNumberByAxis;
import static de.schmiereck.hex2d.utils.DirUtils.calcDirProb;

import java.util.Objects;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

/**
 * <pre><code>
 *              dir               X    Y
 *              np                0     0
 * left-right:  ap    ABCDEFG     1     0
 * left-down:   bp    AFDBGEC     1     1
 * left-up:     cp    ACEGBDF     1    -1
 * right-left:  an    GFEDCBA    -1     0
 * right-up:    bn    CEGBDFA     0    -1
 * right-down:  cn    FDBGECA     0     1
 *
 *     bn  cp    A
 *      \ /
 *  an---A---ap     A
 *      / \
 *     cn  bp    A
 *
 *  A    bn  cp    A
 *        \ /
 *    an---A---ap    A
 *        / \
 *  A    cn  bp    A
 *
 * </code></pre>
 */
@Component
public class HexGridService {

    public static final int PROBABILITY = 1 * 2 * 3 * 4 * 5 * 7;
    public static final int PROBABILITY_0 = 0;
    public static final int PROBABILITY_1_1 = PROBABILITY;
    public static final int PROBABILITY_1_2 = PROBABILITY / 2;
    public static final int PROBABILITY_1_3 = PROBABILITY / 3;
    public static final int PROBABILITY_2_3 = PROBABILITY_1_3 * 2;
    public static final int PROBABILITY_1_4 = PROBABILITY / 4;
    public static final int PROBABILITY_2_4 = PROBABILITY_1_4 * 2;
    public static final int PROBABILITY_3_4 = PROBABILITY_1_4 * 3;
    public static final int PROBABILITY_1_8 = PROBABILITY / 8;
    public static final int PROBABILITY_2_8 = PROBABILITY_1_8 * 2;
    public static final int PROBABILITY_3_8 = PROBABILITY_1_8 * 3;
    public static final int PROBABILITY_1_10 = PROBABILITY / 10;
    public static final int PROBABILITY_4_10 = PROBABILITY_1_10 * 4;
    public static final int PROBABILITY_6_10 = PROBABILITY_1_10 * 6;
    public static final int PROBABILITY_9_10 = PROBABILITY_1_10 * 9;
    private HexGrid hexGrid;

    private int cellArrPos = 0;
    private int stepCount = 0;

    private static final int[][][] DirOffsetArr = {
            {
                    {0, 0},    // NP
                    {1, 0},    // AP
                    {0, 1},    // BP
                    {0, -1},    // CP
                    {-1, 0},    // AN
                    {-1, -1},    // BN
                    {-1, 1}     // CN
            },
            {
                    {0, 0},    // NP
                    {1, 0},    // AP
                    {1, 1},    // BP
                    {1, -1},    // CP
                    {-1, 0},    // AN
                    {0, -1},    // BN
                    {0, 1}     // CN
            }
    };

    public void initialize(final int sizeX, final int sizeY) {
        this.hexGrid = new HexGrid(sizeX, sizeY);

        final GridNode gridNode = this.hexGrid.getGridNode(3, 5);

        final PartStep partStep = new PartStep(null, Cell.Dir.NP, PROBABILITY);
        gridNode.addPartStep(this.getActCellArrPos(), partStep);

        //partStep.setProb(Cell.Dir.AP, PROBABILITY_1_1);
        initDirProb(partStep, Cell.Dir.AP, 0.0D);

        //partStep.setProb(Cell.Dir.AP, PROBABILITY_1_2);
        //partStep.setProb(Cell.Dir.CP, PROBABILITY_1_2);
        //initDirProb(partStep, Cell.Dir.CP, 0.5D);

        //partStep.setProb(Cell.Dir.AP, PROBABILITY_2_3);
        //partStep.setProb(Cell.Dir.CP, PROBABILITY_1_3);
        //initDirProb(partStep, Cell.Dir.AP, -0.25D);
        //initDirProb(partStep, Cell.Dir.CP, 0.75D);

        //partStep.setProb(Cell.Dir.AP, PROBABILITY_9_10);
        //partStep.setProb(Cell.Dir.CP, PROBABILITY_1_10);

        //partStep.setProb(Cell.Dir.CN, PROBABILITY_1_2);
        //partStep.setProb(Cell.Dir.BP, PROBABILITY_1_2);
        //initDirProb(partStep, Cell.Dir.BP, 0.5D);
        //initDirProb(partStep, Cell.Dir.CN, -0.5D);
    }

    private void initDirProb(final PartStep partStep, final Cell.Dir startDir, final double dirOffset) {
        final int startDirNumber = calcDirNumberByAxis(startDir);
        IntStream.rangeClosed(-2, 2).forEach(dirNumberOffset -> {
            final int dirNumber = startDirNumber + dirNumberOffset;
            final Cell.Dir dir = calcAxisByDirNumber(dirNumber);
            final int probalility = (int)(Math.round(calcDirProb(dirOffset, dirNumberOffset)) / 2.0D);
            partStep.setProb(dir, probalility);
        });
    }

    public void calcNext() {
        this.calcGrid();

        this.calcNextCellArrPos();

        this.clearNextGrid();

        this.stepCount++;
    }

    private void calcGrid() {
        for (int posY = 0; posY < this.hexGrid.getNodeCountY(); posY++) {
            for (int posX = 0; posX < this.hexGrid.getNodeCountX(); posX++) {
                final GridNode gridNode = this.hexGrid.getGridNode(posX, posY);
                for (final Cell.Dir dir : Cell.Dir.values()) {
                    final int rowNo = posY % 2;
                    final int[] offsetArr = DirOffsetArr[rowNo][dir.ordinal()];
                    final GridNode sourceGridNode =
                            this.hexGrid.getGridNode(posX + offsetArr[0], posY + offsetArr[1]);

                    final Cell.Dir sourceDir = calcOtherDir(dir);
                    sourceGridNode.getPartStepList(this.getActCellArrPos()).stream().forEach(sourcePartStep -> {
                        final int sourceDirProb = sourcePartStep.getProb(sourceDir);
                        if (sourceDirProb > 0) {
                            final PartStep partStep = new PartStep(sourcePartStep, sourceDir, sourceDirProb);

                            for (final Cell.Dir copyDir : Cell.Dir.values()) {
                                final int copyDirProb = sourcePartStep.getProb(copyDir);
                                partStep.setProb(copyDir, copyDirProb);
                            }

                            gridNode.addPartStep(this.getNextCellArrPos(), partStep);
                        }
                    });
                }
            }
        }
    }

    private void clearNextGrid() {
        for (int posY = 0; posY < this.hexGrid.getNodeCountY(); posY++) {
            for (int posX = 0; posX < this.hexGrid.getNodeCountX(); posX++) {
                final GridNode gridNode = this.hexGrid.getGridNode(posX, posY);
                gridNode.getPartStepList(this.getNextCellArrPos()).clear();
            }
        }
    }

    private static final Cell.Dir[] OtherDirArr = {

            Cell.Dir.NP,    // NP
            Cell.Dir.AN,    // AP
            Cell.Dir.BN,    // BP
            Cell.Dir.CN,    // CP
            Cell.Dir.AP,    // AN
            Cell.Dir.BP,    // BN
            Cell.Dir.CP     // CN
    };

    private Cell.Dir calcOtherDir(Cell.Dir dir) {
        return OtherDirArr[dir.ordinal()];
    }

    public HexGrid getHexGrid() {
        return this.hexGrid;
    }

    public GridNode retrieveGridNode(final int posX, final int posY) {
        return this.hexGrid.getGridNode(posX, posY);
    }

    private int getActCellArrPos() {
        return this.cellArrPos;
    }

    private int getNextCellArrPos() {
        return this.cellArrPos == 0 ? 1 : 0;
    }

    private void calcNextCellArrPos() {
        this.cellArrPos = this.getNextCellArrPos();
    }

    public double retrieveActGridNodeProbability(final int posX, final int posY) {
        final GridNode gridNode = this.hexGrid.getGridNode(posX, posY);
        double probability = 0.0D;
        for (final PartStep partStep : gridNode.getPartStepList(this.getActCellArrPos())) {
            probability += calcProbability(partStep);
        }
        return probability * PROBABILITY;
    }

    private double calcProbability(final PartStep partStep) {
        final double probability = partStep.getProbability();
        final PartStep parentPartStep = partStep.getParentPartStep();
        final double retProbability;
        if (partStep.getCalcuated()) {
            retProbability = partStep.getCalcuatedProbability();
        } else {
            if (Objects.nonNull(parentPartStep)) {
                retProbability = calcProbability(parentPartStep) * (probability / PROBABILITY);
            } else {
                retProbability = probability / PROBABILITY;
            }
            //partStep.setCalcuatedProbability(retProbability);
            //partStep.setCalcuated(true);
            //partStep.setParentPartStep(null);
        }

        return retProbability;
    }

    public int retrieveStepCount() {
        return this.stepCount;
    }

    /**
     * 0    1   2   3   4   5   6   7       8       9       10      11      12      13
     * 1    3   9   27  81  243 729 2.187   6.516   19.683  59.049  177.147 53.441  1.594.323
     */
    public long retrievePartStepCount() {
        long partStepCount = 0;
        for (int posY = 0; posY < this.hexGrid.getNodeCountY(); posY++) {
            for (int posX = 0; posX < this.hexGrid.getNodeCountX(); posX++) {
                final GridNode gridNode = this.hexGrid.getGridNode(posX, posY);
                partStepCount += gridNode.getPartStepList(this.getActCellArrPos()).size();
            }
        }
        return partStepCount;
    }
}
