package com.project.carty.Models;

import com.google.firebase.database.Exclude;

public class Upload {
    private String mName;
    private String mImageUrl;
    private String mprice;
    private String category;
    private String mpriceNEW;

    private String mKey;
   // private String mquantity;
    public Upload() {
        //empty constructor needed
    }
    public Upload(String name, String imageUrl, String price, String category_name,String newprice
            /*, String quantity*/) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        mName = name;
        mImageUrl = imageUrl;
        mprice=price;
        category=category_name;
        mpriceNEW=newprice;
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
        return mprice;
   }
   public void setPrice(String price){

        mprice=price;
   }

  /*  public String getQuantity() {
        return mquantity;
    }
    public void setQuantity(String quantity) {
        mquantity = quantity;
    }*/

    public String getcategory() {
        return category;
    }
    public void setcategory(String category_name) {
        category = category_name;
    }
    public String getMpriceNEW() {
        return mpriceNEW;
    }

    public void setMpriceNEW(String newPrice) {
        this.mpriceNEW = newPrice;
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
