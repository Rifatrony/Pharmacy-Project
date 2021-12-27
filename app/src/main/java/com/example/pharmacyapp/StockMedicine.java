package com.example.pharmacyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class StockMedicine extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_medicine);

        this.setTitle("Stock");

        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new RecFragment()).commit();

    }
}