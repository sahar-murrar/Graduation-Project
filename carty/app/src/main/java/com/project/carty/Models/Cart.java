package com.project.carty.Models;

import com.google.firebase.database.Exclude;

public class Cart {
    private String mName;
    private String mImageUrl;
    private String mprice;
    private String mquantity;
    private String mKey;
    public Cart() {
        //empty constructor needed
    }
    public Cart(String name, String imageUrl, String price, String quantity) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        mName = name;
        mImageUrl = imageUrl;
        mprice=price;
        mquantity=quantity;

    }

    public String getName() {
        return mName;
    }
    public void setName(String name) {
        mName = name;
    }
    public String getImageUrl() {
        return mImageUrl;
    }
    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getPrice(){
        return mprice;
    }
    public void setPrice(String price){

        mprice=price;
    }
    public String getQuantity() {
        return mquantity;
    }
    public void setQuantity(String quantity) {
        mquantity = quantity;
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
