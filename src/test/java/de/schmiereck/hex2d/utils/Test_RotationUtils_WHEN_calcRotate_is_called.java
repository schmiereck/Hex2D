package de.schmiereck.hex2d.utils;

import static de.schmiereck.hex2d.step0.utils.DirUtils.calcAxisByDirNumber;
import static org.junit.jupiter.api.Assertions.assertEquals;

import de.schmiereck.hex2d.step0.service.Cell;
import de.schmiereck.hex2d.step0.service.HexGridService;
import de.schmiereck.hex2d.step0.service.PartStep;

import de.schmiereck.hex2d.step0.utils.DirUtils;
import de.schmiereck.hex2d.step0.utils.RotationUtils;
import org.junit.jupiter.api.Test;

public class Test_RotationUtils_WHEN_calcRotate_is_called {

    @Test
    public void GIVEN_max_5_step_3_THEN_rotate() {
        // ARRANGE
        final int maxRot = 5;
        final PartStep partStep = new PartStep(null, 10L);
        DirUtils.initDirProb(partStep, Cell.Dir.AP, 0.0D);
        RotationUtils.initRotate(partStep, Cell.Dir.AP, maxRot);

        // ACT & ASSERT
        assertEquals(5, partStep.getRot(Cell.Dir.AP));
        assertEquals(0, partStep.getRot(Cell.Dir.BP));
        assertEquals(2L, RotationUtils.calcRotateValue(partStep, HexGridService.PROBABILITY, maxRot));
        assertRotateValues(partStep, new int[] { 2, 0, 0, 0, 0, 0}, maxRot);

        RotationUtils.calcRotate(partStep, 5, 3);

        assertEquals(2, partStep.getRot(Cell.Dir.AP));
        assertEquals(3, partStep.getRot(Cell.Dir.BP));
        assertEquals(3L, RotationUtils.calcRotateValue(partStep, HexGridService.PROBABILITY, maxRot));
        assertRotateValues(partStep, new int[] { 1, 2, 0, 0, 0, 0}, maxRot);

        RotationUtils.calcRotate(partStep, 5, 3);

        assertEquals(0, partStep.getRot(Cell.Dir.AP));
        assertEquals(4, partStep.getRot(Cell.Dir.BP));
        assertEquals(1, partStep.getRot(Cell.Dir.CN));
        assertEquals(3L, RotationUtils.calcRotateValue(partStep, HexGridService.PROBABILITY, maxRot));
        assertRotateValues(partStep, new int[] { 0, 3, 0, 0, 0, 0}, maxRot);

        RotationUtils.calcRotate(partStep, 5, 3);

        assertEquals(0, partStep.getRot(Cell.Dir.AP));
        assertEquals(1, partStep.getRot(Cell.Dir.BP));
        assertEquals(4, partStep.getRot(Cell.Dir.CN));
        assertEquals(3L, RotationUtils.calcRotateValue(partStep, HexGridService.PROBABILITY, maxRot));
        assertRotateValues(partStep, new int[] { 0, 0, 3, 0, 0, 0}, maxRot);

        RotationUtils.calcRotate(partStep, 5, 3);

        assertEquals(0, partStep.getRot(Cell.Dir.AP));
        assertEquals(0, partStep.getRot(Cell.Dir.BP));
        assertEquals(3, partStep.getRot(Cell.Dir.CN));
        assertEquals(2, partStep.getRot(Cell.Dir.AN));
        assertEquals(3L, RotationUtils.calcRotateValue(partStep, HexGridService.PROBABILITY, maxRot));
        assertRotateValues(partStep, new int[] { 0, 0, 2, 1, 0, 0}, maxRot);

        RotationUtils.calcRotate(partStep, 5, 3);

        assertEquals(0, partStep.getRot(Cell.Dir.AP));
        assertEquals(0, partStep.getRot(Cell.Dir.BP));
        assertEquals(0, partStep.getRot(Cell.Dir.CN));
        assertEquals(5, partStep.getRot(Cell.Dir.AN));
        assertEquals(2L, RotationUtils.calcRotateValue(partStep, HexGridService.PROBABILITY, maxRot));
        assertRotateValues(partStep, new int[] { 0, 0, 0, 2, 0, 0}, maxRot);

        RotationUtils.calcRotate(partStep, 5, 3);

        assertEquals(0, partStep.getRot(Cell.Dir.AP));
        assertEquals(0, partStep.getRot(Cell.Dir.BP));
        assertEquals(0, partStep.getRot(Cell.Dir.CN));
        assertEquals(2, partStep.getRot(Cell.Dir.AN));
        assertEquals(3, partStep.getRot(Cell.Dir.BN));
        assertEquals(3L, RotationUtils.calcRotateValue(partStep, HexGridService.PROBABILITY, maxRot));
        assertRotateValues(partStep, new int[] { 0, 0, 0, 1, 2, 0}, maxRot);

        RotationUtils.calcRotate(partStep, 5, 3);

        assertEquals(0, partStep.getRot(Cell.Dir.AP));
        assertEquals(0, partStep.getRot(Cell.Dir.BP));
        assertEquals(0, partStep.getRot(Cell.Dir.CN));
        assertEquals(0, partStep.getRot(Cell.Dir.AN));
        assertEquals(4, partStep.getRot(Cell.Dir.BN));
        assertEquals(1, partStep.getRot(Cell.Dir.CP));
        assertEquals(3L, RotationUtils.calcRotateValue(partStep, HexGridService.PROBABILITY, maxRot));
        assertRotateValues(partStep, new int[] { 0, 0, 0, 0, 3, 0}, maxRot);

        RotationUtils.calcRotate(partStep, 5, 3);

        assertEquals(0, partStep.getRot(Cell.Dir.AP));
        assertEquals(0, partStep.getRot(Cell.Dir.BP));
        assertEquals(0, partStep.getRot(Cell.Dir.CN));
        assertEquals(0, partStep.getRot(Cell.Dir.AN));
        assertEquals(1, partStep.getRot(Cell.Dir.BN));
        assertEquals(4, partStep.getRot(Cell.Dir.CP));
        assertEquals(3L, RotationUtils.calcRotateValue(partStep, HexGridService.PROBABILITY, maxRot));
        assertRotateValues(partStep, new int[] { 0, 0, 0, 0, 0, 3}, maxRot);

        RotationUtils.calcRotate(partStep, 5, 3);

        assertEquals(2, partStep.getRot(Cell.Dir.AP));
        assertEquals(0, partStep.getRot(Cell.Dir.BP));
        assertEquals(0, partStep.getRot(Cell.Dir.CN));
        assertEquals(0, partStep.getRot(Cell.Dir.AN));
        assertEquals(0, partStep.getRot(Cell.Dir.BN));
        assertEquals(3, partStep.getRot(Cell.Dir.CP));
        assertEquals(3L, RotationUtils.calcRotateValue(partStep, HexGridService.PROBABILITY, maxRot));
        assertRotateValues(partStep, new int[] { 1, 0, 0, 0, 0, 2}, maxRot);

        RotationUtils.calcRotate(partStep, 5, 3);

        assertRot(partStep, new int[]{5, 0, 0, 0, 0, 0});
        assertEquals(2L, RotationUtils.calcRotateValue(partStep, HexGridService.PROBABILITY, maxRot));
        assertRotateValues(partStep, new int[] { 2, 0, 0, 0, 0, 0}, maxRot);
    }
    
    @Test
    public void GIVEN_max_2_3_4_5_step_5_THEN_rotate() {
        // ARRANGE
        final int maxRot = 2 * 3 * 4 * 5;
        final PartStep partStep = new PartStep(null, HexGridService.PROBABILITY);
        DirUtils.initDirProb(partStep, Cell.Dir.AP, 0.0D);
        RotationUtils.initRotate(partStep, Cell.Dir.AP, maxRot);

        // ACT & ASSERT
        for (int pos = 0; pos < maxRot; pos++) {
            long rotateValue = RotationUtils.calcRotateValue(partStep, HexGridService.PROBABILITY, maxRot);
            System.out.printf("%d:\t%d\n", pos, rotateValue);

            RotationUtils.calcRotate(partStep, maxRot, 5);
        }
/*
        assertRot(partStep, new int[]{120, 0, 0, 0, 0, 0});
        assertEquals(60L, RotationUtils.calcRotateValue(partStep, HexGridService.PROBABILITY, maxRot));
        assertRotateValues(partStep, new int[]{60, 0, 0, 0, 0, 0}, maxRot);

        RotationUtils.calcRotate(partStep, maxRot, 3);

        assertRot(partStep, new int[] { 117, 3, 0, 0, 0, 0 });
        assertEquals(60L, RotationUtils.calcRotateValue(partStep, HexGridService.PROBABILITY, maxRot));
        assertRotateValues(partStep, new int[] { 58, 2, 0, 0, 0, 0 }, maxRot);

        RotationUtils.calcRotate(partStep, maxRot, 3);

        assertRot(partStep, new int[] { 114, 6, 0, 0, 0, 0 });
        assertEquals(61L, RotationUtils.calcRotateValue(partStep, HexGridService.PROBABILITY, maxRot));
        assertRotateValues(partStep, new int[] { 57, 4, 0, 0, 0, 0 }, maxRot);

        RotationUtils.calcRotate(partStep, maxRot, 3);
        */
    }
    
    private void assertRot(final PartStep partStep, final int[] rotArr) {
        for (int pos = 0; pos < 6; pos++) {
            assertEquals(rotArr[pos], partStep.getRot(calcAxisByDirNumber(pos)),
                    String.format("pos: %d", pos));
        }
    }
    
    private void assertRotateValues(final PartStep partStep, final int[] rotArr, final int maxRot) {
        for (int pos = 0; pos < 6; pos++) {
            assertEquals(rotArr[pos], RotationUtils.calcRotateValue(partStep, calcAxisByDirNumber(pos), HexGridService.PROBABILITY, maxRot),
                    String.format("pos: %d", pos));
        }
    }
}
