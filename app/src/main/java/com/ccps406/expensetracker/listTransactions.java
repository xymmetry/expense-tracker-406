package com.ccps406.expensetracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.core.view.View;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class listTransactions extends AppCompatActivity {


    public static final String TAG = "TAG";
    TextView amount, description, dated;
    //FirebaseAuth auth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userID;
    private ListenerRegistration listenery;
    final DocumentReference ref = db.collection("transactions")
            .document("Ed9f2BLO7fbymH10wNgo");
    private CollectionReference colref = db.collection("transactions");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_transactions);



        //db = FirebaseFirestore.getInstance();
        amount = findViewById(R.id.amount);
        //description = findViewById(R.id.desc);
        //dated = findViewById(R.id.date1);

        colref.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
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

       /* db.collection("transactions")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //This code will be executed when the data is completely received
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                Log.d("data received", document.getId() + " => " + document.getData());

                                // Displaying data in textview
                                amount.setText(document.getString("amount"));
                                description.setText(document.getString("description"));
                                dated.setText(document.getString("date"));

                            }
                        }


                    }
                });*/



    }
}