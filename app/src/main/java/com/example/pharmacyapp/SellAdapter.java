package com.example.pharmacyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SellAdapter extends RecyclerView.Adapter<SellAdapter.MyViewHolder> {


    Context context;
    ArrayList<SellMedicineDataHolder> list;

    public SellAdapter(Context context, ArrayList<SellMedicineDataHolder> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.sell_details_sample_layout,parent,false);
        return new SellAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        SellMedicineDataHolder data = list.get(position);
        holder.sellMedicineNameTextView.setText(data.getMedicine_name());
        holder.sellDateTextView.setText(data.getSell_date());
        holder.sellQuantityTextView.setText(data.getSell_quantity());
        holder.totalSellPriceTextView.setText(data.getTotal_price());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView sellMedicineNameTextView,sellDateTextView, sellQuantityTextView, totalSellPriceTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //supplierNameTextView = itemView.findViewById(R.id.supplierNameTextView);
            sellMedicineNameTextView = itemView.findViewById(R.id.sellMedicineNameTextView);
            sellDateTextView = itemView.findViewById(R.id.sellDateTextView);
            sellQuantityTextView = itemView.findViewById(R.id.sellQuantityTextView);
            totalSellPriceTextView = itemView.findViewById(R.id.totalSellPriceTextView);

        }
    }


}
