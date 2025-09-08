package de.schmiereck.hex2d.step2.service;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Collections; // added

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
 * 100 -> 20, 50, 30 =>
 *
 *                     0,8
 *                4 -> 2
 *                     1,2
 *         20 -> 10
 *                6
 *
 *               10
 *  100 -> 50 -> 25
 *               15
 *
 *                6
 *         30 -> 15
 *                9
 *
 * </code></pre>
 */
@Component
public class HexGridService {
    public static final boolean UseTimeDistribution = false;;
    public static final long NEW_PARTS = UseTimeDistribution ? 4 : 2;

    //public static final long PROBABILITY = 2L * 3 * 5 * 7 * 11 * 13 * 17 * 19 * 23 * 29 * 31 * 37 * 41 * 43 * 47 * 53 * 59 * 61 * 67 * 71 * 73 * 79 * 83 * 89 * 97;
    //public static final long PROBABILITY = 2L * 3 * 5 * 7 * 11 * 13 * 17 * 19 * 23 * 29 * 31 * 37 * 41 * 43 * 47;
    public static final long PROBABILITY =
            NEW_PARTS * NEW_PARTS * NEW_PARTS * NEW_PARTS *
            NEW_PARTS * NEW_PARTS * NEW_PARTS * NEW_PARTS *
            NEW_PARTS * NEW_PARTS * NEW_PARTS * NEW_PARTS *
            NEW_PARTS * NEW_PARTS * NEW_PARTS * NEW_PARTS; //
    public static final long PROBABILITY_0 = 0;
    public static final long PROBABILITY_1_1 = PROBABILITY;
    public static final long PROBABILITY_1_2 = PROBABILITY / 2;
    public static final long PROBABILITY_1_3 = PROBABILITY / 3;
    public static final long PROBABILITY_2_3 = PROBABILITY_1_3 * 2;
    public static final long PROBABILITY_1_4 = PROBABILITY / 4;
    public static final long PROBABILITY_2_4 = PROBABILITY_1_4 * 2;
    public static final long PROBABILITY_3_4 = PROBABILITY_1_4 * 3;
    public static final long PROBABILITY_1_8 = PROBABILITY / 8;
    public static final long PROBABILITY_2_8 = PROBABILITY_1_8 * 2;
    public static final long PROBABILITY_3_8 = PROBABILITY_1_8 * 3;
    public static final long PROBABILITY_1_10 = PROBABILITY / 10;
    public static final long PROBABILITY_4_10 = PROBABILITY_1_10 * 4;
    public static final long PROBABILITY_6_10 = PROBABILITY_1_10 * 6;
    public static final long PROBABILITY_9_10 = PROBABILITY_1_10 * 9;

    public static final long POSITION = 2 * 2 * 2; //
    public static final long IMPULSE = 2 * 2; //

    private static final int[][][] DirOffsetArr = {
            {
                    //!np {0, 0},    // NP
                    {1, 0},    // AP
                    {0, 1},    // BP
                    {0, -1},    // CP
                    {-1, 0},    // AN
                    {-1, -1},    // BN
                    {-1, 1}     // CN
            },
            {
                    //!np {0, 0},    // NP
                    {1, 0},    // AP
                    {1, 1},    // BP
                    {1, -1},    // CP
                    {-1, 0},    // AN
                    {0, -1},    // BN
                    {0, 1}     // CN
            }
    };

    //public static final int DIMENSION = 4;
    //public static final long EIGENTIME_MAX = 1024;
    //public static final long EIGENTIME_MAX = 6 * 6; //
    public static final long EIGENTIME_MAX = 6; //

    //private NumService numService = new NumService(PROBABILITY);

    private HexGrid hexGrid;

    private int cellArrPos = 0;
    private int stepCount = 0;

    public void initialize(final int sizeX, final int sizeY) {
        final boolean[] useStepArr = { true, false, false };

        this.hexGrid = new HexGrid(sizeX, sizeY);
        if (useStepArr[0]) {
            //final GridNode gridNode = this.hexGrid.getGridNode(3, 5);
            final GridNode gridNode = this.hexGrid.getGridNode(5, this.hexGrid.getNodeCountY() - 2);

            final PartEvent partEvent = new PartEvent();

            final PartStep partStep = new PartStep(partEvent, PROBABILITY, 0L, 0L, 1L);
            gridNode.addPartStep(this.getActCellArrPos(), partStep);
        }
        if (useStepArr[1]) {
            //final GridNode gridNode = this.hexGrid.getGridNode(3, 5);
            final GridNode gridNode = this.hexGrid.getGridNode(this.hexGrid.getNodeCountX() / 2, this.hexGrid.getNodeCountY() - 2);

            final PartEvent partEvent = new PartEvent();

            final PartStep partStep = new PartStep(partEvent, PROBABILITY, 0L, 0L, 0L);
            gridNode.addPartStep(this.getActCellArrPos(), partStep);

            //partStep.setProb(Cell.Dir.AP, PROBABILITY_1_1);
            //initDirProb(partStep, Cell.Dir.AP, 0.0D);
        }
        if (useStepArr[2]) {
            //final GridNode gridNode = this.hexGrid.getGridNode(3, 5);
            final GridNode gridNode = this.hexGrid.getGridNode(this.hexGrid.getNodeCountX() - 5, this.hexGrid.getNodeCountY() - 2);

            final PartEvent partEvent = new PartEvent();

            final PartStep partStep = new PartStep(partEvent, PROBABILITY, 0L, 0L, -1L);
            gridNode.addPartStep(this.getActCellArrPos(), partStep);

            //partStep.setProb(Cell.Dir.AP, PROBABILITY_1_1);
            //initDirProb(partStep, Cell.Dir.AP, 0.0D);
        }
    }

    public void calcNext() {
        this.calcGrid();

        this.calcNextCellArrPos();

        this.clearNextGrid();

        this.stepCount++;
    }

    private void calcGrid() {
        // Populate probabilities:
        //System.out.printf("=========================================================%n");
        for (int posY = 0; posY < this.hexGrid.getNodeCountY(); posY++) {
            for (int posX = 0; posX < this.hexGrid.getNodeCountX(); posX++) {
                final GridNode sourceGridNode = this.hexGrid.getGridNode(posX, posY);
                //      bn  cp    A
                //       \ /
                //   an---A---ap     A
                //       / \
                //      cn  bp    A
                final GridNode targetTimeLGridNode = this.getNeighbourGridNode(posX, posY, Cell.Dir.BN);
                final GridNode targetTimeRGridNode = this.getNeighbourGridNode(posX, posY, Cell.Dir.CP);
                final GridNode targetSpaceLGridNode = this.getNeighbourGridNode(posX, posY, Cell.Dir.AN);
                final GridNode targetSpaceRGridNode = this.getNeighbourGridNode(posX, posY, Cell.Dir.AP);

                final List<PartStep> partStepList = sourceGridNode.getPartStepList(this.getActCellArrPos());
                if (!partStepList.isEmpty()) {
                    //System.out.printf("-------------------------------------------%n");
                    partStepList.stream().forEach(sourcePartStep -> {
                        final long sourceProb = sourcePartStep.getProbability();
                        final long sourceOrt = sourcePartStep.getOrt();
                        final long sourceImpulse = sourcePartStep.getImpulse();
                        final long sourceEigentime = sourcePartStep.getEigentime();

                        if (sourceProb >= NEW_PARTS) {
                            //final long newProbability = (sourcePartStep.getProbability() * sourceProb) / PROBABILITY;
                            final long newProbability = sourceProb / NEW_PARTS;
                            final long newTimeLProbability = newProbability;
                            final long newTimeRProbability = newProbability;
                            final long newPosLProbability = newProbability;
                            final long newPosRProbability = newProbability;
                            final long newEigentime = sourceEigentime;
                            final long newPosEigentime = (sourceEigentime + 1L) % EIGENTIME_MAX;
                            final long newPosEigentime2 = (sourceEigentime + 2L) % EIGENTIME_MAX;

                            if (UseTimeDistribution) {
                                final PartStep newTimeLPartStep =
                                        new PartStep(sourcePartStep.getPartEvent(), newTimeLProbability, newEigentime,
                                                0L, sourceImpulse);
                                final PartStep newTimeRPartStep =
                                        new PartStep(sourcePartStep.getPartEvent(), newTimeRProbability, newEigentime,
                                                0L, sourceImpulse);

                                targetTimeLGridNode.addPartStep(this.getNextCellArrPos(), newTimeLPartStep);
                                targetTimeRGridNode.addPartStep(this.getNextCellArrPos(), newTimeRPartStep);
                            }

                            if (sourceImpulse > 0) {
                                final PartStep newLPartStep;
                                final PartStep newRPartStep;

                                final long newLPartPos = ((POSITION / 2) + sourceOrt) + sourceImpulse;
                                final long newRPartPos = -((POSITION / 2) - sourceOrt) + sourceImpulse;

                                if (newRPartPos <= -POSITION) {
                                    newLPartStep =
                                            new PartStep(sourcePartStep.getPartEvent(),
                                                    newPosLProbability + newPosRProbability, newPosEigentime,
                                                    newLPartPos, sourceImpulse);
                                    newRPartStep = null;
                                } else {
                                    if (newLPartPos >= POSITION) {
                                        newLPartStep = null;
                                        newRPartStep =
                                                new PartStep(sourcePartStep.getPartEvent(),
                                                        newPosLProbability + newPosRProbability, newPosEigentime,
                                                        newRPartPos, sourceImpulse);
                                    } else {
                                        newLPartStep =
                                                new PartStep(sourcePartStep.getPartEvent(),
                                                        newPosLProbability, newPosEigentime,
                                                        newLPartPos, sourceImpulse);
                                        newRPartStep =
                                                new PartStep(sourcePartStep.getPartEvent(),
                                                        newPosRProbability, newPosEigentime,
                                                        newRPartPos, sourceImpulse);
                                    }
                                }
                                if (Objects.nonNull(newLPartStep)) {
                                    addPartStepToGridNode(targetTimeLGridNode, newLPartStep, "newLPartStep");
                                }
                                if (Objects.nonNull(newRPartStep)) {
                                    addPartStepToGridNode(targetTimeRGridNode, newRPartStep, "newRPartStep");
                                }
                            } else {
                                if (sourceImpulse < 0) {
                                    final PartStep newLPartStep;
                                    final PartStep newRPartStep;

                                    final long newLPartPos = ((POSITION / 2) + sourceOrt) + sourceImpulse;
                                    final long newRPartPos = -((POSITION / 2) - sourceOrt) + sourceImpulse;

                                    if (newLPartPos >= POSITION) {
                                        newLPartStep = null;
                                        newRPartStep =
                                                new PartStep(sourcePartStep.getPartEvent(),
                                                        newPosLProbability + newPosRProbability, newPosEigentime,
                                                        newRPartPos, sourceImpulse);
                                    } else {
                                        if (newRPartPos <= -POSITION) {
                                            newLPartStep =
                                                    new PartStep(sourcePartStep.getPartEvent(),
                                                            newPosLProbability + newPosRProbability, newPosEigentime,
                                                            newLPartPos, sourceImpulse);
                                            newRPartStep = null;
                                        } else {
                                            newLPartStep =
                                                    new PartStep(sourcePartStep.getPartEvent(), newPosLProbability, newPosEigentime,
                                                            newLPartPos, sourceImpulse);
                                            newRPartStep =
                                                    new PartStep(sourcePartStep.getPartEvent(), newPosRProbability, newPosEigentime,
                                                            newRPartPos, sourceImpulse);
                                        }
                                    }

                                    if (Objects.nonNull(newLPartStep)) {
                                        addPartStepToGridNode(targetTimeLGridNode, newLPartStep, "newLPartStep");
                                    }
                                    if (Objects.nonNull(newRPartStep)) {
                                        addPartStepToGridNode(targetTimeRGridNode, newRPartStep, "newRPartStep");
                                    }
                                } else {
                                    final PartStep newLPartStep =
                                            new PartStep(sourcePartStep.getPartEvent(), newPosLProbability, newPosEigentime,
                                                    sourceOrt, sourceImpulse);
                                    final PartStep newRPartStep =
                                            new PartStep(sourcePartStep.getPartEvent(), newPosRProbability, newPosEigentime,
                                                    sourceOrt, sourceImpulse);
                                    if (Objects.nonNull(newLPartStep)) {
                                        addPartStepToGridNode(targetTimeLGridNode, newLPartStep, "newLPartStep");
                                    }
                                    if (Objects.nonNull(newRPartStep)) {
                                        addPartStepToGridNode(targetTimeRGridNode, newRPartStep, "newRPartStep");
                                    }
                                }
                            }

                            final long leftProb = sourceProb -
                                    (
                                            UseTimeDistribution ?
                                                    (newTimeLProbability + newTimeRProbability) : 0L +
                                            newPosLProbability + newPosRProbability
                                    );

                            if (leftProb > 0) {
                                final PartStep newPartStep =
                                        new PartStep(sourcePartStep.getPartEvent(), leftProb, sourceEigentime,
                                                sourceOrt, sourceImpulse);
                                addPartStepToGridNode(sourceGridNode, newPartStep, "newPartStep");
                            }
                        } else {
                            final PartStep newPartStep =
                                    new PartStep(sourcePartStep.getPartEvent(), sourceProb, sourceEigentime,
                                            sourceOrt, sourceImpulse);
                            addPartStepToGridNode(sourceGridNode, newPartStep, "newPartStep");
                        }
                    });
                    //partStepList.clear();
                }
            }
        }
        // Interactions probabilities:
        for (int posY = 0; posY < this.hexGrid.getNodeCountY(); posY++) {
            for (int posX = 0; posX < this.hexGrid.getNodeCountX(); posX++) {
                final GridNode sourceGridNode = this.hexGrid.getGridNode(posX, posY);

                final List<PartStep> partStepList = sourceGridNode.getPartStepList(this.getNextCellArrPos());
                // Kompatible PartSteps zu einem PartStep mit summierter Probability zusammenfassen:
                this.mergeCompatiblePartSteps(partStepList);
            }
        }
    }

    private void addPartStepToGridNode(final GridNode sourceGridNode, final PartStep newPartStep, final String partType) {
        sourceGridNode.addPartStep(this.getNextCellArrPos(), newPartStep);
        //System.out.printf("%s: ort=%d, impulse=%d, prob=%d%n", partType, newPartStep.getOrt(), newPartStep.getImpulse(), newPartStep.getProbability());
    }

    private GridNode getNeighbourGridNode(final int posX, final int posY, final Cell.Dir dir) {
        final int rowNo = posY % 2;
        final int[] offsetArr = DirOffsetArr[rowNo][dir.ordinal()];
        final GridNode sourceGridNode =
                this.hexGrid.getGridNode(posX + offsetArr[0], posY + offsetArr[1]);
        return sourceGridNode;
    }

    // Fügt alle kompatiblen PartSteps einer Liste zu einem Eintrag mit der Gesamtsumme zusammen.
    private void mergeCompatiblePartSteps(final List<PartStep> partStepList) {
        if (partStepList == null || partStepList.size() <= 1) return;

        final List<PartStep> representativePartStepList = new ArrayList<>();
        final List<Long> sumProbList = new ArrayList<>();

        for (final PartStep partStep : partStepList) {
            int foundPartStepPos = -1;
            for (int representativePartStepPos = 0; representativePartStepPos < representativePartStepList.size(); representativePartStepPos++) {
                if (this.isCompatible(partStep, representativePartStepList.get(representativePartStepPos))) {
                    foundPartStepPos = representativePartStepPos;
                    break;
                }
            }
            if (foundPartStepPos >= 0) {
                sumProbList.set(foundPartStepPos, sumProbList.get(foundPartStepPos) + partStep.getProbability());
            } else {
                representativePartStepList.add(partStep);
                sumProbList.add(partStep.getProbability());
            }
        }

        partStepList.clear();
        for (int representativePartStepPos = 0; representativePartStepPos < representativePartStepList.size(); representativePartStepPos++) {
            final PartStep repPartStep = representativePartStepList.get(representativePartStepPos);
            final PartStep newPartStep =
                    new PartStep(repPartStep.getPartEvent(), sumProbList.get(representativePartStepPos), repPartStep.getEigentime(),
                            repPartStep.getOrt(), repPartStep.getImpulse());
            partStepList.add(newPartStep);
        }
    }

    private boolean isCompatible(final PartStep sourcePartStep, final PartStep partStep) {
        return (sourcePartStep.getPartEvent() == partStep.getPartEvent()) &&
                (sourcePartStep.getEigentime() == partStep.getEigentime()) &&
                (sourcePartStep.getOrt() == partStep.getOrt()) &&
                (sourcePartStep.getImpulse() == partStep.getImpulse());
    }

    private void clearNextGrid() {
        for (int posY = 0; posY < this.hexGrid.getNodeCountY(); posY++) {
            for (int posX = 0; posX < this.hexGrid.getNodeCountX(); posX++) {
                final GridNode gridNode = this.hexGrid.getGridNode(posX, posY);
                gridNode.getPartStepList(this.getNextCellArrPos()).clear();
            }
        }
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

    /**
     * Gibt eine unveränderliche Liste der aktuellen PartSteps eines Nodes zurück.
     */
    public List<PartStep> retrieveActPartSteps(final int posX, final int posY) {
        final GridNode gridNode = this.hexGrid.getGridNode(posX, posY);
        final List<PartStep> list = gridNode.getPartStepList(this.getActCellArrPos());
        return Collections.unmodifiableList(list);
    }

    /**
     * @return the Probability between <code>0.0D</code> and {@link HexGridService#PROBABILITY}.
     */
    public double retrieveActGridNodeProbability(final int posX, final int posY) {
        //return this.retrieveActGridNodeProbabilityOverPartsOnly(posX, posY);
        return this.retrieveActGridNodeProbabilityOverPartsWithEigentime(posX, posY);
    }

    /**
     * @return the Probability between <code>0.0D</code> and {@link HexGridService#PROBABILITY}.
     */
    public double retrieveActGridNodeProbabilityOverPartsOnly(final int posX, final int posY) {
        final GridNode gridNode = this.hexGrid.getGridNode(posX, posY);
        double probability = 0.0D;
        for (final PartStep partStep : gridNode.getPartStepList(this.getActCellArrPos())) {
            probability += calcProbability(partStep);
        }
        //if (probability > 0.0D)
        //    System.out.print(probability + ", ");
        return probability;
    }

    /**
     * Berechnet die Probability eines Nodes über alle Parts per Pfadintegral.
     * - Eigentime steuert die Phase: eigentime % 4 → Winkel 0°, 60°, 120°, 180°.
     * - Amplitude je Part = sqrt(p_i / PROBABILITY) * e^{i*phi}.
     * - Gesamte Probability = |Summe(Amplituden)|^2 * PROBABILITY.
     *
     * @return Probability im Bereich [0, PROBABILITY].
     */
    public double retrieveActGridNodeProbabilityOverPartsWithEigentime(final int posX, final int posY) {
        final GridNode gridNode = this.hexGrid.getGridNode(posX, posY);
        final List<PartStep> partStepList = gridNode.getPartStepList(this.getActCellArrPos());
        if (partStepList == null || partStepList.isEmpty()) return 0.0D;

        //double currentAngle0 = 0.0D;
        double sumRe = 0.0D;
        double sumIm = 0.0D;

        final int partStepCount = partStepList.size();
        for (final PartStep partStep : partStepList) {
            final long p = Math.max(0L, partStep.getProbability());
            if (p == 0L) continue;

            final double angleRad = HexGridService.calcAngleRadFromEigentime(partStep.getEigentime());
            //final double ampMag = 1.0D / (double) PROBABILITY;
            //final double ampMag = Math.sqrt(p / (double) PROBABILITY);
            //final double ampMag = 1.0D / partStepCount;
            final double ampMag = 1.0D;

            //currentAngle0 += angleRad;

            sumRe += ampMag * Math.cos(angleRad);
            sumIm += ampMag * Math.sin(angleRad);
            //sumRe += ampMag * Math.cos(currentAngle0);
            //sumIm += ampMag * Math.sin(currentAngle0);
        }

        // probNorm ist im Bereich [0,1]
        final double probNorm = (sumRe * sumRe) + (sumIm * sumIm);
        //final double probScaled = probNorm * PROBABILITY;
        final double probScaled = probNorm;

        // Numerische Sicherheit: auf [0, PROBABILITY] clampen.
        return clamp(probScaled, 0.0D, (double) PROBABILITY);
    }

    /**
     * @return the Probability between <code>0.0D</code> and {@link HexGridService#PROBABILITY}.
     */
    private double calcProbability(final PartStep partStep) {
        return partStep.getProbability();
    }

    public int retrieveStepCount() {
        return this.stepCount;
    }

    /** fiona
     *  0   1   2   3   4   5   6   7       8       9       10      11      12      13
     *  1   3   9   27  81  243 729 2.187   6.561   19.683  59.049  177.147 53.441  1.594.323
     *  1   3   6   17  27  54  105 186     316     511     808     1.254   1.884   2.741
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

    /**
     * Öffentliche Hilfsmethode für Darstellung: wandelt Eigentime in Radiant-Winkel.
     */
    public static double calcAngleRadFromEigentime(final long eigentime) {
        final long mod = Math.floorMod(eigentime, EIGENTIME_MAX);
        final double deg = mod * (360.0D / EIGENTIME_MAX);
        return Math.toRadians(deg);
    }

    private static double clamp(final double v, final double min, final double max) {
        return Math.max(min, Math.min(max, v));
    }
}
