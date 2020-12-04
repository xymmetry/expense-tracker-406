package com.ccps406.expensetracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashFragment extends Fragment {

    //pie chart initialization
    PieChart expenseChart;


    // parameters

/*    FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String userID = mAuth.getCurrentUser().getUid();
    DocumentReference historyRef = mStore.collection("users").document(userID);
    DocumentReference financialRef = mStore.collection("users").document(userID).collection("Profile").document("Financial");
    TextView mBudget, mIncome, mExpense, mNet;*/

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
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


/*    private ArrayList<PieEntry> expensesDataSet(){
        ArrayList<PieEntry> expenses = new ArrayList<>();
        historyRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                 for (QueryDocumentSnapshot document : task.getResult()){
                     double amount = document.getDouble("amount");
                     String description = document.getString("description");
                     expenses.add(new PieEntry((float) amount, description));

                 }
                }
                else{
                    expenses.add(new PieEntry(0, "No Expenses"));
                }
            }

        });
        return expenses;
    }*/

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

        //Pie Chart visual settings
        expenseChart = getView().findViewById(R.id.expensechart);
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


        // extra

/*
        mBudget = getView().findViewById(R.id.budgetGoal);
        mNet = getView().findViewById(R.id.netBalance);
        mIncome = getView().findViewById(R.id.incomeView);
        mExpense = getView().findViewById(R.id.textView5);
        financialRef.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                double eTotal = documentSnapshot.getDouble("eTotal");
                double iTotal = documentSnapshot.getDouble("iTotal");
                double netBalance = iTotal + (-eTotal);
                double budget = documentSnapshot.getDouble("budget");
                mBudget.setText(Double.toString(budget));
                mNet.setText(Double.toString(netBalance));
                mIncome.setText(Double.toString(iTotal));
                mExpense.setText(Double.toString(eTotal));


            }
        });
*/



    }


}