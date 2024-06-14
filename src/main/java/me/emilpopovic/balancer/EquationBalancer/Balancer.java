package me.emilpopovic.balancer.EquationBalancer;

import me.emilpopovic.balancer.SystemSolver.LinearSystem;
import me.emilpopovic.balancer.SystemSolver.NoUniqueSolutionError;
import me.emilpopovic.balancer.MathUtil.Rational;

import java.util.*;

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

    public static Map<String, Map<String, Integer>> getBalancedMap(String eq) throws EquationBalancingFailedError {
        List<Integer> solution = getIntegerSolution(eq);

        String[] parts = eq.split(">");

        Map<String, Integer> reactants = new LinkedHashMap<>();
        Map<String, Integer> products  = new LinkedHashMap<>();

        Map<String, Map<String, Integer>> result = new HashMap<>();
        result.put("reactants", reactants);
        result.put("products",  products);

        List<String> reactantStrings = Parser.getCompounds(parts[0]);
        List<String> productStrings  = Parser.getCompounds(parts[1]);

        for (int i = 0; i < reactantStrings.size(); i++) {
            reactants.put(reactantStrings.get(i), solution.get(i));
        }

        for (int i = 0; i < productStrings .size(); i++) {
            products.put(productStrings.get(i), solution.get(i + reactants.size()));
        }

        return result;
    }

    public static String getBalancedString(String eq) throws EquationBalancingFailedError {
        Map<String, Map<String, Integer>> map = getBalancedMap(eq);

        Map<String, Integer> reactants = map.get("reactants");

        Map<String, Integer> products = map.get("products");

        return equationSideToString(reactants) +
                " > " +
                equationSideToString(products);
    }

    private static StringBuilder equationSideToString(Map<String, Integer> compounds) {
        StringBuilder result = new StringBuilder();
        List<String> compoundsKeyList = new ArrayList<>(compounds.keySet());

        for (int i = 0; i < compounds.size(); i++) {
            String symbol = compoundsKeyList.get(i);
            Integer coefficient = compounds.get(symbol);

            result.append((i == 0) ? "" : " + ")
                    .append((coefficient == 1) ? "" : String.format("%d ", coefficient))
                    .append(symbol);
        }

        return result;
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
        System.out.println(getBalancedMap(input));
    }

    //endregion

}
