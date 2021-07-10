package com.project.carty.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.project.carty.R;

import android.view.View;
import android.widget.Button;

public class CategoriesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_list);
        Button category1=findViewById(R.id.category1);
        Button category2=findViewById(R.id.category2);
        Button category3=findViewById(R.id.category3);
        Button category4=findViewById(R.id.category4);
        Button category5=findViewById(R.id.category5);
        Button category6=findViewById(R.id.category6);
        Button category7=findViewById(R.id.category7);
        Button category8=findViewById(R.id.category8);

        category1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(CategoriesListActivity.this, UploadProductsActivity.class);
                startActivity(intent1);
            }
        });

        category2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(CategoriesListActivity.this, UploadProductsActivity2.class);
                startActivity(intent2);
            }
        });
        category3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(CategoriesListActivity.this, UploadProductsActivity3.class);
                startActivity(intent3);
            }
        });
        category4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(CategoriesListActivity.this, UploadProductsActivity4.class);
                startActivity(intent3);
            }
        });

        category5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(CategoriesListActivity.this, UploadProductsActivity5.class);
                startActivity(intent3);
            }
        });

        category6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(CategoriesListActivity.this, UploadProductsActivity6.class);
                startActivity(intent3);
            }
        });

        category7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(CategoriesListActivity.this, UploadProductsActivity7.class);
                startActivity(intent3);
            }
        });

        category8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(CategoriesListActivity.this, UploadProductsActivity8.class);
                startActivity(intent3);
            }
        });
    }
}