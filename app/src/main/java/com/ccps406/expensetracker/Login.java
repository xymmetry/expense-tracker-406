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

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login extends AppCompatActivity {
    private static final String TAG = "TAG";
    EditText loginName, logInPass;
    Button loginButton, gSignIn;
    FirebaseAuth fAuth;
    TextView mError;
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onStart(){
        super.onStart();

        FirebaseUser user = fAuth.getCurrentUser();
        if (user != null){
            Intent intent = new Intent(getApplicationContext(), Dashhost.class); // changed from MainActivity.class to Dashhost.class
            startActivity(intent);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginName = findViewById(R.id.user_login_email);
        logInPass = findViewById(R.id.user_login_password);
        loginButton = findViewById(R.id.login_btn);
        fAuth = FirebaseAuth.getInstance();
        mError = findViewById(R.id.email_error);
        //gSignIn = findViewById(R.id.google_sign);

      /*  gSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
                 }
            });*/
        //googleSignIn();
       loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginName.getText().toString().trim();
                String password = logInPass.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    loginName.setError("Invalid Email");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    logInPass.setError("Invalid entry");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Dashhost.class)); // Changed MainActivity.class to Dashhost.class
                            }
                            else{
                                mError.setText("Incorrect Email or password, Try Again!");
                                Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                    }
                });
            }
        });
    }

/*
    public void googleSignIn(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail()
                .requestProfile()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
*/
  /*  private void  signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }*/

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
//                firebaseAuthWithGoogle(account);
//
//            } catch (ApiException e) {
//                // Google Sign In failed, update UI appropriately
//                Log.w(TAG, "Google sign in failed", e);
//                // ...
//            }
//        }
//    }

//    private void firebaseAuthWithGoogle(GoogleSignInAccount acct){
//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//        fAuth.signInWithCredential(credential)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                       if(task.isSuccessful()){
//                           FirebaseUser user = fAuth.getCurrentUser();
//                           Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                           startActivity(intent);
//                       }
//                       else{
//                           Toast.makeText(Login.this, "Failed", Toast.LENGTH_SHORT).show();
//                       }
//                    }
//                });
//    }

    public void goToMain(View v){
        startActivity(new Intent(getApplicationContext(), Mainpage.class));
        finish();
    }
}