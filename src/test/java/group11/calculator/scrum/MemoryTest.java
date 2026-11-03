package group11.calculator.scrum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MemoryTest {

    private static final double DELTA = 1e-9;
    private Memory memory;

    @BeforeEach
    void setUp() {
        memory = new Memory();
    }

    // ---------- US8: memory functions ----------

    @Test
    void memoryStartsAtZero() {
        assertEquals(0, memory.recall(), DELTA);
    }

    @Test
    void storeSavesValue() {                         // MS
        memory.store(10);
        assertEquals(10, memory.recall(), DELTA);
    }

    @Test
    void storeReplacesPreviousValue() {              // MS
        memory.store(10);
        memory.store(4);
        assertEquals(4, memory.recall(), DELTA);
    }

    @Test
    void addSumsToMemory() {                         // M+
        memory.store(10);
        memory.add(5);
        assertEquals(15, memory.recall(), DELTA);
    }

    @Test
    void addWorksFromZero() {                        // M+
        memory.add(7);
        assertEquals(7, memory.recall(), DELTA);
    }

    @Test
    void subtractRemovesFromMemory() {               // M-
        memory.store(10);
        memory.subtract(3);
        assertEquals(7, memory.recall(), DELTA);
    }

    @Test
    void subtractCanGoNegative() {                   // M-
        memory.subtract(5);
        assertEquals(-5, memory.recall(), DELTA);
    }

    @Test
    void clearResetsMemoryToZero() {                 // MC
        memory.store(99);
        memory.clear();
        assertEquals(0, memory.recall(), DELTA);
    }

    @Test
    void recallDoesNotChangeMemory() {               // MR
        memory.store(8);
        memory.recall();
        memory.recall();
        assertEquals(8, memory.recall(), DELTA);
    }

    @Test
    void memoryAndCalculatorAreIndependent() {       // US5 + US8
        Calculator calc = new Calculator();
        calc.add(2, 3);
        memory.store(calc.getLastResult());
        calc.clear();
        assertEquals(0, calc.getLastResult(), DELTA);
        assertEquals(5, memory.recall(), DELTA);
    }
}