package com.ccps406.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    PieChart expenseChart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expenseChart = findViewById(R.id.expensechart);
        PieDataSet expensesDataSet = new PieDataSet(expensesDataSet(), "Expenses");
        expensesDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        expensesDataSet.setValueLineColor(R.color.design_default_color_primary);
        expensesDataSet.setValueTextSize(20f);

        PieData expenseSet = new PieData(expensesDataSet);

        expenseChart.setData(expenseSet);
        expenseChart.getDescription().setEnabled(false);
        expenseChart.animate();
        expenseChart.setEntryLabelTextSize(15);
        expenseChart.setHoleRadius(30);
        expensesDataSet.setDrawValues(false);
        expenseChart.getLegend().setEnabled(false);
        expenseChart.setDrawHoleEnabled(false);



    }




    public void seeTransactions(View view){
        startActivity(new Intent(getApplicationContext(),listTransactions.class));

    }

    public void logout(View view){
        //FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Transactions.class));
        finish();
    }

    private ArrayList<PieEntry> expensesDataSet(){
        ArrayList<PieEntry> expenses = new ArrayList<>();
        expenses.add(new PieEntry(320, "Housing"));
        expenses.add(new PieEntry(305, "Bills"));
        expenses.add(new PieEntry(200, "Food"));
        expenses.add(new PieEntry(120, "Transportation"));
        expenses.add(new PieEntry(90, "Entertainment"));
        return expenses;
    }

}