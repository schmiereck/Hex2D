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

    // numerator
    public void divNum(final Num num, final int numerator) {
        final Num.NumSerie numSerie = this.getFirstNumSerie(num);
        numSerie.setNum(numerator);
    }

    public double calcNumber(final Num num) {
        double retNumber = this.denominator;
        final Num.NumSerie numSerie = this.getFirstNumSerie(num);

        for (int numPos = 0; numPos < this.denominator * 2 + 1; numPos++) {
            if (numSerie.getNum(numPos) > 0L) {
                for (int pos = 0; pos < numSerie.getNum(numPos); pos++) {
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
