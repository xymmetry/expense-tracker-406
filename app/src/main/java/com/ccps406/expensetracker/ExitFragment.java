package com.ccps406.expensetracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExitFragment extends Fragment{

    // parameters

/*    FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    CollectionReference historyRef = mStore.collection("users").document(user.getUid()).collection("History");*/

    // new parameters

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

    public ExitFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExitFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExitFragment newInstance(String param1, String param2) {
        ExitFragment fragment = new ExitFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    // functions below

    public void showData(){

        personnelInfo.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
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

        financeInfo.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                mBudget.setText(Double.toString(documentSnapshot.getDouble("budget")));
                budgetDb = documentSnapshot.getDouble("budget");
            }
        });
    }

/*    public void updateInfo(View v){
        String name = mName.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String budget = mBudget.getText().toString().trim();

        if(TextUtils.isEmpty(name)){mName.setText(nameDb);return;}
        if(isNameChanged(name) && !TextUtils.isEmpty(name)){
            personnelInfo.update("username", name).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getActivity(), "Username Updated", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Successfully updated name");
                }
            });
        }

        if(TextUtils.isEmpty(email)){mEmail.setText(emailDb);return;}
        if(isEmailChanged(email) && !TextUtils.isEmpty(email)){
            personnelInfo.update("email", email).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getActivity(), "Email Updated", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getActivity(), "Password Changed", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Successfully updated password");
                }
            });
        }

        if(TextUtils.isEmpty(budget)){mBudget.setError("Enter Amount");return;}
        if(isBudgetChanged(Double.parseDouble(budget))){
            financeInfo.update("budget", Double.parseDouble(budget)).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getActivity(), "Budget updated", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Successfully updated budget");
                }
            });
        }
        updateAuth();

    }*/

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

/*    public void logout(View v){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(), Login.class));
    }*/

    // end of functions list

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View exitView = inflater.inflate(R.layout.activity_profile, container, false);

        exitView.findViewById(R.id.profile_logout).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), Mainpage.class);
                startActivity(intent);
            }});

        exitView.findViewById(R.id.update_profile).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String name = mName.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String budget = mBudget.getText().toString().trim();

                if(TextUtils.isEmpty(name)){mName.setText(nameDb);return;}
                if(isNameChanged(name) && !TextUtils.isEmpty(name)){
                    personnelInfo.update("username", name).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), "Username Updated", Toast.LENGTH_LONG).show();
                            Log.d(TAG, "Successfully updated name");
                        }
                    });
                }

                if(TextUtils.isEmpty(email)){mEmail.setText(emailDb);return;}
                if(isEmailChanged(email) && !TextUtils.isEmpty(email)){
                    personnelInfo.update("email", email).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), "Email Updated", Toast.LENGTH_LONG).show();
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
                            Toast.makeText(getActivity(), "Password Changed", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Successfully updated password");
                        }
                    });
                }

                if(TextUtils.isEmpty(budget)){mBudget.setError("Enter Amount");return;}
                if(isBudgetChanged(Double.parseDouble(budget))){
                    financeInfo.update("budget", Double.parseDouble(budget)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), "Budget updated", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Successfully updated budget");
                        }
                    });
                }
                updateAuth();





            }});

        return exitView;

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        userID = user.getUid();


        personnelInfo = mStore.collection("users").document(userID)
                .collection("Profile").document("Information");

        financeInfo = mStore.collection("users").document(userID)
                .collection("Profile").document("Financial");

        mEmail = getView().findViewById(R.id.email_preview);
        mName = getView().findViewById(R.id.name_preview);
        mPassword = getView().findViewById(R.id.new_password);
        mPassConfirmation = getView().findViewById(R.id.confirm_password);
        mBudget = getView().findViewById(R.id.budget_view);
        showData();


    }
}