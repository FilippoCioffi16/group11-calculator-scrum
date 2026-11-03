package group11.calculator.scrum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private static final double DELTA = 1e-9;
    private Calculator calc;

    @BeforeEach
    void setUp() {
        calc = new Calculator(); // fresh object for every test
    }

    // ---------- US2: addition and subtraction ----------

    @Test
    void addIntegers() {
        assertEquals(8, calc.add(5, 3), DELTA);
    }

    @Test
    void addDecimals() {
        assertEquals(4.0, calc.add(2.5, 1.5), DELTA);
    }

    @Test
    void addNegativeNumbers() {
        assertEquals(-7, calc.add(-3, -4), DELTA);
    }

    @Test
    void subtractIntegers() {
        assertEquals(2, calc.subtract(5, 3), DELTA);
    }

    @Test
    void subtractCanGiveNegativeResult() {
        assertEquals(-2, calc.subtract(3, 5), DELTA);
    }

    @Test
    void subtractDecimals() {
        assertEquals(1.0, calc.subtract(2.5, 1.5), DELTA);
    }

    // ---------- US3: multiplication and division ----------

    @Test
    void multiplyIntegers() {
        assertEquals(12, calc.multiply(3, 4), DELTA);
    }

    @Test
    void multiplyByZero() {
        assertEquals(0, calc.multiply(7, 0), DELTA);
    }

    @Test
    void multiplyNegativeNumbers() {
        assertEquals(6, calc.multiply(-2, -3), DELTA);
    }

    @Test
    void divideIntegersGivesDecimal() {
        assertEquals(2.5, calc.divide(5, 2), DELTA);
    }

    @Test
    void divideZeroByNumber() {
        assertEquals(0, calc.divide(0, 5), DELTA);
    }

    // ---------- US4: division by zero ----------

    @Test
    void divideByZeroThrowsException() {
        assertThrows(ArithmeticException.class, () -> calc.divide(5, 0));
    }

    @Test
    void divideZeroByZeroThrowsException() {
        assertThrows(ArithmeticException.class, () -> calc.divide(0, 0));
    }

    @Test
    void failedDivisionDoesNotChangeLastResult() {
        calc.add(2, 3);
        assertThrows(ArithmeticException.class, () -> calc.divide(5, 0));
        assertEquals(5, calc.getLastResult(), DELTA);
    }

    // ---------- US5: last result ----------

    @Test
    void lastResultStartsAtZero() {
        assertEquals(0, calc.getLastResult(), DELTA);
    }

    @Test
    void lastResultIsUpdatedByEveryOperation() {
        calc.add(1, 1);
        assertEquals(2, calc.getLastResult(), DELTA);
        calc.subtract(10, 4);
        assertEquals(6, calc.getLastResult(), DELTA);
        calc.multiply(3, 3);
        assertEquals(9, calc.getLastResult(), DELTA);
        calc.divide(9, 3);
        assertEquals(3, calc.getLastResult(), DELTA);
        calc.power(2, 3);
        assertEquals(8, calc.getLastResult(), DELTA);
        calc.squareRoot(16);
        assertEquals(4, calc.getLastResult(), DELTA);
    }

    @Test
    void lastResultCanBeUsedInNextCalculation() {
        double first = calc.add(5, 3);                       // 8
        double second = calc.multiply(calc.getLastResult(), 2);
        assertEquals(8, first, DELTA);
        assertEquals(16, second, DELTA);
    }

    // ---------- US6: clear ----------

    @Test
    void clearResetsLastResultToZero() {
        calc.add(2, 3);
        calc.clear();
        assertEquals(0, calc.getLastResult(), DELTA);
    }

    // ---------- US9: power and square root ----------

    @Test
    void powerWithPositiveExponent() {
        assertEquals(1024, calc.power(2, 10), DELTA);
    }

    @Test
    void powerWithZeroExponent() {
        assertEquals(1, calc.power(5, 0), DELTA);
    }

    @Test
    void powerWithNegativeExponent() {
        assertEquals(0.25, calc.power(2, -2), DELTA);
    }

    @Test
    void squareRootOfPositiveNumber() {
        assertEquals(4, calc.squareRoot(16), DELTA);
    }

    @Test
    void squareRootOfZero() {
        assertEquals(0, calc.squareRoot(0), DELTA);
    }

    @Test
    void squareRootOfNegativeNumberThrowsException() {
        assertThrows(ArithmeticException.class, () -> calc.squareRoot(-4));
    }

    // ---------- US10: trigonometric functions (degrees) ----------

    @Test
    void sineInDegrees() {
        assertEquals(0, calc.sin(0), DELTA);
        assertEquals(0.5, calc.sin(30), DELTA);
        assertEquals(1, calc.sin(90), DELTA);
    }

    @Test
    void cosineInDegrees() {
        assertEquals(1, calc.cos(0), DELTA);
        assertEquals(0.5, calc.cos(60), DELTA);
        assertEquals(0, calc.cos(90), DELTA);
    }

    @Test
    void tangentInDegrees() {
        assertEquals(0, calc.tan(0), DELTA);
        assertEquals(1, calc.tan(45), DELTA);
    }

    @Test
    void tangentOf90And270DegreesThrowsException() {
        assertThrows(ArithmeticException.class, () -> calc.tan(90));
        assertThrows(ArithmeticException.class, () -> calc.tan(270));
    }

    @Test
    void trigonometryWorksWithNegativeAngles() {
        assertEquals(-0.5, calc.sin(-30), DELTA);
    }

    // ---------- US11: logarithm (base 10) ----------

    @Test
    void logarithmBase10() {
        assertEquals(0, calc.log(1), DELTA);
        assertEquals(1, calc.log(10), DELTA);
        assertEquals(3, calc.log(1000), DELTA);
    }

    @Test
    void logarithmOfZeroThrowsException() {
        assertThrows(ArithmeticException.class, () -> calc.log(0));
    }

    @Test
    void logarithmOfNegativeNumberThrowsException() {
        assertThrows(ArithmeticException.class, () -> calc.log(-5));
    }

    // ---------- US12: angle conversion ----------

    @Test
    void degreesToRadians() {
        assertEquals(Math.PI, calc.toRadians(180), DELTA);
        assertEquals(Math.PI / 2, calc.toRadians(90), DELTA);
    }

    @Test
    void radiansToDegrees() {
        assertEquals(180, calc.toDegrees(Math.PI), DELTA);
        assertEquals(90, calc.toDegrees(Math.PI / 2), DELTA);
    }

    @Test
    void conversionBecomesLastResult() {
        calc.toDegrees(Math.PI);
        assertEquals(180, calc.getLastResult(), DELTA);
    }

    @Test
    void conversionCanBeAppliedToPreviousResult() {
        calc.multiply(2, Math.PI);                  // 2*pi radians
        double degrees = calc.toDegrees(calc.getLastResult());
        assertEquals(360, degrees, DELTA);
    }
}