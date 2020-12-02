package com.ccps406.expensetracker;

import java.util.Calendar;
import java.util.Date;

public class TransactionModel {

     String description;
     double amount;
     String type;
     Date startDate;

    public TransactionModel() {
    }

    public TransactionModel(double amount, String description, Date date, String type) {
        this.amount = amount;
        this.description = description;
        this.startDate = date;
        this.type = type;
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setStartDate(Date date) {
        this.startDate = date;
    }

    public Date getStartDate() {
        return startDate;
    }


}
