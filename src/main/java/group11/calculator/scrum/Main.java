package group11.calculator.scrum;

public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Memory memory = new Memory();
        InputHandler input = new InputHandler();

        System.out.println("Benvenuto nella Calcolatrice Scrum - Group 11!");

        boolean running = true;
        while (running) {
            input.printMenu();
            Operation op = Operation.fromInput(input.readChoice());

            if (op == null) {
                System.out.println("Opzione non valida. Riprova.");
            } else if (op.getOperands() == 0) {
                running = runCommand(op, calculator, memory);
            } else {
                runCalculation(op, calculator, memory, input);
            }
        }
    }

    // Operazione con uno o due operandi.
    private static void runCalculation(Operation op, Calculator calculator,
                                       Memory memory, InputHandler input) {
        double a;
        double b = 0;
        if (op.getOperands() == 2) {
            a = input.readNumber("Primo numero", calculator.getLastResult(), memory.recall());
            b = input.readNumber("Secondo numero", calculator.getLastResult(), memory.recall());
        } else {
            a = input.readNumber("Numero", calculator.getLastResult(), memory.recall());
        }

        try {
            System.out.println("Risultato: " + op.compute(calculator, a, b));
        } catch (ArithmeticException e) {
            System.out.println("Errore: " + e.getMessage()); // US4
        }
    }

    /** Comando senza operandi. Restituisce false se si deve uscire. */  
    private static boolean runCommand(Operation op, Calculator calculator, Memory memory) {
        switch (op) {
            case SHOW_LAST:
                System.out.println("Ultimo risultato: " + calculator.getLastResult());
                break;
            case MEMORY_STORE:
                memory.store(calculator.getLastResult());
                System.out.println("Memoria: " + memory.recall());
                break;
            case MEMORY_ADD:
                memory.add(calculator.getLastResult());
                System.out.println("Memoria: " + memory.recall());
                break;
            case MEMORY_SUBTRACT:
                memory.subtract(calculator.getLastResult());
                System.out.println("Memoria: " + memory.recall());
                break;
            case MEMORY_RECALL:
                System.out.println("Valore in memoria: " + memory.recall());
                break;
            case MEMORY_CLEAR:
                memory.clear();
                System.out.println("Memoria azzerata.");
                break;
            case CLEAR:
                calculator.clear();
                System.out.println("Ultimo risultato azzerato.");
                break;
            case EXIT:
                System.out.println("Chiusura programma. Arrivederci!");
                return false;
            default:
                break;
        }
        return true;
    }
}