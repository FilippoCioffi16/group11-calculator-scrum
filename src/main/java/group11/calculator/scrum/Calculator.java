package group11.calculator.scrum;
/** Logica di calcolo. Nessun input/output (Separation of Concerns). */
public class Calculator {

    private double lastResult = 0; // US5

    // ---- US2 ----
    public double add(double a, double b) {
        return store(a + b);
    }

    public double subtract(double a, double b) {
        return store(a - b);
    }

    // ---- US3 + US4 ----
    public double multiply(double a, double b) {
        return store(a * b);
    }

    public double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Divisione per zero non consentita");
        }
        return store(a / b);
    }

    // ---- US5 ----
    public double getLastResult() {
        return lastResult;
    }

    // ---- US6 ----
    public void clear() {
        lastResult = 0;
    }

    // ---- US9 ----
    public double power(double base, double exponent) {
        return store(Math.pow(base, exponent));
    }

    public double squareRoot(double a) {
        if (a < 0) {
            throw new ArithmeticException("Radice quadrata di un numero negativo");
        }
        return store(Math.sqrt(a));
    }

    // ---- US10 (angoli in gradi) ----
    public double sin(double degrees) {
        return store(Math.sin(Math.toRadians(degrees)));
    }

    public double cos(double degrees) {
        return store(Math.cos(Math.toRadians(degrees)));
    }

    public double tan(double degrees) {
        double radians = Math.toRadians(degrees);
        if (Math.abs(Math.cos(radians)) < 1e-12) {
            throw new ArithmeticException("Tangente non definita per questo angolo");
        }
        return store(Math.tan(radians));
    }

    // ---- US11 (base 10) ----
    public double log(double a) {
        if (a <= 0) {
            throw new ArithmeticException("Il logaritmo richiede un numero positivo");
        }
        return store(Math.log10(a));
    }

    // ---- US12 ----
    public double toRadians(double degrees) {
        return store(Math.toRadians(degrees));
    }

    public double toDegrees(double radians) {
        return store(Math.toDegrees(radians));
    }

    /** Salva il risultato (DRY: prima era ripetuto in ogni metodo). */
    private double store(double result) {
        lastResult = result;
        return lastResult;
    }
}