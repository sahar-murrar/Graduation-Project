package com.project.carty.Models;

import com.google.firebase.database.Exclude;

public class Sales {
    private String mName;
    private String mImageUrl;
    private String OldPrice;
    private String SalePrice;
    private String SalePercentage;
    private String category;
    private String mKey;
    // private String mquantity;
    public Sales() {
        //empty constructor needed
    }
    public Sales(String name, String imageUrl, String original_price, String sale_price, String sale_percentage,String category_name /*, String quantity*/) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        mName = name;
        mImageUrl = imageUrl;
        OldPrice=original_price;
        SalePrice=sale_price;
        SalePercentage=sale_percentage;
        category=category_name;
        //  mquantity=quantity;

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
        return OldPrice;
    }
    public void setPrice(String price){

        OldPrice=price;
    }

    public String getSalePrice(){
        return SalePrice;
    }
    public void setSalePrice(String sale_price){

        SalePrice=sale_price;
    }

    public String getSalePercentage(){
        return SalePercentage;
    }
    public void setSalePercentage(String sale_percentage){

        SalePercentage=sale_percentage;
    }

    public String getcategory() {
        return category;
    }
    public void setcategory(String category_name) {
        category = category_name;
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
