import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.NavigableMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class SparsePolynomialTest {

    @Test
    void testGetDegree() {
        Polynomial a = new SparsePolynomial("2x + 1");
        Polynomial b = new SparsePolynomial("3x^43 + 126x^27 - 238x^16 - x^5");
        Polynomial c = new SparsePolynomial("2x^123123123 + 123123123x^43 - 321321x^4");
        Polynomial d = new SparsePolynomial("123123123x^123321789 - 1324568x^43");
        Polynomial e = new SparsePolynomial("33579574x^863753 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");
        Polynomial f = new SparsePolynomial("33579574x^-1 - 17986434x^-23 + 67433427x^-215 - 123332x^-2268 - 1756x^-4322 + 125x^-34523 - 14x^-863753");
        Polynomial g = new SparsePolynomial("33579574x^-637864 - 17986434x^-832664 + 67433427x^-962664 - 123332x^-9999999 - 1756x^-98765433 + 125x^-99999999 - 14x^-999999999");

        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> { new SparsePolynomial("x^21474836479"); });
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> { new SparsePolynomial("x^-21474836479"); });
        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> { new SparsePolynomial("0x^123321789 - 1324568x^43"); });
        Exception exception4 = assertThrows(IllegalArgumentException.class, () -> { new SparsePolynomial("32x^-43 - 0x^-64367"); });
        Exception exception5 = assertThrows(IllegalArgumentException.class, () -> { new SparsePolynomial("99999999999999x^123321789 - 1324568x^43"); });
        Exception exception6 = assertThrows(IllegalArgumentException.class, () -> { new SparsePolynomial("99999999999999x^-7543 + 1324568x^-123421789"); });
        Exception exception7 = assertThrows(IllegalArgumentException.class, () -> { new SparsePolynomial(""); });

        assertAll(
                () -> assertEquals(1, a.degree()),
                () -> assertEquals(43, b.degree()),
                () -> assertEquals(123123123, c.degree()),
                () -> assertEquals(123321789, d.degree()),
                () -> assertEquals(863753, e.degree()),
                () -> assertEquals(-1, f.degree()),
                () -> assertEquals(-637864, g.degree()),
                () -> assertEquals("given number is out of boundary or canonical format is wrong", exception1.getMessage()),
                () -> assertEquals("given number is out of boundary or canonical format is wrong", exception2.getMessage()),
                () -> assertEquals("not form of canonical string representation", exception3.getMessage()),
                () -> assertEquals("not form of canonical string representation", exception4.getMessage()),
                () -> assertEquals("given number is out of boundary or canonical format is wrong", exception5.getMessage()),
                () -> assertEquals("given number is out of boundary or canonical format is wrong", exception6.getMessage()),
                () -> assertEquals("Empty string is not allowed", exception7.getMessage())
        );
    }

    @Test
    void testGetCoefficient() {
        Polynomial a = new SparsePolynomial("2x + 1");
        Polynomial b = new SparsePolynomial("3x^43 + 126x^27 - 238x^16 - x^5");
        Polynomial c = new SparsePolynomial("2x^123123123 + 123123123x^43 - 321321x^4");
        Polynomial d = new SparsePolynomial("123123123x^123321789 - 1324568x^43");
        Polynomial e = new SparsePolynomial("33579574x^863753 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");
        Polynomial f = new SparsePolynomial("33579574x^-1 - 17986434x^-23 + 67433427x^-215 - 123332x^-2268 - 1756x^-4322 + 125x^-34523 - 14x^-863753");
        Polynomial g = new SparsePolynomial("33579574x^999999999 - 17986434x^-832664 + 67433427x^-962664 - 123332x^-9999999 - 1756x^-98765433 + 125x^-99999999 - 14x^-999999999");

        assertAll(
                () -> assertEquals(2, a.getCoefficient(1)),
                () -> assertEquals(1, a.getCoefficient(0)),
                () -> assertEquals(-238, b.getCoefficient(16)),
                () -> assertEquals(0, b.getCoefficient(123123123)),
                () -> assertEquals(0, c.getCoefficient(-13456)),
                () -> assertEquals(123123123, d.getCoefficient(123321789)),
                () -> assertEquals(0, d.getCoefficient(-999999999)),
                () -> assertEquals(33579574, e.getCoefficient(863753)),
                () -> assertEquals(0, d.getCoefficient(0)),
                () -> assertEquals(33579574, f.getCoefficient(-1)),
                () -> assertEquals(-14, f.getCoefficient(-863753)),
                () -> assertEquals(0, f.getCoefficient(-863754)),
                () -> assertEquals(-14, g.getCoefficient(-999999999)),
                () -> assertEquals(33579574, g.getCoefficient(999999999))
        );
    }

    @Test
    void testIsZero() {
        Polynomial a = new SparsePolynomial("2x + 1");
        Polynomial b = new SparsePolynomial("3x^2 + x + 3");
        Polynomial c = a.add(a.minus());
        Polynomial d = b.subtract(b);
        Polynomial e = new SparsePolynomial("3x^-2 + x^-3");

        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> { new SparsePolynomial("0"); });
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> { new SparsePolynomial("-0"); });
        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> { new SparsePolynomial("0x^2 + 4"); });
        Exception exception4 = assertThrows(IllegalArgumentException.class, () -> { new SparsePolynomial("-4 + 0x^-2"); });
        Exception exception5 = assertThrows(IllegalArgumentException.class, () -> { new SparsePolynomial("000"); });
        Exception exception6 = assertThrows(IllegalArgumentException.class, () -> { new SparsePolynomial("0 - 0 + 0 - 0"); });
        Exception exception7 = assertThrows(IllegalArgumentException.class, () -> { new SparsePolynomial("x + 0"); });

        assertAll(
                () -> assertFalse((a.isZero())),
                () -> assertFalse((b.isZero())),
                () -> assertFalse((e.isZero())),
                () -> assertTrue((c.isZero())),
                () -> assertTrue((d.isZero())),
                () -> assertEquals("not form of canonical string representation", exception1.getMessage()),
                () -> assertEquals("not form of canonical string representation", exception2.getMessage()),
                () -> assertEquals("not form of canonical string representation", exception3.getMessage()),
                () -> assertEquals("not form of canonical string representation", exception4.getMessage()),
                () -> assertEquals("not form of canonical string representation", exception5.getMessage()),
                () -> assertEquals("not form of canonical string representation", exception6.getMessage()),
                () -> assertEquals("not form of canonical string representation", exception7.getMessage())
        );
    }

    @Test
    void testAdd() {
        Polynomial a = new SparsePolynomial("2x + 1");
        Polynomial b = new SparsePolynomial("3x^43 + 126x^27 - 238x^16 - x^5");
        Polynomial c = new SparsePolynomial("2124x^12312312 + 123123123x^43 - 321321x^4");
        Polynomial d = new SparsePolynomial("1231231x^12332178 - 1324568x^43");
        Polynomial e = new SparsePolynomial("33579574x^863753 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");
        Polynomial f = new SparsePolynomial("923456789x^-21 - 62342378x^-23 + 6433427x^-43 - 83432x^-232 - 9325x^-234 + 95123x^-75454 - 542x^-863753");
        Polynomial g = new SparsePolynomial("923456789x^863753 - 62342378x^75454 + 6433427x^-53 - 83432x^-59 - 9325x^-85 + 95123x^-100 - 542x^-16947");
        Polynomial h = new SparsePolynomial("923456789x^-5000 - 62342378x^-14532 + 6433427x^-39473 - 83432x^-49683 - 9325x^-124345 + 95123x^-1254332 - 542x^-4556733");
        Polynomial i = new SparsePolynomial("999999999x^863753 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");
        Polynomial j = new SparsePolynomial("923456789x^8637534 - 62342378x^75454 + 6433427x^234 - 83432x^232 - 9325x^85 + 95123x^-9999999 - 542x^-999999999");
        Polynomial k = new DensePolynomial("999999999x^863753 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");

        assertAll(
                () -> assertThrows(NullPointerException.class, () -> { a.add(null); }),
                () -> assertEquals(new SparsePolynomial("4x + 2"), ((SparsePolynomial) a.add(a))),
                () -> assertEquals(new SparsePolynomial("6x^43 + 252x^27 - 476x^16 - 2x^5"), (SparsePolynomial) b.add(b)),
                () -> assertEquals(new SparsePolynomial("1231231x^12332178 + 2124x^12312312 + 121798555x^43 - 321321x^4"), (SparsePolynomial) c.add(d)),
                () -> assertEquals(new SparsePolynomial("67159148x^863753 - 35972868x^75454 + 134866854x^234 - 246664x^232 - 3512x^43 + 250x^23 - 28x^21"), (SparsePolynomial) e.add(e)),
                () -> assertEquals(new SparsePolynomial("1846913578x^-21 - 124684756x^-23 + 12866854x^-43 - 166864x^-232 - 18650x^-234 + 190246x^-75454 - 1084x^-863753"), (SparsePolynomial) f.add(f)),
                () -> assertEquals(new SparsePolynomial("923456789x^863753 - 62342378x^75454 + 923456789x^-21 - 62342378x^-23 + 6433427x^-43 + 6433427x^-53 - 83432x^-59 - 9325x^-85 + 95123x^-100 - 83432x^-232 - 9325x^-234 - 542x^-16947 + 95123x^-75454 - 542x^-863753"), (SparsePolynomial) f.add(g)),
                () -> assertEquals(new SparsePolynomial("33579574x^863753 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21 + 923456789x^-5000 - 62342378x^-14532 + 6433427x^-39473 - 83432x^-49683 - 9325x^-124345 + 95123x^-1254332 - 542x^-4556733"), (SparsePolynomial) e.add(h)),
                () -> assertEquals(new SparsePolynomial("1999999998x^863753 - 35972868x^75454 + 134866854x^234 - 246664x^232 - 3512x^43 + 250x^23 - 28x^21"), (SparsePolynomial) i.add(i)),
                () -> assertEquals(new SparsePolynomial("923456789x^8637534 + 999999999x^863753 - 80328812x^75454 + 73866854x^234 - 206764x^232 - 9325x^85 - 1756x^43 + 125x^23 - 14x^21 + 95123x^-9999999 - 542x^-999999999"), (SparsePolynomial) j.add(i)),
                () -> assertEquals(new SparsePolynomial("923456789x^8637534 + 999999999x^863753 - 80328812x^75454 + 73866854x^234 - 206764x^232 - 9325x^85 - 1756x^43 + 125x^23 - 14x^21 + 95123x^-9999999 - 542x^-999999999"), (SparsePolynomial) j.add(k)),
                () -> assertEquals(new SparsePolynomial("1999999998x^863753 - 35972868x^75454 + 134866854x^234 - 246664x^232 - 3512x^43 + 250x^23 - 28x^21"), (SparsePolynomial) i.add(k)),
                () -> assertTrue(i.add(i.minus()).isZero()),
                () -> assertTrue(j.add(j.minus()).isZero()),
                () -> assertTrue(i.add(i.minus()).add(i.add(i.minus())).isZero()),
                () -> assertTrue(i.add(i.minus()).add(k.add(k.minus())).isZero())
                );
    }

    @Test
    void testMultiply() {
        Polynomial a = new SparsePolynomial("2x + 1");
        Polynomial b = new SparsePolynomial("4x^2 + 4x + 1");
        Polynomial c = new SparsePolynomial("3x^43 + 126x^27 - 238x^16 - x^5");
        Polynomial d = new SparsePolynomial("2124x^112313 + 123123123x^43 - 321321x^4 + 3 - x^-43 - 2124x^-112313");
        Polynomial e = new SparsePolynomial("123123123x^1578 - 1324568x^-43");
        Polynomial f = new SparsePolynomial("43254x^-43 - 1324567x^-1578");
        Polynomial g = new SparsePolynomial("43254x^1578 - 1324567x^53 + 6342341");
        Polynomial h = new SparsePolynomial("-999999999x^-5333 - 1324567x^-157813");
        Polynomial i = new DensePolynomial("4325x^64356 + 6342341");
        Polynomial j = new DensePolynomial("99999999x^1578 + 6342341");

        assertAll(
                () -> assertThrows(NullPointerException.class, () -> { a.multiply(null); }),
                () -> assertEquals(new SparsePolynomial("8x^3 + 12x^2 + 6x + 1"), ((SparsePolynomial) a.multiply(b))),
                () -> assertEquals(new SparsePolynomial("6372x^112356 + 267624x^112340 - 505512x^112329 - 2124x^112318 + 369369369x^86 - 1666355686x^70 + 761467798x^59 - 123123123x^48 - 963963x^47 + 9x^43 - 40486446x^31 + 378x^27 + 76474398x^20 - 714x^16 + 321321x^9 - 3x^5 - 3 - 126x^-16 + 238x^-27 + x^-38 - 6372x^-112270 - 267624x^-112286 + 505512x^-112297 + 2124x^-112308"), ((SparsePolynomial) c.multiply(d))),
                () -> assertEquals(new SparsePolynomial("- 479491804x^113891 + 1481584864x^112270 + 1597676329x^1621 - 1101242027x^1582 + 369369369x^1578 - 123123123x^1535 - 745589448 + 409752024x^-39 - 3973704x^-43 + 1324568x^-86 + 479491804x^-110735 - 1481584864x^-112356"), ((SparsePolynomial) d.multiply(e))),
                () -> assertEquals(new SparsePolynomial("- 191884798x^1535 - 622466325 - 1458289424x^-86 + 2132405288x^-1621"), ((SparsePolynomial) e.multiply(f))),
                () -> assertEquals(new SparsePolynomial("1870908516x^1535 - 1458246170x^10 - 1458246170 - 546289330x^-43 + 2131080721x^-1525 + 100439629x^-1578"), ((SparsePolynomial) f.multiply(g))),
                () -> assertEquals(new SparsePolynomial("32071397x^59023 - 1442360123x^-5333 - 1433784979x^-93457 + 100439629x^-157813"), ((SparsePolynomial) h.multiply(i))),
                () -> assertEquals(new SparsePolynomial("- 469325057x^-3755 - 1442360123x^-5333 + 92733207x^-156235 + 100439629x^-157813"), ((SparsePolynomial) h.multiply(j))),
                () -> assertEquals(new SparsePolynomial("615681270x^-5376 - 915410967x^-6911 - 1458246170x^-157856 + 2131080721x^-159391"), ((SparsePolynomial) f.multiply(h))),
                () -> assertEquals(new SparsePolynomial("- 469325057x^-3755 - 1442360123x^-5333 + 92733207x^-156235 + 100439629x^-157813"), ((SparsePolynomial) h.multiply(j))),
                () -> assertEquals(new SparsePolynomial("1946600372x^113891 + 586230396x^112313 - 1851089587x^1621 - 1449337303x^1582 + 299999997x^1578 - 99999999x^1535 - 647871297x^43 - 2112854157x^4 + 19027023 - 6342341x^-43 - 1946600372x^-110735 - 586230396x^-112313"), ((SparsePolynomial) d.multiply(j))),
                () -> assertTrue(a.add(a.minus()).multiply(a).isZero()),
                () -> assertTrue(a.add(a.minus()).multiply(i).isZero()),
                () -> assertTrue(a.add(a.minus()).multiply(a.add(a.minus())).isZero()),
                () -> assertTrue(a.add(a.minus()).multiply(i.add(i.minus())).isZero())
        );
    }

    @Test
    void testSubtract() {
        Polynomial a = new SparsePolynomial("2x + 1");
        Polynomial b = new SparsePolynomial("3x^43 + 126x^27 - 238x^16 - x^5 + 3x + 4");
        Polynomial c = new SparsePolynomial("2124x^12312312 + 123123123x^43 - 321321x^4");
        Polynomial d = new SparsePolynomial("1231231x^12332178 + 123123123x^43");
        Polynomial e = new SparsePolynomial("33579574x^863753 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");
        Polynomial f = new SparsePolynomial("923456789x^-21 - 62342378x^-23 + 6433427x^-43 - 83432x^-232 - 9325x^-234 + 95123x^-75454 - 542x^-863753");
        Polynomial g = new SparsePolynomial("923456789x^863753 - 62342378x^75454 + 6433427x^-53 - 83432x^-59 - 9325x^-85 + 95123x^-100 - 542x^-16947");
        Polynomial h = new SparsePolynomial("923456789x^-5000 - 62342378x^-14532 + 6433427x^-39473 - 83432x^-49683 - 9325x^-124345 + 95123x^-1254332 - 542x^-4556733");
        Polynomial i = new SparsePolynomial("999999999x^863753 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");
        Polynomial j = new SparsePolynomial("923456789x^8637534 - 62342378x^75454 + 6433427x^234 - 83432x^232 - 9325x^85 + 95123x^-9999999 - 542x^-999999999");
        Polynomial k = new DensePolynomial("-999999999x^863753 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");

        assertAll(
                () -> assertThrows(NullPointerException.class, () -> { a.subtract(null); }),
                () -> assertEquals(new SparsePolynomial("- 3x^43 - 126x^27 + 238x^16 + x^5 - x - 3"), ((SparsePolynomial) a.subtract(b))),
                () -> assertEquals(new SparsePolynomial("3x^43 + 126x^27 - 238x^16 - x^5 + x + 3"), ((SparsePolynomial) b.subtract(a))),
                () -> assertEquals(new SparsePolynomial("- 1231231x^12332178 + 2124x^12312312 - 321321x^4"), (SparsePolynomial) c.subtract(d)),
                () -> assertEquals(new SparsePolynomial("33579574x^863753 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21 - 923456789x^-21 + 62342378x^-23 - 6433427x^-43 + 83432x^-232 + 9325x^-234 - 95123x^-75454 + 542x^-863753"), (SparsePolynomial) e.subtract(f)),
                () -> assertEquals(new SparsePolynomial("- 923456789x^863753 + 62342378x^75454 - 6433427x^-53 + 83432x^-59 + 9325x^-85 - 95123x^-100 + 923456789x^-5000 - 62342378x^-14532 + 542x^-16947 + 6433427x^-39473 - 83432x^-49683 - 9325x^-124345 + 95123x^-1254332 - 542x^-4556733"), (SparsePolynomial) h.subtract(g)),
                () -> assertEquals(new SparsePolynomial("923456789x^8637534 - 999999999x^863753 - 44355944x^75454 - 61000000x^234 + 39900x^232 - 9325x^85 + 1756x^43 - 125x^23 + 14x^21 + 95123x^-9999999 - 542x^-999999999"), (SparsePolynomial) j.subtract(i)),
                () -> assertEquals(new SparsePolynomial("923456789x^8637534 + 999999999x^863753 - 44355944x^75454 - 61000000x^234 + 39900x^232 - 9325x^85 + 1756x^43 - 125x^23 + 14x^21 + 95123x^-9999999 - 542x^-999999999"), (SparsePolynomial) j.subtract(k)),
                () -> assertEquals(new SparsePolynomial("1999999998x^863753"), (SparsePolynomial) i.subtract(k)),
                () -> assertEquals(new SparsePolynomial("- 923456789x^8637534 + 999999999x^863753 + 44355944x^75454 + 61000000x^234 - 39900x^232 + 9325x^85 - 1756x^43 + 125x^23 - 14x^21 - 95123x^-9999999 + 542x^-999999999"), (SparsePolynomial) i.subtract(j)),
                () -> assertEquals(new SparsePolynomial("- 999999999x^863753 + 17986434x^75454 - 67433427x^234 + 123332x^232 + 1756x^43 - 125x^23 + 14x^21 + 923456789x^-5000 - 62342378x^-14532 + 6433427x^-39473 - 83432x^-49683 - 9325x^-124345 + 95123x^-1254332 - 542x^-4556733"), (SparsePolynomial) h.subtract(i)),
                () -> assertTrue(j.subtract(j).isZero()),
                () -> assertTrue(i.subtract(i).isZero()),
                () -> assertTrue(i.add(i.minus()).add(i.add(i.minus())).isZero()),
                () -> assertTrue(i.add(i.minus()).add(k.add(k.minus())).isZero())
        );
    }

    @Test
    void testMinus() {
        Polynomial a = new SparsePolynomial("2x + 1");
        Polynomial b = new SparsePolynomial("3x^43 + 126x^27 - 238x^16 - x^5 + 3x + 4");
        Polynomial c = new SparsePolynomial("2124x^12312312 + 123123123x^43 - 321321x^4");
        Polynomial d = new SparsePolynomial("1231231x^12332178 + 123123123x^43");
        Polynomial e = new SparsePolynomial("33579574x^863753 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");
        Polynomial f = new SparsePolynomial("923456789x^-21 - 62342378x^-23 + 6433427x^-43 - 83432x^-232 - 9325x^-234 + 95123x^-75454 - 542x^-863753");
        Polynomial g = new SparsePolynomial("923456789x^863753 - 62342378x^75454 + 6433427x^-53 - 83432x^-59 - 9325x^-85 + 95123x^-100 - 542x^-16947");
        Polynomial h = new SparsePolynomial("923456789x^-5000 - 62342378x^-14532 + 6433427x^-39473 - 83432x^-49683 - 9325x^-124345 + 95123x^-1254332 - 542x^-4556733");
        Polynomial i = new SparsePolynomial("999999999x^863753 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");
        Polynomial j = new SparsePolynomial("999999x^999999999 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");
        Polynomial k = new SparsePolynomial("999999999x^999999999 - 999999999x^-999999999");

        assertAll(
                () -> assertEquals(new SparsePolynomial("- 2x - 1"), (a.minus())),
                () -> assertEquals(new SparsePolynomial("- 3x^43 - 126x^27 + 238x^16 + x^5 - 3x - 4"), b.minus()),
                () -> assertEquals(new SparsePolynomial("- 2124x^12312312 - 123123123x^43 + 321321x^4"), c.minus()),
                () -> assertEquals(new SparsePolynomial("- 1231231x^12332178 - 123123123x^43"), d.minus()),
                () -> assertEquals(new SparsePolynomial("- 33579574x^863753 + 17986434x^75454 - 67433427x^234 + 123332x^232 + 1756x^43 - 125x^23 + 14x^21"), e.minus()),
                () -> assertEquals(new SparsePolynomial("- 923456789x^-21 + 62342378x^-23 - 6433427x^-43 + 83432x^-232 + 9325x^-234 - 95123x^-75454 + 542x^-863753"), f.minus()),
                () -> assertEquals(new SparsePolynomial("- 923456789x^863753 + 62342378x^75454 - 6433427x^-53 + 83432x^-59 + 9325x^-85 - 95123x^-100 + 542x^-16947"), g.minus()),
                () -> assertEquals(new SparsePolynomial("- 923456789x^-5000 + 62342378x^-14532 - 6433427x^-39473 + 83432x^-49683 + 9325x^-124345 - 95123x^-1254332 + 542x^-4556733"), h.minus()),
                () -> assertEquals(new SparsePolynomial("- 999999999x^863753 + 17986434x^75454 - 67433427x^234 + 123332x^232 + 1756x^43 - 125x^23 + 14x^21"), i.minus()),
                () -> assertEquals(new SparsePolynomial("- 999999x^999999999 + 17986434x^75454 - 67433427x^234 + 123332x^232 + 1756x^43 - 125x^23 + 14x^21"), j.minus()),
                () -> assertEquals(new SparsePolynomial("- 999999999x^999999999 + 999999999x^-999999999"), k.minus()),
                () -> assertTrue(j.subtract(j).minus().isZero()),
                () -> assertTrue(i.add(i.minus()).isZero())
        );
    }

    @Test
    void testWellFormed() {
        Polynomial a = new SparsePolynomial("2x + 1");
        Polynomial b = new SparsePolynomial("3x^43 + 126x^27 - 238x^16 - x^5 + 3x + 4");
        Polynomial c = new SparsePolynomial("2124x^12312312 + 123123123x^43 - 321321x^4");
        Polynomial d = new SparsePolynomial("1231231x^12332178 + 123123123x^43");
        Polynomial e = new SparsePolynomial("33579574x^863753 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");
        Polynomial f = new SparsePolynomial("923456789x^-21 - 62342378x^-23 + 6433427x^-43 - 83432x^-232 - 9325x^-234 + 95123x^-75454 - 542x^-863753");
        Polynomial g = new SparsePolynomial("923456789x^863753 - 62342378x^75454 + 6433427x^-53 - 83432x^-59 - 9325x^-85 + 95123x^-100 - 542x^-16947");
        Polynomial h = new SparsePolynomial("923456789x^-5000 - 62342378x^-14532 + 6433427x^-39473 - 83432x^-49683 - 9325x^-124345 + 95123x^-1254332 - 542x^-4556733");

        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> { new SparsePolynomial("x^1.0"); });
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> { new SparsePolynomial("x^1/4"); });
        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> { new SparsePolynomial("1231231/2x^12332178 + 123123123x^43"); });
        Exception exception4 = assertThrows(IllegalArgumentException.class, () -> { new SparsePolynomial("923456789x^-21 - 62342378x^-23 + 6433427x^-43.0 - 83432x^-232 - 9325x^-234 + 95123x^-75454 - 542x^-863753"); });
        Exception exception5 = assertThrows(IllegalArgumentException.class, () -> { new SparsePolynomial("923456789x^-5000 - 62342378x^-14532 + 6433427x^-39473 - 83432x^-49683 - 9325x^-124345 + 95123x^-1254332 - 542x^-4556733.0"); });

        assertAll(
                () -> assertTrue(a.wellFormed()),
                () -> assertTrue(b.wellFormed()),
                () -> assertTrue(c.wellFormed()),
                () -> assertTrue(d.wellFormed()),
                () -> assertTrue(e.wellFormed()),
                () -> assertTrue(f.wellFormed()),
                () -> assertTrue(g.wellFormed()),
                () -> assertTrue(h.wellFormed()),
                () -> assertEquals("coefficients and degrees must always be integer and integer format", exception1.getMessage()),
                () -> assertEquals("coefficients and degrees must always be integer and integer format", exception2.getMessage()),
                () -> assertEquals("coefficients and degrees must always be integer and integer format", exception3.getMessage()),
                () -> assertEquals("coefficients and degrees must always be integer and integer format", exception4.getMessage()),
                () -> assertEquals("coefficients and degrees must always be integer and integer format", exception5.getMessage())
        );

        ((SparsePolynomial) a).getStringPolynomialArr()[0] = "2.00";
        ((SparsePolynomial) b).getStringPolynomialArr()[1] = "43.643";
        ((SparsePolynomial) c).getStringPolynomialArr()[4] = "-321321.0.00";
        ((SparsePolynomial) d).getStringPolynomialArr()[2] = "12332178/1";
        ((SparsePolynomial) e).getStringPolynomialArr()[13] = "21.";
        ((SparsePolynomial) f).getStringPolynomialArr()[8] = "-9325.";

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
    void testToString() {
        Polynomial a = new SparsePolynomial("2x + 1");
        Polynomial b = new SparsePolynomial("3x^43 + 126x^27 - 238x^16 - x^5 + 3x + 4");
        Polynomial c = new SparsePolynomial("2124x^12312312 + 123123123x^43 - 321321x^4");
        Polynomial d = new SparsePolynomial("1231231x^12332178 + 123123123x^43");
        Polynomial e = new SparsePolynomial("33579574x^863753 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");
        Polynomial f = new SparsePolynomial("923456789x^-21 - 62342378x^-23 + 6433427x^-43 - 83432x^-232 - 9325x^-234 + 95123x^-75454 - 542x^-863753");
        Polynomial g = new SparsePolynomial("923456789x^863753 - 62342378x^75454 + 6433427x^-53 - 83432x^-59 - 9325x^-85 + 95123x^-100 - 542x^-16947");
        Polynomial h = new SparsePolynomial("923456789x^-5000 - 62342378x^-14532 + 6433427x^-39473 - 83432x^-49683 - 9325x^-124345 + 95123x^-1254332 - 542x^-4556733");
        Polynomial i = new SparsePolynomial("999999999x^863753 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");
        Polynomial j = new SparsePolynomial("999999x^999999999 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");
        Polynomial k = new SparsePolynomial("999999999x^999999999 - 999999999x^-999999999");

        assertAll(
                () -> assertEquals("2x + 1", (a.toString())),
                () -> assertEquals("3x^43 + 126x^27 - 238x^16 - x^5 + 3x + 4", b.toString()),
                () -> assertEquals("2124x^12312312 + 123123123x^43 - 321321x^4", c.toString()),
                () -> assertEquals("1231231x^12332178 + 123123123x^43", d.toString()),
                () -> assertEquals("33579574x^863753 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21", e.toString()),
                () -> assertEquals("923456789x^-21 - 62342378x^-23 + 6433427x^-43 - 83432x^-232 - 9325x^-234 + 95123x^-75454 - 542x^-863753", f.toString()),
                () -> assertEquals("923456789x^863753 - 62342378x^75454 + 6433427x^-53 - 83432x^-59 - 9325x^-85 + 95123x^-100 - 542x^-16947", g.toString()),
                () -> assertEquals("923456789x^-5000 - 62342378x^-14532 + 6433427x^-39473 - 83432x^-49683 - 9325x^-124345 + 95123x^-1254332 - 542x^-4556733", h.toString()),
                () -> assertEquals("999999999x^863753 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21", i.toString()),
                () -> assertEquals("999999x^999999999 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21", j.toString()),
                () -> assertEquals("999999999x^999999999 - 999999999x^-999999999", k.toString()),
                () -> assertEquals("0", k.add(k.minus()).toString()),
                () -> assertEquals("0", k.subtract(k).toString())
        );
    }

    @Test
    void testEqual() {
        Polynomial a = new SparsePolynomial("3x^43 + 126x^27 - 238x^16 - x^5");
        Polynomial b = new SparsePolynomial("2124x^43 + 123123123x^16 - 321321x^4");
        Polynomial c = new SparsePolynomial("2122x^43 + 123123123x^16 - 321321x^4");
        Polynomial d = new SparsePolynomial("33579574x^1233217 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");
        Polynomial e = new SparsePolynomial("-923456789x^863753 - 62342378x^75454 + 6433427x^234 - 83432x^232 - 9325x^85 + 95123x^59 - 542x^53");
        Polynomial f = new SparsePolynomial("33579574x^1233217 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21 - x");
        Polynomial g = new DensePolynomial("33579574x^1233217 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21 - x");
        Polynomial h = new SparsePolynomial("999999x^999999999 + 17986434x^75454 - 67433427x^234 + 123332x^232 + 1756x^43 - 125x^23 + 14x^21");
        Polynomial i = new SparsePolynomial("- 999999x^999999999 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");
        Polynomial j = new SparsePolynomial("- x^-1 - 14x^-21 + 125x^-23 - 1756x^-43 - 123332x^-232 + 67433427x^-234 - 17986434x^-75454 + 33579574x^-1233217");

        assertAll(
                () -> assertFalse(a.equals(null)),
                () -> assertTrue(a.equals(a)),
                () -> assertFalse(b.equals(c)),
                () -> assertTrue(h.equals(i.minus())),
                () -> assertFalse(a.equals("Hello")),
                () -> assertFalse(a.equals(99999)),
                () -> assertTrue(new SparsePolynomial("2x^43").equals(b.subtract(c))),
                () -> assertTrue(d.subtract(d).equals(e.add(e.minus()))),
                () -> assertTrue(d.subtract(f).equals(new SparsePolynomial("x"))),
                () -> assertFalse(f.equals(g)),
                () -> assertFalse(j.equals(g))
        );
    }

    @Test
    void testGetPolynomialMap() {
        Polynomial a = new SparsePolynomial("3x^43 + 126x^27 - 238x^16 - x^5");
        Polynomial b = new SparsePolynomial("2124x^43 + 123123123x^16 - 321321x^4");
        Polynomial c = new SparsePolynomial("2122x^43 + 123123123x^16 - 321321x^4");
        Polynomial d = new SparsePolynomial("33579574x^1233217 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");
        Polynomial e = new SparsePolynomial("33579574x^1233217 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");
        Polynomial f = new SparsePolynomial("33579574x^1233217 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21 - x");
        Polynomial h = new SparsePolynomial("999999x^999999999 + 17986434x^75454 - 67433427x^234 + 123332x^232 + 1756x^43 - 125x^23 + 14x^21");
        Polynomial i = new SparsePolynomial("- 999999x^999999999 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");
        Polynomial j = new SparsePolynomial("- x^-1 - 14x^-21 + 125x^-23 - 1756x^-43 - 123332x^-232 + 67433427x^-234 - 17986434x^-75454 + 33579574x^-1233217");

        TreeMap<Integer, Integer> bTreeMap = new TreeMap<Integer, Integer>();
        bTreeMap.put(43, 2124);
        bTreeMap.put(16, 123123123);
        bTreeMap.put(4, -321321);
        NavigableMap bMap = bTreeMap.descendingMap();
        TreeMap<Integer, Integer> iTreeMap = new TreeMap<Integer, Integer>();
        iTreeMap.put(999999999, -999999);
        iTreeMap.put(75454, -17986434);
        iTreeMap.put(234, 67433427);
        iTreeMap.put(232, -123332);
        iTreeMap.put(43, -1756);
        iTreeMap.put(23, 125);
        iTreeMap.put(21, -14);
        NavigableMap iMap = iTreeMap.descendingMap();

        assertAll(
                () -> assertFalse(((SparsePolynomial) a).getPolynomialMap().equals(((SparsePolynomial) b).getPolynomialMap())),
                () -> assertFalse(((SparsePolynomial) b).getPolynomialMap().equals(((SparsePolynomial) c).getPolynomialMap())),
                () -> assertFalse(((SparsePolynomial) f).getPolynomialMap().equals(((SparsePolynomial) h).getPolynomialMap())),
                () -> assertTrue(((SparsePolynomial) d).getPolynomialMap().equals(((SparsePolynomial) e).getPolynomialMap())),
                () -> assertTrue(((SparsePolynomial) b).getPolynomialMap().equals(bMap)),
                () -> assertTrue(((SparsePolynomial) i).getPolynomialMap().equals(iMap)),
                () -> assertFalse(((SparsePolynomial) j).getPolynomialMap().equals(bMap))
        );
    }

    @Test
    void testGetStringPolynomialArr() {
        Polynomial a = new SparsePolynomial("3x^43 + 126x^27 - 238x^16 - x^5");
        Polynomial b = new SparsePolynomial("2124x^43 + 123123123x^16 - 321321x^4");
        Polynomial c = new SparsePolynomial("2122x^-52 + 123123123x^-256 - 321321x^-863");
        Polynomial d = new SparsePolynomial("33579574x^1233217 - 17986434x^75454 + 67433427x^234 - 123332x^232 - 1756x^43 + 125x^23 - 14x^21");
        Polynomial e = new SparsePolynomial("-999999x^999999999 - 17986434x^75454");
        Polynomial f = new SparsePolynomial("2369x^-9999999 - 17986434x^-99975454");

        String[] aStringArr = new String[]{"3","43","126","27","-238","16","-1","5"};
        String[] bStringArr = new String[]{"2124","43","123123123","16","-321321","4"};
        String[] cStringArr = new String[]{"2122","-52","123123123","-256","-321321","-863"};
        String[] dStringArr = new String[]{"33579574","1233217","-17986434","75454","67433427","234","-123332","232","-1756","43","125","23","-14","21"};
        String[] eStringArr = new String[]{"-999999","999999999","-17986434","75454"};
        String[] fStringArr = new String[]{"2369","-9999999","-17986434","-99975454"};
        assertAll(
                () -> assertArrayEquals(((SparsePolynomial) a).getStringPolynomialArr(),aStringArr),
                () -> assertArrayEquals(((SparsePolynomial) b).getStringPolynomialArr(),bStringArr),
                () -> assertArrayEquals(((SparsePolynomial) c).getStringPolynomialArr(),cStringArr),
                () -> assertArrayEquals(((SparsePolynomial) d).getStringPolynomialArr(),dStringArr),
                () -> assertArrayEquals(((SparsePolynomial) e).getStringPolynomialArr(),eStringArr),
                () -> assertArrayEquals(((SparsePolynomial) f).getStringPolynomialArr(),fStringArr),
                () -> assertFalse(Arrays.equals(((SparsePolynomial) f).getStringPolynomialArr(),eStringArr)),
                () -> assertFalse(Arrays.equals(((SparsePolynomial) a).getStringPolynomialArr(),fStringArr))

        );
    }
}