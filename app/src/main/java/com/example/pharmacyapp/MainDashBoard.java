package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainDashBoard extends AppCompatActivity implements View.OnClickListener {

    private CardView medicineCardView,stockCardView,purchaseCardView,
            sellCardView,customerCardView,paymentCardView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dash_board);

        this.setTitle("Dashboard");


        //Add back Button on tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        medicineCardView = findViewById(R.id.medicineCardViewId);
        stockCardView = findViewById(R.id.stockCardViewId);
        purchaseCardView = findViewById(R.id.purchaseCardViewId);
        sellCardView = findViewById(R.id.sellCardViewId);
        customerCardView = findViewById(R.id.customerCardViewId);
        paymentCardView = findViewById(R.id.paymentCardViewId);


        medicineCardView.setOnClickListener(this);
        stockCardView.setOnClickListener(this);
        purchaseCardView.setOnClickListener(this);
        sellCardView.setOnClickListener(this);
        customerCardView.setOnClickListener(this);
        paymentCardView.setOnClickListener(this);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        if (view.getId()==R.id.medicineCardViewId){
            Intent intent = new Intent(this,MedicineAll.class);
            startActivity(intent);
        }
        if (view.getId()==R.id.stockCardViewId){

        }
        if (view.getId()==R.id.purchaseCardViewId){

            Intent intent = new Intent(this,PurchaseMedicine.class);
            startActivity(intent);

        }
        if (view.getId()==R.id.sellCardViewId){

        }if (view.getId()==R.id.customerCardViewId){

        }
        if (view.getId()==R.id.paymentCardViewId){

        }


    }
}