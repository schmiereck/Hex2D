package de.schmiereck.hex2d.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class NumServiceTest {

    @Test
    void calcNumber() {
        NumService numService = new NumService(100);

        Num num = numService.createNum();

        numService.divNum(num, 70);
        numService.divNum(num, 5);

        final double number = numService.calcNumber(num);

        assertEquals(100.0D * (70.0D / 100.0D) * (5.0D / 100.0D), number, 0.01D);
    }

    @Test
    void calcNumberD2N0() {
        NumService numService = new NumService(2);

        Num num = numService.createNum();

        numService.divNum(num, 0);

        final double number = numService.calcNumber(num);

        assertEquals(2.0D * (0.0D / 2.0D), number, 0.01D);  // 0.0D
        assertEquals(0.0D, number, 0.01D);
    }

    @Test
    void calcNumberD2N1N1N1() {
        NumService numService = new NumService(2);

        Num num = numService.createNum();

        numService.divNum(num, 1);
        numService.divNum(num, 1);
        numService.divNum(num, 1);

        final double number = numService.calcNumber(num);

        assertEquals(2.0D * (1.0D / 2.0D) * (1.0D / 2.0D) * (1.0D / 2.0D), number, 0.01D);  // 0.25D
        assertEquals(0.25D, number, 0.01D);
    }

    @Test
    void calcNumberD2N1N1() {
        NumService numService = new NumService(2);

        Num num = numService.createNum();

        numService.divNum(num, 1);
        numService.divNum(num, 1);

        final double number = numService.calcNumber(num);

        assertEquals(2.0D * (1.0D / 2.0D) * (1.0D / 2.0D), number, 0.01D);  // 0.5D
        assertEquals(0.5D, number, 0.01D);
    }

    @Test
    void calcNumberD2N1N1N3() {
        NumService numService = new NumService(2);

        Num num = numService.createNum();

        numService.divNum(num, 1);
        numService.divNum(num, 1);
        numService.divNum(num, 3);

        final double number = numService.calcNumber(num);

        assertEquals(2.0D * (1.0D / 2.0D) * (1.0D / 2.0D) * (3.0D / 2.0D), number, 0.01D);  // 0.75D
        assertEquals(0.75D, number, 0.01D);
    }

    @Test
    void calcNumberD2N1() {
        NumService numService = new NumService(2);

        Num num = numService.createNum();

        numService.divNum(num, 1);

        final double number = numService.calcNumber(num);

        assertEquals(2.0D * (1.0D / 2.0D), number, 0.01D);  // 1.0D
        assertEquals(1.0D, number, 0.01D);
    }

    /*
    @Test
    void calcNumberD2N1m7() {
        NumService numService = new NumService(2);

        Num num = numService.createNum();

        numService.divNum(num, 4);
        numService.divNum(num, 4);
        numService.divNum(num, 4);
        numService.divNum(num, 4);
        numService.divNum(num, 4);
        numService.divNum(num, 4);
        numService.divNum(num, 4);

        final double number = numService.calcNumber(num);

        //assertEquals(2.0D * (1.0D / 2.0D) * (3.0D / 2.0D), number, 0.01D);  // 1.75D
        assertEquals(1.75D, number, 0.01D);
    }
    */

    @Test
    void calcNumberD2N1N3() {
        NumService numService = new NumService(2);

        Num num = numService.createNum();

        numService.divNum(num, 1);
        numService.divNum(num, 3);

        final double number = numService.calcNumber(num);

        assertEquals(2.0D * (1.0D / 2.0D) * (3.0D / 2.0D), number, 0.01D);  // 1.5D
        assertEquals(1.5D, number, 0.01D);
    }

    @Test
    void calcNumberD2N2() {
        NumService numService = new NumService(2);

        Num num = numService.createNum();

        numService.divNum(num, 2);

        final double number = numService.calcNumber(num);

        assertEquals(2.0D * (2.0D / 2.0D), number, 0.01D);  // 2.0D
        assertEquals(2.0D, number, 0.01D);
    }
}