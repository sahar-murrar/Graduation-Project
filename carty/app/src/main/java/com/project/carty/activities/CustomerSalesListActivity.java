package com.project.carty.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.carty.Adapters.CartAdapter;
import com.project.carty.Adapters.CustomerSalesAdapter;
import com.project.carty.Adapters.ProductsAdapter;
import com.project.carty.CustomerProducts.ProductsListActivity;
import com.project.carty.Models.Cart;
import com.project.carty.Models.Sales;
import com.project.carty.R;

import java.util.ArrayList;
import java.util.List;

public class CustomerSalesListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CustomerSalesAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private List<Sales> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sales_list);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));


        mProgressCircle = findViewById(R.id.progress_circle);
        mUploads = new ArrayList<>();

        mDatabaseRef =  FirebaseDatabase.getInstance().getReference("Customers Sales List");

        final String uploadId = mDatabaseRef.push().getKey();
       mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

               for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Sales  upload = postSnapshot.getValue(Sales.class);
                    mUploads.add(upload);
                }
                mAdapter = new CustomerSalesAdapter(CustomerSalesListActivity.this, mUploads);
                mRecyclerView.setAdapter(mAdapter);
                mProgressCircle.setVisibility(View.INVISIBLE);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CustomerSalesListActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

        });




    }



}