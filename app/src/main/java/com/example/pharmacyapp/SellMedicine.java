package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class SellMedicine extends AppCompatActivity {

    Spinner medicineNameSpinner, paymentTypeSpinner;

    EditText sellDateEditText;
    TextInputLayout customerNameEditText,
            sellQuantityEditText, sellPriceEditText, sellPaidAmount;

    String selectedMedicineUid = null;

    EditText totalPrice, sellDueAmount;
    TextView stockQuantityTextView;

    Button sellButton;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    ArrayList<String> medicineNamelist;
    ArrayList<String> paymentTypeList;
    ArrayList<String> sellPriceList;
    ArrayList<String> medicineUidList;


    ArrayAdapter<String> medicineNameadapter;
    ArrayAdapter<String> paymentTypeAdapter;

    double recentMedicineQuantity;
    double finalMedicineQuantity;

    DatabaseReference db, dbAccount, dbSell, dbStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_medicine);

        this.setTitle(R.string.sell);

        //Add back Button on tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        medicineNameSpinner = findViewById(R.id.spinnerMedicineNameId);
        paymentTypeSpinner = findViewById(R.id.spinnerPaymentTypeId);

        customerNameEditText = findViewById(R.id.customerNameId);
        sellDateEditText = findViewById(R.id.sellDateId);
        sellQuantityEditText = findViewById(R.id.sellQuantityId);
        sellPriceEditText = findViewById(R.id.unitSellPriceId);
        totalPrice = findViewById(R.id.totalSellPriceId);
        sellPaidAmount = findViewById(R.id.sellPaidAmountId);
        sellDueAmount = findViewById(R.id.sellDueAmountId);
        stockQuantityTextView = findViewById(R.id.stockQuantityId);

        sellButton = findViewById(R.id.sellBtnId);

        db = FirebaseDatabase.getInstance().getReference(user.getUid())
                .child("Medicine").child("Medicine List");

        dbAccount = FirebaseDatabase.getInstance().getReference(user.getUid())
                .child("Medicine").child("Accounts");

        dbStock = FirebaseDatabase.getInstance().getReference(user.getUid())
                .child("Medicine").child("Stock Medicine");

        dbSell = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Sell Medicine");

        //Medicine Name
        medicineNamelist = new ArrayList<>();
        sellPriceList = new ArrayList<>();
        medicineUidList = new ArrayList<>();



        //Payment type
        paymentTypeList = new ArrayList<>();

        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sellMedicine();
            }
        });

        sellDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SellMedicine.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month +1;
                        String date = day+"/"+month+"/"+year;
                        sellDateEditText.setText(date);
                    }
                },year,month,day);

                datePickerDialog.show();
            }
        });



        fetchMedicineNameData();
        fetchPaymentTypeData();
    }

    //Back button on top navbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }


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
                        sellPriceList.add(data.getSell_price());
                        medicineUidList.add(data.getUid());
                    }
                }
                setMedicineAdapter(medicineNamelist, sellPriceList, medicineUidList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                System.out.println("Failed Message ------------"+error.getMessage());
            }
        });
    }

    private void setMedicineAdapter(ArrayList<String> list, ArrayList<String> sellPriceList, ArrayList<String> medicineUidList) {
        medicineNameadapter = new ArrayAdapter<>(SellMedicine.this, android.R.layout.simple_spinner_dropdown_item, list);
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

                String sellPrice = sellPriceList.get(position);

                sellPriceEditText.getEditText().setText(sellPrice);

                double price = Double.parseDouble(sellPriceEditText.getEditText().getText().toString());

                TextWatcher textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (!sellPriceEditText.getEditText().getText().toString().equals("") && !sellQuantityEditText.getEditText().getText().toString().equals("")){

                            double temp1 = Double.parseDouble(sellQuantityEditText.getEditText().getText().toString());
                            double res= price*temp1;
                            //int due = res - paidPrice;

                            totalPrice.setText(String.valueOf(res));
                            //sellDueAmount.setText(String.valueOf(due));

                            TextWatcher textWatcher1 = new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                    if (!totalPrice.getText().toString().equals("") && !sellPaidAmount.getEditText().getText().toString().equals("")) {

                                        double temp2 = Double.parseDouble(sellPaidAmount.getEditText().getText().toString());
                                        double result = res - temp2;
                                        //int due = res - paidPrice;

                                        sellDueAmount.setText(String.valueOf(result));
                                    }
                                }

                                @Override
                                public void afterTextChanged(Editable editable) {

                                }
                            };

                            totalPrice.addTextChangedListener(textWatcher1);
                            sellPaidAmount.getEditText().addTextChangedListener(textWatcher1);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (!editable.toString().equals("")) {

                            finalMedicineQuantity = recentMedicineQuantity - Double.parseDouble(editable.toString());
                            stockQuantityTextView.setText(String.valueOf(finalMedicineQuantity));

                            System.out.println("RECENT STOCk ----> "+finalMedicineQuantity);

                        } else {

                            System.out.println(" NULL  ");
                            stockQuantityTextView.setText(String.valueOf(recentMedicineQuantity));
                        }
                    }
                };

                sellPriceEditText.getEditText().addTextChangedListener(textWatcher);
                sellQuantityEditText.getEditText().addTextChangedListener(textWatcher);
                //sellPaidAmount.getEditText().addTextChangedListener(textWatcher);
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

        paymentTypeAdapter = new ArrayAdapter<>(SellMedicine.this, android.R.layout.simple_spinner_dropdown_item,paymentTypeList);
        paymentTypeSpinner.setAdapter(paymentTypeAdapter);

    }

    private void sellMedicine() {


        String customer_name= customerNameEditText.getEditText().getText().toString();
        String medicine_name= medicineNameSpinner.getSelectedItem().toString();

        String sell_date= sellDateEditText.getText().toString();
        String payment_type= paymentTypeSpinner.getSelectedItem().toString();

        String stock_quantity= stockQuantityTextView.getText().toString();
        String sell_quantity= sellQuantityEditText.getEditText().getText().toString();
        String unit_sell_price= sellPriceEditText.getEditText().getText().toString();
        String total_price= totalPrice.getText().toString();
        String paid_amount= sellPaidAmount.getEditText().getText().toString();
        String due_amount= sellDueAmount.getText().toString();


        String key = db.push().getKey();

        SellMedicineDataHolder obj = new SellMedicineDataHolder(customer_name, medicine_name, sell_date, payment_type, stock_quantity,
                sell_quantity, unit_sell_price, total_price, paid_amount, due_amount);

        FirebaseDatabase addAccount = FirebaseDatabase.getInstance();
        DatabaseReference node = addAccount.getReference(user.getUid());

        node.child("Medicine").child("Sell Medicine").child(key).setValue(obj);

        assert user != null;
        dbStock= FirebaseDatabase.getInstance().getReference(user.getUid());
        assert key != null;


        HashMap<String, Object> stockMap = new HashMap<>();
        stockMap.put("stock_quantity", stockQuantityTextView.getText().toString());

        dbStock.child("Medicine").child("Stock Medicine").child(selectedMedicineUid).updateChildren(stockMap);


        Toast.makeText(getApplicationContext(),"Medicine Sell",Toast.LENGTH_LONG).show();

    }
}