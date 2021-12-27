package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
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

    /*DatePickerDialog.OnDateSetListener setListener;*/

    Spinner manufactureSpinner, medicineNameSpinner, paymentTypeSpinner;
    TextView storeQuantityTextView, totalPrice;

    EditText  buyDateEditText, batchIdEditText, expireDateEditText,
            quantityEditText, manufacturePriceEditText;

    Button saveButton;


    //String storeQuantity;

    DatabaseReference db, dbAccount, dbStock, dbref, databaseReference, dbTv;


    //ValueEventListener listener4;
    ValueEventListener listener;

    ArrayList<String> menufacturelist;
    ArrayList<String> medicineNamelist;
    ArrayList<String> manufacturerPriceList;
    ArrayList<String> paymentTypeList;

    ArrayAdapter<String> menufactureadapter;
    ArrayAdapter<String> medicineNameadapter;
    ArrayAdapter<String> paymentTypeAdapter;


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
        paymentTypeSpinner = findViewById(R.id.spinnerPaymentTypeId);
        saveButton = findViewById(R.id.saveButtonId);

        //storeQuantityTextView = findViewById(R.id.storeQuantityId);

        buyDateEditText = findViewById(R.id.buyDateId);
        batchIdEditText = findViewById(R.id.batchId);
        expireDateEditText = findViewById(R.id.expierDateId);
        quantityEditText = findViewById(R.id.quantityId);
        manufacturePriceEditText = findViewById(R.id.manufacturePriceId);

        totalPrice = findViewById(R.id.totalPriceId);

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

        dbAccount = FirebaseDatabase.getInstance().getReference(user.getUid())
                .child("Medicine").child("Accounts");

        dbTv = FirebaseDatabase.getInstance().getReference(user.getUid())
                .child(":Medicine").child("Medicine List");

        databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid())
                .child("Medicine").child("Purchase Medicine");

        dbStock = FirebaseDatabase.getInstance().getReference(user.getUid())
                .child("Medicine").child("Stock Medicine");

        //Manufacture
        menufacturelist = new ArrayList<>();
        menufactureadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,menufacturelist);
        manufactureSpinner.setAdapter(menufactureadapter);

        //Medicine Name
        medicineNamelist = new ArrayList<>();
        manufacturerPriceList = new ArrayList<>();

        //Payment type
        paymentTypeList = new ArrayList<>();

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
        fetchPaymentTypeData();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertPurchaseMedicine();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertIntoStock();
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

                /*menufacturelist.add(0, "Choose Manufacture");*/

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

                /*medicineNamelist.add(0,"Choose Medicine Name");*/

                for (DataSnapshot medicineNamedata : snapshot.getChildren()){

                    addMedicineDataHolder data = medicineNamedata.getValue(addMedicineDataHolder.class);
                    if (data != null)
                    {
                        medicineNamelist.add(data.getM_name());
                        manufacturerPriceList.add(data.getManufacture_price());
                    }
                }
                setMedicineAdapter(medicineNamelist, manufacturerPriceList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                System.out.println("Failed Message ------------"+error.getMessage());
            }
        });
    }

    private void setMedicineAdapter(ArrayList<String> list, ArrayList<String> manufacturerPriceList) {
        medicineNameadapter = new ArrayAdapter<>(PurchaseMedicine.this, android.R.layout.simple_spinner_dropdown_item, list);
        medicineNameSpinner.setAdapter(medicineNameadapter);

        medicineNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String mPrice = manufacturerPriceList.get(position);

                manufacturePriceEditText.setText(mPrice);

                int price = Integer.parseInt(manufacturePriceEditText.getText().toString());

                TextWatcher textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (!manufacturePriceEditText.getText().toString().equals("") && !quantityEditText.getText().toString().equals("")){

                            int temp1 = Integer.parseInt(quantityEditText.getText().toString());

                            totalPrice.setText(String.valueOf(price * temp1));
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                };
                manufacturePriceEditText.addTextChangedListener(textWatcher);
                quantityEditText.addTextChangedListener(textWatcher);


                /*mPrice = mPrice.replaceAll("[^-?0-9]+","");*/

                /*mfTextview.setText(manufacturerPriceList.get(position)+"/-");*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void fetchPaymentTypeData() {

        dbAccount.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                /*paymentTypeList.add(0,"Choose Account");*/

                for (DataSnapshot paymentType : snapshot.getChildren()){
                    addAccountDataHolder data = paymentType.getValue(addAccountDataHolder.class);
                    if (data != null){
                        paymentTypeList.add(data.getBank_name());
                    }
                }
                setAccountAdapter(paymentTypeList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setAccountAdapter(ArrayList<String> paymentTypeList) {

        paymentTypeAdapter = new ArrayAdapter<>(PurchaseMedicine.this, android.R.layout.simple_spinner_dropdown_item,paymentTypeList);
        paymentTypeSpinner.setAdapter(paymentTypeAdapter);

    }

    //purchase medicine
    private void insertPurchaseMedicine() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String key = databaseReference.push().getKey();

        String s_manufacture = manufactureSpinner.getSelectedItem().toString();
        String s_medicine_name = medicineNameSpinner.getSelectedItem().toString();
        String buy_date = buyDateEditText.getText().toString().trim();
        //Add new
        String payment_type = paymentTypeSpinner.getSelectedItem().toString();
        String batch_id = batchIdEditText.getText().toString().trim();
        String expire_date = expireDateEditText.getText().toString().trim();

        //Here will come stock quantity from stock table

        String quantity = quantityEditText.getText().toString().trim();

        //Delete unit price from here

        String manufacture_price = manufacturePriceEditText.getText().toString().trim();
        String total_price = totalPrice.getText().toString();
        purchaseMedicineDataHolder obj = new purchaseMedicineDataHolder(s_manufacture, s_medicine_name, buy_date, payment_type, batch_id, expire_date, quantity, manufacture_price, total_price);

        assert user != null;
        databaseReference= FirebaseDatabase.getInstance().getReference(user.getUid());
        assert key != null;
        databaseReference.child("Medicine").child("Purchase Medicine").child(key).setValue(obj);

        buyDateEditText.setText("");
        batchIdEditText.setText("");
        expireDateEditText.setText("");
        quantityEditText.setText("");
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
        }

        if (batch_id.isEmpty()){
            batchIdEditText.setError("Required");
            batchIdEditText.requestFocus();
            return;
        }

        if (expire_date.isEmpty()){
            batchIdEditText.setError("Required");
            batchIdEditText.requestFocus();
            return;
        }

        if (quantity.isEmpty()){
            quantityEditText.setError("Required");
            quantityEditText.requestFocus();
            return;
        }


        if (manufacture_price.isEmpty()){
            manufacturePriceEditText.setError("Required");
            manufacturePriceEditText.requestFocus();
            return;
        }

        Toast.makeText(getApplicationContext(),"Medicine Purchase Successfully",Toast.LENGTH_LONG).show();

    }

    private void insertIntoStock() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String key = databaseReference.push().getKey();

        String manufactureName = manufactureSpinner.getSelectedItem().toString();
        String medicineName = medicineNameSpinner.getSelectedItem().toString();
        String buyDate = buyDateEditText.getText().toString();
        String paymentType = paymentTypeSpinner.getSelectedItem().toString();
        String batchId = batchIdEditText.getText().toString().trim();
        String expireDate = expireDateEditText.getText().toString();
        String quantity = quantityEditText.getText().toString().trim();
        String manufacturePrice = manufacturePriceEditText.getText().toString();
        String total_Price = totalPrice.getText().toString();


        StockMedicineDataHolder obj = new StockMedicineDataHolder(manufactureName, medicineName, buyDate, paymentType, batchId, expireDate, quantity, manufacturePrice, total_Price);

        assert user != null;
        databaseReference= FirebaseDatabase.getInstance().getReference(user.getUid());
        assert key != null;
        databaseReference.child("Medicine").child("Stock Medicine").child(key).setValue(obj);

        if (buyDate.isEmpty()){
            buyDateEditText.setError("Required");
            buyDateEditText.requestFocus();
            return;
        }

        Toast.makeText(getApplicationContext(),"Medicine Added To Stock",Toast.LENGTH_LONG).show();


    }

}