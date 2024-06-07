package me.emilpopovic.balancer.EquationBalancer;

import me.emilpopovic.balancer.EquationBalancer.EquationFormatting.EquationDto;
import me.emilpopovic.balancer.EquationBalancer.EquationFormatting.EquationToken;
import me.emilpopovic.balancer.SystemSolver.LinearSystem;
import me.emilpopovic.balancer.SystemSolver.NoUniqueSolutionError;
import me.emilpopovic.balancer.MathUtil.Rational;

import java.util.LinkedList;
import java.util.List;

public class Balancer {

    //region Logic

    public static List<Integer> getIntegerSolution(String eq) throws EquationBalancingFailedError {
        LinearSystem system = new LinearSystem(Parser.parseToMatrix(eq));

        List<Rational> solution;
        try {
            solution = system.getSolution();
        } catch (NoUniqueSolutionError e) {
            throw new EquationBalancingFailedError("Equation cannot be balanced", e);
        }

        solution.add(new Rational(-1));

        List<Integer> denominators = new LinkedList<>();
        for (Rational r: solution) {
            denominators.add((int) r.getDenominator());
        }
        int commonDenominator = lcmList(denominators);

        solution.replaceAll(rational -> rational.multiply(-commonDenominator));

        List<Integer> intSolution = new LinkedList<>();
        for (Rational r: solution) {
            intSolution.add((int) r.getNumerator());
        }

        return intSolution;
    }

    public static EquationDto getBalancedEquation(String eq) throws EquationBalancingFailedError {
        List<Integer> solution = getIntegerSolution(eq);

        String[] parts = eq.split(">");

        List<String> reactantsStr = Parser.getCompounds(parts[0]);
        List<EquationToken> reactants = new LinkedList<>();
        reactantsStr.forEach(s -> reactants.add(new EquationToken(Element.withSymbol(s))));

        List<String> productsStr = Parser.getCompounds(parts[1]);
        List<EquationToken> products = new LinkedList<>();
        reactantsStr.forEach(s -> products.add(new EquationToken(Element.withSymbol(s))));

        for (int i = 0; i < reactantsStr.size(); i++) {
            reactants.get(i).setCoefficient(solution.get(i));
        }

        for (int i = 0; i < productsStr.size(); i++) {
            products.get(i).setCoefficient(solution.get(i + reactants.size()));
        }

        return new EquationDto(reactants, products);
    }

    public static String getBalancedString(String eq) throws EquationBalancingFailedError {
        return getBalancedEquation(eq).toString();
    }

    //endregion

    //region Util

    private static int gcd(int a, int b) {
        if (b == 0) { return a; }
        return gcd(b,a % b);
    }

    private static int lcm(int a, int b) {
        return Math.abs(a * b) / gcd(a, b);
    }

    private static int lcmList(List<Integer> numbers) {
        return numbers.stream().reduce(1, Balancer::lcm);
    }

    //endregion

    //region Test & Example

    public static void main(String[] args) throws EquationBalancingFailedError {
        String input = "CrO4(2-)+H3O(+)>Cr2O7(2-)+H2O";

        List<Integer> solution = getIntegerSolution(input);

        System.out.println(solution);
        System.out.println(getBalancedString(input));
    }

    //endregion
}
