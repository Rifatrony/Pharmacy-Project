package com.example.pharmacyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        this.setTitle("Customer Details");

        getSupportFragmentManager().beginTransaction().replace(R.id.customerWrapper,new CustomerFirstFragment()).commit();

    }
}