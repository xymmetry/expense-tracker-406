package com.ccps406.expensetracker;

import java.nio.charset.StandardCharsets;

import javax.crypto.EncryptedPrivateKeyInfo;

public class PassowordHelper {

    private int offset;
    private String password;

    public PassowordHelper(){}

    public PassowordHelper (String password){
        this.password = password;
        this.offset = 5;
    }


    public boolean validLength() {
        if(this.password.length() < 8 || this.password.length() > 24)
            return false;
        return true;
    }

    public boolean isValidPassword(){
        if(!containsCaps() || !validLength() || !containsNum()) return false;
        return true;
    }

    public boolean containsCaps (){
        int count = 0;
        for(int i = 0;  i < this.password.length(); i++){
            char ch = this.password.charAt(i);
            if(ch >= 'A' && ch <= 'Z'){
                count++;
            }
        }

        if(count < 1 )
            return false;

        return true;
    }


    public boolean containsNum (){
        int count = 0;
        for(int i = 0;  i < this.password.length(); i++){
            char ch = this.password.charAt(i);
            if(ch >= '0' && ch <= '9'){
                count++;
            }
        }
        if(count < 1 )
            return false;
        return true;
    }

    public boolean passwordsMatch(String pass){
        if(!this.password.equals(pass))
            return false;
        return true;
    }

    public String encrypt(){
        StringBuilder encrypted = new StringBuilder("");
        for( char c : password.toCharArray()){
            c += offset;
            encrypted.append(c);
        }
        return encrypted.toString();
    }

    public String decrypt(String enPass){
        StringBuilder decrypted = new StringBuilder("");
        for (char ch : enPass.toCharArray()){
            ch -= offset;
            decrypted.append(ch);
        }
        return decrypted.toString();
    }


}
