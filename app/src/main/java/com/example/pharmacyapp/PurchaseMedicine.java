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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import java.util.HashMap;
import java.util.Objects;

public class PurchaseMedicine extends AppCompatActivity {

    /*DatePickerDialog.OnDateSetListener setListener;*/

    Spinner manufactureSpinner, medicineNameSpinner, paymentTypeSpinner, supplierSpinner;
    TextView stockQuantityTextView, totalPrice, dueAmount;
    String selectedMedicineUid = null;
    String selectedSupplierUid = null;
    String purchase_Uid;
    String supplier_uid;
    String supplierUid;

    EditText expireDateEditText, buyDateEditText;
    TextInputLayout batchIdEditText,
            quantityEditText, manufacturePriceEditText, purchasePaidAmount;

    Button saveButton;
    FirebaseUser user;
    //String storeQuantity;

    DatabaseReference db, dbAccount, dbStock, dbref, databaseReference, dbTv, dbSupplier;

    ArrayList<String> menufacturelist;
    ArrayList<String> supplierList;

    ArrayList<String> medicineNamelist;
    ArrayList<String> medicineUidList;
    ArrayList<String> supplierUidList;
    ArrayList<String> manufacturerPriceList;
    ArrayList<String> paymentTypeList;

    ArrayAdapter<String> menufactureadapter;
    ArrayAdapter<String> supplierAdapter;
    ArrayAdapter<String> medicineNameadapter;
    ArrayAdapter<String> paymentTypeAdapter;

    double recentMedicineQuantity;
    double finalMedicineQuantity;

    //ArrayAdapter<String> stockMedicineNameAdapter;


    int medicineStockFromDb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_medicine);

        getMedicineStockFormDb();


        this.setTitle("Purchase");

        //Add back Button on tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        manufactureSpinner = findViewById(R.id.spinnerManufactureId);
        medicineNameSpinner = findViewById(R.id.spinnerMedicineNameId);
        paymentTypeSpinner = findViewById(R.id.spinnerPaymentTypeId);
        supplierSpinner = findViewById(R.id.spinnerSuppilerId);
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


        dbSupplier = FirebaseDatabase.getInstance().getReference(user.getUid())
                .child("Medicine").child("Supplier List");

        //Manufacture
        menufacturelist = new ArrayList<>();
        menufactureadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,menufacturelist);
        manufactureSpinner.setAdapter(menufactureadapter);

        supplierList = new ArrayList<>();
        supplierAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,supplierList);
        supplierSpinner.setAdapter(supplierAdapter);

        //Medicine Name
        medicineNamelist = new ArrayList<>();
        manufacturerPriceList = new ArrayList<>();
        medicineUidList = new ArrayList<>();

        //stockQuantityList = new ArrayList<>();


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
        fetchSupplier();
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

    private void getMedicineStockFormDb() {
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
    private void fetchSupplier() {
        dbSupplier.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot supplierName : snapshot.getChildren()){

                    SupplierDataHolder data = supplierName.getValue(SupplierDataHolder.class);
                    if (data != null)
                    {
                        supplierList.add(data.getSupplierName());
                        //supplierUidList.add(data.getSupplier_uid());
                    }
                    //supplierList.add(Objects.requireNonNull(menudata.getValue()).toString());
                }
                setSupplierAdapter(supplierList, supplierUidList);


                //supplierAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setSupplierAdapter(ArrayList<String> list, ArrayList<String> supplierUidList ) {
        supplierAdapter = new ArrayAdapter<>(PurchaseMedicine.this, android.R.layout.simple_spinner_dropdown_item, list);
        supplierSpinner.setAdapter(supplierAdapter);


        supplierSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //String supplierUid = supplierUidList.get(i);
                //selectedSupplierUid = supplierUid;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    // fetch manufacture name through spinner
    private void fetchManufactureData() {
        dbref.addValueEventListener(new ValueEventListener() {
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
                        medicineUidList.add(data.getUid());
                    }
                }
                setMedicineAdapter(medicineNamelist, manufacturerPriceList, medicineUidList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                System.out.println("Failed Message ------------"+error.getMessage());
            }
        });
    }

    private void setMedicineAdapter(ArrayList<String> list, ArrayList<String> manufacturerPriceList, ArrayList<String> medicineUidList ) {
        medicineNameadapter = new ArrayAdapter<>(PurchaseMedicine.this, android.R.layout.simple_spinner_dropdown_item, list);
        medicineNameSpinner.setAdapter(medicineNameadapter);

        medicineNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String medicineUid = medicineUidList.get(position);
                selectedMedicineUid = medicineUid;

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference quantityRef = FirebaseDatabase.getInstance().getReference(user.getUid()+"/Medicine/Stock Medicine/"+medicineUid);
                quantityRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()) {
                            StockMedicineDataHolder data = snapshot.getValue(StockMedicineDataHolder.class);
                            if (data != null) {
                                stockQuantityTextView.setText(data.getStock_quantity());
                                recentMedicineQuantity = Double.parseDouble(data.getStock_quantity());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                String mPrice = manufacturerPriceList.get(position);

                manufacturePriceEditText.getEditText().setText(mPrice);

                double price = Double.parseDouble(manufacturePriceEditText.getEditText().getText().toString());

                TextWatcher textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (!manufacturePriceEditText.getEditText().getText().toString().equals("") && !quantityEditText.getEditText().getText().toString().equals("")){

                            double temp1 = Double.parseDouble(quantityEditText.getEditText().getText().toString());
                            double res= price*temp1;

                            totalPrice.setText(String.valueOf(res));

                            TextWatcher textWatcher1 = new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {




                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                    if (!totalPrice.getText().toString().equals("") && !purchasePaidAmount.getEditText().getText().toString().equals("")) {

                                        double temp2 = Integer.parseInt(purchasePaidAmount.getEditText().getText().toString());
                                        double result = res - temp2;

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

                        if (!editable.toString().equals("")) {

                            finalMedicineQuantity = recentMedicineQuantity + Double.parseDouble(editable.toString());
                            stockQuantityTextView.setText(String.valueOf(finalMedicineQuantity));

                            System.out.println("RECENT STOCk ----> "+finalMedicineQuantity);

                        } else {

                            System.out.println(" NULL  ");
                            stockQuantityTextView.setText(String.valueOf(recentMedicineQuantity));
                        }
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

    //purchase medicine
    private void insertPurchaseMedicine() {

        user = FirebaseAuth.getInstance().getCurrentUser();
        String key = dbStock.push().getKey();

        String s_manufacture = manufactureSpinner.getSelectedItem().toString();
        String s_medicine_name = medicineNameSpinner.getSelectedItem().toString();
        String buy_date = buyDateEditText.getText().toString().trim();
        //Add new
        String payment_type = paymentTypeSpinner.getSelectedItem().toString();
        String batch_id = batchIdEditText.getEditText().getText().toString().trim();
        String expire_date = expireDateEditText.getText().toString().trim();
        String quantity = quantityEditText.getEditText().getText().toString().trim();
        String manufacture_price = manufacturePriceEditText.getEditText().getText().toString().trim();

        String total_price = totalPrice.getText().toString();
        String purchase_paid_amount = purchasePaidAmount.getEditText().getText().toString().trim();
        String purchase_due_amount = dueAmount.getText().toString().trim();

        purchase_Uid = databaseReference.push().getKey();

        //Here will come stock quantity from stock table

        purchaseMedicineDataHolder obj = new purchaseMedicineDataHolder(s_manufacture, s_medicine_name, buy_date, payment_type, batch_id, expire_date, quantity, manufacture_price, total_price, purchase_paid_amount, purchase_due_amount, purchase_Uid);

        assert user != null;
        databaseReference= FirebaseDatabase.getInstance().getReference(user.getUid());
        assert key != null;
        databaseReference.child("Medicine").child("Purchase Medicine").child(purchase_Uid).setValue(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                SupplierDataHolder obj = new SupplierDataHolder("",supplier_uid);

                String supplierUid = obj.getSupplier_uid();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference stockRef = FirebaseDatabase.getInstance().getReference(user.getUid()+"/Medicine/Supplier Ledger/"+purchase_Uid);

                supplierLedgerDataHolder data = new supplierLedgerDataHolder(purchase_Uid,supplierUid, quantity);
                stockRef.setValue(data);
            }
        });

        //  StockMedicineDataHolder data = new StockMedicineDataHolder(manufactureName, medicineName, buyDate, paymentType, batchId, expireDate, total_quantity, manufacturePrice, total_Price, key);

        assert user != null;
        dbStock= FirebaseDatabase.getInstance().getReference(user.getUid());
        assert key != null;

        HashMap<String, Object> stockMap = new HashMap<>();
        stockMap.put("stock_quantity", stockQuantityTextView.getText().toString());

        dbStock.child("Medicine").child("Stock Medicine").child(selectedMedicineUid).updateChildren(stockMap);

        buyDateEditText.setText("");
        batchIdEditText.getEditText().setText("");
        expireDateEditText.setText("");
        quantityEditText.getEditText().setText("");
        manufacturePriceEditText.getEditText().setText("");
        purchasePaidAmount.getEditText().setText("");
        totalPrice.setText("");
        dueAmount.setText("");

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
    }
}