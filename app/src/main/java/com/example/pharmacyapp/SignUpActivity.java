package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText signUpEmailEditText, sigUpPasswordEditText;

    private Button signUpButton;
    private TextView signInTextView;

    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        this.setTitle("Sign Up");

        mAuth = FirebaseAuth.getInstance();


        signUpEmailEditText = findViewById(R.id.signUpEmailEditTextId);
        sigUpPasswordEditText = findViewById(R.id.signUpPasswordEditTextId);
        signUpButton = findViewById(R.id.signUpButtonId);
        signInTextView = findViewById(R.id.signInTextViewId);

        progressBar = findViewById(R.id.progressBarId);



        signInTextView.setOnClickListener(this);
        signUpButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.signUpButtonId){
            userRegister();
        }
        else if (view.getId()==R.id.signInTextViewId){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void userRegister() {


        String email = signUpEmailEditText.getText().toString().trim();
        String password = sigUpPasswordEditText.getText().toString().trim();

        if (email.isEmpty()){
            signUpEmailEditText.setError("Enter email address");
            signUpEmailEditText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signUpEmailEditText.setError("Enter a valid email address");
            signUpEmailEditText.requestFocus();
            return;
        }
        if (password.isEmpty()){
            sigUpPasswordEditText.setError("Enter password");
            sigUpPasswordEditText.requestFocus();
            return;
        }
        if (password.length()<6){
            sigUpPasswordEditText.setError("Minimum password length is 6");
            sigUpPasswordEditText.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Register is successful", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Register is not successful", Toast.LENGTH_LONG).show();
                }
            }
        });



    }
}