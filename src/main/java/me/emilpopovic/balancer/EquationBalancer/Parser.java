package me.emilpopovic.balancer.EquationBalancer;

import me.emilpopovic.balancer.MathUtil.RationalMatrix;

import java.util.*;

public class Parser {

    public static RationalMatrix parseToMatrix(String input) throws EquationParseError {
        input = input.replaceAll(" ", "");

        String[] parts = input.split(">");
        if (parts.length != 2) { throw new EquationParseError("Invalid input"); }

        List<Element> elements = new ArrayList<>(getElements(input));

        List<String> reactants = getCompounds(parts[0]);
        List<String> products = getCompounds(parts[1]);

        if (reactants.isEmpty()) { throw new EquationParseError("No reactants"); }
        if (products.isEmpty()) { throw new EquationParseError("No products"); }

        List<String> compounds = new ArrayList<>(reactants);
        compounds.addAll(products);

        int rowCount = elements.size() + ((hasCharge(input)) ? 1 : 0);
        int columnCount = reactants.size() + products.size();

        int[][] systemMatrix = new int[rowCount][columnCount];

        for (int i = 0; i < elements.size(); i++) {
            for (int j = 0; j < columnCount; j++) {
                int atomCount = countElementInCompound(elements.get(i), compounds.get(j));

                if (j < reactants.size()) {
                    systemMatrix[i][j] = atomCount;
                } else {
                    systemMatrix[i][j] = -atomCount;
                }
            }
        }

        if (hasCharge(input)) {
            for (int i = 0; i < reactants.size(); i++) {
                systemMatrix[rowCount - 1][i] = getCompoundCharge(reactants.get(i));
            }

            for (int i = 0; i < products.size(); i++) {
                systemMatrix[rowCount - 1][i + reactants.size()] = -getCompoundCharge(products.get(i));
            }
        }

        return RationalMatrix.of(systemMatrix);
    }

    private static int countElementInCompound(Element element, String compound) {
        int count = 0;

        for (int i = 0; i < compound.length(); i++) {
            Element currentElement = null;

            for (Element e : (element.symbol.length() == 2) ? Element.getTwoLetter() : Element.getOneLetter()) {
                if (compound.substring(i).startsWith(e.symbol)) {
                    currentElement = e;
                    break;
                }
            }

            if (currentElement != element) { continue; }

            boolean numberPresent = false;
            int j = i + element.symbol.length();
            for (; j < compound.length() && Character.isDigit(compound.charAt(j)); j++) {
                numberPresent = true;
            }

            if (numberPresent) {
                count += Integer.parseInt(compound.substring(i + element.symbol.length(), j));
            } else {
                count++;
            }
            i = j - 1;
        }

        return count;
    }

    private static int getCompoundCharge(String compound) throws EquationParseError {
        if (compound.endsWith("+)")) {
            String[] split = compound.split("\\(");
            if (split.length != 2) { throw new EquationParseError("Invalid charge format"); }

            String chargeCountNumber = split[1].split("\\+")[0];
            return (chargeCountNumber.isEmpty()) ? 1 : Integer.parseInt(chargeCountNumber);

        } else if (compound.endsWith("-)")) {
            String[] split = compound.split("\\(");
            if (split.length != 2) { throw new EquationParseError("Invalid charge format"); }

            String chargeCountNumber = split[1].split("-")[0];
            return (chargeCountNumber.isEmpty()) ? -1 : -Integer.parseInt(chargeCountNumber);
        }
        return 0;
    }

    public static List<String> getCompounds(String input) {
        List<String> compounds = new LinkedList<>();

        for (String c: input.replaceAll("\\+\\)", "Qp").split("\\+")) {
            compounds.add(c.replaceAll("Qp", "+)"));
        }

        return compounds;
    }

    private static Set<Element> getElements(String input) {
        Set<Element> elements = new LinkedHashSet<>();

        for (int i = 0; i < input.length(); i++) {
            boolean foundTwoLetter = false;

            for (Element e: Element.getTwoLetter()) {
                if (input.substring(i).startsWith(e.symbol)) {
                    elements.add(e);
                    foundTwoLetter = true;
                    break;
                }
            }

            if (foundTwoLetter) { continue; }

            for (Element e: Element.getOneLetter()) {
                if (input.substring(i).startsWith(e.symbol)) {
                    elements.add(e);
                    break;
                }
            }
        }

        return elements;
    }

    private static boolean hasCharge(String input) {
        return input.contains("+)") || input.contains("-)");
    }

    //region Test & Example

    public static void main(String[] args) throws EquationParseError {
        String input = "H3PO4+H20>H3O(+)+PO4(3-)";
        RationalMatrix systemMatrix = parseToMatrix(input);
        systemMatrix.print();
    }

    //endregion

}
