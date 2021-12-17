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
import androidx.constraintlayout.helper.widget.Layer;
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

public class viewNoteAdapter extends FirebaseRecyclerAdapter<addNoteDataholder,viewNoteAdapter.myViewHolder> {

    public viewNoteAdapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull final addNoteDataholder model) {

        holder.title.setText(model.getTitle());
        holder.description.setText(model.getDescription());


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.title.getContext())
                        .setContentHolder(new ViewHolder(R.layout.edit_note_dialog_content))
                        .setExpanded(true,550)
                        .create();

                View myview = dialogPlus.getHolderView();

                EditText title = myview.findViewById(R.id.utitle);
                EditText description = myview.findViewById(R.id.udesc);
                Button submit = myview.findViewById(R.id.updateBtnId);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                title.setText(model.getTitle());
                description.setText(model.getDescription());

                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("title", title.getText().toString());
                        map.put("description",description.getText().toString());

                        FirebaseDatabase.getInstance().getReference(user.getUid())
                                .child("Medicine").child("Note Details")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Toast.makeText(holder.title.getContext(), "Updated Successfully", Toast.LENGTH_LONG).show();
                                        dialogPlus.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
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
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.title.getContext());
                builder.setIcon(R.drawable.question);
                builder.setTitle("Delete Panel");
                builder.setMessage("Are you sure");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Note Details")
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_sample_layout,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView title, description;
        ImageView edit, delete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            title= itemView.findViewById(R.id.titleTextViewId);
            description= itemView.findViewById(R.id.descTextViewId);

            edit = itemView.findViewById(R.id.btn1);
            delete = itemView.findViewById(R.id.btn2);
        }
    }

}
