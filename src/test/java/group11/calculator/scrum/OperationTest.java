package group11.calculator.scrum;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OperationTest {

    private static final double DELTA = 1e-9;

    // ---------- US1: choose an operation by number or symbol ----------

    @Test
    void findsOperationByNumber() {
        assertEquals(Operation.ADD, Operation.fromInput("1"));
        assertEquals(Operation.SUBTRACT, Operation.fromInput("2"));
        assertEquals(Operation.MULTIPLY, Operation.fromInput("3"));
        assertEquals(Operation.DIVIDE, Operation.fromInput("4"));
    }

    @Test
    void findsOperationBySymbol() {
        assertEquals(Operation.ADD, Operation.fromInput("+"));
        assertEquals(Operation.SUBTRACT, Operation.fromInput("-"));
        assertEquals(Operation.MULTIPLY, Operation.fromInput("*"));
        assertEquals(Operation.DIVIDE, Operation.fromInput("/"));
        assertEquals(Operation.POWER, Operation.fromInput("^"));
    }

    @Test
    void symbolSearchIgnoresCase() {
        assertEquals(Operation.SQRT, Operation.fromInput("SQRT"));
        assertEquals(Operation.SIN, Operation.fromInput("Sin"));
    }

    @Test
    void zeroAndQuitBothMeanExit() {
        assertEquals(Operation.EXIT, Operation.fromInput("0"));
        assertEquals(Operation.EXIT, Operation.fromInput("q"));
    }

    @Test
    void memorySymbolsAreRecognised() {              // US8
        assertEquals(Operation.MEMORY_STORE, Operation.fromInput("ms"));
        assertEquals(Operation.MEMORY_ADD, Operation.fromInput("m+"));
        assertEquals(Operation.MEMORY_SUBTRACT, Operation.fromInput("m-"));
        assertEquals(Operation.MEMORY_RECALL, Operation.fromInput("mr"));
        assertEquals(Operation.MEMORY_CLEAR, Operation.fromInput("mc"));
    }

    @Test
    void conversionSymbolsAreRecognised() {          // US12
        assertEquals(Operation.TO_RAD, Operation.fromInput("rad"));
        assertEquals(Operation.TO_DEG, Operation.fromInput("deg"));
    }

    // ---------- US7: invalid choices ----------

    @Test
    void unknownInputReturnsNull() {
        assertNull(Operation.fromInput("pippo"));
        assertNull(Operation.fromInput("99"));
        assertNull(Operation.fromInput(""));
    }

    // ---------- Menu consistency ----------

    @Test
    void numbersAreUnique() {
        Set<Integer> seen = new HashSet<>();
        for (Operation op : Operation.values()) {
            assertTrue(seen.add(op.getNumber()), "Duplicate number: " + op.getNumber());
        }
    }

    @Test
    void symbolsAreUnique() {
        Set<String> seen = new HashSet<>();
        for (Operation op : Operation.values()) {
            assertTrue(seen.add(op.getSymbol().toLowerCase()),
                    "Duplicate symbol: " + op.getSymbol());
        }
    }

    @Test
    void everyOperationCanBeFoundByItsOwnNumberAndSymbol() {
        for (Operation op : Operation.values()) {
            assertEquals(op, Operation.fromInput(String.valueOf(op.getNumber())));
            assertEquals(op, Operation.fromInput(op.getSymbol()));
        }
    }

    // ---------- Number of operands ----------

    @Test
    void operandCountsAreCorrect() {
        assertEquals(2, Operation.ADD.getOperands());
        assertEquals(2, Operation.POWER.getOperands());
        assertEquals(1, Operation.SQRT.getOperands());
        assertEquals(1, Operation.LOG.getOperands());
        assertEquals(1, Operation.TO_RAD.getOperands());
        assertEquals(0, Operation.MEMORY_ADD.getOperands());
        assertEquals(0, Operation.EXIT.getOperands());
    }

    // ---------- compute() delegates to Calculator ----------

    @Test
    void computeTwoOperandOperations() {
        Calculator calc = new Calculator();
        assertEquals(8, Operation.ADD.compute(calc, 5, 3), DELTA);
        assertEquals(2, Operation.SUBTRACT.compute(calc, 5, 3), DELTA);
        assertEquals(15, Operation.MULTIPLY.compute(calc, 5, 3), DELTA);
        assertEquals(2.5, Operation.DIVIDE.compute(calc, 5, 2), DELTA);
        assertEquals(8, Operation.POWER.compute(calc, 2, 3), DELTA);
    }

    @Test
    void computeOneOperandOperationsIgnoresSecondValue() {
        Calculator calc = new Calculator();
        assertEquals(4, Operation.SQRT.compute(calc, 16, 999), DELTA);
        assertEquals(0.5, Operation.SIN.compute(calc, 30, 999), DELTA);
        assertEquals(3, Operation.LOG.compute(calc, 1000, 999), DELTA);
        assertEquals(Math.PI, Operation.TO_RAD.compute(calc, 180, 999), DELTA);
    }

    @Test
    void computeUpdatesCalculatorLastResult() {
        Calculator calc = new Calculator();
        Operation.ADD.compute(calc, 2, 2);
        assertEquals(4, calc.getLastResult(), DELTA);
    }

    @Test
    void computePropagatesErrors() {                 // US4
        Calculator calc = new Calculator();
        assertThrows(ArithmeticException.class, () -> Operation.DIVIDE.compute(calc, 1, 0));
        assertThrows(ArithmeticException.class, () -> Operation.SQRT.compute(calc, -1, 0));
        assertThrows(ArithmeticException.class, () -> Operation.LOG.compute(calc, 0, 0));
    }
}
