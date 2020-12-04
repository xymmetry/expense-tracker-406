package com.ccps406.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class Profile extends AppCompatActivity {

    public static final String TAG = "Success";

    FirebaseFirestore mStore;
    FirebaseAuth mAuth;
    FirebaseUser user;

    EditText mEmail, mPassword, mPassConfirmation, mName, mBudget;

    DocumentReference personnelInfo, financeInfo;

    String userID;
    String nameDb, emailDb, passwordDb, orgPass;
    double budgetDb;
    PassowordHelper checkPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        userID = user.getUid();


        personnelInfo = mStore.collection("users").document(userID)
                .collection("Profile").document("Information");

        financeInfo = mStore.collection("users").document(userID)
                .collection("Profile").document("Financial");

        mEmail = findViewById(R.id.email_preview);
        mName = findViewById(R.id.name_preview);
        mPassword = findViewById(R.id.new_password);
        mPassConfirmation = findViewById(R.id.confirm_password);
        mBudget = findViewById(R.id.budget_view);
        showData();
    }

    public void showData(){

        personnelInfo.addSnapshotListener(Profile.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                    mEmail.setText(documentSnapshot.getString("email"));
                    mName.setText(documentSnapshot.getString("username"));
                    mPassword.setText(documentSnapshot.getString("password"));
                    nameDb = documentSnapshot.getString("username");
                    emailDb = documentSnapshot.getString("email");
                    passwordDb = documentSnapshot.getString("password");
            }
        });

        financeInfo.addSnapshotListener(Profile.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
            mBudget.setText(Double.toString(documentSnapshot.getDouble("budget")));
            budgetDb = documentSnapshot.getDouble("budget");
            }
        });
    }

    public void updateInfo(View v){
        String name = mName.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String budget = mBudget.getText().toString().trim();

        if(TextUtils.isEmpty(name)){mName.setText(nameDb);return;}
        if(isNameChanged(name) && !TextUtils.isEmpty(name)){
            personnelInfo.update("username", name).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Profile.this, "Username Updated", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Successfully updated name");
                }
            });
        }

        if(TextUtils.isEmpty(email)){mEmail.setText(emailDb);return;}
        if(isEmailChanged(email) && !TextUtils.isEmpty(email)){
            personnelInfo.update("email", email).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Profile.this, "Email Updated", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Successfully updated email");
                }
            });
        }

        if(TextUtils.isEmpty(password)){mPassword.setText(passwordDb);return;}
        orgPass = passwordDb;
        if(isPasswordChanged(password) && !TextUtils.isEmpty(password)){
            checkPass = new PassowordHelper(password);
            if(!checkPass.isValidPassword()){mPassword.setError("Invalid Password");return;}
            String confirmPass = mPassConfirmation.getText().toString().trim();
            if(TextUtils.isEmpty(confirmPass) || !checkPass.passwordsMatch(confirmPass))
            {mPassConfirmation.setError("Password does not match");return;}
            if(confirmPass.length() < 8 || confirmPass.length() > 24){mPassConfirmation.setError("Length must be 8-24 characters");}
            personnelInfo.update("password", password).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Profile.this, "Password Changed", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Successfully updated password");
                }
            });
        }

        if(TextUtils.isEmpty(budget)){mBudget.setError("Enter Amount");return;}
        if(isBudgetChanged(Double.parseDouble(budget))){
            financeInfo.update("budget", Double.parseDouble(budget)).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Profile.this, "Budget updated", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Successfully updated budget");
                }
            });
        }
        updateAuth();

    }

    public void updateAuth(){
        if (user != null){
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), orgPass);
            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        if (isEmailChanged(user.getEmail())) {
                            user.updateEmail(emailDb).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.d(TAG, "Updated email");
                                }
                            });
                        }
                        if (isPasswordChanged(orgPass)) {
                            user.updatePassword(passwordDb).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.d(TAG, "Update password");
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    public boolean isBudgetChanged(double bg){
        return !(budgetDb == bg);
    }

    public boolean isEmailChanged(String em){
        return !emailDb.equals(em);
    }

    public boolean isNameChanged(String nm){
        return !nameDb.equals(nm);
    }

    public boolean isPasswordChanged(String pass){
        return !passwordDb.equals(pass);
    }

    public void logout(View v){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}