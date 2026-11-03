package group11.calculator.scrum;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InputHandlerTest {

    private static final double DELTA = 1e-9;
    private static final double LAST = 42.0;
    private static final double MEM = 7.5;

    private InputStream originalIn;
    private PrintStream originalOut;
    private ByteArrayOutputStream output;

    @BeforeEach
    void saveStreams() {
        originalIn = System.in;
        originalOut = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void restoreStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    /** Creates an InputHandler that reads the given text instead of the keyboard. */
    private InputHandler handlerWithInput(String text) {
        System.setIn(new ByteArrayInputStream(text.getBytes()));
        return new InputHandler();
    }

    // ---------- US1: reading numbers ----------

    @Test
    void readsInteger() {
        InputHandler in = handlerWithInput("5\n");
        assertEquals(5, in.readNumber("n", LAST, MEM), DELTA);
    }

    @Test
    void readsDecimalWithDot() {
        InputHandler in = handlerWithInput("2.5\n");
        assertEquals(2.5, in.readNumber("n", LAST, MEM), DELTA);
    }

    @Test
    void readsNegativeNumber() {
        InputHandler in = handlerWithInput("-5\n");
        assertEquals(-5, in.readNumber("n", LAST, MEM), DELTA);
    }

    @Test
    void readsTwoNumbersInARow() {
        InputHandler in = handlerWithInput("3\n4\n");
        assertEquals(3, in.readNumber("a", LAST, MEM), DELTA);
        assertEquals(4, in.readNumber("b", LAST, MEM), DELTA);
    }

    // ---------- US5: last result with 'r' ----------

    @Test
    void rReturnsLastResult() {
        InputHandler in = handlerWithInput("r\n");
        assertEquals(LAST, in.readNumber("n", LAST, MEM), DELTA);
    }

    @Test
    void rIsCaseInsensitive() {
        InputHandler in = handlerWithInput("R\n");
        assertEquals(LAST, in.readNumber("n", LAST, MEM), DELTA);
    }

    // ---------- US8: memory with 'm' ----------

    @Test
    void mReturnsMemoryValue() {
        InputHandler in = handlerWithInput("m\n");
        assertEquals(MEM, in.readNumber("n", LAST, MEM), DELTA);
    }

    // ---------- US7: invalid input ----------

    @Test
    void invalidTextIsRejectedAndNumberIsAskedAgain() {
        InputHandler in = handlerWithInput("abc\n9\n");
        assertEquals(9, in.readNumber("n", LAST, MEM), DELTA);
        assertTrue(output.toString().contains("Input non valido"));
    }

    @Test
    void severalInvalidInputsInARow() {
        InputHandler in = handlerWithInput("x\n???\n1,5,3\n8\n");
        assertEquals(8, in.readNumber("n", LAST, MEM), DELTA);
    }

    @Test
    void commaDecimalIsRejected() {
        // The agreed decimal separator is the dot (Locale.US)
        InputHandler in = handlerWithInput("2,5\n4\n");
        assertEquals(4, in.readNumber("n", LAST, MEM), DELTA);
    }

    // ---------- US1: reading the menu choice ----------

    @Test
    void readsChoiceAsNumber() {
        InputHandler in = handlerWithInput("1\n");
        assertEquals("1", in.readChoice());
    }

    @Test
    void readsChoiceAsSymbol() {
        InputHandler in = handlerWithInput("+\n-\nsqrt\nm+\n");
        assertEquals("+", in.readChoice());
        assertEquals("-", in.readChoice());
        assertEquals("sqrt", in.readChoice());
        assertEquals("m+", in.readChoice());
    }

    @Test
    void choiceReadingIgnoresSurroundingSpaces() {
        InputHandler in = handlerWithInput("   *   \n");
        assertEquals("*", in.readChoice());
    }

    @Test
    void choiceAndNumberCanBeMixed() {
        // "-" as an operation, then "-5" as a number: no ambiguity
        InputHandler in = handlerWithInput("-\n3\n-5\n");
        assertEquals("-", in.readChoice());
        assertEquals(3, in.readNumber("a", LAST, MEM), DELTA);
        assertEquals(-5, in.readNumber("b", LAST, MEM), DELTA);
    }

    // ---------- Menu printing ----------

    @Test
    void menuListsEveryOperation() {
        InputHandler in = handlerWithInput("");
        in.printMenu();
        String text = output.toString();
        for (Operation op : Operation.values()) {
            assertTrue(text.contains(op.getDescription()),
                    "Menu is missing: " + op.getDescription());
        }
    }
}