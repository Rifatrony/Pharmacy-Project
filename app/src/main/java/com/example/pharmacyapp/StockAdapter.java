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

public class StockAdapter extends FirebaseRecyclerAdapter<purchaseMedicineDataHolder,StockAdapter.myViewHolder> {

    public StockAdapter(@NonNull FirebaseRecyclerOptions<purchaseMedicineDataHolder> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull purchaseMedicineDataHolder model) {

        holder.medicineNameTextView.setText(model.getS_medicine_name());
        holder.manufactureNameTextView.setText(model.getS_manufacture());
        holder.buyDateTextView.setText(model.getBuy_date());


        holder.medicineNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new StockDetails(model.getS_manufacture(),model.getS_medicine_name(),model.getBuy_date(),model.getPayment_type(),model.getBatch_id(), model.getExpire_date(),model.getQuantity(),model.getManufacture_price(),model.getTotal_price())).addToBackStack(null).commit();
            }
        });

        holder.manufactureNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new StockDetails(model.getS_manufacture(),model.getS_medicine_name(),model.getBuy_date(),model.getPayment_type(),model.getBatch_id(), model.getExpire_date(),model.getQuantity(),model.getManufacture_price(),model.getTotal_price())).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_stock_layout,parent,false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView medicineNameTextView, manufactureNameTextView, buyDateTextView;

        public myViewHolder(@NonNull View itemView) {


            super(itemView);

            medicineNameTextView = itemView.findViewById(R.id.medicineNameTextView);
            manufactureNameTextView = itemView.findViewById(R.id.manufactureNameTextView);
            buyDateTextView = itemView.findViewById(R.id.buyDateTextView);
        }
    }

}
