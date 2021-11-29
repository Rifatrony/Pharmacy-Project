package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PurchaseMedicine extends AppCompatActivity {

    EditText  buyDateEditText, batchIdEditText, expireDateEditText,
            quantityEditText, unitPriceEditText, manufacturePriceEditText;

    Button saveButton;

    Spinner menufacturespinner, medicineNamespinner;

    DatabaseReference db;
    DatabaseReference dbref;

    ValueEventListener listener4;
    ValueEventListener listener;

    ArrayList<String> menufacturelist;
    ArrayList<String> medicineNamelist;

    ArrayAdapter<String> menufactureadapter;
    ArrayAdapter<String> medicineNameadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_medicine);

        this.setTitle("Purchase");

        //Add back Button on tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        buyDateEditText = findViewById(R.id.buyDateId);
        batchIdEditText = findViewById(R.id.batchId);
        expireDateEditText = findViewById(R.id.expierDateId);
        quantityEditText = findViewById(R.id.quantityId);
        unitPriceEditText = findViewById(R.id.unitPriceId);
        manufacturePriceEditText = findViewById(R.id.manufacturePriceId);

        saveButton = findViewById(R.id.saveButtonId);

        menufacturespinner = findViewById(R.id.spinnerManufactureId);
        medicineNamespinner = findViewById(R.id.spinnerMedicineNameId);

        //String key = db.push().getKey();

        dbref = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Manufacture Name");
        db = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Medicine List").child("m_name");


        //Manufacture
        menufacturelist = new ArrayList<>();
        menufactureadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,menufacturelist);
        menufacturespinner.setAdapter(menufactureadapter);

        //Medicine Name
        medicineNamelist = new ArrayList<>();
        medicineNameadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,medicineNamelist);
        medicineNamespinner.setAdapter(medicineNameadapter);

        fetchManufactureData();
        fetchMedicineNameData();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //insertPurchaseMedicine();
            }
        });


    }




    //Back button on top navbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    // fetch manufacture data through spinner
    private void fetchManufactureData() {

        listener = dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot menudata : snapshot.getChildren())
                    menufacturelist.add(menudata.getValue().toString());

                menufactureadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void fetchMedicineNameData() {
        listener4 = db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot medicineNamedata : snapshot.getChildren())
                    medicineNamelist.add(medicineNamedata.getValue().toString());

                medicineNameadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //purchase medicine
    private void insertPurchaseMedicine() {


        String Buy_Date = buyDateEditText.getText().toString().trim();
        String Batch_ID = batchIdEditText.getText().toString().trim();
        String Expire_Date = expireDateEditText.getText().toString().trim();
        String Quantity = quantityEditText.getText().toString().trim();
        String Unit_Price = unitPriceEditText.getText().toString().trim();
        String Manufacture_Price = manufacturePriceEditText.getText().toString().trim();
        String Manufacture = menufacturespinner.getSelectedItem().toString();
        String Medicine_Name = medicineNamespinner.getSelectedItem().toString();
    }

}