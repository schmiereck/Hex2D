package de.schmiereck.hex2d.math;

import de.schmiereck.hex2d.HexGridService;

import java.util.ArrayList;
import java.util.List;

public class Num {
    public static class NumSerie {
        private final long[] numArr;

        public NumSerie(final int numSize) {
            this.numArr = new long[numSize];
        }

        public void setNum(final int num) {
            this.numArr[num]++;
        }

        public long getNum(int num) {
            return this.numArr[num];
        }
    }

    final int numSize;

    private final List<NumSerie> numSerieList = new ArrayList<>();

    public Num(final int numSize) {
        this.numSize = numSize;
    }

    public int getNumSerieListSize() {
        return this.numSerieList.size();
    }

    public void addNumSerie(final NumSerie numSerie) {
        this.numSerieList.add(numSerie);
    }
    public NumSerie getNumSerie(final int pos) {
        return this.numSerieList.get(pos);
    }

}
