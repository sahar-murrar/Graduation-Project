package com.project.carty.Models;

import com.google.firebase.database.Exclude;

public class TotalPrice {
    private String TotalPrice;
    private String NewTotalPrice;
    private String mKey;
    public TotalPrice() {
        //empty constructor needed
    }
    public TotalPrice(String totalprice,String newtotalprice_after_discount) {

        TotalPrice = totalprice;
        NewTotalPrice=newtotalprice_after_discount;


    }

    public String getTotalPrice() {
        return TotalPrice;
    }
    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getNewTotalPrice() {
        return NewTotalPrice;
    }
    public void setNewTotalPrice(String newtotalprice_after_discount) {
        NewTotalPrice = newtotalprice_after_discount;
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
