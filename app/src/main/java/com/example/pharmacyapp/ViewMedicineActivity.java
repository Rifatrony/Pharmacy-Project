package com.example.pharmacyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ViewMedicineActivity extends AppCompatActivity {

    /*ListView listView;*/

    RecyclerView recyclerView;
    showMedicineAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_medicine);

        this.setTitle("Medicine List");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = findViewById(R.id.viewMedicineRecyclerViewId);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<addMedicineDataHolder> options =
                new FirebaseRecyclerOptions.Builder<addMedicineDataHolder>()
                        .setQuery(FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Medicine List"), addMedicineDataHolder.class)
                        .build();
        
        
        adapter = new showMedicineAdapter(options);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    // Search Medicine

    @Override
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

        FirebaseRecyclerOptions<addMedicineDataHolder> options =
                new FirebaseRecyclerOptions.Builder<addMedicineDataHolder>()
                        .setQuery(FirebaseDatabase.getInstance().getReference(user.getUid())
                                .child("Medicine").child("Medicine List").orderByChild("m_name")
                                .startAt(s).endAt(s+"\uf8ff"), addMedicineDataHolder.class)
                                .build();

        adapter = new showMedicineAdapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }
}