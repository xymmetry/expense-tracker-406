package com.ccps406.expensetracker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashFragment extends Fragment {

    //pie chart initialization
    PieChart expenseChart;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DashFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashFragment newInstance(String param1, String param2) {
        DashFragment fragment = new DashFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    //public void seeTransactions(View view){
    //    startActivity(new Intent(getApplicationContext(),listTransactions.class));

    //}

    //public void logout(View view){
        //FirebaseAuth.getInstance().signOut();
    //    startActivity(new Intent(getApplicationContext(),Transactions.class));
    //    finish();
    //}

    private ArrayList<PieEntry> expensesDataSet(){
        ArrayList<PieEntry> expenses = new ArrayList<>();
        expenses.add(new PieEntry(320, "Housing"));
        expenses.add(new PieEntry(305, "Bills"));
        expenses.add(new PieEntry(200, "Food"));
        expenses.add(new PieEntry(120, "Transportation"));
        expenses.add(new PieEntry(90, "Entertainment"));
        return expenses;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        expenseChart = getActivity().findViewById(R.id.expensechart);
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


}