package com.ccps406.expensetracker;

public class UserFinanceInformation {

    private double budget;
    private double eTotal;
    private double iTotal;

    public UserFinanceInformation(){
    }

    public UserFinanceInformation(double budget, double eTotal, double iTotal){
        this.budget = budget;
        this.eTotal = eTotal;
        this.iTotal = iTotal;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double geteTotal() {
        return eTotal;
    }

    public void seteTotal(double eTotal) {
        this.eTotal = eTotal;
    }

    public double getiTotal() {
        return iTotal;
    }

    public void setiTotal(double iTotal) {
        this.iTotal = iTotal;
    }
}
