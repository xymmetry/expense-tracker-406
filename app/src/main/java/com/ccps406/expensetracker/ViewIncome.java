package com.ccps406.expensetracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static java.time.LocalDate.now;


public class ViewIncome extends AppCompatActivity {

    TextView amount;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String userID = auth.getCurrentUser().getUid();

    final DocumentReference dr = db.document("sampleModel2/" +userID +"/Income/Individual");
    private CollectionReference colref = db.collection("sampleModel2")
            .document(userID)
            .collection("Income")
            .document("Individual")
            .collection("transactions");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_income);

        amount = findViewById(R.id.amount);

        colref.orderBy("date")
                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    return;
                }

                String receiveData = "";

                for (QueryDocumentSnapshot documentSnapshot : value ){
                    Model model = documentSnapshot.toObject(Model.class);

                    String amount = model.getAmount();
                    String description = model.getDescription();
                    String date = model.getDate();

                    receiveData +=  "  " + amount +"               " +description +"               "+ date+"\n";

                }

                amount.setText(receiveData);
            }
        });
    }
}