package com.example.pharmacyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Accounts extends AppCompatActivity {
    CardView bankAccountCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        this.setTitle("Accounts");


        bankAccountCardView = findViewById(R.id.bankAccountCardViewId);
        bankAccountCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddAccount.class);
                startActivity(intent);
            }
        });
    }
}