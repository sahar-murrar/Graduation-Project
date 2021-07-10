package com.project.carty.Models;

import com.google.firebase.database.Exclude;

public class Order {
  //  private String totalprice;
 //   private String mName;
 //   private String mprice;
    private String mKey;
  //  private String Quantity;
    private String FullName;
    private String PhoneNum;
    private String email;
    private String street;
    private String city;
    private String OrderDate;
    private String OrderTime;
    private String payment_method;
    private String TotalPrice;
    private String NewTotalPrice;
    private String CardNum;

    public Order() {
        //empty constructor needed
    }
   // public Order(String name, String price ,String Totalprice, String quantity, String firstname, String lastname, String phone) {
   public Order(String fullname, String phone, String Orderdate, String Ordertime, String Email, String Street, String City, String PaymentMethod,String totalprice,String newtotalprice_after_discount,String cardnum) {

       FullName=fullname;
        PhoneNum=phone;
       OrderDate=Orderdate;
       OrderTime=Ordertime;
       email=Email;
       street=Street;
       city=City;
       payment_method=PaymentMethod;
       TotalPrice = totalprice;
       NewTotalPrice=newtotalprice_after_discount;
       CardNum=cardnum;

    }
/*
    public String getName() {
        return mName;
    }
    public void setName(String name) {
        mName = name;
    }

    public String getPrice(){
        return mprice;
    }
    public void setPrice(String price){

        mprice=price;
    }

    public String getTotalPrice(){
        return totalprice;
    }
    public void setTotalPrice(String totalprice){

        totalprice=totalprice;
    }
    public String getQuantity() {
        return Quantity;
    }
    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
*/
    public String getFullName() {
        return FullName;
    }
    public void setFullName(String fullname) {
        FullName = fullname;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }
    public void setPhoneNum(String phone) {
        PhoneNum = phone;
    }

    public String getOrderDate() {
        return OrderDate;
    }
    public void setOrderDate(String date) {
        OrderDate = date;
    }

    public String getOrderTime() {
        return OrderTime;
    }
    public void setOrderTime(String time) {
        OrderTime = time;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String Email) {
        email = Email;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String Street) {
        street = Street;
    }

   public String getCity() {
        return city;
    }
    public void setCity(String City) {
        city = City;
    }

    public String getPayment_method() {
        return payment_method;
    }
    public void setPayment_method(String PaymentMethod) {
        payment_method = PaymentMethod;
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
    public String getCardNum() {
        return CardNum;
    }
    public void setCardNum(String cardnum) {
        CardNum = cardnum;
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


