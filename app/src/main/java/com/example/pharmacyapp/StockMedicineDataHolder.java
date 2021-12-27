package com.example.pharmacyapp;

public class StockMedicineDataHolder {

    String manufactureName, medicineName, buyDate, paymentType, batchId, expireDate, quantity, manufacturePrice, total_Price;

    public StockMedicineDataHolder() {
    }

    public StockMedicineDataHolder(String manufactureName, String medicineName, String buyDate, String paymentType, String batchId, String expireDate, String quantity, String manufacturePrice, String total_Price) {
        this.manufactureName = manufactureName;
        this.medicineName = medicineName;
        this.buyDate = buyDate;
        this.paymentType = paymentType;
        this.batchId = batchId;
        this.expireDate = expireDate;
        this.quantity = quantity;
        this.manufacturePrice = manufacturePrice;
        this.total_Price = total_Price;
    }

    public String getManufactureName() {
        return manufactureName;
    }

    public void setManufactureName(String manufactureName) {
        this.manufactureName = manufactureName;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getManufacturePrice() {
        return manufacturePrice;
    }

    public void setManufacturePrice(String manufacturePrice) {
        this.manufacturePrice = manufacturePrice;
    }

    public String getTotal_Price() {
        return total_Price;
    }

    public void setTotal_Price(String total_Price) {
        this.total_Price = total_Price;
    }
}
