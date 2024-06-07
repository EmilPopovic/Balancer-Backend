package me.emilpopovic.balancer.EquationBalancer;

public class EquationParseError extends EquationBalancingFailedError {
    public EquationParseError() {
        super();
    }

    public EquationParseError(String message) {
        super(message);
    }

    public EquationParseError(String message, Throwable cause) {
        super(message, cause);
    }

    public EquationParseError(Throwable cause) {
        super(cause);
    }
}
