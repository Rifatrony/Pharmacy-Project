package com.example.pharmacyapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerFirstFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView customerRecyclerView;
    CustomerAdapter adapter;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    public CustomerFirstFragment() {

    }

    public static CustomerFirstFragment newInstance(String param1, String param2) {
        CustomerFirstFragment fragment = new CustomerFirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_customer_first, container, false);

        customerRecyclerView = view.findViewById(R.id.customerRecyclerViewId);
        customerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<SellMedicineDataHolder> options =
                new FirebaseRecyclerOptions.Builder<SellMedicineDataHolder>()
                        .setQuery(FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Sell Medicine"), SellMedicineDataHolder.class)
                        .build();

        adapter = new CustomerAdapter(options);
        customerRecyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}