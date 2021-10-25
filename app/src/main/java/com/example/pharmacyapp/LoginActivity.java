package com.example.pharmacyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText signInEmailEditText, signInPasswordEditText;
    private Button signInButton;
    private TextView signUpTextView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signInEmailEditText = findViewById(R.id.signInEmailEditTextId);
        signInPasswordEditText = findViewById(R.id.signInPasswordEditTextId);

        signInButton = findViewById(R.id.signInButtonId);
        signUpTextView = findViewById(R.id.signUpTextViewId);

        signInButton.setOnClickListener(this);
        signUpTextView.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {

        if (view.getId()==R.id.signInButtonId){
            Intent intent = new Intent(this,SignUpActivity.class);
            startActivity(intent);
        }
        else if (view.getId()==R.id.signUpTextViewId){
            Intent intent = new Intent(this,SignUpActivity.class);
            startActivity(intent);
        }


    }
}