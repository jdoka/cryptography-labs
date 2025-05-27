package ru.cryptography.labs;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Signature {
    static long p = 11117;
    static BigInteger pp = BigInteger.valueOf(p);
    static long a = 338;
    static long b = 157;
    static long q = 5479;

    public static void main(String[] args) {
        Point P = findP();
        System.out.println(P);// Point{x=3085, y=5327}

    }

    /**
     * Находит и возвращает точку P такую что P != 0 что Pq = 0
     */
    static Point findP() {
        while (true) {
            Point P = getRandomPoint();
            if (order(P) == q) {
                return P;
            }
        }
    }

    /**
     * Генерирует случайную точку на эллиптической кривой, заданной константами p, a и b
     */
    static Point getRandomPoint() {
        SecureRandom random = new SecureRandom();
        while (true) {
            long x = mod(random.nextLong());
            long y2 = mod(mod(x * x) * x) + mod(a * x) + b;
            y2 = mod(y2);
            for (int y = 2; y < p; y++) {
                BigInteger yB = BigInteger.valueOf(y);
                BigInteger pow = yB.modPow(BigInteger.TWO, pp);
                if (pow.longValue() == y2) {
                    return new Point(x, y);
                }
            }
        }
    }

    /**
     * Возвращает порядок точки, т.е. число, на которое нужно умножить точку, чтобы получить 0
     */
    static long order(Point Point) {
        Point sum = Point;
        Point zero = new Point(0, 0);
        long i;
        for (i = 0; i <= p; i++) {
            if (sum.equals(zero)) {
                return i + 1;
            }
            sum = sum(sum, Point);
        }
        return i;
    }

    /**
     * Складывает две точки и возвращает результат
     */
    static Point sum(Point p, Point q) {
        if (isMinusP(p, q)) {// условие когда p = -q
            return new Point(0, 0);
        }
        long x1 = p.x.longValue();
        long x2 = q.x.longValue();
        long y1 = p.y.longValue();
        long y2 = q.y.longValue();
        if (p.equals(q)) { // условие когда p = q
            long chX = mod(3 * x1 * x1) + a;
            chX = mod(chX);
            long znX = reciprocal(2 * y1);
            long n = chX * znX;
            n = mod(n);

            long x = mod(n * n) - mod(2 * x1);
            x = mod(x);
            long y = mod(n * mod(x1 - x)) - y1;
            y = mod(y);
            return new Point(x, y);

        } else { // условие когда p != q
            long ch = mod(y2 - y1);
            long zn = reciprocal(x2 - x1);
            long n = ch * zn;
            n = mod(n);

            long x = mod(n * n) - x1 - x2;
            x = mod(x);
            long y = mod(n * mod(x1 - x)) - y1;
            y = mod(y);
            return new Point(x, y);
        }
    }

    /**
     * Возвращает значение числа по модулю p
     */
    static long mod(long n) {
        BigInteger nn = BigInteger.valueOf(n);
        return nn.mod(pp).longValue();
    }

    /**
     * Является ли minusP=-p
     */
    static boolean isMinusP(Point p, Point minusP) {
        return (mod(p.y.longValue() + minusP.y.longValue()) == 0) && (p.x.longValue() == minusP.x.longValue());
    }

    /**
     * Возвращает обратный элемент по модулю p
     */
    static long reciprocal(long n) {
        if (n == 0) {
            return 0;
        }
        BigInteger nn = BigInteger.valueOf(n);
        BigInteger r = nn.modInverse(pp);
        return r.longValue();
    }
}
