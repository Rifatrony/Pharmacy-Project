package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout signInEmailEditText, signInPasswordEditText;
    private Button signInButton;
    private TextView signUpTextView;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("Login");

       /* //Full Screen

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }*/

        mAuth = FirebaseAuth.getInstance();

        signInEmailEditText = findViewById(R.id.signInEmailEditTextId);
        signInPasswordEditText = findViewById(R.id.signInPasswordEditTextId);

        signInButton = findViewById(R.id.signInButtonId);
        signUpTextView = findViewById(R.id.signUpTextViewId);
        progressBar = findViewById(R.id.progressBarId);

        signInButton.setOnClickListener(this);
        signUpTextView.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {

        if (view.getId()==R.id.signInButtonId){

           /* Intent intent = new Intent(this,MainDashBoard.class);
            startActivity(intent);*/

            userLogin();
        }
        else if (view.getId()==R.id.signUpTextViewId){
            Intent intent = new Intent(this,SignUpActivity.class);
            startActivity(intent);
        }


    }

    private void userLogin() {
        String email = signInEmailEditText.getEditText().getText().toString().trim();
        String password = signInPasswordEditText.getEditText().getText().toString().trim();

        if (email.isEmpty()){
            signInEmailEditText.getEditText().setError("Enter email address");
            signInEmailEditText.getEditText().requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signInEmailEditText.getEditText().setError("Enter a valid email address");
            signInEmailEditText.getEditText().requestFocus();
            return;
        }
        if (password.isEmpty()){
            signInPasswordEditText.getEditText().setError("Enter password");
            signInPasswordEditText.getEditText().requestFocus();
            return;
        }
        if (password.length()<6){
            signInPasswordEditText.getEditText().setError("Minimum password length is 6");
            signInPasswordEditText.getEditText().requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this,MainDashBoard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    signInEmailEditText.getEditText().setText("");
                    signInPasswordEditText.getEditText().setText("");
                }
                else {
                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}