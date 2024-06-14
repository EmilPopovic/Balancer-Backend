package me.emilpopovic.balancer.SystemSolver;

import me.emilpopovic.balancer.MathUtil.RationalMatrix;
import me.emilpopovic.balancer.MathUtil.Rational;

import java.util.ArrayList;
import java.util.List;

public class LinearSystem {

    private final RationalMatrix systemMatrix = new RationalMatrix();
    private int equationCount = 0;
    private List<Rational> solution = new ArrayList<>();

    //region Getters

    public RationalMatrix getEquations() {
        return systemMatrix;
    }

    public List<Rational> getSolution() throws NoUniqueSolutionError {
        if (solution.isEmpty()) {
            solve();
        }
        return new ArrayList<>(solution);
    }

    //endregion

    //region Constructors

    public LinearSystem(List<List<Rational>> equations) {
        for (List<Rational> equation : equations) {
            boolean added = addEquation(equation);
            if (added) {
                equationCount++;
            }
        }
    }

    public LinearSystem(RationalMatrix m) {
        this(m.getData());
    }

    //endregion

    //region Operations

    public boolean addEquation(List<Rational> equation) {
        if (isIndependentFromSystem(equation)) {
            systemMatrix.addRow(equation);
            return true;
        }
        return false;
    }

    private boolean isIndependentFromSystem(List<Rational> eq) {
        if (equationCount == 0) {
            return true;
        }

        if (eq.size() != systemMatrix.getColumnCount()) {
            throw new IllegalArgumentException("Incompatible vector dimension");
        }

        RationalMatrix extendedMat = new RationalMatrix(systemMatrix);
        extendedMat.addRow(eq);

        return extendedMat.rank() == equationCount + 1;
    }

    private void solve() throws NoUniqueSolutionError {
        if (!hasUniqueSolution()) { throw new NoUniqueSolutionError("System does not have a unique solution"); }

        RationalMatrix rref = systemMatrix.RREF();
        solution = rref.getSolution();
    }

    public boolean hasUniqueSolution() {
        if (equationCount == 0) {
            return false;
        }
        return equationCount == systemMatrix.getColumnCount() - 1;
    }

    //endregion

    //region Test & Example

    public static void main(String[] args) throws NoUniqueSolutionError {
        //"H3PO4+H20>H3O(+)+PO4(3-)"

        RationalMatrix m = RationalMatrix.of(new int[][]{
                {3, 2, -3, 0},
                {1, 0, 0, -1},
                {4, 1, -1, -4},
                {0, 0, -1, 3}
        });

        LinearSystem system = new LinearSystem(m);

        List<Rational> solution = system.getSolution();

        System.out.println("System matrix:");
        system.getEquations().print();
        System.out.println();

        System.out.println("Solution: " + solution);
    }

    //endregion

}
