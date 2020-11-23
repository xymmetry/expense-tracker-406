package com.ccps406.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditTransactions extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transactions);

        //update = findViewById(R.id.button4);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amt = amount.getText().toString().trim();
                String desc = description.getText().toString().trim();
                String dat = dated.getText().toString().trim();

                dr.update(amt,desc,dat);

            }
        });



    }
}