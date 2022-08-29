package de.schmiereck.hex2d;

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

    public static final int PROBABILITY = 1 * 2 * 3 * 5 * 7;
    public static final int PROBABILITY_1_1 = PROBABILITY;
    public static final int PROBABILITY_1_2 = PROBABILITY / 2;
    public static final int PROBABILITY_1_3 = PROBABILITY / 3;
    public static final int PROBABILITY_2_3 = PROBABILITY_1_3 * 2;
    private HexGrid hexGrid;

    private int cellArrPos = 0;

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

        gridNode.setProbability(this.getActCellArrPos(), PROBABILITY);
        gridNode.setProbabilityDenominator(this.getActCellArrPos(), 0);

        gridNode.setProb(this.getActCellArrPos(), Cell.Dir.AP, PROBABILITY_1_1);

        gridNode.setProb(this.getActCellArrPos(), Cell.Dir.AP, PROBABILITY_1_2);
        gridNode.setProb(this.getActCellArrPos(), Cell.Dir.CP, PROBABILITY_1_2);

        //gridNode.setProb(this.getActCellArrPos(), Cell.Dir.AP, PROBABILITY_2_3);
        //gridNode.setProb(this.getActCellArrPos(), Cell.Dir.CP, PROBABILITY_1_3);
    }

    public void calcNext() {
        this.calcGrid();

        this.calcNextCellArrPos();

        this.clearNextGrid();
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

                    final Cell.Dir otherDir = calcOtherDir(dir);
                    final long otherProbability = sourceGridNode.getProbability(this.getActCellArrPos());
                    final int otherDirProb = sourceGridNode.getProb(this.getActCellArrPos(), otherDir);
                    final long targetProbability = (otherProbability * otherDirProb);
                    if (targetProbability > 0) {
                        final long probabilityDenominator = sourceGridNode.getProbabilityDenominator(this.getActCellArrPos());

                        gridNode.addProbability(this.getNextCellArrPos(), targetProbability);

                        gridNode.setProbabilityDenominator(this.getNextCellArrPos(), probabilityDenominator + 1);

                        for (final Cell.Dir copyDir : Cell.Dir.values()) {
                            final int copyDirProb = sourceGridNode.getProb(this.getActCellArrPos(), copyDir);
                            gridNode.setProb(this.getNextCellArrPos(), copyDir, copyDirProb);
                        }
                    }
                }
            }
        }
    }

    private void clearNextGrid() {
        for (int posY = 0; posY < this.hexGrid.getNodeCountY(); posY++) {
            for (int posX = 0; posX < this.hexGrid.getNodeCountX(); posX++) {
                final GridNode gridNode = this.hexGrid.getGridNode(posX, posY);
                gridNode.setProbability(this.getNextCellArrPos(), 0);
                gridNode.setProbabilityDenominator(this.getNextCellArrPos(), 0);
                for (final Cell.Dir dir : Cell.Dir.values()) {
                    gridNode.setProb(this.getNextCellArrPos(), dir, 0);
                }
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
        final long probability = gridNode.getProbability(this.getActCellArrPos());
        final long probabilityDenominator = gridNode.getProbabilityDenominator(this.getActCellArrPos());
        if (probability > 0 || probabilityDenominator > 0) {
            final double denominator = Math.pow(PROBABILITY, probabilityDenominator);
            return probability / denominator;
        } else {
            return 0.0D;
        }
    }
}
