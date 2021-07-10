package com.project.carty.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.carty.Adapters.AdminProductsAdapter;
import com.project.carty.Adapters.AdminSalesAdapter;
import com.project.carty.Adapters.CustomerSalesAdapter;
import com.project.carty.AdminProducts.AdminProductsListActivity;
import com.project.carty.Models.Cart;
import com.project.carty.Models.Sales;
import com.project.carty.Models.Upload;
import com.project.carty.R;

import java.util.ArrayList;
import java.util.List;

public class AdminSalesListActivity extends AppCompatActivity implements AdminSalesAdapter.onItemClickListener{
    private RecyclerView mRecyclerView;
    private AdminSalesAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseRefCustomer;
    private ValueEventListener mDBListener;
    private List<Sales> mUploads;
    private List<Upload> mUploads3;
    private List<Cart> mUploads4;
    private List<Cart> mUploads5;
    private List<Upload> mUploads6;
    private List<Cart> mUploads7;
    private List<Cart> mUploads8;
    private String edited_percentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sales_list);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));


        mProgressCircle = findViewById(R.id.progress_circle);

        mUploads = new ArrayList<>();
        mUploads3 = new ArrayList<>();
        mUploads4 = new ArrayList<>();
        mUploads5 = new ArrayList<>();
        mUploads6 = new ArrayList<>();
        mUploads7 = new ArrayList<>();
        mUploads8 = new ArrayList<>();

        mAdapter = new AdminSalesAdapter(AdminSalesListActivity.this, mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(AdminSalesListActivity.this);
        mDatabaseRef =  FirebaseDatabase.getInstance().getReference("Admin Sales List");
        mDatabaseRefCustomer =  FirebaseDatabase.getInstance().getReference("Customers Sales List");

        final String uploadId = mDatabaseRef.push().getKey();
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Sales  upload = postSnapshot.getValue(Sales.class);
                    upload.setKey(postSnapshot.getKey());
                    mUploads.add(upload);

                    DatabaseReference ref = mDatabaseRefCustomer.child(upload.getName());
                    ref.setValue(upload);
                }
                mAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AdminSalesListActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);

            }

        });




    }

    @Override
    public void onDeleteClick(int position, String category) {
        final DatabaseReference Users = FirebaseDatabase.getInstance().getReference("Users");
        final DatabaseReference UsersOrders = FirebaseDatabase.getInstance().getReference("Users Orders Lists");
        final DatabaseReference categoryRef = FirebaseDatabase.getInstance().getReference(category);
        Sales selectedItem = mUploads.get(position);
        final String selectedKey = selectedItem.getKey();
        final String orginal_name = mUploads.get(position).getName().toString();
        final String orginal_price = mUploads.get(position).getPrice().toString();
       // mDatabaseRef.child(selectedKey).removeValue();
       // mDatabaseRefCustomer.child(selectedKey).removeValue();


        categoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for ( DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String id=postSnapshot.getKey();
                    //for (DataSnapshot postSnapshot1 : postSnapshot.child("Favorite List").getChildren()) {
                        Upload upload = postSnapshot.getValue(Upload.class);
                      //  mUploads3.add(upload);
                   // }
                    String name = upload.getName().toString();
                    String price = upload.getPrice().toString();

                        if (name.equals(orginal_name)) {
                            categoryRef.child(id).child("mpriceNEW").setValue(price);
                        }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for ( DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String id=postSnapshot.getKey();
                    for (DataSnapshot postSnapshot1 : postSnapshot.child("Favorite List").getChildren()) {
                        Upload upload = postSnapshot1.getValue(Upload.class);
                      //  mUploads3.add(upload);
                   // }

                  //  for (int i = 0; i < mUploads3.size(); i++) {
                     //   String name = mUploads3.get(i).getName().toString();
                      //  String price = mUploads3.get(i).getPrice().toString();
                    String name = upload.getName().toString();
                    String price = upload.getPrice().toString();
                        if (name.equals(orginal_name) && price.contains("OFF")) {
                            Users.child(id).child("Favorite List").child(name).child("price").setValue(orginal_price);
                            Users.child(id).child("Favorite List").child(name).child("mpriceNEW").setValue(orginal_price);

                        }

                        else if (name.equals(orginal_name)) {
                            Users.child(id).child("Favorite List").child(name).child("price").setValue(orginal_price);
                            Users.child(id).child("Favorite List").child(name).child("mpriceNEW").setValue(orginal_price);


                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for ( DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String id=postSnapshot.getKey();
                    for (DataSnapshot postSnapshot1 : postSnapshot.child("Cart List").getChildren()) {
                        Cart upload = postSnapshot1.getValue(Cart.class);
                        mUploads4.add(upload);
                   // }

                   // for (int i = 0; i < mUploads4.size(); i++) {
                      //  String name = mUploads4.get(i).getName().toString();
                        //String price = mUploads4.get(i).getPrice().toString();
                        String name = upload.getName().toString();
                        String price = upload.getPrice().toString();
                        if (name.equals(orginal_name) && price.contains("OFF")) {
                            Users.child(id).child("Cart List").child(name).child("price").setValue(orginal_price);

                        }

                        else if (name.equals(orginal_name)) {
                            Users.child(id).child("Cart List").child(name).child("price").setValue(orginal_price);

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
                      //  mUploads5.add(upload);
                   // }

                  //  for (int i = 0; i < mUploads5.size(); i++) {
                    //    String name = mUploads5.get(i).getName().toString();
                    //    String price = mUploads5.get(i).getPrice().toString();
                    String name = upload.getName().toString();
                    String price = upload.getPrice().toString();
                        if (name.equals(orginal_name) && price.contains("OFF")) {

                            UsersOrders.child(id).child("Orders List").child(name).child("price").setValue(orginal_price);

                        }
                        else if (name.equals(orginal_name)) {
                            UsersOrders.child(id).child("Orders List").child(name).child("price").setValue(orginal_price);

                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDatabaseRef.child(selectedKey).removeValue();
        mDatabaseRefCustomer.child(selectedKey).removeValue();

        Toast.makeText(AdminSalesListActivity.this, "Removed Successfully From Sales List", Toast.LENGTH_SHORT).show();
    }
    public void onEditSaleClick(final int position, String category) {
        final DatabaseReference Users = FirebaseDatabase.getInstance().getReference("Users");
        final DatabaseReference UsersOrders = FirebaseDatabase.getInstance().getReference("Users Orders Lists");
        final DatabaseReference categoryRef = FirebaseDatabase.getInstance().getReference(category);
        final AlertDialog.Builder builder= new AlertDialog.Builder(AdminSalesListActivity.this);
        builder.setTitle("Edit Sale Percentage");
        final EditText percentage=new EditText(AdminSalesListActivity.this);
        percentage.setInputType(InputType.TYPE_CLASS_TEXT);
        // Editable num=quantity.getText();

        builder.setView(percentage);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edited_percentage = percentage.getText().toString();
                if (TextUtils.isEmpty(edited_percentage)) {
                    percentage.setError("Enter the Percentage!");
                    return;
                } else {
                    Sales selectedItem = mUploads.get(position);
                    final String selectedKey = selectedItem.getKey();
                    final String orginal_name = mUploads.get(position).getName().toString();

                    mDatabaseRef.child(selectedKey).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Sales upload = dataSnapshot.getValue(Sales.class);
                            String original_price[]=upload.getPrice().toString().split(" ");
                            int originalPrice=Integer.parseInt(original_price[0]);
                            int precentage=Integer.parseInt(edited_percentage);
                            double precent=precentage*0.01;
                            double salePrice=originalPrice*(precent);
                            double final_price_after_sale=originalPrice-salePrice;
                            final String fprice=String.valueOf(final_price_after_sale);

                            mDatabaseRef.child(selectedKey).child("salePercentage").setValue(edited_percentage +"%");
                            mDatabaseRef.child(selectedKey).child("salePrice").setValue(fprice+" ₪");
                            mDatabaseRefCustomer.child(selectedKey).child("salePercentage").setValue(edited_percentage+"%");
                            mDatabaseRefCustomer.child(selectedKey).child("salePrice").setValue(fprice+" ₪");


                            categoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for ( DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                        String id=postSnapshot.getKey();
                                       // for (DataSnapshot postSnapshot1 : postSnapshot.child("Favorite List").getChildren()) {
                                            Upload upload = postSnapshot.getValue(Upload.class);
                                            mUploads6.add(upload);

                                            String name = upload.getName().toString();
                                            String price = upload.getPrice().toString();


                                            if (name.equals(orginal_name)) {
                                                categoryRef.child(id).child("mpriceNEW").setValue(fprice+" ₪");

                                            }

                                       // }
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                            Users.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for ( DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                        String id=postSnapshot.getKey();
                                        for (DataSnapshot postSnapshot1 : postSnapshot.child("Favorite List").getChildren()) {
                                            Upload upload = postSnapshot1.getValue(Upload.class);
                                            mUploads6.add(upload);

                                            String name = upload.getName().toString();
                                            String price = upload.getPrice().toString();


                                                if (name.equals(orginal_name) && price.contains("OFF")) {
                                                    Users.child(id).child("Favorite List").child(name).child("price").setValue(fprice + " ₪ **" + edited_percentage + "% OFF" + "**");

                                                }
                                                else if (name.equals(orginal_name)) {
                                                    Users.child(id).child("Favorite List").child(name).child("price").setValue(fprice + " ₪ **" + edited_percentage + "% OFF" + "**");

                                                }

                                        }
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                            Users.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for ( DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                        String id=postSnapshot.getKey();
                                        for (DataSnapshot postSnapshot1 : postSnapshot.child("Cart List").getChildren()) {
                                            Cart upload = postSnapshot1.getValue(Cart.class);
                                            mUploads7.add(upload);

                                            String name = upload.getName().toString();
                                            String price = upload.getPrice().toString();


                                            if (name.equals(orginal_name) && price.contains("OFF")) {
                                                Users.child(id).child("Cart List").child(name).child("price").setValue(fprice + " ₪ **" + edited_percentage + "% OFF" + "**");

                                            }
                                            else if (name.equals(orginal_name)) {
                                                Users.child(id).child("Cart List").child(name).child("price").setValue(fprice + " ₪ **" + edited_percentage + "% OFF" + "**");

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
                                            mUploads8.add(upload);

                                            String name = upload.getName().toString();
                                            String price = upload.getPrice().toString();


                                            if (name.equals(orginal_name) && price.contains("OFF")) {
                                                UsersOrders.child(id).child("Orders List").child(name).child("price").setValue(fprice + " ₪ **" + edited_percentage + "% OFF" + "**");

                                            }

                                            else if (name.equals(orginal_name) ) {
                                                UsersOrders.child(id).child("Orders List").child(name).child("price").setValue(fprice + " ₪ **" + edited_percentage + "% OFF" + "**");

                                            }

                                        }
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            Toast.makeText(AdminSalesListActivity.this, "Sale Percentage Edited Successfully", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        builder.show();

    }

  /*  @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }

   */
}