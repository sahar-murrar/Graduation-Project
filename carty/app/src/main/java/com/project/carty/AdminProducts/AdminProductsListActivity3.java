package com.project.carty.AdminProducts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.project.carty.Adapters.AdminProductsAdapter;
import com.project.carty.Adapters.ProductsAdapter;
import com.project.carty.Models.Cart;
import com.project.carty.Models.Sales;
import com.project.carty.Models.Upload;
import com.project.carty.R;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.ArrayList;
import java.util.List;

public class AdminProductsListActivity3 extends AppCompatActivity implements AdminProductsAdapter.onItemClickListener {
    private RecyclerView mRecyclerView;
    private AdminProductsAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;
    private List<Sales> mUploads1;
    private List<Sales> mUploads2;
    private List<Upload> mUploads3;
    private List<Cart> mUploads4;
    private List<Cart> mUploads5;
    private List<Upload> mUploads6;
    private List<Cart> mUploads7;
    private List<Cart> mUploads8;
    private List<Upload> mUploads9;
    private List<Cart> mUploads10;
    private List<Cart> mUploads11;
    private List<Sales> mUploads12;
    private List<Sales> mUploads13;
    private ValueEventListener mDBListener;
    private String edited_price;
    private String edited_name;
    private String sale_percentage;
    private TextView sale_all;
    private String sale_percentage_all;
    private String category_name="Bakery and Sweets";
    private String fprice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_products_list3);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mProgressCircle = findViewById(R.id.progress_circle);
        sale_all= findViewById(R.id.sale_all);
        sale_all.setVisibility(View.INVISIBLE);

        mUploads = new ArrayList<>();
        mUploads1 = new ArrayList<>();
        mUploads2 = new ArrayList<>();
        mUploads3 = new ArrayList<>();
        mUploads4 = new ArrayList<>();
        mUploads5 = new ArrayList<>();
        mUploads6 = new ArrayList<>();
        mUploads7 = new ArrayList<>();
        mUploads8 = new ArrayList<>();
        mUploads9 = new ArrayList<>();
        mUploads10 = new ArrayList<>();
        mUploads11 = new ArrayList<>();
        mUploads12 = new ArrayList<>();
        mUploads13 = new ArrayList<>();
        mAdapter = new AdminProductsAdapter(AdminProductsListActivity3.this, mUploads,mDatabaseRef);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(AdminProductsListActivity3.this);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Bakery and Sweets");
        mDBListener= mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setKey(postSnapshot.getKey());
                    mUploads.add(upload);

                }

                mAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);

                sale_all.setVisibility(View.VISIBLE);

                //  mAdapter = new AdminProductsAdapter(AdminProductsListActivity.this, mUploads,mDatabaseRef);
                // mRecyclerView.setAdapter(mAdapter);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AdminProductsListActivity3.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
                sale_all.setVisibility(View.INVISIBLE);
            }
        });

        sale_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder= new AlertDialog.Builder(AdminProductsListActivity3.this);
                builder.setTitle("Enter the Sale Percentage You Want to Apply for All Products:");
                final EditText saleAll=new EditText(AdminProductsListActivity3.this);
                saleAll.setInputType(InputType.TYPE_CLASS_TEXT);
                // Editable num=quantity.getText();

                builder.setView(saleAll);
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sale_percentage_all = saleAll.getText().toString();
                        if (TextUtils.isEmpty(sale_percentage_all)) {
                            saleAll.setError("Enter the Sale Percentage!");
                            return;
                        }
                        else {
                            Toast.makeText(AdminProductsListActivity3.this, "All Category Products are Added Successfully to Sales", Toast.LENGTH_LONG).show();
                            mDatabaseRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                        String id=postSnapshot.getKey();
                                        Upload upload = postSnapshot.getValue(Upload.class);
                                        String original_price[] = upload.getPrice().toString().split(" ");
                                        final String org_name=upload.getName().toString();
                                        int originalPrice = Integer.parseInt(original_price[0]);
                                        int precentage = Integer.parseInt(sale_percentage_all);
                                        double precent = precentage * 0.01;
                                        double salePrice = originalPrice * (precent);
                                        double final_price_after_sale = originalPrice - salePrice;
                                        final String fprice = String.valueOf(final_price_after_sale);
                                        DatabaseReference saleListRef = FirebaseDatabase.getInstance().getReference("Customers Sales List");
                                        DatabaseReference AdminsaleListRef = FirebaseDatabase.getInstance().getReference("Admin Sales List");

                                        Sales sale = new Sales(upload.getName().toString(), upload.getImageUrl().toString(), upload.getPrice().toString()
                                                , fprice + " ₪", sale_percentage_all + "%",category_name);


                                        String uploadId = saleListRef.push().getKey();
                                        DatabaseReference ref = saleListRef.child(upload.getName());
                                        DatabaseReference ref1 = AdminsaleListRef.child(upload.getName());
                                        ref.setValue(sale);
                                        ref1.setValue(sale);
                                        //mDatabaseRef.child(id).child("mpriceNEW").setValue(fprice + " ₪");
                                        changeIn_Favorites_Cart_Orders(fprice,org_name);

                                    }
                                 //   Toast.makeText(AdminProductsListActivity3.this, "All Category Products are Added Successfully to Sales", Toast.LENGTH_LONG).show();

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
        });



    }

    public void changeIn_Favorites_Cart_Orders(final String fprice, final String orgin_name){
        final DatabaseReference Users = FirebaseDatabase.getInstance().getReference("Users");
        final DatabaseReference UsersOrders = FirebaseDatabase.getInstance().getReference("Users Orders Lists");


        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for ( DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String id=postSnapshot.getKey();

                    Upload upload = postSnapshot.getValue(Upload.class);
                    // mUploads6.add(upload);

                    String name = upload.getName().toString();
                    String price = upload.getPrice().toString();


                    if (name.equals(orgin_name)) {
                        mDatabaseRef.child(id).child("mpriceNEW").setValue(fprice + " ₪");

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
                        mUploads6.add(upload);

                        String name = upload.getName().toString();
                        String price = upload.getPrice().toString();


                        if (name.equals(orgin_name)) {
                            Users.child(id).child("Favorite List").child(name).child("mpriceNEW").setValue(fprice+" ₪");

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


                        if (name.equals(orgin_name) ) {
                            Users.child(id).child("Cart List").child(name).child("price").setValue(fprice+" ₪");

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


                        if (name.equals(orgin_name)) {
                            UsersOrders.child(id).child("Orders List").child(name).child("price").setValue(fprice+" ₪");

                        }

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onDeleteClick(final int position) {
        final DatabaseReference saleListRef = FirebaseDatabase.getInstance().getReference("Customers Sales List");
        final DatabaseReference AdminsaleListRef = FirebaseDatabase.getInstance().getReference("Admin Sales List");
        final DatabaseReference Users = FirebaseDatabase.getInstance().getReference("Users");
        final DatabaseReference UsersOrders = FirebaseDatabase.getInstance().getReference("Users Orders Lists");
        Upload selectedItem = mUploads.get(position);
        final String selectedKey = selectedItem.getKey();

        final String orginal_name = mUploads.get(position).getName().toString();
        mDatabaseRef.child(selectedKey).removeValue();
        saleListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Sales upload = postSnapshot.getValue(Sales.class);
                    mUploads1.add(upload);
                }
                for (int i = 0; i < mUploads1.size(); i++) {
                    String name = mUploads1.get(i).getName().toString();
                    if (name.equals(orginal_name)) {
                        saleListRef.child(name).removeValue();

                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        AdminsaleListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Sales upload = postSnapshot.getValue(Sales.class);
                    mUploads2.add(upload);
                }

                for (int i = 0; i < mUploads2.size(); i++) {
                    String name = mUploads2.get(i).getName().toString();
                    if (name.equals(orginal_name)) {
                        AdminsaleListRef.child(name).removeValue();

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
                        mUploads3.add(upload);
                    }

                    for (int i = 0; i < mUploads3.size(); i++) {
                        String name = mUploads3.get(i).getName().toString();
                        if (name.equals(orginal_name)) {
                            Users.child(id).child("Favorite List").child(name).removeValue();

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
                    }

                    for (int i = 0; i < mUploads4.size(); i++) {
                        String name = mUploads4.get(i).getName().toString();
                        if (name.equals(orginal_name)) {
                            Users.child(id).child("Cart List").child(name).removeValue();

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
                        mUploads5.add(upload);
                    }

                    for (int i = 0; i < mUploads5.size(); i++) {
                        String name = mUploads5.get(i).getName().toString();
                        if (name.equals(orginal_name)) {
                            UsersOrders.child(id).child("Orders List").child(name).removeValue();

                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        Toast.makeText(AdminProductsListActivity3.this, "Removed Successfully From Bakery and Sweets Category", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onEditPriceClick(final int position) {
        final DatabaseReference Users = FirebaseDatabase.getInstance().getReference("Users");
        final DatabaseReference UsersOrders = FirebaseDatabase.getInstance().getReference("Users Orders Lists");
        final DatabaseReference saleListRef =  FirebaseDatabase.getInstance().getReference("Customers Sales List");
        final DatabaseReference AdminsaleListRef =  FirebaseDatabase.getInstance().getReference("Admin Sales List");
        final AlertDialog.Builder builder= new AlertDialog.Builder(AdminProductsListActivity3.this);
        builder.setTitle("Edit the Product Price:");
        final EditText price=new EditText(AdminProductsListActivity3.this);
        price.setInputType(InputType.TYPE_CLASS_TEXT);
        // Editable num=quantity.getText();

        builder.setView(price);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edited_price = price.getText().toString();
                if (TextUtils.isEmpty(edited_price)) {
                    price.setError("Enter the Price!");
                    return;
                } else {
                    Upload selectedItem = mUploads.get(position);
                    final String selectedKey = selectedItem.getKey();
                    final String orginal_name = mUploads.get(position).getName().toString();
                    mDatabaseRef.child(selectedKey).child("price").setValue(edited_price+" ₪");
                    mDatabaseRef.child(selectedKey).child("mpriceNEW").setValue(edited_price+" ₪");

                    Users.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for ( DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                String id=postSnapshot.getKey();
                                for (DataSnapshot postSnapshot1 : postSnapshot.child("Favorite List").getChildren()) {
                                    //  String id1=postSnapshot1.getKey();
                                    Upload upload = postSnapshot1.getValue(Upload.class);
                                    mUploads6.add(upload);

                                    String name = upload.getName().toString();
                                    String price = upload.getPrice().toString();


                                    if (name.equals(orginal_name)) {
                                        Users.child(id).child("Favorite List").child(name).child("price").setValue(edited_price+" ₪");
                                        Users.child(id).child("Favorite List").child(name).child("mpriceNEW").setValue(edited_price+" ₪");

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
                                    // String id1=postSnapshot1.getKey();
                                    Cart upload = postSnapshot1.getValue(Cart.class);
                                    mUploads7.add(upload);

                                    String name = upload.getName().toString();
                                    String price = upload.getPrice().toString();


                                    if (name.equals(orginal_name) ) {
                                        Users.child(id).child("Cart List").child(name).child("price").setValue(edited_price+" ₪");

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
                                    //  String id1=postSnapshot1.getKey();
                                    Cart upload = postSnapshot1.getValue(Cart.class);
                                    mUploads8.add(upload);

                                    String name = upload.getName().toString();
                                    String price = upload.getPrice().toString();


                                    if (name.equals(orginal_name)) {
                                        UsersOrders.child(id).child("Orders List").child(name).child("price").setValue(edited_price+" ₪");

                                    }

                                }
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    saleListRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for ( DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                String id=postSnapshot.getKey();

                                Sales upload = postSnapshot.getValue(Sales.class);
                                mUploads12.add(upload);

                                String name = upload.getName().toString();
                                String price[] = upload.getPrice().toString().split(" ");
                                String percentage[]=upload.getSalePercentage().toString().split("%");


                                if (name.equals(orginal_name) ) {
                                    saleListRef.child(name).child("price").setValue(edited_price+ " ₪");
                                    int originalPrice=Integer.parseInt(edited_price);
                                    int precentagee=Integer.parseInt(percentage[0]);
                                    double precent=precentagee*0.01;
                                    double salePrice=originalPrice*(precent);
                                    double final_price_after_sale=originalPrice-salePrice;
                                    String fprice=String.valueOf(final_price_after_sale);
                                    saleListRef.child(name).child("salePrice").setValue(fprice+ " ₪");

                                }


                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    AdminsaleListRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for ( DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                String id=postSnapshot.getKey();

                                Sales upload = postSnapshot.getValue(Sales.class);
                                mUploads13.add(upload);

                                String name = upload.getName().toString();
                                String price[] = upload.getPrice().toString().split(" ");
                                String percentage[]=upload.getSalePercentage().toString().split("%");


                                if (name.equals(orginal_name) ) {
                                    AdminsaleListRef.child(name).child("price").setValue(edited_price+ " ₪");
                                    int originalPrice=Integer.parseInt(edited_price);
                                    int precentagee=Integer.parseInt(percentage[0]);
                                    double precent=precentagee*0.01;
                                    double salePrice=originalPrice*(precent);
                                    double final_price_after_sale=originalPrice-salePrice;
                                    String fprice=String.valueOf(final_price_after_sale);
                                    AdminsaleListRef.child(name).child("salePrice").setValue(fprice+ " ₪");


                                }

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    Toast.makeText(AdminProductsListActivity3.this, "This Product Price Edited Successfully", Toast.LENGTH_SHORT).show();
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

    public void onEditNameClick(final int position) {
        final DatabaseReference Users = FirebaseDatabase.getInstance().getReference("Users");
        final DatabaseReference UsersOrders = FirebaseDatabase.getInstance().getReference("Users Orders Lists");
        final DatabaseReference saleListRef =  FirebaseDatabase.getInstance().getReference("Customers Sales List");
        final DatabaseReference AdminsaleListRef =  FirebaseDatabase.getInstance().getReference("Admin Sales List");
        final AlertDialog.Builder builder= new AlertDialog.Builder(AdminProductsListActivity3.this);
        builder.setTitle("Edit the Product Name:");
        final EditText name=new EditText(AdminProductsListActivity3.this);
        name.setInputType(InputType.TYPE_CLASS_TEXT);
        // Editable num=quantity.getText();

        builder.setView(name);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edited_name = name.getText().toString();
                if (TextUtils.isEmpty(edited_name)) {
                    name.setError("Enter the Name!");
                    return;
                } else {
                    Upload selectedItem = mUploads.get(position);
                    final String selectedKey = selectedItem.getKey();
                    final String orginal_name = mUploads.get(position).getName().toString();
                    mDatabaseRef.child(selectedKey).child("name").setValue(edited_name);

                    Users.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for ( DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                String id=postSnapshot.getKey();
                                for (DataSnapshot postSnapshot1 : postSnapshot.child("Favorite List").getChildren()) {
                                    Upload upload = postSnapshot1.getValue(Upload.class);
                                    mUploads9.add(upload);


                                    String name = upload.getName().toString();
                                    String price = upload.getPrice().toString();
                                    String image = upload.getImageUrl().toString();
                                    String newPrice=upload.getMpriceNEW().toString();
                                    String category=upload.getcategory().toString();



                                    if (name.equals(orginal_name)) {
                                        // name=edited_name;

                                        Users.child(id).child("Favorite List").child(name).removeValue();
                                        Users.child(id).child("Favorite List").child(edited_name).child("name").setValue(edited_name);
                                        Users.child(id).child("Favorite List").child(edited_name).child("price").setValue(price);
                                        Users.child(id).child("Favorite List").child(edited_name).child("imageUrl").setValue(image);
                                        Users.child(id).child("Favorite List").child(edited_name).child("mpriceNEW").setValue(newPrice);
                                        Users.child(id).child("Favorite List").child(edited_name).child("category").setValue(category);

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
                                    mUploads10.add(upload);

                                    String name = upload.getName().toString();
                                    String price = upload.getPrice().toString();
                                    String image = upload.getImageUrl().toString();
                                    String quan = upload.getQuantity().toString();

                                    if (name.equals(orginal_name) ) {
                                        // name=edited_name;
                                        Users.child(id).child("Cart List").child(name).removeValue();
                                        Users.child(id).child("Cart List").child(edited_name).child("name").setValue(edited_name);
                                        Users.child(id).child("Cart List").child(edited_name).child("price").setValue(price);
                                        Users.child(id).child("Cart List").child(edited_name).child("quantity").setValue(quan);
                                        Users.child(id).child("Cart List").child(edited_name).child("imageUrl").setValue(image);
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
                                    mUploads11.add(upload);

                                    String name = upload.getName().toString();
                                    String price = upload.getPrice().toString();
                                    String image = upload.getImageUrl().toString();
                                    String quan = upload.getQuantity().toString();

                                    if (name.equals(orginal_name)) {
                                        //  name=edited_name;
                                        UsersOrders.child(id).child("Orders List").child(name).removeValue();
                                        UsersOrders.child(id).child("Orders List").child(edited_name).child("name").setValue(edited_name);
                                        UsersOrders.child(id).child("Orders List").child(edited_name).child("price").setValue(price);
                                        UsersOrders.child(id).child("Orders List").child(edited_name).child("quantity").setValue(quan);
                                        UsersOrders.child(id).child("Orders List").child(edited_name).child("imageUrl").setValue(image);
                                    }

                                }
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    saleListRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for ( DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                String id=postSnapshot.getKey();

                                Sales upload = postSnapshot.getValue(Sales.class);
                                //   mUploads11.add(upload);

                                String name = upload.getName().toString();
                                String price = upload.getPrice().toString();
                                String image = upload.getImageUrl().toString();
                                String percentage = upload.getSalePercentage().toString();
                                String saleprice = upload.getSalePrice().toString();

                                if (name.equals(orginal_name)) {
                                    //  name=edited_name;
                                    saleListRef.child(name).removeValue();
                                    saleListRef.child(edited_name).child("name").setValue(edited_name);
                                    saleListRef.child(edited_name).child("price").setValue(price);
                                    saleListRef.child(edited_name).child("salePercentage").setValue(percentage);
                                    saleListRef.child(edited_name).child("salePrice").setValue(saleprice);
                                    saleListRef.child(edited_name).child("imageUrl").setValue(image);
                                }

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });



                    AdminsaleListRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for ( DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                String id=postSnapshot.getKey();

                                Sales upload = postSnapshot.getValue(Sales.class);
                                //   mUploads11.add(upload);

                                String name = upload.getName().toString();
                                String price = upload.getPrice().toString();
                                String image = upload.getImageUrl().toString();
                                String percentage = upload.getSalePercentage().toString();
                                String saleprice = upload.getSalePrice().toString();

                                if (name.equals(orginal_name)) {
                                    //  name=edited_name;
                                    AdminsaleListRef.child(name).removeValue();
                                    AdminsaleListRef.child(edited_name).child("name").setValue(edited_name);
                                    AdminsaleListRef.child(edited_name).child("price").setValue(price);
                                    AdminsaleListRef.child(edited_name).child("salePercentage").setValue(percentage);
                                    AdminsaleListRef.child(edited_name).child("salePrice").setValue(saleprice);
                                    AdminsaleListRef.child(edited_name).child("imageUrl").setValue(image);
                                }

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    Toast.makeText(AdminProductsListActivity3.this, "This Product Name Edited Successfully", Toast.LENGTH_SHORT).show();
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

    public void onMakeSaleClick(final int position) {
        final DatabaseReference Users = FirebaseDatabase.getInstance().getReference("Users");
        final DatabaseReference UsersOrders = FirebaseDatabase.getInstance().getReference("Users Orders Lists");
        final AlertDialog.Builder builder= new AlertDialog.Builder(AdminProductsListActivity3.this);
        builder.setTitle("Enter the Sale Percentage You Want:");
        final EditText sale=new EditText(AdminProductsListActivity3.this);
        sale.setInputType(InputType.TYPE_CLASS_TEXT);
        // Editable num=quantity.getText();

        builder.setView(sale);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sale_percentage = sale.getText().toString();
                if (TextUtils.isEmpty(sale_percentage)) {
                    sale.setError("Enter the Sale Percentage!");
                    return;
                } else {
                    Upload selectedItem = mUploads.get(position);
                    final String selectedKey = selectedItem.getKey();
                    final String orginal_name = mUploads.get(position).getName().toString();
                    final String original_price[] = mUploads.get(position).getMpriceNEW().toString().split(" ");
                    double originalPrice=Double.parseDouble(original_price[0]);
                    int precentage=Integer.parseInt(sale_percentage);
                    double precent=precentage*0.01;
                    double salePrice=originalPrice*(precent);
                    double final_price_after_sale=originalPrice-salePrice;
                    fprice=String.valueOf(final_price_after_sale);
                    DatabaseReference saleListRef =  FirebaseDatabase.getInstance().getReference("Customers Sales List");
                    DatabaseReference AdminsaleListRef =  FirebaseDatabase.getInstance().getReference("Admin Sales List");

                    Sales sale = new Sales(selectedItem.getName().toString(),selectedItem.getImageUrl().toString(),selectedItem.getPrice().toString()
                            ,fprice+" ₪", sale_percentage+"%",category_name);


                    String uploadId = saleListRef.push().getKey();
                    DatabaseReference ref=saleListRef.child(selectedItem.getName());
                    DatabaseReference ref1=AdminsaleListRef.child(selectedItem.getName());
                    ref.setValue(sale);
                    ref1.setValue(sale);
                    mDatabaseRef.child(selectedKey).child("mpriceNEW").setValue(fprice+" ₪");
                    Toast.makeText(AdminProductsListActivity3.this, "Added Successfully to Sales", Toast.LENGTH_SHORT).show();

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


                                    if (name.equals(orginal_name)) {
                                        Users.child(id).child("Favorite List").child(name).child("mpriceNEW").setValue(fprice+" ₪");

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


                                    if (name.equals(orginal_name) ) {
                                        Users.child(id).child("Cart List").child(name).child("price").setValue(fprice+" ₪");

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


                                    if (name.equals(orginal_name)) {
                                        UsersOrders.child(id).child("Orders List").child(name).child("price").setValue(fprice+" ₪");

                                    }

                                }
                            }

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