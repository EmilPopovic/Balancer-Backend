package me.emilpopovic.balancer.EquationBalancer.EquationFormatting;

import java.util.List;

public record EquationDto(List<EquationToken> reactants, List<EquationToken> products) {

    public void addReactant(EquationToken token) {
        reactants.add(token);
    }

    public void addProduct(EquationToken token) {
        products.add(token);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        formatEquationSide(result, reactants);

        result.append(" > ");

        formatEquationSide(result, products);

        return result.toString();
    }

    private void formatEquationSide(StringBuilder result, List<EquationToken> tokens) {
        for (int i = 0; i < tokens.size(); i++) {
            EquationToken token = tokens.get(i);

            result.append((token.getCoefficient() == 1) ? "" : token.getCoefficient())
                    .append((token.getCoefficient() == 1) ? "" : " ")
                    .append(token.getElement().symbol)
                    .append((i == tokens.size() - 1) ? "" : " + ");
        }
    }
}
