package me.emilpopovic.balancer.SystemSolver;

public class NoUniqueSolutionError extends Exception {
    public NoUniqueSolutionError() {
        super();
    }

    public NoUniqueSolutionError(String message) {
        super(message);
    }

    public NoUniqueSolutionError(String message, Throwable cause) {
        super(message, cause);
    }

    public NoUniqueSolutionError(Throwable cause) {
        super(cause);
    }
}
