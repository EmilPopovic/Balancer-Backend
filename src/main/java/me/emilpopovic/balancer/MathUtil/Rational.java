package me.emilpopovic.balancer.MathUtil;

public class Rational extends Number implements Comparable<Rational> {

    public static final Rational ZERO = new Rational(0);
    public static final Rational ONE = new Rational(1);

    private final long numerator;
    private final long denominator;

    //region Getters

    public long getNumerator() {
        return numerator;
    }

    public long getDenominator() {
        return denominator;
    }

    //endregion

    //region Constructors

    public Rational(int n) {
        this(n, 1);
    }

    public Rational(int numerator, int denominator) {
        this((long) numerator, denominator);
    }

    public Rational(long n) {
        this(n, 1L);
    }

    public Rational(long numerator, long denominator) {
        if (denominator == 0) { throw new ArithmeticException("Denominator cannot be zero"); }

        long gcd = gcd(Math.abs(numerator), Math.abs(denominator));

        if(denominator < 0) {
            numerator *= -1;
            denominator *= -1;
        }

        this.numerator = numerator / gcd;
        this.denominator = denominator / gcd;
    }

    //endregion

    //region Factories

    public static Rational parseRational(String s) throws NumberFormatException {
        if (!s.contains("/")) {
            return new Rational(Long.parseLong(s));
        } else {
            String[] parts = s.split("/");
            if (parts.length != 2) {
                throw new NumberFormatException();
            }

            return new Rational(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
        }
    }

    //endregion

    //region Operations
    public Rational negate() {
        return Rational.negate(this);
    }

    public static Rational negate(Rational f) {
        if (f == null) { throw new IllegalArgumentException("Fraction cannot be null"); }

        return new Rational(-f.numerator, f.denominator);
    }

    public Rational invert() {
        return Rational.invert(this);
    }

    public static Rational invert(Rational f) {
        if (f == null) { throw new IllegalArgumentException("Fraction cannot be null"); }

        return new Rational(f.denominator, f.numerator);
    }

    public Rational add(Rational other) {
        return Rational.add(this, other);
    }

    public static Rational add(Rational a, Rational b) {
        if (a == null || b == null) { throw new IllegalArgumentException("Fraction cannot be null"); }

        return new Rational(a.numerator * b.denominator + a.denominator * b.numerator, a.denominator * b.denominator);
    }

    public Rational add(long n) {
        return add(new Rational(n));
    }

    public Rational add(int n) {
        return add((long) n);
    }

    public Rational subtract(Rational other) {
        return Rational.subtract(this, other);
    }

    public static Rational subtract(Rational a, Rational b) {
        return Rational.add(a, b.negate());
    }

    public Rational multiply(Rational other) {
        return Rational.multiply(this, other);
    }

    public static Rational multiply(Rational a, Rational b) {
        if (a == null || b == null) { throw new IllegalArgumentException("Fraction cannot be null"); }

        return new Rational(a.numerator * b.numerator, a.denominator * b.denominator);
    }

    public Rational multiply(long n) {
        return multiply(new Rational(n));
    }

    public Rational multiply(int n) {
        return multiply((long) n);
    }

    public Rational divide(Rational other) {
        return Rational.divide(this, other);
    }

    public static Rational divide(Rational a, Rational b) {
        return Rational.multiply(a, b.invert());
    }

    public Rational pow(int p) {
        return Rational.pow(this, p);
    }

    public static Rational pow(Rational f, int p) {
        if (f == null) { throw new IllegalArgumentException("Fraction cannot be null"); }

        return new Rational((long) Math.pow(f.numerator, p), (long) Math.pow(f.denominator, p));
    }

    //endregion

    //region typeValue overrides

    @Override
    public int intValue() {
        return (int) longValue();
    }

    @Override
    public long longValue() {
        return numerator / denominator;
    }

    @Override
    public float floatValue() {
        return (float) doubleValue();
    }

    @Override
    public double doubleValue() {
        return (double) numerator / denominator;
    }

    //endregion

    //region Other

    private static long gcd(long a, long b) {
        if (b == 0) { return a; }
        return gcd(b,a % b);
    }

    @Override
    public int compareTo(Rational o) {
        long top = numerator * o.denominator;
        long bottom = denominator * o.numerator;

        if (top > bottom) {
            return 1;
        } else if (top < bottom) {
            return -1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Rational f) {
            return compareTo(f) == 0;
        } else if (obj instanceof Number n) {
            return doubleValue() - n.doubleValue() < 1e-8;
        }
        return false;
    }

    @Override
    public String toString() {
        if (denominator == 1) {
            return String.format("%d", numerator);
        }
        return String.format("%d/%d", numerator, denominator);
    }

    //endregion

    //region Test & Example

    public static void main(String[] args) {
        Rational r1 = Rational.parseRational("2/-3");
        Rational r2 = new Rational(5, 3);
        dump("r1", r1);
        dump("r2", r2);
        dump("r1 + r2", r1.add(r2));
        dump("r1 - r2", r1.subtract(r2));
        dump("r1 * r2", r1.multiply(r2));
        dump("r1 / r2", r1.divide(r2));
        dump("r2 ^ 2", r2.pow(2));
    }

    public static void dump(String name, Rational r) {
        System.out.printf("%s = %s%n", name, r);
        System.out.printf("%s.negate() = %s%n", name, r.negate());
        System.out.printf("%s.invert() = %s%n", name, r.invert());
        System.out.printf("%s.intValue() = %,d%n", name, r.intValue());
        System.out.printf("%s.longValue() = %,d%n", name, r.longValue());
        System.out.printf("%s.floatValue() = %,f%n", name, r.floatValue());
        System.out.printf("%s.doubleValue() = %,f%n", name, r.doubleValue());
        System.out.println();
    }

    //endregion
}
