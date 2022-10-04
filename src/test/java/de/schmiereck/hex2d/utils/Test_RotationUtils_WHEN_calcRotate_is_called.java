package de.schmiereck.hex2d.utils;

import static de.schmiereck.hex2d.utils.DirUtils.initDirProb;
import static org.junit.jupiter.api.Assertions.assertEquals;

import de.schmiereck.hex2d.Cell;
import de.schmiereck.hex2d.HexGridService;
import de.schmiereck.hex2d.PartStep;

import org.junit.jupiter.api.Test;

public class Test_RotationUtils_WHEN_calcRotate_is_called {

    @Test
    public void GIVEN_max_5_step_3_THEN_rotate() {
        // ARRANGE
        final PartStep partStep = new PartStep(null, 10L);
        DirUtils.initDirProb(partStep, Cell.Dir.AP, 0.0D);
        RotationUtils.initRotate(partStep, Cell.Dir.AP, 5);

        assertEquals(5, partStep.getRot(Cell.Dir.AP));
        assertEquals(0, partStep.getRot(Cell.Dir.BP));
        assertEquals(2L, RotationUtils.calcRotateValue(partStep, HexGridService.PROBABILITY, 5));

        // ACT & ASSERT
        RotationUtils.calcRotate(partStep, 5, 3);

        assertEquals(2, partStep.getRot(Cell.Dir.AP));
        assertEquals(3, partStep.getRot(Cell.Dir.BP));
        assertEquals(3L, RotationUtils.calcRotateValue(partStep, HexGridService.PROBABILITY, 5));

        RotationUtils.calcRotate(partStep, 5, 3);

        assertEquals(0, partStep.getRot(Cell.Dir.AP));
        assertEquals(4, partStep.getRot(Cell.Dir.BP));
        assertEquals(1, partStep.getRot(Cell.Dir.CN));
        assertEquals(4L, RotationUtils.calcRotateValue(partStep, HexGridService.PROBABILITY, 5));

        RotationUtils.calcRotate(partStep, 5, 3);

        assertEquals(0, partStep.getRot(Cell.Dir.AP));
        assertEquals(1, partStep.getRot(Cell.Dir.BP));
        assertEquals(4, partStep.getRot(Cell.Dir.CN));
        assertEquals(4L, RotationUtils.calcRotateValue(partStep, HexGridService.PROBABILITY, 5));

        RotationUtils.calcRotate(partStep, 5, 3);

        assertEquals(0, partStep.getRot(Cell.Dir.AP));
        assertEquals(0, partStep.getRot(Cell.Dir.BP));
        assertEquals(3, partStep.getRot(Cell.Dir.CN));
        assertEquals(2, partStep.getRot(Cell.Dir.AN));
        assertEquals(5L, RotationUtils.calcRotateValue(partStep, HexGridService.PROBABILITY, 5));

        RotationUtils.calcRotate(partStep, 5, 3);

        assertEquals(0, partStep.getRot(Cell.Dir.AP));
        assertEquals(0, partStep.getRot(Cell.Dir.BP));
        assertEquals(0, partStep.getRot(Cell.Dir.CN));
        assertEquals(5, partStep.getRot(Cell.Dir.AN));
        assertEquals(5L, RotationUtils.calcRotateValue(partStep, HexGridService.PROBABILITY, 5));

        RotationUtils.calcRotate(partStep, 5, 3);

        assertEquals(0, partStep.getRot(Cell.Dir.AP));
        assertEquals(0, partStep.getRot(Cell.Dir.BP));
        assertEquals(0, partStep.getRot(Cell.Dir.CN));
        assertEquals(2, partStep.getRot(Cell.Dir.AN));
        assertEquals(3, partStep.getRot(Cell.Dir.BN));
        assertEquals(5L, RotationUtils.calcRotateValue(partStep, HexGridService.PROBABILITY, 5));

        RotationUtils.calcRotate(partStep, 5, 3);

        assertEquals(0, partStep.getRot(Cell.Dir.AP));
        assertEquals(0, partStep.getRot(Cell.Dir.BP));
        assertEquals(0, partStep.getRot(Cell.Dir.CN));
        assertEquals(0, partStep.getRot(Cell.Dir.AN));
        assertEquals(4, partStep.getRot(Cell.Dir.BN));
        assertEquals(1, partStep.getRot(Cell.Dir.CP));
        assertEquals(4L, RotationUtils.calcRotateValue(partStep, HexGridService.PROBABILITY, 5));

        RotationUtils.calcRotate(partStep, 5, 3);

        assertEquals(0, partStep.getRot(Cell.Dir.AP));
        assertEquals(0, partStep.getRot(Cell.Dir.BP));
        assertEquals(0, partStep.getRot(Cell.Dir.CN));
        assertEquals(0, partStep.getRot(Cell.Dir.AN));
        assertEquals(1, partStep.getRot(Cell.Dir.BN));
        assertEquals(4, partStep.getRot(Cell.Dir.CP));
        assertEquals(4L, RotationUtils.calcRotateValue(partStep, HexGridService.PROBABILITY, 5));

        RotationUtils.calcRotate(partStep, 5, 3);

        assertEquals(2, partStep.getRot(Cell.Dir.AP));
        assertEquals(0, partStep.getRot(Cell.Dir.BP));
        assertEquals(0, partStep.getRot(Cell.Dir.CN));
        assertEquals(0, partStep.getRot(Cell.Dir.AN));
        assertEquals(0, partStep.getRot(Cell.Dir.BN));
        assertEquals(3, partStep.getRot(Cell.Dir.CP));
        assertEquals(3L, RotationUtils.calcRotateValue(partStep, HexGridService.PROBABILITY, 5));

        RotationUtils.calcRotate(partStep, 5, 3);

        assertEquals(5, partStep.getRot(Cell.Dir.AP));
        assertEquals(0, partStep.getRot(Cell.Dir.BP));
        assertEquals(0, partStep.getRot(Cell.Dir.CN));
        assertEquals(0, partStep.getRot(Cell.Dir.AN));
        assertEquals(0, partStep.getRot(Cell.Dir.BN));
        assertEquals(0, partStep.getRot(Cell.Dir.CP));
        assertEquals(2L, RotationUtils.calcRotateValue(partStep, HexGridService.PROBABILITY, 5));
    }
}
