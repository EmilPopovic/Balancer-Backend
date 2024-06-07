package me.emilpopovic.balancer.MathUtil;

import java.util.LinkedList;
import java.util.List;

public interface Matrix<N extends Number> {

    //region Getters

    List<List<N>> getData();

    default int getRowCount() {
        return getData().size();
    }

    default Integer getColumnCount() {
        return (getData().isEmpty()) ? null : getRow(0).size();
    }

    default N get(int i, int j) {
        if (i >= getRowCount() || j >= getColumnCount()) { throw new IllegalArgumentException("Rows must be of same size"); }
        return getRow(i).get(j);
    }

    default List<N> getRow(int i) {
        if (i >= getRowCount()) { throw new IllegalArgumentException("Row not within matrix"); }
        return getData().get(i);
    }

    default List<N> getColumn(int j) {
        if (j >= getColumnCount()) { throw new IllegalArgumentException("Column not within matrix"); }
        List<N> column = new LinkedList<>();
        for (int i = 0; i < j; i++) {
            column.add(get(i, j));
        }
        return column;
    }

    List<N> getSolution();

    //endregion

    //region Operations

    default boolean isSquare() {
        return getRowCount() == getColumnCount();
    }

    default void addRow(List<N> row) throws IllegalArgumentException {
        if (getColumnCount() != null && row.size() != getColumnCount()) { throw new IllegalArgumentException("Incompatible number of elements in row"); }

        getData().add(row);
    }

    Matrix<N> getMinor(int row, int column);

    N determinant();

    int rank();

    Matrix<N> RREF();

    //endregion
}
