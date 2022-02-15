package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainDashBoard extends AppCompatActivity implements View.OnClickListener {

    private CardView medicineCardView,stockCardView,purchaseCardView,
            sellCardView,customerCardView,paymentCardView, noteCardView, expireCardView,
            allReportCardView, supplierCardView;

    private AlertDialog.Builder alertDialogBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dash_board);

        this.setTitle(R.string.dashboard);



        //Add back Button on tool bar
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/


        medicineCardView = findViewById(R.id.medicineCardViewId);
        stockCardView = findViewById(R.id.stockCardViewId);
        purchaseCardView = findViewById(R.id.purchaseCardViewId);
        sellCardView = findViewById(R.id.sellCardViewId);
        customerCardView = findViewById(R.id.customerCardViewId);
        paymentCardView = findViewById(R.id.paymentCardViewId);
        noteCardView = findViewById(R.id.noteCardViewId);
        expireCardView = findViewById(R.id.expireCardViewId);
        allReportCardView = findViewById(R.id.allReportCardViewId);
        supplierCardView = findViewById(R.id.supplierCardViewId);


        medicineCardView.setOnClickListener(this);
        stockCardView.setOnClickListener(this);
        purchaseCardView.setOnClickListener(this);
        sellCardView.setOnClickListener(this);
        customerCardView.setOnClickListener(this);
        paymentCardView.setOnClickListener(this);
        noteCardView.setOnClickListener(this);
        expireCardView.setOnClickListener(this);
        allReportCardView.setOnClickListener(this);
        supplierCardView.setOnClickListener(this);


    }

  /*  @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }*/
  public void onBackPressed() {

      alertDialogBuilder = new AlertDialog.Builder(MainDashBoard.this);

      //set title
      alertDialogBuilder.setTitle("Confirm Exit");

      //Setting message

      alertDialogBuilder.setMessage("Do you really want to exit ?");

      //set icon

      alertDialogBuilder.setIcon(R.drawable.helpicondelete);

      alertDialogBuilder.setCancelable(false);

      //set positive button
      alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
              //exit
              finish();
          }
      });

      alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {

              dialogInterface.cancel();
          }
      });

      /*alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {

              Toast.makeText(MainDashBoard.this, "You have clicked on cancel button",Toast.LENGTH_SHORT).show();
          }
      });*/

      AlertDialog alertDialog = alertDialogBuilder.create();
      alertDialog.show();

  }

    @Override
    public void onClick(View view) {

        if (view.getId()==R.id.medicineCardViewId){
            Intent intent = new Intent(this,SubDeshboard.class);
            startActivity(intent);
        }

        if (view.getId()==R.id.stockCardViewId){
            Intent intent = new Intent(this,StockMedicine.class);
            startActivity(intent);
        }

        if (view.getId()==R.id.purchaseCardViewId){
            Intent intent = new Intent(this,PurchaseMedicine.class);
            startActivity(intent);
        }

        if (view.getId()==R.id.sellCardViewId){
            Intent intent = new Intent(this,SellMedicine.class);
            startActivity(intent);
        }

        if (view.getId()==R.id.customerCardViewId){
            Intent intent = new Intent(this,CustomerActivity.class);
            startActivity(intent);
        }

        if (view.getId()==R.id.paymentCardViewId){
            Intent intent = new Intent(getApplicationContext(),Accounts.class);
            startActivity(intent);
        }

        if (view.getId()==R.id.noteCardViewId){
            Intent intent = new Intent(this,ShowNoteActivity.class);
            startActivity(intent);
        }

        if (view.getId()==R.id.expireCardViewId){
            Intent intent = new Intent(this,ExpireSoonActivity.class);
            startActivity(intent);
        }

        if (view.getId()==R.id.allReportCardViewId){
            Intent intent = new Intent(this,All_Report.class);
            startActivity(intent);
        }

        if (view.getId()==R.id.supplierCardViewId){
            Intent intent = new Intent(this,SupplierActivity.class);
            startActivity(intent);
        }
    }
}