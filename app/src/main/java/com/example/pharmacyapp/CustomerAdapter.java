package com.example.pharmacyapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class CustomerAdapter extends FirebaseRecyclerAdapter<SellMedicineDataHolder,CustomerAdapter.myViewHolder> {

    public CustomerAdapter(@NonNull FirebaseRecyclerOptions<SellMedicineDataHolder> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull SellMedicineDataHolder model) {
        holder.customerName.setText(model.getCustomer_name());
        holder.medicineName.setText(model.getMedicine_name());
        holder.total_price.setText(model.getTotal_price());
        holder.due.setText(model.getDue_amount());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_sample_layout,parent,false);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_sample_layout,parent,false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView customerName, medicineName, total_price, due;

        public myViewHolder(@NonNull View itemView) {

            super(itemView);

            customerName = itemView.findViewById(R.id.customerNameTextView);
            medicineName = itemView.findViewById(R.id.medicineNameTextView);
            total_price = itemView.findViewById(R.id.totalPriceTextView);
            due = itemView.findViewById(R.id.dueTextView);

        }
    }
}
