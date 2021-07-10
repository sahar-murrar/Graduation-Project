package com.project.carty.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.carty.R;


public class SignInAdmin extends AppCompatActivity {



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);

        Button LoginBtn = findViewById(R.id.singin);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            EditText  mEmail = findViewById(R.id.LoginActivity_Email);
            EditText   mPassword = findViewById(R.id.LoginActivity_Password);
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if (email.equals("carty@carty.com") && password.equals("carty")) {
                    Toast.makeText(SignInAdmin.this, "Signed In Successfully As Admin", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignInAdmin.this, adminDrawerActivity.class));

                } else {
                    Toast.makeText(SignInAdmin.this, "Error in login as admin !!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

}