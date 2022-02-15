package com.example.pharmacyapp;

public class SupplierDataHolder {

    String supplierName, supplier_uid;

    public SupplierDataHolder() {
    }

    public SupplierDataHolder(String supplierName, String supplier_uid) {
        this.supplierName = supplierName;
        this.supplier_uid = supplier_uid;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplier_uid() {
        return supplier_uid;
    }

    public void setSupplier_uid(String supplier_uid) {
        this.supplier_uid = supplier_uid;
    }
}
