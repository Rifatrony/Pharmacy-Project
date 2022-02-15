package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowNoteActivity extends AppCompatActivity {

    RecyclerView recview;
    viewNoteAdapter adapter;
    FloatingActionButton fb;

    //RecyclerView accountRecyclerView;
    ArrayList<addNoteDataholder> list;

    DatabaseReference db;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);
        this.setTitle("Note");

        recview = findViewById(R.id.recview);

        fb = findViewById(R.id.fadd);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddNoteActivity.class);
                startActivity(intent);
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Note Details");

        recview.setHasFixedSize(true);
        recview.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new viewNoteAdapter(this,list);
        recview.setAdapter(adapter);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    addNoteDataholder obj = data.getValue(addNoteDataholder.class);
                    list.add(obj);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*list = new ArrayList<>();
        adapter = new viewNoteAdapter(this,list);
        recview.setAdapter(adapter);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    addNoteDataholder obj = data.getValue(addNoteDataholder.class);
                    list.add(obj);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        /*FirebaseRecyclerOptions<addNoteDataholder> options =
                new FirebaseRecyclerOptions.Builder<addNoteDataholder>()
                        .setQuery(FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Note Details"), addNoteDataholder.class)
                        .build();

        adapter = new viewNoteAdapter(options);
        recview.setAdapter(adapter);*/

    }

    // Search Medicine

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);


        MenuItem item = menu.findItem(R.id.search);


        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                processSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processSearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void processSearch(String s) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseRecyclerOptions<addNoteDataholder> options =
                new FirebaseRecyclerOptions.Builder<addNoteDataholder>()
                        .setQuery(FirebaseDatabase.getInstance().getReference(user.getUid())
                                .child("Medicine").child("Note Details").orderByChild("description")
                                .startAt(s).endAt(s+"\uf8ff"), addNoteDataholder.class)
                        .build();

        adapter = new viewNoteAdapter(options);
        adapter.startListening();
        recview.setAdapter(adapter);

    }*/

}