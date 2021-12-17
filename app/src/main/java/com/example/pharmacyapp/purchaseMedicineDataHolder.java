package com.example.pharmacyapp;

public class purchaseMedicineDataHolder {

    String s_manufacture,s_medicine_name,
            buy_date,batch_id,expire_date,
            quantity,unit_price,manufacture_price;

    public purchaseMedicineDataHolder() {


    }

    public purchaseMedicineDataHolder(String s_manufacture, String s_medicine_name, String buy_date, String batch_id, String expire_date, String quantity, String unit_price, String manufacture_price) {
        this.s_manufacture = s_manufacture;
        this.s_medicine_name = s_medicine_name;
        this.buy_date = buy_date;
        this.batch_id = batch_id;
        this.expire_date = expire_date;
        this.quantity = quantity;
        this.unit_price = unit_price;
        this.manufacture_price = manufacture_price;
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

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public String getManufacture_price() {
        return manufacture_price;
    }

    public void setManufacture_price(String manufacture_price) {
        this.manufacture_price = manufacture_price;
    }
}
