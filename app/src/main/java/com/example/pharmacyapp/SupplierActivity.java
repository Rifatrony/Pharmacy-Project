package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SupplierActivity extends AppCompatActivity {

    EditText supplierEditText;
    Button supplierSaveButton;
    DatabaseReference dbSupplier;

    String uid;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);

        this.setTitle("Supplier");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        supplierEditText = findViewById(R.id.supplierEditTextId);
        supplierSaveButton = findViewById(R.id.supplierButtonId);

        dbSupplier = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Supplier List");

        supplierSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertSupplier();
            }
        });
    }

    private void insertSupplier() {
        String data = supplierEditText.getText().toString().trim();

        uid = dbSupplier.push().getKey();

        SupplierDataHolder obj = new SupplierDataHolder(data, uid);

        DatabaseReference stockRef = FirebaseDatabase.getInstance().getReference(user.getUid()+"/Medicine/Supplier List/"+uid);
        stockRef.setValue(obj);

    }
}