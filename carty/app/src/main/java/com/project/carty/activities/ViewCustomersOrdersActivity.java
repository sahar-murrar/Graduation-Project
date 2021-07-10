package com.project.carty.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.carty.Adapters.ProductsAdapter;
import com.project.carty.Adapters.ViewCustomersOrdersAdapter;
import com.project.carty.CustomerProducts.ProductsListActivity;
import com.project.carty.Models.Cart;
import com.project.carty.Models.Order;
import com.project.carty.Models.TotalPrice;
import com.project.carty.Models.Upload;
import com.project.carty.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ViewCustomersOrdersActivity extends AppCompatActivity implements ViewCustomersOrdersAdapter.onItemClickListener {
    private RecyclerView mRecyclerView;
    private ViewCustomersOrdersAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private List<Order> mUploads;
    private ValueEventListener mDBListener;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customers_orders);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mProgressCircle = findViewById(R.id.progress_circle);
        mUploads = new ArrayList<>();
        mAdapter = new ViewCustomersOrdersAdapter(ViewCustomersOrdersActivity.this, mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(ViewCustomersOrdersActivity.this);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");
        mDBListener=mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads.clear();
               for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                   id=postSnapshot.getKey();
                   Iterator<DataSnapshot> items = postSnapshot.child("Information").getChildren().iterator();
                   HashMap<String, Object> order = null;
                   while (items.hasNext()) {
                       DataSnapshot item = items.next();
                       order = (HashMap<String, Object>) item.getValue();
                       mUploads.add(new Order(order.get("fullname").toString(), order.get("phone").toString(), order.get("Orderdate").toString(),
                               order.get("Ordertime").toString(), order.get("Email").toString(), order.get("Street").toString(),
                               order.get("city").toString(),order.get("PaymentMethod").toString(),order.get("totalprice").toString(),
                               order.get("newtotalprice_after_discount").toString(),order.get("cardnum").toString()));
                   }

               }
                mAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ViewCustomersOrdersActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public void onDeleteClick(final int position, String email) {
        final DatabaseReference UsersOrders = FirebaseDatabase.getInstance().getReference("Users Orders Lists");
        final DatabaseReference Users = FirebaseDatabase.getInstance().getReference("Users");
        final String newemail=email.replace('.','-');


        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String idd=postSnapshot.getKey();
                    Iterator<DataSnapshot> items = postSnapshot.child("Information").getChildren().iterator();
                    HashMap<String, Object> order = null;
                    while (items.hasNext()) {
                        DataSnapshot item = items.next();
                        order = (HashMap<String, Object>) item.getValue();

                        String emaill= order.get("Email").toString();
                        String User_email=emaill.replace('.','-');
                        if (User_email.equals(newemail)) {
                            Users.child(idd).child("Information").removeValue();

                        }

                    }

                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        UsersOrders.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for ( DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String id=postSnapshot.getKey();
                    for (DataSnapshot postSnapshot1 : postSnapshot.child("Orders List").getChildren()) {
                        Cart upload = postSnapshot1.getValue(Cart.class);

                        if (id.equals(newemail)) {
                            UsersOrders.child(id).child("Orders List").removeValue();

                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Toast.makeText(ViewCustomersOrdersActivity.this, "This Order Removed Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }

}
