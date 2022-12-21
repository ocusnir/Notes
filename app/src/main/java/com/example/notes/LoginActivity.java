package com.example.notes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText emailLogin, passwordLogin;
    ProgressBar progressBar;
    TextView sign_up;
    Button   loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailLogin = findViewById(R.id.login_email_edit_text);
        passwordLogin = findViewById(R.id.login_password_edit_text);
        loginBtn = findViewById(R.id.login_button);
        progressBar = findViewById(R.id.progress_Bar);
        sign_up  = findViewById(R.id.sign_up_text_button);

        loginBtn.setOnClickListener((v)-> loginUser());
        sign_up.setOnClickListener((v)-> startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class)));

    }

    void loginUser(){

        String email = emailLogin.getText().toString();
        String password = passwordLogin.getText().toString();

        boolean isValidated = validateData(email, password);
        if(!isValidated){
            return;
        }
        loginAccountInFirebase(email,password);

    }

    void loginAccountInFirebase(String email, String password){

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        changeInProgress(true);
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
//                changeInProgress(false);
                if(task.isSuccessful()){
                    //Login is successful
                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                        //go to mainActivity
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }else{
                        Utility.showToast(LoginActivity.this, "Account with this email is not found!");
                    }
                }else {
                    //Login is failed
                    Utility.showToast(LoginActivity.this, task.getException().getLocalizedMessage());
                }
            }
        });
    }
//
//        void changeInProgress(boolean inProgress){
//        if(inProgress){
//            progressBar.setVisibility(View.VISIBLE);
//            loginBtn.setVisibility(View.GONE);
//        }else{
//            progressBar.setVisibility(View.GONE);
//            loginBtn.setVisibility(View.VISIBLE);
//        }
//    }

    boolean validateData(String email, String password){
        //validate data that is input by user.
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailLogin.setError("Email is invalid");
            return false;
        }
        if(password.length()<5){
            passwordLogin.setError("Password is too short");
            return false;
        }
        return true;

    }
}