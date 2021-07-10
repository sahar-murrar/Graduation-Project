package com.project.carty.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.carty.R;

import java.util.HashMap;

public class CreditCardActivity extends AppCompatActivity {

    private CardForm cardForm;
    private TextView buy;
    private AlertDialog.Builder alertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);

        cardForm = findViewById(R.id.card_form);
        buy = findViewById(R.id.buy_button);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .setup(CreditCardActivity.this);

        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardForm.isValid()) {
                    alertBuilder = new AlertDialog.Builder(CreditCardActivity.this);
                    alertBuilder.setTitle("Confirm before purchase");
                    alertBuilder.setMessage("Card number: " + cardForm.getCardNumber() + "\n" +
                            "Card expiry date: " + cardForm.getExpirationMonth().toString()+ "/"+cardForm.getExpirationYear().toString() + "\n" +
                            "Card CVV: " + cardForm.getCvv() + "\n" +
                            "Postal code: " + cardForm.getPostalCode() + "\n" +
                            "Phone number: " + cardForm.getMobileNumber());
                    alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Toast.makeText(CreditCardActivity.this, "Your Order Sent Successfully", Toast.LENGTH_SHORT).show();

                          //  final DatabaseReference cardLisRef = FirebaseDatabase.getInstance().getReference("Card Info");
                            final DatabaseReference cardLisRef = FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Card Info");
                            final HashMap<String, Object> UserMap = new HashMap<>();
                            String firsttwodigits=cardForm.getExpirationMonth().toString();
                            String lasttwodigits=cardForm.getExpirationYear().toString();
                            UserMap.put("cardnum", cardForm.getCardNumber().toString());
                            UserMap.put("expirationDate",firsttwodigits+"/"+ lasttwodigits);
                            UserMap.put("cvv",cardForm.getCvv().toString());
                            UserMap.put("postalCode", cardForm.getPostalCode().toString());
                            UserMap.put("countryCode", cardForm.getCountryCode().toString());
                            UserMap.put("phone_number", cardForm.getMobileNumber().toString());
                            cardLisRef.setValue(UserMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                              if (task.isSuccessful()) {
                                      Intent intent = new Intent(CreditCardActivity.this, OrdersListActivity.class);
                                      startActivity(intent);

                              }
                                }
                            });

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

                } else {
                    Toast.makeText(CreditCardActivity.this, "Please complete the form", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}


