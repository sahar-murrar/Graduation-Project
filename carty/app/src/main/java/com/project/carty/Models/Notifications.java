package com.project.carty.Models;

import com.google.firebase.database.Exclude;

public class Notifications {
    private String ProductsNames;
    private String CustomerName;
    private String category;
    private String mImageUrl;
    private String mKey;
    // private String mquantity;
    public Notifications() {
        //empty constructor needed
    }
    public Notifications(String productsname, String categoryname, String customerName, String imageUrl) {
        if (productsname.trim().equals("")) {
            productsname = "No Name";
        }
        ProductsNames = productsname;
        category=categoryname;
        CustomerName=customerName;
        mImageUrl = imageUrl;

    }

    public String getProductsNames() {
        return ProductsNames;
    }
    public void setProductsNames(String productsname) {
        ProductsNames = productsname;
    }

    public String getcategory() {
        return category;
    }
    public void setcategory(String categoryname) {
        category = categoryname;
    }

    public String getCustomerName() {
        return CustomerName;
    }
    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }
    @Exclude
    public void setKey(String key) {
        mKey = key;
    }
}
