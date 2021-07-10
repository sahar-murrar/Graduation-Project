package com.project.carty.Models;

import com.google.firebase.database.Exclude;

public class CreditCard {
    private String mKey;
    private String CardNum;
    private String ExpirationDate;
    private String CVV;
    private String PostalCode;
    private String CountryCode;
    private String Phonenumber;


    public CreditCard() {
        //empty constructor needed
    }
    // public Order(String name, String price ,String Totalprice, String quantity, String firstname, String lastname, String phone) {
    public CreditCard(String cardnum, String expirationDate, String cvv, String postalCode, String countryCode, String phone_number) {

        CardNum=cardnum;
        ExpirationDate=expirationDate;
        CVV=cvv;
        PostalCode=postalCode;
        CountryCode=countryCode;
        Phonenumber=phone_number;

    }

    public String getCardNum() {
        return CardNum;
    }
    public void setCardNum(String cardnum) {
        CardNum = cardnum;
    }

    public String getExpirationDate() {
        return ExpirationDate;
    }
    public void setExpirationDate(String expirationDate) {
        ExpirationDate = expirationDate;
    }

    public String getCVV() {
        return CVV;
    }
    public void setCVV(String cvv) {
        CVV = cvv;
    }

    public String getPostalCode() {
        return PostalCode;
    }
    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getCountryCode() {
        return CountryCode;
    }
    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public String getPhonenumber() {
        return Phonenumber;
    }
    public void setPhonenumber(String phone_number) {
        Phonenumber = phone_number;
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


