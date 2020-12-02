package com.ccps406.expensetracker;

import java.util.Date;

public class TransactionRecurringModel {

    private String description;
    Date startDate, nextDate;
    double amount;
    int repeatAfterXDays, occurrences;
    boolean recurring;
    String type;


    public TransactionRecurringModel(){}


    public TransactionRecurringModel(double amount, String description,
                                     int repeatAfterXDays, int occurrences, Date startDate, String type, Date nextDate){

        this.amount = amount;
        this.description = description;
        this.type = type;
        this.recurring = true;
        this.repeatAfterXDays = repeatAfterXDays;
        this.occurrences = occurrences;
        this.startDate = startDate;
        this.nextDate = nextDate;

    }

    public Date getNextDate() {
        return nextDate;
    }

    public void setNextDate(Date nextDate) {
        this.nextDate = nextDate;
    }

    public boolean getRecurring(){
        return recurring;
    }

    public void setRecurring(){
        this.recurring = true;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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

    public int getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
    }

    public int getRepeatAfterXDays() {
        return repeatAfterXDays;
    }

    public void setRepeatAfterXDays(int repeatAfterXDays) {
        this.repeatAfterXDays = repeatAfterXDays;
    }

    public String getType() {
        return type;
    }

    public void setType(String type){
        this.type = type;
    }
}
