import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class wDensePolynomialTest {

    @Test
    void testGetDegree() {
        Polynomial a = new DensePolynomial("2x + 1");
        assertEquals(a.add(a),"");
        Polynomial b = new DensePolynomial("3x^43 + 126x^27 - 238x^16 - x^5");
        Polynomial c = new DensePolynomial("2x^123123123 + 123123123x^43 - 321321x^4");
        Polynomial d = new DensePolynomial("123123123x^123321789 - 1324568x^43");
        Polynomial e = new DensePolynomial("33579574x^863753 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");

        assertThrows(ArithmeticException.class, () -> { new DensePolynomial("x^2147483647"); });
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> { new DensePolynomial("x^-2147483647"); });
        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> { new DensePolynomial("2x^-123123"); });
        Exception exception4 = assertThrows(IllegalArgumentException.class, () -> { new DensePolynomial("0x^123321789 - 1324568x^43"); });
        Exception exception5 = assertThrows(IllegalArgumentException.class, () -> { new DensePolynomial("99999999999999x^123321789 - 1324568x^43"); });
        Exception exception6 = assertThrows(IllegalArgumentException.class, () -> { new DensePolynomial(""); });

        assertAll(
                () -> assertEquals(123123123, c.degree()),
                () -> assertEquals(1, a.degree()),
                () -> assertEquals(43, b.degree()),
                () -> assertEquals(123321789, d.degree()),
                () -> assertEquals(863753, e.degree()),
                () -> assertEquals(0, (e.add(e.minus())).degree()),

                () -> assertEquals("Your number is out of boundary to handle", exception1.getMessage()),
                () -> assertEquals("Your number is out of boundary to handle", exception2.getMessage()),
                () -> assertEquals("degrees cannot be negative.", exception3.getMessage()),
                () -> assertEquals("not form of canonical string representation", exception4.getMessage()),
                () -> assertEquals("given number is out of boundary or canonical format is wrong", exception5.getMessage()),
                () -> assertEquals("Empty string is not allowed", exception6.getMessage())
        );
    }


    @Test
    void testGetCoefficient() {
        Polynomial a = new DensePolynomial("2x + 1");
        Polynomial b = new DensePolynomial("3x^43 + 126x^27 - 238x^16 - x^5");
        Polynomial c = new DensePolynomial("2124x^123123123 + 123123123x^43 - 321321x^4");
        Polynomial d = new DensePolynomial("123123123x^123321789 - 1324568x^43");
        Polynomial e = new DensePolynomial("33579574x^863753 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");

        assertAll(
                () -> assertEquals(2, a.getCoefficient(1)),
                () -> assertEquals(1, a.getCoefficient(0)),
                () -> assertEquals(-238, b.getCoefficient(16)),
                () -> assertEquals(0, b.getCoefficient(123123123)),
                () -> assertEquals(2124, c.getCoefficient(123123123)),
                () -> assertEquals(123123123, d.getCoefficient(123321789)),
                () -> assertEquals(0, d.getCoefficient(-999999999)),
                () -> assertEquals(-1324568, d.getCoefficient(43)),
                () -> assertEquals(0, d.getCoefficient(0)),
                () -> assertEquals(33579574, e.getCoefficient(863753)),
                () -> assertEquals(0, e.getCoefficient(863755)),
                () -> assertEquals(-14, e.getCoefficient(21))
        );
    }

    @Test
    void testIsZero() {
        Polynomial a = new DensePolynomial("2x + 1");
        Polynomial b = new DensePolynomial("3x^2 + x + 3");
        Polynomial c = (DensePolynomial) a.add(a.minus());
        Polynomial d = (DensePolynomial) b.subtract(b);

        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> { new DensePolynomial("0"); });
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> { new DensePolynomial("-0"); });
        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> { new DensePolynomial("0x^2 + 4"); });
        Exception exception4 = assertThrows(IllegalArgumentException.class, () -> { new DensePolynomial("x + 0"); });
        Exception exception5 = assertThrows(IllegalArgumentException.class, () -> { new DensePolynomial("000"); });
        Exception exception6 = assertThrows(IllegalArgumentException.class, () -> { new DensePolynomial("0 - 0 + 0 - 0"); });

        assertAll(
                () -> assertFalse((a.isZero())),
                () -> assertFalse((b.isZero())),
                () -> assertTrue((c.isZero())),
                () -> assertTrue((d.isZero())),

                () -> assertEquals("not form of canonical string representation", exception1.getMessage()),
                () -> assertEquals("not form of canonical string representation", exception2.getMessage()),
                () -> assertEquals("not form of canonical string representation", exception3.getMessage()),
                () -> assertEquals("not form of canonical string representation", exception4.getMessage()),
                () -> assertEquals("not form of canonical string representation", exception5.getMessage()),
                () -> assertEquals("not form of canonical string representation", exception6.getMessage())
        );
    }

    @Test
    void testAdd() {
        Polynomial a = new DensePolynomial("2x + 1");
        Polynomial b = new DensePolynomial("3x^43 + 126x^27 - 238x^16 - x^5");
        Polynomial c = new DensePolynomial("2124x^12312312 + 123123123x^43 - 321321x^4");
        Polynomial d = new DensePolynomial("1231231x^12332178 - 1324568x^43");
        Polynomial e = new DensePolynomial("33579574x^863753 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");
        Polynomial f = new DensePolynomial("923456789x^863753 - 62342378x^75454 + 6433427x^234 - 83432x^232 - 9325x^85 + 95123x^59 - 542x^53");
        Polynomial g = new SparsePolynomial("923456789x^863753 - 62342378x^75454 + 6433427x^234 - 83432x^232 - 9325x^85 + 95123x^59 - 542x^53");
        Polynomial h = new SparsePolynomial("923456789x^-5000 - 62342378x^-14532 + 6433427x^-39473 - 83432x^-49683 - 9325x^-124345 + 95123x^-1254332 - 542x^-4556733");
        Polynomial i = new DensePolynomial("999999999x^863753 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");
        Polynomial j = new SparsePolynomial("923456789x^8637534 - 62342378x^75454 + 6433427x^234 - 83432x^232 - 9325x^85 + 95123x^59 - 542x^53");
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> { e.add(h); });

        assertAll(
                () -> assertThrows(NullPointerException.class, () -> { a.add(null); }),
                () -> assertEquals(new DensePolynomial("4x + 2"), ((DensePolynomial) a.add(a))),
                () -> assertEquals(new DensePolynomial("6x^43 + 252x^27 - 476x^16 - 2x^5"), (DensePolynomial) b.add(b)),
                () -> assertEquals(new DensePolynomial("1231231x^12332178 + 2124x^12312312 + 121798555x^43 - 321321x^4"), (DensePolynomial) c.add(d)),
                () -> assertEquals(new DensePolynomial("67159148x^863753 - 35972868x^75454 + 134866854x^234 - 246664x^232 - 3512x^43 + 250x^23 - 28x^21"), (DensePolynomial) e.add(e)),
                () -> assertEquals(new DensePolynomial("1846913578x^863753 - 124684756x^75454 + 12866854x^234 - 166864x^232 - 18650x^85 + 190246x^59 - 1084x^53"), (DensePolynomial) f.add(f)),
                () -> assertEquals(new DensePolynomial("1846913578x^863753 - 124684756x^75454 + 12866854x^234 - 166864x^232 - 18650x^85 + 190246x^59 - 1084x^53"), (DensePolynomial) f.add(g)),
                () -> assertEquals(new DensePolynomial("1999999998x^863753 - 35972868x^75454 + 134866854x^234 - 246664x^232 - 3512x^43 + 250x^23 - 28x^21"), (DensePolynomial) i.add(i)),
                () -> assertEquals(new DensePolynomial("923456789x^8637534 + 999999999x^863753 - 80328812x^75454 + 73866854x^234 - 206764x^232 - 9325x^85 + 95123x^59 - 542x^53 - 1756x^43 + 125x^23 - 14x^21"), (DensePolynomial) i.add(j)),
                () -> assertEquals("Cannot add DensePolynomial with SparsePolynomial that has negative coefficient", exception1.getMessage()),
                () -> assertTrue((a.add(a.minus())).isZero()),
                () -> assertTrue((a.add(a.minus())).add(a.add(a.minus())).isZero()),
                () -> assertTrue((a.add(a.minus())).add(g.add(g.minus())).isZero())
        );
    }

    @Test
    void testMultiply() {
        Polynomial a = new DensePolynomial("2x + 1");
        Polynomial b = new DensePolynomial("4x^2 + 4x + 1");
        Polynomial c = new DensePolynomial("3x^43 + 126x^27 - 238x^16 - x^5");
        Polynomial d = new DensePolynomial("2124x^112313 + 123123123x^43 - 321321x^4");
        Polynomial e = new DensePolynomial("123123123x^1578 - 1324568x^43");
        Polynomial f = new SparsePolynomial("43254x^-53 - 1324567x^-1578");
        Polynomial g = new DensePolynomial("43254x^1578 - 1324567x^53 + 6342341");
        Polynomial h = new SparsePolynomial("-9343124x^-5333 - 1324567x^-157813");
        Polynomial i = new SparsePolynomial("- 6342341 + 43254x^-1578");
        Polynomial j = new DensePolynomial("43254x^1578 + 6342341");
        Polynomial k = new DensePolynomial("999999999x^863753 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");

        assertAll(
                () -> assertThrows(NullPointerException.class, () -> { a.multiply(null); }),
                () -> assertEquals(b, ((DensePolynomial) a.multiply(a))),
                () -> assertEquals(new DensePolynomial("6372x^112356 + 267624x^112340 - 505512x^112329 - 2124x^112318 + 369369369x^86 - 1666355686x^70 + 761467798x^59 - 123123123x^48 - 963963x^47 - 40486446x^31 + 76474398x^20 + 321321x^9"), ((DensePolynomial) d.multiply(c))),
                () -> assertEquals(new DensePolynomial("- 479491804x^113891 + 1481584864x^112356 + 1597676329x^1621 - 1101242027x^1582 - 745589448x^86 + 409752024x^47"), ((DensePolynomial) d.multiply(e))),
                () -> assertEquals(new SparsePolynomial("- 191884798x^1525 - 622466325 - 1458289424x^-10 + 2132405288x^-1535"), ((SparsePolynomial) e.multiply(f))),
                () -> assertEquals(new SparsePolynomial("1870908516x^1525 + 1378474956 - 546289330x^-53 + 2131080721x^-1525 + 100439629x^-1578"), ((SparsePolynomial) g.multiply(f))),
                () -> assertEquals(new SparsePolynomial("- 400559672x^-3755 + 1792947532x^-5280 + 385369628x^-5333 - 1458246170x^-156235 + 2131080721x^-157760 + 100439629x^-157813"), ((SparsePolynomial) g.multiply(h))),
                () -> assertEquals(new SparsePolynomial("546289330x^1578 - 1049724725 - 546289330x^-1578"), ((SparsePolynomial) j.multiply(i))),
                () -> assertEquals(new DensePolynomial("- 615681270x^865331 + 1442360123x^863753 - 596135660x^77032 - 1766420234x^75454 + 482657474x^1812 - 1039635032x^1810 - 75954024x^1621 + 5406750x^1601 - 605556x^1599 + 1535431519x^234 - 529552340x^232 + 1747751092x^43 + 792792625x^23 - 88792774x^21"), ((DensePolynomial) j.multiply(k))),
                () -> assertTrue(a.add(a.minus()).multiply(k).isZero()),
                () -> assertTrue(a.add(a.minus()).multiply(i).isZero()),
                () -> assertTrue(a.add(a.minus()).multiply(k.add(k.minus())).isZero()),
                () -> assertTrue(a.add(a.minus()).multiply(i.add(i.minus())).isZero())
        );

    }

    @Test
    void testSubtract() {
        Polynomial a = new DensePolynomial("2x + 1");
        Polynomial b = new DensePolynomial("3x^43 + 126x^27 - 238x^16 - x^5");
        Polynomial c = new DensePolynomial("2124x^43 + 123123123x^16 - 321321x^4");
        Polynomial d = new DensePolynomial("123123123x^123321789 - 1324568x^43");
        Polynomial e = new DensePolynomial("33579574x^1233217 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");
        Polynomial f = new DensePolynomial("-923456789x^863753 - 62342378x^75454 + 6433427x^234 - 83432x^232 - 9325x^85 + 95123x^59 - 542x^53");
        Polynomial g = new SparsePolynomial("-923456789x^863753 - 62342378x^75454 + 6433427x^234 - 83432x^232 - 9325x^85 + 95123x^59 - 542x^53 - x");
        Polynomial h = new SparsePolynomial("923456789x^-5000 - 62342378x^-14532 + 6433427x^-39473 - 83432x^-49683 - 9325x^-124345 + 95123x^-1254332 - 542x^-4556733");
        Polynomial i = new DensePolynomial("-999999999x^863753 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> { i.subtract(h); });

        assertAll(
                () -> assertThrows(NullPointerException.class, () -> { a.subtract(null); }),
                () -> assertEquals(new DensePolynomial("- 3x^43 - 126x^27 + 238x^16 + x^5 + 2x + 1"), ((DensePolynomial) a.subtract(b))),
                () -> assertEquals(new DensePolynomial("3x^43 + 126x^27 - 238x^16 - x^5 - 2x - 1"), ((DensePolynomial) b.subtract(a))),
                () -> assertEquals(new DensePolynomial("- 2121x^43 + 126x^27 - 123123361x^16 - x^5 + 321321x^4"), (DensePolynomial) b.subtract(c)),
                () -> assertEquals(new DensePolynomial("- 123123123x^123321789 + 33579574x^1233217 - 17986434x^75454 + 67433427x^234 - 123332x^232 + 1322812x^43 + 125x^23 - 14x^21"), (DensePolynomial) e.subtract(d)),
                () -> assertEquals(new DensePolynomial("76543210x^863753 - 44355944x^75454 - 61000000x^234 + 39900x^232 - 9325x^85 + 95123x^59 - 542x^53 + 1756x^43 - 125x^23 + 14x^21"), (DensePolynomial) f.subtract(i)),
                () -> assertEquals(new DensePolynomial("x"), (DensePolynomial) f.subtract(g)),
                () -> assertEquals("Cannot subtract DensePolynomial with SparsePolynomial that has negative coefficient", exception1.getMessage()),
                () -> assertTrue(a.subtract(a).isZero()),
                () -> assertTrue((a.add(a.minus())).subtract(a.add(a.minus())).isZero()),
                () -> assertTrue((a.add(a.minus())).subtract(g.add(g.minus())).isZero())
        );
    }

    @Test
    void testMinus() {
        Polynomial a = new DensePolynomial("2x + 1");
        Polynomial b = new DensePolynomial("3x^43 + 126x^27 - 238x^16 - x^5");
        Polynomial c = new DensePolynomial("2124x^43 + 123123123x^16 - 321321x^4");
        Polynomial d = new DensePolynomial("123123123x^123321789 - 1324568x^43");
        Polynomial e = new DensePolynomial("33579574x^1233217 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");
        Polynomial f = new DensePolynomial("- 923456789x^863753 - 62342378x^75454 + 6433427x^234 - 83432x^232 - 9325x^85 + 95123x^59 - 542x^53");
        Polynomial g = new DensePolynomial("- 999999999x^863753 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");

        assertAll(
                () -> assertEquals(new DensePolynomial("- 3x^43 - 126x^27 + 238x^16 + x^5"), b.minus()),
                () -> assertEquals(new DensePolynomial("- 2124x^43 - 123123123x^16 + 321321x^4"), c.minus()),
                () -> assertEquals(new DensePolynomial("- 123123123x^123321789 + 1324568x^43"), d.minus()),
                () -> assertEquals(new DensePolynomial("- 33579574x^1233217 + 17986434x^75454 - 67433427x^234 + 123332x^232 + 1756x^43 - 125x^23 + 14x^21"), e.minus()),
                () -> assertEquals(new DensePolynomial("923456789x^863753 + 62342378x^75454 - 6433427x^234 + 83432x^232 + 9325x^85 - 95123x^59 + 542x^53"), f.minus()),
                () -> assertEquals(new DensePolynomial("999999999x^863753 + 17986434x^75454 - 67433427x^234 + 123332x^232 + 1756x^43 - 125x^23 + 14x^21"), g.minus()),
                () -> assertTrue(a.add(a.minus()).minus().isZero())
        );
    }

    @Test
    void testToString() {
        Polynomial a = new DensePolynomial("3x^43 + 126x^27 - 238x^16 - x^5");
        Polynomial b = new DensePolynomial("123123123x^131789 - 1324568x^43");
        Polynomial c = new DensePolynomial("33579574x^1233217 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");
        Polynomial d = new SparsePolynomial("- 923456789x^863753 - 62342378x^75454 + 6433427x^234 - 83432x^232 - 9325x^85 + 95123x^59 - 542x^53 - x");
        Polynomial e = new SparsePolynomial("923456789x^-5000 - 62342378x^-14532 + 6433427x^-39473 - 83432x^-49683 - 9325x^-124345 + 95123x^-1254332 - 542x^-4556733");
        Polynomial f = new DensePolynomial("- 999999999x^863753 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");

        assertAll(
                () -> assertEquals("3x^43 + 126x^27 - 238x^16 - x^5", a.toString()),
                () -> assertEquals("123123123x^131789 - 1324568x^43", b.toString()),
                () -> assertEquals("33579574x^1233217 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21", c.toString()),
                () -> assertEquals("- 923456789x^863753 - 62342378x^75454 + 6433427x^234 - 83432x^232 - 9325x^85 + 95123x^59 - 542x^53 - x", d.toString()),
                () -> assertEquals("923456789x^-5000 - 62342378x^-14532 + 6433427x^-39473 - 83432x^-49683 - 9325x^-124345 + 95123x^-1254332 - 542x^-4556733", e.toString()),
                () -> assertEquals("- 999999999x^863753 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21", f.toString()),
                () -> assertEquals("0", f.add(f.minus()).toString())
        );
    }

    @Test
    void testEqual() {
        Polynomial a = new DensePolynomial("3x^43 + 126x^27 - 238x^16 - x^5");
        Polynomial b = new DensePolynomial("2124x^43 + 123123123x^16 - 321321x^4");
        Polynomial c = new DensePolynomial("2122x^43 + 123123123x^16 - 321321x^4");
        Polynomial d = new DensePolynomial("33579574x^1233217 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");
        Polynomial e = new DensePolynomial("-923456789x^863753 - 62342378x^75454 + 6433427x^234 - 83432x^232 - 9325x^85 + 95123x^59 - 542x^53");
        Polynomial f = new DensePolynomial("33579574x^1233217 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21 - x");
        Polynomial g = new SparsePolynomial("33579574x^1233217 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21 - x");

        assertAll(
                () -> assertFalse(a.equals(null)),
                () -> assertTrue(a.equals(a)),
                () -> assertFalse(b.equals(c)),
                () -> assertFalse(a.equals("Hello")),
                () -> assertFalse(a.equals(99999)),
                () -> assertTrue(new DensePolynomial("2x^43").equals(b.subtract(c))),
                () -> assertTrue(d.subtract(d).equals(e.add(e.minus()))),
                () -> assertTrue(d.subtract(f).equals(new DensePolynomial("x"))),
                () -> assertFalse(f.equals(g))
        );
    }

    @Test
    void testWellFormed() {
        Polynomial a = new DensePolynomial("3x^43 + 126x^27 - 238x^16 - x^5");
        Polynomial b = new DensePolynomial("2124x^43 + 123123123x^16 - 321321x^4");
        Polynomial c = new DensePolynomial("2122x^43 + 123123123x^16 - 321321x^4");
        Polynomial d = new DensePolynomial("33579574x^1233217 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");
        Polynomial e = new DensePolynomial("-923456789x^863753 - 62342378x^75454 + 6433427x^234 - 83432x^232 - 9325x^85 + 95123x^59 - 542x^53");
        Polynomial f = new DensePolynomial("33579574x^1233217 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21 - x");

        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> { new DensePolynomial("x^1.0"); });
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> { new DensePolynomial("x^1/4"); });
        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> { new DensePolynomial("1231231/2x^12332178 + 123123123x^43"); });
        Exception exception4 = assertThrows(IllegalArgumentException.class, () -> { new DensePolynomial("33579574/3x^1233217 - 17986434x^75454/2 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21 - x"); });
        Exception exception5 = assertThrows(IllegalArgumentException.class, () -> { new DensePolynomial("33579574x^1233217 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21 - 4.0x"); });

        assertAll(
                () -> assertTrue(a.wellFormed()),
                () -> assertTrue(b.wellFormed()),
                () -> assertTrue(c.wellFormed()),
                () -> assertTrue(d.wellFormed()),
                () -> assertTrue(e.wellFormed()),
                () -> assertTrue(f.wellFormed()),
                () -> assertEquals("coefficients and degrees must always be integer and integer format", exception1.getMessage()),
                () -> assertEquals("coefficients and degrees must always be integer and integer format", exception2.getMessage()),
                () -> assertEquals("coefficients and degrees must always be integer and integer format", exception3.getMessage()),
                () -> assertEquals("coefficients and degrees must always be integer and integer format", exception4.getMessage()),
                () -> assertEquals("coefficients and degrees must always be integer and integer format", exception5.getMessage())
        );

        ((DensePolynomial) a).getStringPolynomialArr()[0] = "43.000";
        ((DensePolynomial) b).getStringPolynomialArr()[3] = "123123123.56843";
        ((DensePolynomial) c).getStringPolynomialArr()[5] = "4/1";
        ((DensePolynomial) d).getStringPolynomialArr()[3] = "75454.75454";
        ((DensePolynomial) e).getStringPolynomialArr()[0] = "-932456789.0";
        ((DensePolynomial) f).getStringPolynomialArr()[15] = "1.0";

        assertAll(
                () -> assertFalse(a.wellFormed()),
                () -> assertFalse(b.wellFormed()),
                () -> assertFalse(c.wellFormed()),
                () -> assertFalse(d.wellFormed()),
                () -> assertFalse(e.wellFormed()),
                () -> assertFalse(f.wellFormed())
        );
    }

    @Test
    void testGetPolynomialArr() {
        Polynomial a = new DensePolynomial("1");
        Polynomial b = new DensePolynomial("2x + 1");
        Polynomial c = new DensePolynomial("2124x^9 + 123123123x^5 - 321321x^3 + 425");
        Polynomial d = new DensePolynomial("789789789x^21532");
        Polynomial e = new DensePolynomial("33579574x^2153424 - 14x^21 - x");

        int[] dArr = new int[21533];
        dArr[21532] = 789789789;
        int[] gArr = new int[2153425];
        gArr[2153424] = 33579574;
        gArr[21] = -14;
        gArr[1] = -1;

        assertAll(
                () -> assertArrayEquals(new int[]{1}, ((DensePolynomial) a).getPolynomialArr()),
                () -> assertArrayEquals(new int[]{1, 2}, ((DensePolynomial) b).getPolynomialArr()),
                () -> assertArrayEquals(new int[]{425, 0, 0, -321321, 0, 123123123, 0, 0, 0, 2124}, ((DensePolynomial) c).getPolynomialArr()),
                () -> assertArrayEquals(dArr, ((DensePolynomial) d).getPolynomialArr()),
                () -> assertArrayEquals(gArr, ((DensePolynomial) e).getPolynomialArr()),
                () -> assertFalse(Arrays.equals(((DensePolynomial) a).getPolynomialArr(), ((DensePolynomial) b).getPolynomialArr())),
                () -> assertFalse(Arrays.equals(((DensePolynomial) c).getPolynomialArr(), ((DensePolynomial) e).getPolynomialArr()))
        );
    }

    @Test
    void testGetStringPolynomialArr() {
        Polynomial a = new DensePolynomial("1");
        Polynomial b = new DensePolynomial("2x + 1");
        Polynomial c = new DensePolynomial("2124x^9 + 123123123x^5 - 321321x^3 + 425");
        Polynomial d = new DensePolynomial("789789789x^21532");
        Polynomial e = new DensePolynomial("33579574x^2153424 - 14x^21 - x");

        String[] aArr = new String[]{"1","0"};
        String[] bArr = new String[]{"2","1","1","0"};
        String[] cArr = new String[]{"2124","9","123123123","5","-321321","3","425","0"};
        String[] dArr = new String[]{"789789789","21532"};
        String[] eArr = new String[]{"33579574","2153424","-14","21","-1","1"};

        assertAll(
                () -> assertArrayEquals(aArr, ((DensePolynomial) a).getStringPolynomialArr()),
                () -> assertArrayEquals(bArr, ((DensePolynomial) b).getStringPolynomialArr()),
                () -> assertArrayEquals(cArr, ((DensePolynomial) c).getStringPolynomialArr()),
                () -> assertArrayEquals(dArr, ((DensePolynomial) d).getStringPolynomialArr()),
                () -> assertArrayEquals(eArr, ((DensePolynomial) e).getStringPolynomialArr()),
                () -> assertFalse(Arrays.equals(((DensePolynomial) a).getStringPolynomialArr(), ((DensePolynomial) b).getStringPolynomialArr())),
                () -> assertFalse(Arrays.equals(((DensePolynomial) c).getStringPolynomialArr(), ((DensePolynomial) e).getStringPolynomialArr()))
        );
    }
}




