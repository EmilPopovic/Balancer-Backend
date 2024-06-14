package me.emilpopovic.balancer.MathUtil;

import java.util.LinkedList;
import java.util.List;

public class RationalMatrix implements Matrix<Rational> {

    private final List<List<Rational>> data = new LinkedList<>();

    //region Getters

    public List<List<Rational>> getData() {
        return data;
    }

    public List<Rational> getSolution() {
        return getColumn(getColumnCount() - 1);
    }

    //endregion

    //region Constructors and Factories

    public RationalMatrix() {
    }

    public RationalMatrix(RationalMatrix m) {
        for (List<Rational> row : m.getData()) {
            addRow(row);
        }
    }

    public static RationalMatrix of(int[][] m) {
        RationalMatrix mat = new RationalMatrix();
        for (int[] row: m) {
            List<Rational> r = new LinkedList<>();
            for (int n: row) {
                r.add(new Rational(n));
            }
            mat.addRow(r);
        }
        return mat;
    }

    //endregion

    //region Operations

    public RationalMatrix transpose() {
        RationalMatrix m = new RationalMatrix();

        for (int j = 0; j < getColumnCount(); j++) {
            List<Rational> column = new LinkedList<>();

            for (List<Rational> row : data) {
                column.add(row.get(j));
            }

            m.addRow(column);
        }

        return m;
    }

    public RationalMatrix getMinor(int row, int column) throws IndexOutOfBoundsException {
        if (row >= getRowCount() || column >= getColumnCount()) { throw new IndexOutOfBoundsException("Element not within matrix"); }

        RationalMatrix minor = new RationalMatrix();
        for (int i = 0; i < getRowCount(); i++) {
            if (row == i) { continue; }

            List<Rational> minorRow = new LinkedList<>();

            for (int j = 0; j < getColumnCount(); j++) {
                if (column == j) { continue; }

                minorRow.add(get(i, j));
            }

            minor.addRow(minorRow);
        }

        return minor;
    }

    public Rational determinant() {
        if (!isSquare()) { throw new IllegalArgumentException("Matrix is not square"); }

        if (getColumnCount() == 1) {
            return get(0, 0);
        } else {
            Rational det = Rational.ZERO;

            for (int i = 0; i < getColumnCount(); i++) {
                Rational subDet = Rational.multiply(getMinor(i, 0).determinant(), get(i, 0));
                det = (i % 2 == 0) ? det.add(subDet) : det.subtract(subDet);
            }

            return det;
        }
    }

    public int rank() {
        RationalMatrix transposedRref = RREF().transpose();

        int rank = 0;

        for (var row: transposedRref.getData()) {
            int countOne = 0;
            boolean isCandidate = true;

            for (Rational elem: row) {
                if (!elem.equals(Rational.ZERO) && !elem.equals(Rational.ONE)) {
                    isCandidate = false;
                    break;
                } else if (elem.equals(Rational.ONE)) {
                    countOne++;
                }
            }

            if (isCandidate && countOne == 1) {
                rank++;
            }
        }

        return rank;
    }

    //endregion

    //region Reduced row echelon form

    public RationalMatrix RREF() {
        RationalMatrix rref = new RationalMatrix(this);

        int lead = 0;
        int rowCount = getRowCount();
        int columnCount = getColumnCount();

        for (int r = 0; r < rowCount; r++) {
            if (columnCount <= lead) {
                return rref;
            }

            int i = r;

            while (rref.get(i, lead).equals(Rational.ZERO)) {
                i++;
                if (rowCount == i) {
                    i = r;
                    lead++;
                    if (columnCount == lead) {
                        return rref;
                    }
                }
            }

            rref.swapRows(i, r);

            if (!rref.get(r, lead).equals(Rational.ZERO)) {
                rref.multiplyRowAtIndex(r, rref.get(r, lead).invert());
            }

            for (i = 0; i < rowCount; i++) {
                if (i != r) {
                    rref.addMultipleOfRow(i, r, rref.get(i, lead).negate());
                }
            }

            lead++;
        }

        return rref;
    }

    private void swapRows(int i, int j) {
        if (i >= getRowCount() || j >= getRowCount()) { throw new IndexOutOfBoundsException("Rows not within matrix"); }

        var temp = getRow(i);
        data.set(i, getRow(j));
        data.set(j, temp);
    }

    private void multiplyRowAtIndex(int i, Rational n) {
        data.set(i, multiplyRow(getRow(i), n));
    }

    private static List<Rational> multiplyRow(List<Rational> row, Rational n) {
        List<Rational> newRow = new LinkedList<>();
        for (Rational r: row) {
            newRow.add(r.multiply(n));
        }
        return newRow;
    }

    private static List<Rational> addRows(List<Rational> row1, List<Rational> row2) {
        if (row1.size() != row2.size()) { throw new IllegalArgumentException("Rows must be of same size"); }

        List<Rational> newRow = new LinkedList<>();
        for (int i = 0; i < row1.size(); i++) {
            newRow.add(Rational.add(row1.get(i), row2.get(i)));
        }
        return newRow;
    }

    private void addMultipleOfRow(int i, int j, Rational n) {
        data.set(i, addRows(getRow(i), multiplyRow(getRow(j), n)));
    }

    //endregion

    //region Test & Example

    public static void main(String[] args) {
        int[][] mat = new int[][]{
                {0, 2, -1, -4},
                {2, 3, -1, -11},
                {-2, 0, -3, 22}
        };

        RationalMatrix m = RationalMatrix.of(mat);

        m.print();
        System.out.println();

        RationalMatrix rref = m.RREF();
        rref.print();
        System.out.println();

        System.out.println(m.rank());

        System.out.println(rref.getSolution());
    }

    public void print() {
        for (List<Rational> row : data) {
            for (Rational r : row) {
                System.out.printf("%6s", r.toString());
            }
            System.out.println();
        }
    }

    //endregion

}
