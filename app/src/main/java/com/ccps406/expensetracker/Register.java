package com.ccps406.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import io.perfmark.Tag;

public class Register extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText regEmail, regPassword, regPasswordConfirm, registeredFirstName, registeredLastName;
    Button registration_btn;
    FirebaseAuth fAuth;
    FirebaseFirestore db;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regPasswordConfirm = findViewById(R.id.user_password2);
        regEmail =  findViewById(R.id.user_email);
        regPassword = findViewById(R.id.user_password);
        registration_btn = findViewById(R.id.Register_btn);
        registeredFirstName = findViewById(R.id.user_name_first);
        registeredLastName = findViewById(R.id.user_name_last);
        fAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        if(fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(),Dashhost.class)); //changed from MainActivity to Dashhost
            finish();
        }

        registration_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = regEmail.getText().toString().trim();
                String password = regPassword.getText().toString().trim();
                String passwordConfirmation = regPasswordConfirm.getText().toString().trim();
                String firstName = registeredFirstName.getText().toString().trim();
                String lastName = registeredLastName.getText().toString().trim();

                 if (TextUtils.isEmpty(email)){
                     regEmail.setError("Invalid Email");
                     return;
                 }
                 if (TextUtils.isEmpty(password)){
                     regPassword.setError("Invalid entry");
                     return;
                 }
                 
                 if (password.length() < 8 || password.length() >12){
                     regPassword.setError("Password must be between 8-12 characters long");
                     return;
                 }

                if(!password.equals(passwordConfirmation)){
                    regPasswordConfirm.setError("Not Matching");
                    return;
                }

                 fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if(task.isSuccessful()){
                             Toast.makeText(Register.this,"Registered", Toast.LENGTH_SHORT).show();

                             userID = fAuth.getCurrentUser().getUid();

                             DocumentReference dr = db.collection("users").document();

                             Map<String, Object> user = new HashMap<>();
                             user.put("first_name", firstName );
                             user.put("last_name", lastName );
                             user.put("email", email );
                             user.put("password", password);

                             dr.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                 @Override
                                 public void onSuccess(Void aVoid) {
                                     Log.d(TAG, "User " + userID + " profile:" );
                                 }
                             });
                             startActivity(new Intent(getApplicationContext(),Dashhost.class)); // changed from MainActivity.class to Dashhost.class
                         }
                         else{
                             Toast.makeText(Register.this,"Error occurred, Try Again!", Toast.LENGTH_SHORT).show();
                         }
                     }
                 });
            }
        });
    }
}