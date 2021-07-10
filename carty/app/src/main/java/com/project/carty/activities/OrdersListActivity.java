package com.project.carty.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.project.carty.Adapters.OrdersAdapter;
import com.project.carty.Models.Cart;
import com.project.carty.R;

import java.util.HashMap;
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

public class OrdersListActivity extends AppCompatActivity implements OrdersAdapter.onItemClickListener {

    private RecyclerView mRecyclerView;
    private OrdersAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private TextView totalprice;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private List<Cart> mUploads;
    private String keyval;
    private TextView info;
    private CardView card;
    private LinearLayout linear;
    private double total=0;
    private TextView userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list);
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
        totalprice=findViewById(R.id.totalprice);
        totalprice.setVisibility(View.INVISIBLE);
        userInfo=findViewById(R.id.userInfo);
        userInfo.setVisibility(View.INVISIBLE);
        mUploads = new ArrayList<>();
        mAdapter = new OrdersAdapter(OrdersListActivity.this, mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(OrdersListActivity.this);

       // mDatabaseRef = FirebaseDatabase.getInstance().getReference("Orders List");
        String email=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String newemail=email.replace('.','-');
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users Orders Lists")
                .child(newemail).child("Orders List");
        final String uploadId = mDatabaseRef.push().getKey();
        mDBListener= mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Cart upload = postSnapshot.getValue(Cart.class);
                    String[] price=upload.getPrice().split(" ");
                    String quantity=upload.getQuantity();
                    double p=Double.parseDouble(price[0]);
                    int q=Integer.parseInt(quantity);
                    double totallprice=p*q;
                    total=total+totallprice;
                    String fprice=String.valueOf(total);
                    totalprice.setText("Final Total Price= "+fprice +" ₪");

                    final DatabaseReference totalPriceRef = FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Total Price").child("Prices");
                    final HashMap<String, Object> UserMap = new HashMap<>();
                    UserMap.put("totalprice", fprice +" ₪");
                    UserMap.put("newtotalprice_after_discount", fprice +" ₪");
                    // UserMap.put("Product Name",Product_name);
                    //orderLisRef.child("Order Info").child(Phonenum.getText().toString()).updateChildren(UserMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    totalPriceRef.setValue(UserMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                               // Toast.makeText(OrdersListActivity.this, "Pay with Credit Card to have a 20% discount on the total price!!", Toast.LENGTH_SHORT).show();

                            }

                        }
                   });





                upload.setKey(postSnapshot.getKey());
                    mUploads.add(upload);
                }
                mAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);
               info.setVisibility(View.VISIBLE);
               linear.setVisibility(View.VISIBLE);
               totalprice.setVisibility(View.VISIBLE);
               userInfo.setVisibility(View.VISIBLE);
               card.setVisibility(View.VISIBLE);

               userInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent =new Intent(OrdersListActivity.this, UserActitvity.class);
                        //  intent.putExtra("Name", FirstName.getText().toString() +" "+ LastName.getText().toString());
                        startActivity(intent);

                    }

               });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(OrdersListActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
                info.setVisibility(View.INVISIBLE);
                linear.setVisibility(View.INVISIBLE);
                totalprice.setVisibility(View.INVISIBLE);
                userInfo.setVisibility(View.INVISIBLE);
                card.setVisibility(View.INVISIBLE);
            }

        });



    }

    @Override
    public void onDeleteClick(int position) {
        Cart selectedItem = mUploads.get(position);
        final String selectedKey = selectedItem.getKey();
        mDatabaseRef.child(selectedKey).removeValue();
        Toast.makeText(OrdersListActivity.this, "Removed Successfully from Cart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }
}