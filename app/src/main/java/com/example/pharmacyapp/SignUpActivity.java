package com.example.pharmacyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText signUpFnameEditText, signUpUnameEditText, signUpEmailEditText, signUpPhoneEditText, signUpConfirmPasswordEditText, sigUpPasswordEditText;
    private Button signUpButton;
    private TextView signInTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpFnameEditText = findViewById(R.id.signUpFullNameEditTextId);
        signUpUnameEditText = findViewById(R.id.signUpUsernameEditTextId);
        signUpEmailEditText = findViewById(R.id.signUpEmailEditTextId);
        signUpPhoneEditText = findViewById(R.id.signUpPhoneNumberEditTextId);
        sigUpPasswordEditText = findViewById(R.id.signUpPasswordEditTextId);
        signUpConfirmPasswordEditText = findViewById(R.id.signUpConfirmPasswordEditTextId);


        signUpButton = findViewById(R.id.signUpButtonId);
        signInTextView = findViewById(R.id.signInTextViewId);

        signInTextView.setOnClickListener(this);
        signUpButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

    }
}