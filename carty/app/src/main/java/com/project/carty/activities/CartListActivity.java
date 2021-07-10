package com.project.carty.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.project.carty.Adapters.CartAdapter;
import com.project.carty.Models.Cart;
import com.project.carty.R;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class CartListActivity extends AppCompatActivity implements CartAdapter.onItemClickListener {

    private RecyclerView mRecyclerView;
    private CartAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private TextView mNext;
  //  CartAdapter.FooterViewHolder footerview;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference cartListRef;
    private ValueEventListener mDBListener;
    private List<Cart> mUploads;
   // private  int total=0;
   // private TextView totalprice;
    private String keyval;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));


        mProgressCircle = findViewById(R.id.progressBar);
       mNext=findViewById(R.id.next);
     //   totalprice=findViewById(R.id.info);
        mNext.setVisibility(View.INVISIBLE);
       // totalprice.setVisibility(View.INVISIBLE);
        mUploads = new ArrayList<>();
        mAdapter = new CartAdapter(CartListActivity.this, mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(CartListActivity.this);



        //mDatabaseRef = FirebaseDatabase.getInstance().getReference("Cart List");
        String email=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String newemail=email.replace('.','-');
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Cart List");


      //  cartListRef = FirebaseDatabase.getInstance().getReference("Orders List");
        cartListRef = FirebaseDatabase.getInstance().getReference("Users Orders Lists")
                .child(newemail).child("Orders List");
        final String uploadId = mDatabaseRef.push().getKey();
        mDBListener= mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Cart  upload = postSnapshot.getValue(Cart.class);
                   /* String[] price=upload.getPrice().split(" ");
                    String quantity=upload.getQuantity();
                    int p=Integer.parseInt(price[0]);
                    int q=Integer.parseInt(quantity);
                    int totallprice=p*q;
                    total=total+totallprice;
                    String fprice=String.valueOf(total);
                    totalprice.setText("Final Total Price= "+fprice +" ₪");*/

                    upload.setKey(postSnapshot.getKey());
                    mUploads.add(upload);
                }
                mAdapter.notifyDataSetChanged();

                mProgressCircle.setVisibility(View.INVISIBLE);
               mNext.setVisibility(View.VISIBLE);
             // totalprice.setVisibility(View.VISIBLE);
                mNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Cart upload = postSnapshot.getValue(Cart.class);
                            upload.setKey(postSnapshot.getKey());
                            mUploads.add(upload);


                            String uploadId = cartListRef.push().getKey();
                            DatabaseReference ref = cartListRef.child(upload.getName());
                            ref.setValue(upload);
                           // Toast.makeText(CartListActivity.this, "Added Successfully to orders", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CartListActivity.this, UserInformationActivity.class);
                            startActivity(intent);
                        }
                    }
                });

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CartListActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
               mNext.setVisibility(View.INVISIBLE);
             //  totalprice.setVisibility(View.INVISIBLE);
            }

        });




    }

    @Override
    public void onDeleteClick(int position) {
        Cart selectedItem = mUploads.get(position);
        final String selectedKey = selectedItem.getKey();
        mDatabaseRef.child(selectedKey).removeValue();
        cartListRef.child(selectedKey).removeValue();
        Toast.makeText(CartListActivity.this, "Removed Successfully from Cart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }

}