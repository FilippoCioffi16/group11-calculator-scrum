package group11.calculator.scrum;
/**
 * Tutte le voci del menu: numero, simbolo, descrizione, numero di operandi
 * (0 = comando) e calcolo da eseguire.
 */
public enum Operation {

    ADD(1, "+", "Addizione", 2, (c, a, b) -> c.add(a, b)),
    SUBTRACT(2, "-", "Sottrazione", 2, (c, a, b) -> c.subtract(a, b)),
    MULTIPLY(3, "*", "Moltiplicazione", 2, (c, a, b) -> c.multiply(a, b)),
    DIVIDE(4, "/", "Divisione", 2, (c, a, b) -> c.divide(a, b)),
    POWER(5, "^", "Potenza", 2, (c, a, b) -> c.power(a, b)),
    SQRT(6, "sqrt", "Radice quadrata", 1, (c, a, b) -> c.squareRoot(a)),
    SIN(7, "sin", "Seno (angolo in gradi)", 1, (c, a, b) -> c.sin(a)),
    COS(8, "cos", "Coseno (angolo in gradi)", 1, (c, a, b) -> c.cos(a)),
    TAN(9, "tan", "Tangente (angolo in gradi)", 1, (c, a, b) -> c.tan(a)),
    LOG(10, "log", "Logaritmo in base 10", 1, (c, a, b) -> c.log(a)),
    TO_RAD(11, "rad", "Converti da gradi a radianti", 1, (c, a, b) -> c.toRadians(a)),
    TO_DEG(12, "deg", "Converti da radianti a gradi", 1, (c, a, b) -> c.toDegrees(a)),

    // Comandi senza operandi (gestiti dal main)
    SHOW_LAST(13, "ans", "Mostra l'ultimo risultato", 0, null),
    MEMORY_STORE(14, "ms", "Memoria: salva l'ultimo risultato", 0, null),
    MEMORY_ADD(15, "m+", "Memoria: somma l'ultimo risultato", 0, null),
    MEMORY_SUBTRACT(16, "m-", "Memoria: sottrai l'ultimo risultato", 0, null),
    MEMORY_RECALL(17, "mr", "Memoria: mostra il valore", 0, null),
    MEMORY_CLEAR(18, "mc", "Memoria: azzera", 0, null),
    CLEAR(19, "c", "Azzera l'ultimo risultato", 0, null),
    EXIT(0, "q", "Esci", 0, null);

    @FunctionalInterface
    public interface Computation {
        double apply(Calculator c, double a, double b);
    }

    private final int number;
    private final String symbol;
    private final String description;
    private final int operands;
    private final Computation computation;

    Operation(int number, String symbol, String description,
              int operands, Computation computation) {
        this.number = number;
        this.symbol = symbol;
        this.description = description;
        this.operands = operands;
        this.computation = computation;
    }

    public int getNumber()         { return number; }
    public String getSymbol()      { return symbol; }
    public String getDescription() { return description; }
    public int getOperands()       { return operands; }

    public double compute(Calculator c, double a, double b) {
        return computation.apply(c, a, b);
    }

    /** Trova l'operazione dal numero ("1") o dal simbolo ("+"). Null se non esiste. */
    public static Operation fromInput(String text) {
        for (Operation op : values()) {
            if (String.valueOf(op.number).equals(text) || op.symbol.equalsIgnoreCase(text)) {
                return op;
            }
        }
        return null;
    }
}