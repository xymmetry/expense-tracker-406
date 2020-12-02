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
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class Register extends AppCompatActivity {

    PassowordHelper helper;
    public static final String TAG = "EmailExists";
    EditText regEmail, regPassword, regPasswordConfirm, registeredFirstName;
    Button registration_btn;
    TextView mError;
    UserInformation personalInfo;
    UserFinanceInformation defaultInfo;
    private FirebaseAuth fAuth;
    FirebaseFirestore db;
    FirebaseUser user;
    String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        regPasswordConfirm = findViewById(R.id.user_password2);
        regEmail =  findViewById(R.id.user_email);
        regPassword = findViewById(R.id.user_password);
        registration_btn = findViewById(R.id.Register_btn);
        registeredFirstName = findViewById(R.id.user_name_first);
        mError = findViewById(R.id.reg_email_error);
        registration_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = regEmail.getText().toString().trim();
                String password = regPassword.getText().toString().trim();
                String passwordConfirmation = regPasswordConfirm.getText().toString().trim();
                String firstName = registeredFirstName.getText().toString().trim();

                if(TextUtils.isEmpty(firstName)){
                    registeredFirstName.setError("Required Field");
                    return;
                }


                 if (TextUtils.isEmpty(email)){
                     regEmail.setError("Required Field");
                     return;
                 }
                 if (TextUtils.isEmpty(password)){
                     regPassword.setError("Required Field");
                     return;
                 }

                 helper = new PassowordHelper(password);
                 if (!helper.isValidPassword()){
                     regPassword.setError("Password must be between 8-24 characters long");
                     return;
                 }

                if(!helper.passwordsMatch(passwordConfirmation)){
                    regPasswordConfirm.setError("Password does not match");
                    return;
                }

                personalInfo = new UserInformation(firstName, email, password);
                createNewUser(personalInfo);
            }
        });
    }

    public void createNewUser(UserInformation thisUser){

        fAuth.createUserWithEmailAndPassword(thisUser.getEmail(),thisUser.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    user = fAuth.getCurrentUser();
                    UserProfileChangeRequest setUsername = new UserProfileChangeRequest.Builder().setDisplayName(thisUser.getUsername()).build();
                    user.updateProfile(setUsername).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            if(task.isSuccessful()){
                                Log.d(TAG, "Successful");
                            }
                        }
                    });

                    Toast.makeText(Register.this,"Registered", Toast.LENGTH_SHORT).show();

                    ID = user.getUid();
                    db.collection("users").document(ID).collection("Profile").document("Information")
                            .set(thisUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                        }
                    });

                    defaultInfo = new UserFinanceInformation(1000.00, 00.00, 0.00);
                    db.collection("users").document(ID).collection("Profile").document("Financial").set(defaultInfo)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "Document written");
                                }
                            });

                    startActivity(new Intent(getApplicationContext(),Dashhost.class)); // changed MainActivity to DashHost
                }
                else{
                    mError.setText("Email Already in use, Try Again");
                }
            }
        });
    }

    public void returnLogin(View v){
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}