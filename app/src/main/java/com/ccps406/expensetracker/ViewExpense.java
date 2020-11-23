package com.ccps406.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ViewExpense extends AppCompatActivity {

    TextView textViewData;
    Button editTransaction;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    String userID = auth.getCurrentUser().getUid();
    private CollectionReference notebookRef = db.collection("sampleModel2")
            .document(userID)
            .collection("Expense")
            .document("Individual")
            .collection("transactions");
    private DocumentReference noteRef = db.document("sampleModel2/"+userID+"/Expense/Individual");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expense);
        textViewData = findViewById(R.id.textView4);
        //editTransaction = findViewById(R.id.button4);

        /*editTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EditTransactions.class));
                finish();
            }
        });*/
    }



    @Override
    protected void onStart() {
        super.onStart();

        notebookRef.orderBy("date")
                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                String data = "";
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Model note = documentSnapshot.toObject(Model.class);
                    //note.setAmount(documentSnapshot.getId());
                    String amount = note.getAmount();
                    String description = note.getDescription();
                    String date = note.getDate();
                    data +=  "  " + amount +"               " +description +"               "+ date+"\n";
                }
                textViewData.setText(data);
            }
        });

    }

}