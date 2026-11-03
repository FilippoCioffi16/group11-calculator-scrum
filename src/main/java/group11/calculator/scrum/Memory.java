package group11.calculator.scrum;
/** Memoria della calcolatrice (US8). */
public class Memory {

    private double value = 0;

    /** MS: sostituisce il valore in memoria. */
    public void store(double v) {
        this.value = v;
    }

    /** M+: somma v alla memoria. */
    public void add(double v) {
        this.value += v;
    }

    /** M-: sottrae v dalla memoria. */
    public void subtract(double v) {
        this.value -= v;
    }

    /** MR: restituisce il valore in memoria. */
    public double recall() {
        return this.value;
    }

    /** MC: azzera la memoria. */
    public void clear() {
        this.value = 0;
    }
}