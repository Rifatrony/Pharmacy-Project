package com.example.pharmacyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class ExpireSoonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expire_soon);

        this.setTitle("Expire Soon");

        /*LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.activity_expire_soon,null,false);
        drawer.addView(view,0);*/
    }
}