package group11.calculator.scrum;
import java.util.Locale;
import java.util.Scanner;

/** Lettura da tastiera e stampa del menu. */
public class InputHandler {

    // Locale.US: il separatore decimale è il punto (3.5)
    private final Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

    /**
     * Legge un numero reale (US1). Accetta 'r' = ultimo risultato (US5)
     * e 'm' = valore in memoria (US8). Altro input viene rifiutato (US7).
     */
    public double readNumber(String prompt, double lastResult, double memoryValue) {
        System.out.print(prompt + " (o 'r' = ultimo risultato, 'm' = memoria): ");
        while (true) {
            if (scanner.hasNextDouble()) {
                return scanner.nextDouble();
            }
            String token = scanner.next();
            if (token.equalsIgnoreCase("r")) {
                return lastResult;
            }
            if (token.equalsIgnoreCase("m")) {
                return memoryValue;
            }
            System.out.print("Input non valido. Inserisci un numero, 'r' o 'm': ");
        }
    }

    /** Legge la scelta del menu come testo: "1", "+", "sqrt", "m+"... */
    public String readChoice() {
        System.out.print("Scegli un'operazione (numero o simbolo): ");
        return scanner.next().trim();
    }

    /** Stampa il menu generandolo dall'enum Operation. */
    public void printMenu() {
        System.out.println("\n=== CALCOLATRICE ===");
        for (Operation op : Operation.values()) {
            System.out.printf("%2d | %-4s | %s%n",
                    op.getNumber(), op.getSymbol(), op.getDescription());
        }
    }
}