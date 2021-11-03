package com.example.pharmacyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MedicineAll extends AppCompatActivity implements View.OnClickListener {

    private CardView medicineCategoryCardView, medicineTypeCardView, medicineUnitCardView,
            genericNameCardView, manufactureCardView, addMedicineCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_all);

        medicineCategoryCardView = findViewById(R.id.medicineCategoryCardViewId);
        medicineTypeCardView = findViewById(R.id.medicineTypeCardViewId);
        medicineUnitCardView = findViewById(R.id.medicineUnitCardViewId);
        genericNameCardView = findViewById(R.id.genericNameCardViewId);
        manufactureCardView = findViewById(R.id.manufactureCardViewId);
        addMedicineCardView = findViewById(R.id.addMedicineCardViewId);

        medicineCategoryCardView.setOnClickListener(this);
        medicineUnitCardView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.medicineCategoryCardViewId){
            Intent intent = new Intent(this,MedicineCategory.class);
            startActivity(intent);
        }
    }
}