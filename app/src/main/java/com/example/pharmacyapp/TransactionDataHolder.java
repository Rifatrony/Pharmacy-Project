package com.example.pharmacyapp;

public class TransactionDataHolder {

    String uid, reason, date, amount;

    public TransactionDataHolder() {
    }

    public TransactionDataHolder(String uid, String reason, String date, String amount) {
        this.uid = uid;
        this.reason = reason;
        this.date = date;
        this.amount = amount;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
