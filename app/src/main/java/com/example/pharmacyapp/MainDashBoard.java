package com.example.pharmacyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainDashBoard extends AppCompatActivity implements View.OnClickListener {

    private CardView medicineCardView,stockCardView,purchaseCardView,
            sellCardView,xCardView,yCardView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dash_board);

        medicineCardView = findViewById(R.id.medicineCardViewId);
        stockCardView = findViewById(R.id.stockCardViewId);
        purchaseCardView = findViewById(R.id.purchaseCardViewId);
        sellCardView = findViewById(R.id.sellCardViewId);
        xCardView = findViewById(R.id.xCardViewId);
        yCardView = findViewById(R.id.yCardViewId);


        medicineCardView.setOnClickListener(this);
        stockCardView.setOnClickListener(this);
        purchaseCardView.setOnClickListener(this);
        sellCardView.setOnClickListener(this);
        xCardView.setOnClickListener(this);
        yCardView.setOnClickListener(this);


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

        }
        if (view.getId()==R.id.sellCardViewId){

        }if (view.getId()==R.id.xCardViewId){

        }
        if (view.getId()==R.id.yCardViewId){

        }


    }
}