package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class SellMedicine extends AppCompatActivity {

    Spinner medicineNameSpinner, paymentTypeSpinner;

    TextInputLayout customerNameEditText, sellDateEditText,
            sellQuantityEditText, sellPriceEditText, sellPaidAmount;

    EditText totalPrice, sellDueAmount;
    TextView stockQuantityTextView;

    Button sellButton;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    ArrayList<String> medicineNamelist;
    ArrayList<String> paymentTypeList;
    ArrayList<String> sellPriceList;

    ArrayAdapter<String> medicineNameadapter;
    ArrayAdapter<String> paymentTypeAdapter;

    DatabaseReference db, dbAccount, dbSell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_medicine);

        this.setTitle("Sell Medicine");


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

        dbSell = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Sell Medicine");

        //Medicine Name
        medicineNamelist = new ArrayList<>();
        sellPriceList = new ArrayList<>();


        //Payment type
        paymentTypeList = new ArrayList<>();

        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sellMedicine();
            }
        });

        fetchMedicineNameData();
        fetchPaymentTypeData();
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
                    }
                }
                setMedicineAdapter(medicineNamelist, sellPriceList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                System.out.println("Failed Message ------------"+error.getMessage());
            }
        });
    }

    private void setMedicineAdapter(ArrayList<String> list, ArrayList<String> manufacturerPriceList) {
        medicineNameadapter = new ArrayAdapter<>(SellMedicine.this, android.R.layout.simple_spinner_dropdown_item, list);
        medicineNameSpinner.setAdapter(medicineNameadapter);

        medicineNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String sellPrice = sellPriceList.get(position);

                //int paid_amount = Integer.parseInt(purchasePaidAmount.getEditText().getText().toString());

                sellPriceEditText.getEditText().setText(sellPrice);

                int price = Integer.parseInt(sellPriceEditText.getEditText().getText().toString());
                //int paidPrice = Integer.parseInt(sellPaidAmount.getEditText().getText().toString());

                TextWatcher textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (!sellPriceEditText.getEditText().getText().toString().equals("") && !sellQuantityEditText.getEditText().getText().toString().equals("")){

                            int temp1 = Integer.parseInt(sellQuantityEditText.getEditText().getText().toString());
                            int res= price*temp1;
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

                                        int temp2 = Integer.parseInt(sellPaidAmount.getEditText().getText().toString());
                                        int result = res - temp2;
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

        String sell_date= customerNameEditText.getEditText().getText().toString();
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

        Toast.makeText(getApplicationContext(),"Medicine Sell",Toast.LENGTH_LONG).show();

    }
}