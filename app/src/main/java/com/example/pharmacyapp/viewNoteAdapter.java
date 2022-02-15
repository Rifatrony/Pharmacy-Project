package com.example.pharmacyapp;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class viewNoteAdapter extends RecyclerView.Adapter<viewNoteAdapter.myViewHolder>{

    Context context;
    ArrayList<addNoteDataholder> list;

    public viewNoteAdapter(Context context, ArrayList<addNoteDataholder> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.note_sample_layout,parent,false);
        return new viewNoteAdapter.myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        addNoteDataholder data = list.get(position);

        holder.title.setText(data.getTitle());
        holder.description.setText(data.getDescription());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogPlus dialog = DialogPlus.newDialog(context)
                        .setGravity(Gravity.CENTER)
                        .setMargin(50,0,50,0)
                        .setContentHolder(new ViewHolder(R.layout.edit_note_dialog_content))
                        .setExpanded(false)
                        .create();


                View holderView = (LinearLayout) dialog.getHolderView();

                EditText title = holderView.findViewById(R.id.utitle);
                EditText description = holderView.findViewById(R.id.udesc);
                Button submit = holderView.findViewById(R.id.updateBtnId);

                //String selectedMedicineUid = data.getUid();

                title.setText(data.getTitle());
                description.setText(data.getDescription());

                /*submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        DatabaseReference db = FirebaseDatabase.getInstance().getReference(user.getUid());

                        Map<String, Object> map = new HashMap<>();
                        map.put("title",title.getText().toString());
                        map.put("description",description.getText().toString());
                        db.child("Medicine").child("Note Details").child(db.getKey()).updateChildren(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        dialog.dismiss();
                                    }
                                });

                        //Toast.makeText(context.getApplicationContext(),"Updated Successfully",Toast.LENGTH_SHORT).show();


                    }
                });*/

                dialog.show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

   /* public viewNoteAdapter(@NonNull FirebaseRecyclerOptions options) {
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
                        .setExpanded(true,650)
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
    }*/

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView title, description;
        ImageView edit, delete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            title= itemView.findViewById(R.id.titleTextViewId);
            description= itemView.findViewById(R.id.descTextViewId);

            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
        }
    }

}
