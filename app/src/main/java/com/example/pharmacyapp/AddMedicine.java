package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddMedicine extends AppCompatActivity {

    EditText medicineNameEditText, boxPatternEditText,
            medicineCategoryEditText, medicineUnitEditText,
            sellPriceEditText, manufacturePriceEditText, shelfNumberEditText;

    Button addMedicineButton, viewMedicine;


    Spinner menufacturespinner, gnspinner, medicinetypespinner;

    DatabaseReference dbref;
    DatabaseReference db;
    DatabaseReference mtypedb;
    DatabaseReference databaseReference;

    ValueEventListener listener;
    ValueEventListener listener1;
    ValueEventListener listener2;

    ArrayList<String> menufacturelist;
    ArrayList<String> gnlist;
    ArrayList<String> mtypelist;

    ArrayAdapter<String> menufactureadapter;
    ArrayAdapter<String> gnadapter;
    ArrayAdapter<String> mtypeadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        setTitle("Add Medicine");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //add back button tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        medicineNameEditText=findViewById(R.id.medicineNameEditTextId);
        boxPatternEditText=findViewById(R.id.boxpatternEditTextId);
        medicineCategoryEditText=findViewById(R.id.medicineCategoryEditTextId);
        medicineUnitEditText=findViewById(R.id.medicineUnitEditTextId);
        sellPriceEditText=findViewById(R.id.sellPriceEditTextId);
        manufacturePriceEditText=findViewById(R.id.manufacturePriceEditTextId);
        shelfNumberEditText=findViewById(R.id.shelfNumberEditTextId);

        addMedicineButton = findViewById(R.id.addMedicineButtonId);
        viewMedicine = findViewById(R.id.viewMedicineButtonId);


        menufacturespinner = findViewById(R.id.spinnerManufactureId);
        gnspinner = findViewById(R.id.spinnerGenericNameId);
        medicinetypespinner = findViewById(R.id.spinnerMedicineTypeId);

   /*     String key = dbref.push().getKey();*/

        dbref = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Manufacture Name");
        db = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Generic Name");
        mtypedb = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Medicine Type");
        databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Medicine List");

        //Manufacture
        menufacturelist = new ArrayList<>();
        menufactureadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,menufacturelist);
        menufacturespinner.setAdapter(menufactureadapter);


        //Generic Name
        gnlist = new ArrayList<>();
        gnadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,gnlist);
        gnspinner.setAdapter(gnadapter);

        //Medicine type
        mtypelist = new ArrayList<>();
        mtypeadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,mtypelist);
        medicinetypespinner.setAdapter(mtypeadapter);


        addMedicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertMedicine();
            }
        });
        viewMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AddMedicine.this,ViewMedicineActivity.class);
                startActivity(intent);
            }
        });

        fetchManufactureData();
        fetchGenericName();
        fetchMedicineType();
    }



    //add back button tool bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }


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

    private void fetchGenericName() {
        listener1 = db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot gndata : snapshot.getChildren())

                    gnlist.add(gndata.getValue().toString());

                gnadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void fetchMedicineType() {
        listener2 = mtypedb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot mtypedata : snapshot.getChildren())

                    mtypelist.add(mtypedata.getValue().toString());

                mtypeadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void insertMedicine() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String m_name = medicineNameEditText.getText().toString().trim();
        String box_pattern = boxPatternEditText.getText().toString().trim();
        String m_category = medicineCategoryEditText.getText().toString().trim();
        String m_unit = medicineUnitEditText.getText().toString().trim();
        String sell_price = sellPriceEditText.getText().toString().trim();
        String manufacture_price = manufacturePriceEditText.getText().toString().trim();
        String shelf_no = shelfNumberEditText.getText().toString().trim();

        String s_manufacture = menufacturespinner.getSelectedItem().toString();
        String m_type = medicinetypespinner.getSelectedItem().toString();
        String s_genericName = gnspinner.getSelectedItem().toString();


        String key = databaseReference.push().getKey();

        if (m_name.isEmpty()){
            medicineNameEditText.setError("Required");
            medicineNameEditText.requestFocus();
            return;
        }
        if (box_pattern.isEmpty()){
            boxPatternEditText.setError("Required");
            boxPatternEditText.requestFocus();
            return;
        }
        if (m_category.isEmpty()){
            medicineCategoryEditText.setError("Required");
            medicineCategoryEditText.requestFocus();
            return;
        }
        if (m_unit.isEmpty()){
            medicineUnitEditText.setError("Required");
            medicineUnitEditText.requestFocus();
            return;
        }
        if (sell_price.isEmpty()){
            sellPriceEditText.setError("Required");
            sellPriceEditText.requestFocus();
            return;
        }
        if (manufacture_price.isEmpty()){
            manufacturePriceEditText.setError("Required");
            manufacturePriceEditText.requestFocus();
            return;
        }
        if (shelf_no.isEmpty()){
            shelfNumberEditText.setError("Required");
            shelfNumberEditText.requestFocus();
            return;
        }

        addMedicineDataHolder obj = new addMedicineDataHolder(m_name,box_pattern,m_category,m_unit,sell_price,manufacture_price,shelf_no,s_manufacture,m_type,s_genericName);
        FirebaseDatabase addMedicinedb = FirebaseDatabase.getInstance();
        DatabaseReference node = addMedicinedb.getReference(user.getUid());

        node.child("Medicine").child("Medicine List").child(key).setValue(obj);

        medicineNameEditText.setText("");
        boxPatternEditText.setText("");
        medicineCategoryEditText.setText("");
        medicineUnitEditText.setText("");
        sellPriceEditText.setText("");
        manufacturePriceEditText.setText("");
        shelfNumberEditText.setText("");
        menufacturespinner.getSelectedItem();
        gnspinner.getSelectedItem();
        medicinetypespinner.getSelectedItem();
        Toast.makeText(getApplicationContext(),"Medicine Added Successfully",Toast.LENGTH_LONG).show();


    }
}