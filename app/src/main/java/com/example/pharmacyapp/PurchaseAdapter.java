package com.example.pharmacyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.MyViewHolder> {


    Context context;
    ArrayList<purchaseMedicineDataHolder> list;

    public PurchaseAdapter(Context context, ArrayList<purchaseMedicineDataHolder> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.purchase_details_sample_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        purchaseMedicineDataHolder data = list.get(position);
        holder.medicineNameTextView.setText(data.getS_medicine_name());
        holder.manufactureNameTextView.setText(data.getS_manufacture());
        holder.quantityTextView.setText(data.getQuantity());
        holder.totalPriceTextView.setText(data.getTotal_price());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView supplierNameTextView, medicineNameTextView,manufactureNameTextView, quantityTextView, totalPriceTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //supplierNameTextView = itemView.findViewById(R.id.supplierNameTextView);
            medicineNameTextView = itemView.findViewById(R.id.purchaseMedicineNameTextView);
            manufactureNameTextView = itemView.findViewById(R.id.manufactureNameTextView);
            quantityTextView = itemView.findViewById(R.id.purchaseQuantityTextView);
            totalPriceTextView = itemView.findViewById(R.id.totalPriceTextView);

        }
    }

}
