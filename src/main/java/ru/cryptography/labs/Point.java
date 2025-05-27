package ru.cryptography.labs;

import java.math.BigInteger;
import java.util.Objects;

import static ru.cryptography.labs.Signature.pp;

/**
 * Класс, представляющий точку эллиптической кривой
 */
public class Point {
    BigInteger x;
    BigInteger y;

    Point(long x, long y) {
        this(BigInteger.valueOf(x), BigInteger.valueOf(y));
    }

    Point(BigInteger x, BigInteger y) {
        this.x = x.mod(pp);
        this.y = y.mod(pp);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point Point = (Point) o;
        return Objects.equals(x, Point.x) && Objects.equals(y, Point.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
