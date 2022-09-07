package de.schmiereck.hex2d.math;

/**
 * <code>
 *  denominator
 * -------------
 *  numerator
 * </code>
 */
public class NumService {
    private final int denominator;

    public NumService(final int denominator) {
        this.denominator = denominator;
    }

    public Num createNum() {
        return new Num(this.denominator);
    }

    public Num createNum(final Num num) {
        final Num newNum = this.createNum();
        final Num.NumSerie numSerie = this.getFirstNumSerie(num);
        final Num.NumSerie newNumSerie = this.getFirstNumSerie(newNum);

        for (int pos = 0; pos <= this.denominator * 2; pos++) {
            newNumSerie.setNumCnt(pos, numSerie.getNumCnt(pos));
        }
        return newNum;
    }

    // numerator
    public void divNum(final Num num, final int numerator) {
        final Num.NumSerie numSerie = this.getFirstNumSerie(num);
        numSerie.incNumCnt(numerator);
    }

    public void addDivNum(final Num num, int numPos) {
        final Num.NumSerie numSerie = this.getFirstNumSerie(num);
        numSerie.decNumCnt(numPos);
        numSerie.incNumCnt(numPos + numPos);
    }

    public long getNumCnt(final Num num, int numPos) {
        final Num.NumSerie numSerie = this.getFirstNumSerie(num);
        return numSerie.getNumCnt(numPos);
    }

    public void incDivNum(final Num num, int numPos) {
        final Num.NumSerie numSerie = this.getFirstNumSerie(num);
        numSerie.incNumCnt(numPos);
    }

    public double calcNumber(final Num num) {
        double retNumber = this.denominator;
        final Num.NumSerie numSerie = this.getFirstNumSerie(num);

        for (int numPos = 0; numPos <= this.denominator * 2; numPos++) {
            if (numSerie.getNumCnt(numPos) > 0L) {
                for (int pos = 0; pos < numSerie.getNumCnt(numPos); pos++) {
                    retNumber = retNumber * ((double) numPos / (double) this.denominator);
                }
            }
        }
        return retNumber;
    }

    private Num.NumSerie getFirstNumSerie(Num num) {
        final Num.NumSerie numSerie;
        if (num.getNumSerieListSize() == 0) {
            numSerie = new Num.NumSerie(this.denominator * 2 + 1);
            num.addNumSerie(numSerie);
        } else {
            numSerie = num.getNumSerie(0);
        }
        return numSerie;
    }
}
