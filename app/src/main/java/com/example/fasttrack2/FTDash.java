package com.example.fasttrack2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class FTDash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dashboard);

        // BottomNav
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.dashboard);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        NavController navController = Navigation.findNavController(this, R.id.fragment);
        NavigationUI.setupWithNavController(bottomNav, navController);

        // Expense Pie Chart
        PieChart expenseChart = findViewById(R.id.expensechart);

        ArrayList<PieEntry> expenses = new ArrayList<>();
        expenses.add(new PieEntry(320, "Housing"));
        expenses.add(new PieEntry(305, "Bills"));
        expenses.add(new PieEntry(200, "Food"));
        expenses.add(new PieEntry(120, "Transportation"));
        expenses.add(new PieEntry(90, "Entertainment"));

        PieDataSet expenseDataSet = new PieDataSet(expenses, "Expenses");
        expenseDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        expenseDataSet.setValueTextColor(Color.BLACK);
        expenseDataSet.setValueTextSize(12f);

        PieData pieData = new PieData(expenseDataSet);

        expenseChart.setData(pieData);
        expenseChart.getDescription().setEnabled(false);
        expenseChart.setCenterText("Expenses");
        expenseChart.animate();
    }


}