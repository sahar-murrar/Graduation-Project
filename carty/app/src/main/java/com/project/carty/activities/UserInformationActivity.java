package com.project.carty.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.carty.Adapters.ProductsAdapter;
import com.project.carty.CustomerProducts.ProductsListActivity;
import com.project.carty.Models.Upload;
import com.project.carty.Models.User;
import com.project.carty.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class UserInformationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentuser;
    private TextView useremail;
    private TextView FullName;
    private TextView Phonenum;
    private EditText streetname;
    private String email;
    private String fullname;
    private String phone;
    private Spinner spinner;
    private String selectedCity;

   // private TextView confirm;
   // private RadioButton rb_cash;
  //  private RadioButton rb_visa;
    private String payment;
    private RadioGroup radioGroup;
    private  RadioButton radioButton;
    private AlertDialog.Builder alertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        useremail=findViewById(R.id.useremail);
        FullName=findViewById(R.id.name);

        spinner=findViewById(R.id.spinner2);
       // confirm=findViewById(R.id.confirm);
        Phonenum=findViewById(R.id.price);
        streetname=findViewById(R.id.streetname);
      //  rb_cash=findViewById(R.id.radioButton_cash);
      //  rb_visa=findViewById(R.id.radioButton_visa);
        radioGroup=findViewById(R.id.radioGroup);


      // String email=firebaseAuth.getCurrentUser().getEmail();
        firebaseAuth=FirebaseAuth.getInstance();
        currentuser=firebaseAuth.getCurrentUser();
        if(currentuser != null) {
            email=currentuser.getEmail();
            useremail.setText("  Email: " +email);

            String id=currentuser.getUid();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile Info");

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                        User user = dataSnapshot.getValue(User.class);
                        fullname=user.getName();
                        FullName.setText("  Full Name: " +fullname);
                        phone=user.getPhone();
                        Phonenum.setText("  Phone Number: " +phone);


                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
      //  String city=spinner.getSelectedItem().toString();



      //  if(rb_cash.isSelected()){

    /*        rb_cash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    payment="Cash";
                    rb_cash.setSelected(true);
                    if(rb_cash.isSelected()==true && rb_visa.isSelected()==true){
                        rb_visa.setSelected(false);
                        Toast.makeText(UserInformationActivity.this, "You have to choose only one payment way!", Toast.LENGTH_SHORT).show();
                    }
                    sendOrderInfo(payment);

                }
            });

       // }
      //  else if(rb_visa.isSelected()){
        rb_visa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment="Credit Card";
                if(rb_visa.isSelected()==true && rb_cash.isSelected()==true){
                    rb_cash.setSelected(false);
                    Toast.makeText(UserInformationActivity.this, "You have to choose only one payment way!", Toast.LENGTH_SHORT).show();
                }
                sendOrderInfo(payment);
            }
        });  */


    }

    private Boolean isVallidPhone(String phone){
        String regexStr = "^[0-9]$";
        return (phone.length()==10  || phone.matches(regexStr)==true);

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedCity = parent.getItemAtPosition(position).toString();
      //  Toast.makeText(parent.getContext(), text +"is selected as city", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void sendOrderInfo(final String paymentMethod){
         fullname=FullName.getText().toString().substring(13);
        String phone=Phonenum.getText().toString().substring(16);
        String street=streetname.getText().toString();
        String Email=useremail.getText().toString().substring(9);
        //  Intent intent =getIntent();
        //  String Product_name=intent.getStringExtra("Name");

        if( !TextUtils.isEmpty(street) ) {
            // Toast.makeText(UserInformationActivity.this, "Your Order Sent Successfully", Toast.LENGTH_SHORT).show();
            final String saveCurrentTime, saveCurrentDate;
            Calendar calForDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
            saveCurrentDate = currentDate.format(calForDate.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            saveCurrentTime = currentTime.format(calForDate.getTime());

           // final DatabaseReference orderLisRef = FirebaseDatabase.getInstance().getReference("User Info");
            final DatabaseReference orderLisRef = FirebaseDatabase.getInstance().getReference("Users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Information").child("User Info");
            final HashMap<String, Object> UserMap = new HashMap<>();
            UserMap.put("fullname", fullname);
            UserMap.put("phone", phone);
            UserMap.put("Email", Email);
            UserMap.put("Street", streetname.getText().toString());
            UserMap.put("city",selectedCity);
            UserMap.put("PaymentMethod", paymentMethod);
            UserMap.put("Orderdate", saveCurrentDate);
            UserMap.put("Ordertime", saveCurrentTime);
            UserMap.put("totalprice", "test");
            UserMap.put("newtotalprice_after_discount", "test");
            UserMap.put("cardnum", "");
            // UserMap.put("Product Name",Product_name);
            //orderLisRef.child("Order Info").child(Phonenum.getText().toString()).updateChildren(UserMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            orderLisRef.setValue(UserMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        if(paymentMethod.equals("Cash")) {
                            alertBuilder = new AlertDialog.Builder(UserInformationActivity.this);
                            alertBuilder.setTitle("Confirm before purchase");
                            alertBuilder.setMessage("Are You Sure that you want to Pay by cash?");
                            alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    Toast.makeText(UserInformationActivity.this, "Your Order Sent Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(UserInformationActivity.this, OrdersListActivity.class);
                                    startActivity(intent);
                                }
                            });
                            alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            AlertDialog alertDialog = alertBuilder.create();
                            alertDialog.show();

                        }

                        else if(paymentMethod.equals("Credit Card")){
                            Intent intent = new Intent(UserInformationActivity.this, CreditCardActivity.class);
                            startActivity(intent);
                        }

                    }
                }
            });

        }

        else {

             if (TextUtils.isEmpty(street)) {
                streetname.setError("Enter Street name");
                return;
            }

        }

    }


    public void checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        payment= radioButton.getText().toString();
        sendOrderInfo(payment);

    }
}