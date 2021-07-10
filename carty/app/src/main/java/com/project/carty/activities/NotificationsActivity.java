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
import com.project.carty.Adapters.NotificationsAdapter;
import com.project.carty.Adapters.ProductsAdapter;
import com.project.carty.CustomerProducts.ProductsListActivity;
import com.project.carty.CustomerProducts.ProductsListActivity2;
import com.project.carty.CustomerProducts.ProductsListActivity3;
import com.project.carty.CustomerProducts.ProductsListActivity4;
import com.project.carty.CustomerProducts.ProductsListActivity5;
import com.project.carty.CustomerProducts.ProductsListActivity6;
import com.project.carty.CustomerProducts.ProductsListActivity7;
import com.project.carty.CustomerProducts.ProductsListActivity8;
import com.project.carty.Models.Cart;
import com.project.carty.Models.Notifications;
import com.project.carty.Models.Upload;
import com.project.carty.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity implements NotificationsAdapter.onItemClickListener {
    private RecyclerView mRecyclerView;
    private NotificationsAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private List<Notifications> mUploads;
    private ValueEventListener mDBListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mProgressCircle = findViewById(R.id.progress_circle);

        mUploads = new ArrayList<>();
        mAdapter = new NotificationsAdapter(NotificationsActivity.this, mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(NotificationsActivity.this);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Notifications");
        mDBListener= mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Notifications upload = postSnapshot.getValue(Notifications.class);
                    upload.setKey(postSnapshot.getKey());
                    mUploads.add(upload);
                }

                mAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(NotificationsActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public void onDeleteClick(int position) {
        Notifications selectedItem = mUploads.get(position);
        final String selectedKey = selectedItem.getKey();
        mDatabaseRef.child(selectedKey).removeValue();
        Toast.makeText(NotificationsActivity.this, "This Notification Removed Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNotificationClick(int position,String Category) {

        if(Category.equals("Fruits and Vegetables")) {
            Intent intent9 = new Intent(NotificationsActivity.this, ProductsListActivity.class);
            startActivity(intent9);
        }
        else if (Category.equals("Meat, Poultry and Fish")) {
            Intent intent9 = new Intent(NotificationsActivity.this, ProductsListActivity2.class);
            startActivity(intent9);
        }
        else if (Category.equals("Bakery and Sweets")) {
            Intent intent9 = new Intent(NotificationsActivity.this, ProductsListActivity3.class);
            startActivity(intent9);
        }
        else if (Category.equals("Cheese and Dairy")) {
            Intent intent9 = new Intent(NotificationsActivity.this, ProductsListActivity4.class);
            startActivity(intent9);
        }
        else if (Category.equals("Frozen Food")) {
            Intent intent9 = new Intent(NotificationsActivity.this, ProductsListActivity5.class);
            startActivity(intent9);
        }
        else if (Category.equals("Cleaning Needs")) {
            Intent intent9 = new Intent(NotificationsActivity.this, ProductsListActivity6.class);
            startActivity(intent9);
        }
        else if (Category.equals("Rice, Pasta and Legumes")) {
            Intent intent9 = new Intent(NotificationsActivity.this, ProductsListActivity7.class);
            startActivity(intent9);
        }
        else if (Category.equals("Drinks")) {
            Intent intent9 = new Intent(NotificationsActivity.this, ProductsListActivity8.class);
            startActivity(intent9);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }

}