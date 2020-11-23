package com.ccps406.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Transactions extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText amount, description, dated;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    Button income, expense;
    Switch recurring;
    FirebaseAuth auth;
    FirebaseFirestore db;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        amount = findViewById(R.id.amount);
        description = findViewById(R.id.description);
        dated = findViewById(R.id.date);

        income = findViewById(R.id.income);
        expense = findViewById(R.id.expense);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        dated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Transactions.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;

                String date = month + "/" + day + "/" + year;
                dated.setText(date);
            }
        };


        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String amt =  "+ $" + amount.getText().toString().trim();
                String desc = description.getText().toString().trim();
                String dates = dated.getText().toString().trim();


                if (!amt.isEmpty() && !desc.isEmpty()){
                    //auth.createUserWithEmailAndPassword(amt,desc);
                    userID = auth.getCurrentUser().getUid();

                    DocumentReference dr = db.collection("sampleModel2")
                            .document(userID)
                            .collection("Income")
                            .document("Individual")
                            .collection("transactions")
                            .document();
                    //Map<String, Object> userT = new HashMap<>();
                    //userT.put("amount", amt );
                    //userT.put("description", desc );
                    //userT.put("date",dates);
                    Model model = new Model(amt, desc, dates);


                    dr.set(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Transactions.this, "Transaction Added", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "User " + userID + " profile:" );
                        }
                    });
                }
                else{
                    Toast.makeText(Transactions.this, "Please fill all details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String amt =  "- $" + amount.getText().toString().trim();
                String desc = description.getText().toString().trim();
                String dates = dated.getText().toString().trim();


                if (!amt.isEmpty() && !desc.isEmpty()){
                    //auth.createUserWithEmailAndPassword(amt,desc);
                    userID = auth.getCurrentUser().getUid();

                    DocumentReference dr = db.collection("sampleModel2")
                            .document(userID)
                            .collection("Expense")
                            .document("Individual")
                            .collection("transactions")
                            .document();
                    /*Map<String, Object> userT = new HashMap<>();
                    userT.put("amount", amt );
                    userT.put("description", desc );
                    userT.put("date",dates);*/
                    Model model = new Model(amt, desc, dates);

                    dr.set(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Transactions.this, "Transaction Added", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "User " + userID + " profile:" );
                        }
                    });
                }
                else{
                    Toast.makeText(Transactions.this, "Please fill all details", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}