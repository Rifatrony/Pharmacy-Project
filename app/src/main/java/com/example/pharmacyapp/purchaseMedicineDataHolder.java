package com.example.pharmacyapp;

public class purchaseMedicineDataHolder {

    String s_manufacture, s_medicine_name,
            buy_date, payment_type, batch_id, expire_date,
            quantity, manufacture_price, total_price, purchase_paid_amount, purchase_due_amount, purchase_Uid;

    public purchaseMedicineDataHolder() {


    }

    public purchaseMedicineDataHolder(String s_manufacture, String s_medicine_name, String buy_date,
                                      String payment_type, String batch_id, String expire_date,
                                      String quantity, String manufacture_price, String total_price,
                                      String purchase_paid_amount, String purchase_due_amount, String purchase_Uid) {


        this.s_manufacture = s_manufacture;
        this.s_medicine_name = s_medicine_name;
        this.buy_date = buy_date;
        this.payment_type = payment_type;
        this.batch_id = batch_id;
        this.expire_date = expire_date;
        this.quantity = quantity;
        this.manufacture_price = manufacture_price;
        this.total_price = total_price;
        this.purchase_paid_amount = purchase_paid_amount;
        this.purchase_due_amount = purchase_due_amount;
        this.purchase_Uid = purchase_Uid;

    }

    public String getS_manufacture() {
        return s_manufacture;
    }

    public void setS_manufacture(String s_manufacture) {
        this.s_manufacture = s_manufacture;
    }

    public String getS_medicine_name() {
        return s_medicine_name;
    }

    public void setS_medicine_name(String s_medicine_name) {
        this.s_medicine_name = s_medicine_name;
    }

    public String getBuy_date() {
        return buy_date;
    }

    public void setBuy_date(String buy_date) {
        this.buy_date = buy_date;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(String batch_id) {
        this.batch_id = batch_id;
    }

    public String getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(String expire_date) {
        this.expire_date = expire_date;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getManufacture_price() {
        return manufacture_price;
    }

    public void setManufacture_price(String manufacture_price) {
        this.manufacture_price = manufacture_price;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getPurchase_paid_amount() {
        return purchase_paid_amount;
    }

    public void setPurchase_paid_amount(String purchase_paid_amount) {
        this.purchase_paid_amount = purchase_paid_amount;
    }

    public String getPurchase_due_amount() {
        return purchase_due_amount;
    }

    public void setPurchase_due_amount(String purchase_due_amount) {
        this.purchase_due_amount = purchase_due_amount;
    }

    public String getPurchase_Uid() {
        return purchase_Uid;
    }

    public void setPurchase_Uid(String purchase_Uid) {
        this.purchase_Uid = purchase_Uid;
    }
}