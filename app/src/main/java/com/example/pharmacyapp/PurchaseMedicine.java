package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.Objects;

public class PurchaseMedicine extends AppCompatActivity {


    DatePickerDialog.OnDateSetListener setListener;

    Spinner manufactureSpinner, medicineNameSpinner;

    EditText  buyDateEditText, batchIdEditText, expireDateEditText,
            quantityEditText, unitPriceEditText, manufacturePriceEditText;

    Button saveButton;

    DatabaseReference db;
    DatabaseReference dbref;
    DatabaseReference databaseReference;

    //ValueEventListener listener4;
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

        manufactureSpinner = findViewById(R.id.spinnerManufactureId);
        medicineNameSpinner = findViewById(R.id.spinnerMedicineNameId);
        saveButton = findViewById(R.id.saveButtonId);





        buyDateEditText = findViewById(R.id.buyDateId);
        batchIdEditText = findViewById(R.id.batchId);
        expireDateEditText = findViewById(R.id.expierDateId);
        quantityEditText = findViewById(R.id.quantityId);
        unitPriceEditText = findViewById(R.id.unitPriceId);
        manufacturePriceEditText = findViewById(R.id.manufacturePriceId);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        //String key = db.push().getKey();

        assert user != null;
        dbref = FirebaseDatabase.getInstance().getReference(user.getUid())
                .child("Medicine").child("Manufacture Name");

        db = FirebaseDatabase.getInstance().getReference(user.getUid())
                .child("Medicine").child("Medicine List");

        databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid())
                .child("Medicine").child("Purchase Medicine");

        //Manufacture
        menufacturelist = new ArrayList<>();
        menufactureadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,menufacturelist);
        manufactureSpinner.setAdapter(menufactureadapter);

        //Medicine Name
        medicineNamelist = new ArrayList<>();

        // Date picker of buy medicine date

        buyDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(PurchaseMedicine.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month +1;
                        String date = day+"/"+month+"/"+year;
                        buyDateEditText.setText(date);
                    }
                },year,month,day);

                datePickerDialog.show();
            }
        });

        // Date picker of expire medicine date

        expireDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(PurchaseMedicine.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month +1;
                        String date = day+"/"+month+"/"+year;
                        expireDateEditText.setText(date);
                    }
                },year,month,day);

                datePickerDialog.show();
            }
        });

        fetchManufactureData();
        fetchMedicineNameData();


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertPurchaseMedicine();

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

    // fetch manufacture name through spinner
    private void fetchManufactureData() {

        listener = dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot menudata : snapshot.getChildren())
                    menufacturelist.add(Objects.requireNonNull(menudata.getValue()).toString());

                menufactureadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    // fetch medicine name

    private void fetchMedicineNameData() {

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot medicineNamedata : snapshot.getChildren()){


                    addMedicineDataHolder data = medicineNamedata.getValue(addMedicineDataHolder.class);
                    if (data != null)
                    {
                        medicineNamelist.add(data.getM_name());

                    }

                }

                /*  System.out.println("List SIze ----> "+ medicineNamelist.size());*//**/

                setMedicineAdapter(medicineNamelist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                System.out.println("Failed Message ------------"+error.getMessage());
            }
        });

    }

    private void setMedicineAdapter(ArrayList<String> list) {


        medicineNameadapter = new ArrayAdapter<>(PurchaseMedicine.this, android.R.layout.simple_spinner_dropdown_item, list);
        medicineNameSpinner.setAdapter(medicineNameadapter);
        // medicineNameadapter.notifyDataSetChanged();

    }

    //purchase medicine
    private void insertPurchaseMedicine() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String key = databaseReference.push().getKey();

        String s_manufacture = manufactureSpinner.getSelectedItem().toString();
        String s_medicine_name = medicineNameSpinner.getSelectedItem().toString();
        String buy_date = buyDateEditText.getText().toString().trim();
        String batch_id = batchIdEditText.getText().toString().trim();
        String expire_date = expireDateEditText.getText().toString().trim();
        String quantity = quantityEditText.getText().toString().trim();
        String unit_price = unitPriceEditText.getText().toString().trim();
        String manufacture_price = manufacturePriceEditText.getText().toString().trim();

        purchaseMedicineDataHolder obj = new purchaseMedicineDataHolder(s_manufacture, s_medicine_name, buy_date, batch_id, expire_date, quantity, unit_price, manufacture_price);

        assert user != null;
        databaseReference= FirebaseDatabase.getInstance().getReference(user.getUid());
        assert key != null;
        databaseReference.child("Medicine").child("Purchase Medicine").child(key).setValue(obj);

        buyDateEditText.setText("");
        batchIdEditText.setText("");
        expireDateEditText.setText("");
        quantityEditText.setText("");
        unitPriceEditText.setText("");
        manufacturePriceEditText.setText("");

        if (buy_date.isEmpty()){
            buyDateEditText.setError("Required");
            buyDateEditText.requestFocus();
            return;
        }

        if (batch_id.isEmpty()){
            batchIdEditText.setError("Required");
            batchIdEditText.requestFocus();
            return;
        } if (batch_id.isEmpty()){
            batchIdEditText.setError("Required");
            batchIdEditText.requestFocus();
            return;
        }
        if (expire_date.isEmpty()){
            batchIdEditText.setError("Required");
            batchIdEditText.requestFocus();
            return;
        } if (quantity.isEmpty()){
            quantityEditText.setError("Required");
            quantityEditText.requestFocus();
            return;
        }
        if (unit_price.isEmpty()){
            unitPriceEditText.setError("Required");
            unitPriceEditText.requestFocus();
            return;
        }
        if (manufacture_price.isEmpty()){
            manufacturePriceEditText.setError("Required");
            manufacturePriceEditText.requestFocus();
            return;
        }


        Toast.makeText(getApplicationContext(),"Medicine Purchase Successfully",Toast.LENGTH_LONG).show();

    }

}