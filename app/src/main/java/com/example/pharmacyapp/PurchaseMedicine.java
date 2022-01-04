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
import com.google.android.material.textfield.TextInputLayout;
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
    TextView stockQuantityTextView, totalPrice, dueAmount;

    TextInputLayout buyDateEditText, batchIdEditText, expireDateEditText,
            quantityEditText, manufacturePriceEditText, purchasePaidAmount;

    Button saveButton;

    //String storeQuantity;

    DatabaseReference db, dbAccount, dbStock, dbref, databaseReference, dbTv;


    //ValueEventListener listener4;
    ValueEventListener listener;

    ArrayList<String> menufacturelist;

    ArrayList<String> medicineNamelist;
    //ArrayList<String> stockMedicineNameList;
    ArrayList<String> manufacturerPriceList;
    ArrayList<String> stockQuantityList;
    ArrayList<String> paymentTypeList;

    ArrayAdapter<String> menufactureadapter;
    ArrayAdapter<String> medicineNameadapter;
    //ArrayAdapter<String> stockMedicineNameAdapter;

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
        dueAmount = findViewById(R.id.purchaseDueAmountId);
        purchasePaidAmount = findViewById(R.id.purchasPaidAmountId);
        manufacturePriceEditText = findViewById(R.id.manufacturePriceId);

        totalPrice = findViewById(R.id.totalPriceId);
        stockQuantityTextView = findViewById(R.id.stockQuantityTextViewId);

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
        stockQuantityList = new ArrayList<>();


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
                        buyDateEditText.getEditText().setText(date);
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
                        expireDateEditText.getEditText().setText(date);
                    }
                },year,month,day);

                datePickerDialog.show();
            }
        });

        fetchManufactureData();
        fetchMedicineNameData();
        fetchPaymentTypeData();
        //fetchStockQuantity();

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

                //int paid_amount = Integer.parseInt(purchasePaidAmount.getEditText().getText().toString());

                manufacturePriceEditText.getEditText().setText(mPrice);

                int price = Integer.parseInt(manufacturePriceEditText.getEditText().getText().toString());

                TextWatcher textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (!manufacturePriceEditText.getEditText().getText().toString().equals("") && !quantityEditText.getEditText().getText().toString().equals("")){

                            int temp1 = Integer.parseInt(quantityEditText.getEditText().getText().toString());
                            int res= price*temp1;

                            totalPrice.setText(String.valueOf(res));

                            TextWatcher textWatcher1 = new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                    if (!totalPrice.getText().toString().equals("") && !purchasePaidAmount.getEditText().getText().toString().equals("")) {

                                        int temp2 = Integer.parseInt(purchasePaidAmount.getEditText().getText().toString());
                                        int result = res - temp2;

                                        dueAmount.setText(String.valueOf(result));
                                    }
                                }

                                @Override
                                public void afterTextChanged(Editable editable) {

                                }
                            };
                            totalPrice.addTextChangedListener(textWatcher1);
                            purchasePaidAmount.getEditText().addTextChangedListener(textWatcher1);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                };

                manufacturePriceEditText.getEditText().addTextChangedListener(textWatcher);
                quantityEditText.getEditText().addTextChangedListener(textWatcher);
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

    /*private void fetchStockQuantity() {

        dbStock.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot medicineNameData : snapshot.getChildren()){

                    StockMedicineDataHolder data = medicineNameData.getValue(StockMedicineDataHolder.class);
                    if (data != null)
                    {
                        //stockMedicineNameList.add(data.getMedicineName());
                        stockQuantityList.add(data.getStock_quantity());
                    }
                }
                setStockQuantityAdapter(medicineNamelist, stockQuantityList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/


    /*private void setStockQuantityAdapter(ArrayList<String> list, ArrayList<String> stockQuantityList) {
        medicineNameadapter = new ArrayAdapter<>(PurchaseMedicine.this, android.R.layout.simple_spinner_dropdown_item, list);

        medicineNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String sq = stockQuantityList.get((position));
                int ab = Integer.parseInt(sq);

                //int paid_amount = Integer.parseInt(purchasePaidAmount.getEditText().getText().toString());

                stockQuantityTextView.setText(String.valueOf(ab));

                //int price = Integer.parseInt(manufacturePriceEditText.getEditText().getText().toString());

                *//*TextWatcher textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (!manufacturePriceEditText.getEditText().getText().toString().equals("") && !quantityEditText.getEditText().getText().toString().equals("")){

                            int temp1 = Integer.parseInt(quantityEditText.getEditText().getText().toString());
                            int res= price*temp1;

                            totalPrice.setText(String.valueOf(res));
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                };

                manufacturePriceEditText.getEditText().addTextChangedListener(textWatcher);
                quantityEditText.getEditText().addTextChangedListener(textWatcher);*//*
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }*/


    //purchase medicine
    private void insertPurchaseMedicine() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String key = databaseReference.push().getKey();

        String s_manufacture = manufactureSpinner.getSelectedItem().toString();
        String s_medicine_name = medicineNameSpinner.getSelectedItem().toString();
        String buy_date = buyDateEditText.getEditText().getText().toString().trim();
        //Add new
        String payment_type = paymentTypeSpinner.getSelectedItem().toString();
        String batch_id = batchIdEditText.getEditText().getText().toString().trim();
        String expire_date = expireDateEditText.getEditText().getText().toString().trim();
        String quantity = quantityEditText.getEditText().getText().toString().trim();
        String manufacture_price = manufacturePriceEditText.getEditText().getText().toString().trim();

        String total_price = totalPrice.getText().toString();
        String purchase_paid_amount = purchasePaidAmount.getEditText().getText().toString().trim();
        String purchase_due_amount = dueAmount.getText().toString().trim();


        //Here will come stock quantity from stock table

        String manufactureName = s_manufacture;
        String medicineName = s_medicine_name;
        String buyDate = buy_date;
        String paymentType = payment_type;
        String batchId = batch_id;
        String expireDate = expire_date;
        String total_quantity = quantityEditText.getEditText().getText().toString().trim();
        String manufacturePrice = manufacturePriceEditText.getEditText().getText().toString().trim();
        String total_Price = totalPrice.getText().toString();

        //Delete unit price from here

        purchaseMedicineDataHolder obj = new purchaseMedicineDataHolder(s_manufacture, s_medicine_name, buy_date, payment_type, batch_id, expire_date, quantity, manufacture_price, total_price, purchase_paid_amount, purchase_due_amount);

        assert user != null;
        databaseReference= FirebaseDatabase.getInstance().getReference(user.getUid());
        assert key != null;
        databaseReference.child("Medicine").child("Purchase Medicine").child(key).setValue(obj);

        StockMedicineDataHolder data = new StockMedicineDataHolder(manufactureName, medicineName, buyDate, paymentType, batchId, expireDate, total_quantity, manufacturePrice, total_Price);

        assert user != null;
        dbStock= FirebaseDatabase.getInstance().getReference(user.getUid());
        assert key != null;
        dbStock.child("Medicine").child("Stock Medicine").child(key).setValue(data);

        buyDateEditText.getEditText().setText("");
        batchIdEditText.getEditText().setText("");
        expireDateEditText.getEditText().setText("");
        quantityEditText.getEditText().setText("");
        manufacturePriceEditText.getEditText().setText("");

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

        Toast.makeText(getApplicationContext(),"Medicine Purchase Successfully",Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),"Medicine Added To Stock",Toast.LENGTH_LONG).show();

    }
}