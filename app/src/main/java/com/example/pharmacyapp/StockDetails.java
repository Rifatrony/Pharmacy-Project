package com.example.pharmacyapp;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StockDetails extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    String manufactureName, medicineName, buyDate, paymentType, batchId, expireDate, quantity, manufacturePrice, totalPrice;

    public StockDetails() {

    }

    public StockDetails(String manufactureName, String medicineName, String buyDate, String paymentType, String batchId, String expireDate, String quantity, String manufacturePrice, String totalPrice) {

        this.manufactureName = manufactureName;
        this.medicineName = medicineName;
        this.buyDate = buyDate;
        this.paymentType = paymentType;
        this.batchId = batchId;
        this.expireDate = expireDate;
        this.quantity = quantity;
        this.manufacturePrice = manufacturePrice;
        this.totalPrice = totalPrice;

    }

    public static StockDetails newInstance(String param1, String param2) {
        StockDetails fragment = new StockDetails();
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

        View view = inflater.inflate(R.layout.fragment_stock_details, container, false);

        TextView detailsMedicineNameTextView = view.findViewById(R.id.detailsMedicineNameTextView);
        TextView detailsManufactureNameTextView = view.findViewById(R.id.detailsManufactureNameTextView);
        TextView detailsBuyDateTextView = view.findViewById(R.id.detailsBuyDateTextView);
        TextView detailsPaymentTypeTextView = view.findViewById(R.id.detailsPaymentTypeTextView);
        TextView detailsBatchIdTextView = view.findViewById(R.id.detailsBatchIdTextView);
        TextView detailsExpireDateTextView = view.findViewById(R.id.detailsExpireDateTextView);
        TextView detailsQuantityTextView = view.findViewById(R.id.detailsQuantityTextView);
        TextView detailsManufacturePriceTextView = view.findViewById(R.id.detailsManufacturePriceTextView);
        TextView detailsTotalPriceTextView = view.findViewById(R.id.detailsTotalPriceTextView);

        detailsMedicineNameTextView.setText(medicineName);
        detailsManufactureNameTextView.setText(manufactureName);
        detailsBuyDateTextView.setText(buyDate);
        detailsPaymentTypeTextView.setText(paymentType);
        detailsBatchIdTextView.setText(batchId);
        detailsExpireDateTextView.setText(expireDate);
        detailsQuantityTextView.setText(quantity);
        detailsManufacturePriceTextView.setText(manufacturePrice);
        detailsTotalPriceTextView.setText(totalPrice);

        return view;
    }

    public void onBackPressed(){
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new RecFragment()).addToBackStack(null).commit();

    }
}