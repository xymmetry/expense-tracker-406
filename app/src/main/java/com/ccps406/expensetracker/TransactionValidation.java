package com.ccps406.expensetracker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TransactionValidation {

    int integer;
    double decimal;
    String date_format = "MM/dd/yyyy";

    public TransactionValidation() {
    }

    public boolean isValidDate(String date){
        DateFormat format = new SimpleDateFormat(this.date_format);
        format.setLenient(false);
        try{
            format.parse(date);
        }catch (ParseException e){
            return false;
        }
        return true;
    }

    public boolean isInt(String str){
        if (str == null){
            return false;
        }
        try{
            integer = Integer.parseInt(str);
        }catch(NumberFormatException e){
            return false;
        }

        return true;
    }

    public boolean validDesLen(String description){
        return description.length() >= 1 && description.length() <= 19;
    }

    public boolean isDouble(String str){
        if (str == null){
            return false;
        }
        try{
            decimal = Double.parseDouble(str);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }

}
