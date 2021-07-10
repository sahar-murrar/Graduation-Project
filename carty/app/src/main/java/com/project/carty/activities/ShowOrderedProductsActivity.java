package com.project.carty.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.carty.Adapters.OrdersAdapter;
import com.project.carty.Adapters.ShowOrderedProductsAdapter;
import com.project.carty.Adapters.ViewCustomersOrdersAdapter;
import com.project.carty.Models.Cart;
import com.project.carty.Models.Upload;
import com.project.carty.R;

import java.util.ArrayList;
import java.util.List;

public class ShowOrderedProductsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ShowOrderedProductsAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private TextView info;
    private CardView card;
    private LinearLayout linear;
    private List<Cart> mUploads;

    private String UserEmail="";
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ordered_products);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        info = findViewById(R.id.info);
        info.setVisibility(View.INVISIBLE);
        linear = findViewById(R.id.linear);
        linear.setVisibility(View.INVISIBLE);
        card= findViewById(R.id.card);
        card.setVisibility(View.INVISIBLE);
        mProgressCircle = findViewById(R.id.progressBar);
        mUploads = new ArrayList<>();


        UserEmail=getIntent().getStringExtra("email");
        final String NewUserEmail=UserEmail.replace('.','-');

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users Orders Lists").child(NewUserEmail).child("Orders List");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Cart upload = postSnapshot.getValue(Cart.class);
                    mUploads.add(upload);

                }
                mAdapter = new ShowOrderedProductsAdapter(ShowOrderedProductsActivity.this, mUploads);
                mRecyclerView.setAdapter(mAdapter);
                mProgressCircle.setVisibility(View.INVISIBLE);
                info.setVisibility(View.VISIBLE);
                linear.setVisibility(View.VISIBLE);
                card.setVisibility(View.VISIBLE);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ShowOrderedProductsActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
                info.setVisibility(View.INVISIBLE);
                linear.setVisibility(View.INVISIBLE);
                card.setVisibility(View.INVISIBLE);
            }
        });


    }
}