package com.example.pharmacyapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class showMedicineAdapter extends FirebaseRecyclerAdapter<addMedicineDataHolder,showMedicineAdapter.myViewHolder> {


    public showMedicineAdapter(@NonNull FirebaseRecyclerOptions<addMedicineDataHolder> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull final addMedicineDataHolder model) {

        holder.m_name.setText(model.getM_name());
        holder.s_manufacture.setText(model.getS_manufacture());
        holder.box_pattern.setText(model.getBox_pattern());
        holder.m_category.setText(model.getM_category());
        holder.m_type.setText(model.getM_type());
        holder.m_unit.setText(model.getM_unit());
        holder.s_genericName.setText(model.getS_genericName());
        holder.sell_price.setText(model.getSell_price());
        holder.manufacture_price.setText(model.getManufacture_price());
        holder.shelf_no.setText(model.getShelf_no());


        holder.edit.setOnClickListener(new View.OnClickListener() {
                 @Override
                public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.m_name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.view_medicine_dialog_content))
                        .setExpanded(true,1670)
                        .create();


                View myView = dialogPlus.getHolderView();

                EditText m_name = myView.findViewById(R.id.mnameid);
                EditText manu_name = myView.findViewById(R.id.manunameid);
                EditText box_pattern = myView.findViewById(R.id.boxpid);
                EditText m_category = myView.findViewById(R.id.mcategoryid);
                EditText m_type = myView.findViewById(R.id.mtypeid);
                EditText m_unit = myView.findViewById(R.id.munitid);
                EditText g_name = myView.findViewById(R.id.gnameid);
                EditText sell_price = myView.findViewById(R.id.sellpid);
                EditText manu_price = myView.findViewById(R.id.manupid);
                EditText shelf_no = myView.findViewById(R.id.shelfnoid);

                Button submit = myView.findViewById(R.id.updateid);

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                m_name.setText(model.getM_name());
                manu_name.setText(model.getS_manufacture());
                box_pattern.setText(model.getBox_pattern());
                m_category.setText(model.getM_category());
                m_type.setText(model.getM_type());
                m_unit.setText(model.getM_unit());
                g_name.setText(model.getS_genericName());
                sell_price.setText(model.getSell_price());
                manu_price.setText(model.getManufacture_price());
                shelf_no.setText(model.getShelf_no());


                dialogPlus.show();


                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("m_name",m_name.getText().toString());
                        map.put("s_manufacture",manu_name.getText().toString());
                        map.put("box_pattern",box_pattern.getText().toString());
                        map.put("m_category",m_category.getText().toString());
                        map.put("m_type",m_type.getText().toString());
                        map.put("m_unit",m_unit.getText().toString());
                        map.put("s_genericName",g_name.getText().toString());
                        map.put("sell_price",sell_price.getText().toString());
                        map.put("manufacture_price",manu_price.getText().toString());
                        map.put("shelf_no",shelf_no.getText().toString());

                        FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Medicine List")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Toast.makeText(holder.m_name.getContext(), "Updated Successfully", Toast.LENGTH_LONG).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });

                    }
                });

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                AlertDialog.Builder builder = new AlertDialog.Builder(holder.m_name.getContext());
                builder.setIcon(R.drawable.delete);
                builder.setTitle("Delete Medicine");
                builder.setMessage("Do you really want to delete?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Medicine List")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.show();

            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_medicine_sample_layout,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView m_name,box_pattern,m_category,m_unit,sell_price,
                manufacture_price,shelf_no,s_manufacture,
                s_genericName,m_type;

        Button edit, delete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            m_name = itemView.findViewById(R.id.medicineTextViewId);
            s_manufacture = itemView.findViewById(R.id.manufactureTextViewId);
            box_pattern = itemView.findViewById(R.id.boxPattternTextViewId);
            m_category = itemView.findViewById(R.id.medicineCategoryTextViewId);
            m_type = itemView.findViewById(R.id.medicineTypeTextViewId);
            m_unit = itemView.findViewById(R.id.medicineUnitTextViewId);
            s_genericName = itemView.findViewById(R.id.genericNameTextViewId);
            sell_price = itemView.findViewById(R.id.sellPriceTextViewId);
            manufacture_price = itemView.findViewById(R.id.manufacturePriceTextViewId);
            shelf_no = itemView.findViewById(R.id.shelfNoTextViewId);

            edit = itemView.findViewById(R.id.editId);
            delete = itemView.findViewById(R.id.deleteId);
        }
    }
}
