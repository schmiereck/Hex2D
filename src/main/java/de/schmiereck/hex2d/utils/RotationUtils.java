package de.schmiereck.hex2d.utils;

import static de.schmiereck.hex2d.utils.DirUtils.calcAxisByDirNumber;
import static de.schmiereck.hex2d.utils.DirUtils.calcDirNumberByAxis;

import de.schmiereck.hex2d.Cell;
import de.schmiereck.hex2d.PartStep;

public class RotationUtils {

    public static void initRotate(final PartStep partStep, final Cell.Dir rotDir, final int newRot) {
        partStep.setRotDir(rotDir);
        partStep.setRot(rotDir, newRot);
    }

    public static void calcRotate(final PartStep partStep, final int maxRot, final int rotStep) {
        final Cell.Dir rotDir = partStep.getRotDir();
        final int rot = partStep.getRot(rotDir);

        final int newRotStep = Math.min(rot, rotStep);
        final int newRot = rot - newRotStep;
        partStep.setRot(rotDir, newRot);

        final int diffRot = rotStep - newRotStep;

        final Cell.Dir next1RotDir = calcNextRotDir(rotDir);
        final int next1Rot = partStep.getRot(next1RotDir);
        final int newNext1Rot = next1Rot + newRotStep - diffRot;
        partStep.setRot(next1RotDir, newNext1Rot);

        if (diffRot != 0) {
            final Cell.Dir next2RotDir = calcNextRotDir(next1RotDir);
            final int next2Rot = partStep.getRot(next2RotDir);
            final int newNext2Rot = next2Rot + diffRot;
            partStep.setRot(next2RotDir, newNext2Rot);
        }

        if ((newNext1Rot + diffRot) >= maxRot) {
            partStep.setRotDir(next1RotDir);
        }
    }

    private static Cell.Dir calcNextRotDir(final Cell.Dir rotDir) {
        return calcAxisByDirNumber(calcDirNumberByAxis(rotDir) + 1);
    }

    public static long calcRotateValue(final PartStep partStep, final int maxProb, final int maxRot) {
        long rv = 0L;

        for (final Cell.Dir dir : Cell.Dir.values()) {
            final int prob = maxProb - partStep.getProb(dir);
            final int rot = partStep.getRot(dir);

            final int r = (prob * rot) / maxProb;

            rv += r;
        }
        return rv;
    }
}
