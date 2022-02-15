package com.example.pharmacyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class All_Report extends AppCompatActivity {


    Button purchaseDetailsButton, sellDetailsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_report);

        this.setTitle("All Report");


        purchaseDetailsButton = findViewById(R.id.purchaseDetailsButtonId);
        sellDetailsButton = findViewById(R.id.sellDetailsButtonId);


        purchaseDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(All_Report.this, PurchaseDetails.class );
                startActivity(intent);
            }
        });

        sellDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(All_Report.this, SellDetails.class );
                startActivity(intent);
            }
        });

    }
}