package com.ccps406.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Mainpage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);


    }

    public void registerPage(View view) {
        startActivity(new Intent(getApplicationContext(), Register.class));
    }

    public void loginPage (View view){
        startActivity(new Intent(getApplicationContext(), Login.class));
    }
}