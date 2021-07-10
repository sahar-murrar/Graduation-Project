package com.project.carty.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.carty.Models.Cart;
import com.project.carty.Models.CreditCard;
import com.project.carty.Models.Order;
import com.project.carty.Models.TotalPrice;
import com.project.carty.R;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class UserActitvity extends AppCompatActivity {
     private TextView fname;
    private TextView phone;
    private TextView email;
    private TextView city;
    private TextView street;
    private TextView date;
    private TextView time;
    private TextView payment;
    private TextView totalprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo_layout);
        fname=findViewById(R.id.fname);
        phone=findViewById(R.id.phone);
        email=findViewById(R.id.email);
        street=findViewById(R.id.street);
        city=findViewById(R.id.city);
        date=findViewById(R.id.date);
        time=findViewById(R.id.time);
        payment=findViewById(R.id.payment);
        totalprice=findViewById(R.id.totalprice);

        /*String FirstName=getIntent().getStringExtra("First Name");
        String LastName=upload.getLastName().toString();
        String Phone=upload.getPhoneNum().toString();
        String Date=upload.getOrderDate().toString();
        String Time=upload.getOrderTime().toString();
        fname.setText(FirstName);
        lname.setText(LastName);
        phone.setText(Phone);
        date.setText(Date);
        time.setText(Time);*/





       // DatabaseReference UserInfo = FirebaseDatabase.getInstance().getReference("User Info");
        final DatabaseReference UserInfo = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Information").child("User Info");
        final String uploadId = UserInfo.push().getKey();
        UserInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                         Map<String, Object> UserMap= (Map<String, Object>) dataSnapshot.getValue();
                    Order upload = dataSnapshot.getValue(Order.class);
                    upload.setKey(dataSnapshot.getKey());
                  /*  String FirstName=upload.getFirstName().toString();
                    String LastName=upload.getLastName().toString();
                    String Phone=upload.getPhoneNum().toString();
                    String Date=upload.getOrderDate().toString();
                    String Time=upload.getOrderTime().toString();*/
                        String FullName=(String) UserMap.get("fullname");
                        String Phone=(String) UserMap.get("phone");
                        String Email=(String) UserMap.get("Email");
                        String City=(String) UserMap.get("city");
                        String Street=(String) UserMap.get("Street");
                        String Payment=(String) UserMap.get("PaymentMethod");
                        String Date=(String) UserMap.get("Orderdate");
                        String Time=(String) UserMap.get("Ordertime");


                    fname.setText("Full Name: "+FullName);
                    phone.setText("Phone: "+Phone);
                    email.setText("Email: "+Email);
                    city.setText("City: "+City);
                    street.setText("Street: "+Street);


                    if(Payment.equals("Credit Card")) {
                      //  DatabaseReference CardInfo = FirebaseDatabase.getInstance().getReference("Card Info");
                        final DatabaseReference CardInfo = FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Card Info");

                        CardInfo.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    Map<String, Object> UserMap = (Map<String, Object>) dataSnapshot.getValue();
                                    CreditCard upload = dataSnapshot.getValue(CreditCard.class);
                                    upload.setKey(dataSnapshot.getKey());
                                    final String Creditnum = (String) UserMap.get("cardnum");
                                    String ExpirationDate = (String) UserMap.get("expirationDate");
                                    String CVV = (String) UserMap.get("cvv");
                                    String Postal = (String) UserMap.get("postalCode");
                                    String country = (String) UserMap.get("countryCode");
                                    String phonnum = (String) UserMap.get("phone_number");
                                    payment.setText("Payment Way: Credit Card" + "\n***And the Credit Card Information are:"+"\nCard Num: "+Creditnum+"\nExpiration Date: "+ExpirationDate+
                                            "\nCVV: "+CVV+ "\nPostal Code: "+Postal+"\nCountry Code: "+country+"\nPhone number: "+phonnum);

                                    final DatabaseReference totalpriceRef = FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Total Price").child("Prices");
                                    final String uploadId = totalpriceRef.push().getKey();
                                    totalpriceRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                Map<String, Object> UserMap = (Map<String, Object>) dataSnapshot.getValue();
                                                TotalPrice upload = dataSnapshot.getValue(TotalPrice.class);
                                                upload.setKey(dataSnapshot.getKey());
                                                final String[] Totalprice =  UserMap.get("totalprice").toString().split(" ");
                                                double p=Double.parseDouble(Totalprice[0]);
                                               // double finaltot=p*0.2;//do a 20% sale on the total price
                                                double finaltot=p*0.07;
                                                double fin=p-finaltot;
                                                final String fprice=String.valueOf(fin);
                                                totalprice.setText("Total Price: " +fprice  +" ₪" +" **You  got 7% discount because you payed by credit card**");
                                                totalpriceRef.child("newtotalprice_after_discount").setValue(fprice +" ₪");


                                                final DatabaseReference totalpriceRef1 = FirebaseDatabase.getInstance().getReference("Users")
                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Information").child("User Info");
                                                final String uploadId = totalpriceRef1.push().getKey();
                                                totalpriceRef1.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.exists()) {
                                                            Map<String, Object> UserMap = (Map<String, Object>) dataSnapshot.getValue();
                                                            TotalPrice upload = dataSnapshot.getValue(TotalPrice.class);
                                                            upload.setKey(dataSnapshot.getKey());
                                                            totalpriceRef1.child("totalprice").setValue(Totalprice[0] + " ₪");
                                                            totalpriceRef1.child("newtotalprice_after_discount").setValue(fprice + " ₪");
                                                            totalpriceRef1.child("cardnum").setValue(Creditnum);
                                                        }
                                                    }


                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {

                                                    }

                                                });
                                            }

                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }

                                    });
                                }
                            }


                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }

                        });

                    }
                    else if(Payment.equals("Cash")) {
                      payment.setText("Payment Way: Cash");
                        final DatabaseReference totalpriceRef = FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Total Price").child("Prices");
                        final String uploadId = totalpriceRef.push().getKey();
                        totalpriceRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    Map<String, Object> UserMap = (Map<String, Object>) dataSnapshot.getValue();
                                    TotalPrice upload = dataSnapshot.getValue(TotalPrice.class);
                                    upload.setKey(dataSnapshot.getKey());
                                   final  String Totalprice = (String) UserMap.get("totalprice");
                                    totalprice.setText("Total Price: " + Totalprice +" **You didn't got any discount because you will pay by cash**");

                                    final DatabaseReference totalpriceRef1 = FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Information").child("User Info");
                                    final String uploadId = totalpriceRef1.push().getKey();
                                    totalpriceRef1.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                Map<String, Object> UserMap = (Map<String, Object>) dataSnapshot.getValue();
                                                TotalPrice upload = dataSnapshot.getValue(TotalPrice.class);
                                                upload.setKey(dataSnapshot.getKey());
                                                totalpriceRef1.child("totalprice").setValue(Totalprice);
                                                totalpriceRef1.child("newtotalprice_after_discount").setValue(Totalprice);
                                            }
                                        }


                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }

                                    });
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }

                        });

                    }
                    date.setText("Order Date: "+Date);
                    time.setText("Order Time: "+Time);



                        ///  mUploads.add(upload);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }
}