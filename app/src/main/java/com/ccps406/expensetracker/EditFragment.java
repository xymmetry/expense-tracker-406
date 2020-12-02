package com.ccps406.expensetracker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import androidx.appcompat.app.AppCompatActivity;







/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFragment extends Fragment {

    TextView amount, description, dated;
    Button update;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String userID = auth.getCurrentUser().getUid();
    final DocumentReference dr = db.document("sampleModel2/" +userID +"/Expense/Individual");
    private CollectionReference colref = db.collection("sampleModel2")
            .document(userID)
            .collection("Expense")
            .document("Individual")
            .collection("transactions");

    public EditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditFragment newInstance(String param1, String param2) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View editView = inflater.inflate(R.layout.activity_edit_transactions, container, false);



        // the part below does not work
        editView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String amt = amount.getText().toString().trim();
                String desc = description.getText().toString().trim();
                String dat = dated.getText().toString().trim();

                dr.update(amt,desc,dat);

            }
        });

        return editView;


        //return inflater.inflate(R.layout.activity_edit_transactions, container, false); // changed R.layout.fragment_edit to activity_edit_transactions
    }


    //added activity from original EditTransactions


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

/*
        update.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String amt = amount.getText().toString().trim();
                String desc = description.getText().toString().trim();
                String dat = dated.getText().toString().trim();

                dr.update(amt,desc,dat);

            }
        });
*/


    }
}