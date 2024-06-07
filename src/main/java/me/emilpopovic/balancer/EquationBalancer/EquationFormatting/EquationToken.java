package me.emilpopovic.balancer.EquationBalancer.EquationFormatting;

public class EquationToken {
    private int coefficient;
    private Compound compound;
    private int charge;

    public EquationToken() {
    }

    public EquationToken(Compound compound) {
        this.compound = compound;
    }

    public EquationToken(int coefficient, Compound compound, int charge) {
        this.coefficient = coefficient;
        this.compound = compound;
        this.charge = charge;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public Compound getCompound() {
        return compound;
    }

    public void setCompound(Compound compound) {
        this.compound = compound;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }
}
