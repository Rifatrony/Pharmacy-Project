package com.example.pharmacyapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class StockAdapter extends FirebaseRecyclerAdapter<StockMedicineDataHolder,StockAdapter.myViewHolder> {

    public StockAdapter(@NonNull FirebaseRecyclerOptions<StockMedicineDataHolder> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull StockMedicineDataHolder model) {

        holder.medicineNameTextView.setText(model.getMedicineName());
        holder.manufactureNameTextView.setText(model.getManufactureName());
        holder.stockTextView.setText(model.getStock_quantity());


        holder.medicineNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new StockDetails(model.getManufactureName(),model.getMedicineName(),model.getBuyDate(),model.getPaymentType(),model.getBatchId(), model.getExpireDate(),model.getStock_quantity(),model.getManufacturePrice(),model.getTotal_Price())).addToBackStack(null).commit();
            }
        });

        holder.manufactureNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new StockDetails(model.getManufactureName(),model.getMedicineName(),model.getBuyDate(),model.getPaymentType(),model.getBatchId(), model.getExpireDate(),model.getStock_quantity(),model.getManufacturePrice(),model.getTotal_Price())).addToBackStack(null).commit();
            }
        });

        holder.stockTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new StockDetails(model.getManufactureName(),model.getMedicineName(),model.getBuyDate(),model.getPaymentType(),model.getBatchId(), model.getExpireDate(),model.getStock_quantity(),model.getManufacturePrice(),model.getTotal_Price())).addToBackStack(null).commit();
            }
        });
    }


    //Second we have to create a view and have to return it
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_stock_layout,parent,false);
        return new myViewHolder(view);
    }


    // First we write this part of code when make any adapter

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView medicineNameTextView, manufactureNameTextView, stockTextView;

        public myViewHolder(@NonNull View itemView) {

            super(itemView);

            medicineNameTextView = itemView.findViewById(R.id.medicineNameTextView);
            manufactureNameTextView = itemView.findViewById(R.id.manufactureNameTextView);
            stockTextView = itemView.findViewById(R.id.stockTextView);
        }
    }

}
