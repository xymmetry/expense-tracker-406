package com.ccps406.expensetracker;

public class Model {

    private String amount, description, date;



    public Model(String amount, String description, String date) {
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public Model() {
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
