package me.emilpopovic.balancer.EquationBalancer;

public class EquationBalancingFailedError extends Exception {

    public EquationBalancingFailedError(String message) {
        super(message);
    }

    public EquationBalancingFailedError(String message, Throwable cause) {
        super(message, cause);
    }

}
