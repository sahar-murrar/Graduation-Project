package com.project.carty.Adapters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.project.carty.CustomerProducts.ProductsListActivity;
import com.project.carty.Models.Cart;
import com.project.carty.Models.Order;
import com.project.carty.R;
import com.project.carty.Models.Upload;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminProductsAdapter extends RecyclerView.Adapter<AdminProductsAdapter.ImageViewHolder>{
    private Context mContext;
    private List<Upload> mUploads;
    private DatabaseReference mDatabaseRef;
    private List<Cart> cartlist;
    private String myquatity="1";
    private String edited_price;
    private String edited_name;
    private AdminProductsAdapter.onItemClickListener mListener;
    public AdminProductsAdapter(Context context, List<Upload> uploads,DatabaseReference Ref /*, ArrayList<String> fav*/) {
        mContext = context;
        mUploads = uploads;
        mDatabaseRef=Ref;
        //  mfav=fav;

    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.admin_products_view, parent, false);
        return new ImageViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, final int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        if(uploadCurrent.getPrice().equals(uploadCurrent.getMpriceNEW()))
            holder.textViewPrice.setText(uploadCurrent.getPrice());
        else holder.textViewPrice.setText(uploadCurrent.getMpriceNEW());

        //  holder.textViewQuantity.setText(myquatity);
        Picasso.get()
                .load(uploadCurrent.getImageUrl())
                .fit()
                .centerCrop()
                .into(holder.imageView);


    }

    @Override
    public int getItemCount() {

        return mUploads.size();
    }



    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewPrice;
        public ImageView imageView;
      //  public ImageView favourites_image;
      //  public Button add_to_cart;
        public TextView textViewQuantity;
        public TextView EditProduct;
        public TextView EditProductName;
        public ImageView delete;
        public TextView make_sale;

        public ImageViewHolder(final View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewPrice = itemView.findViewById(R.id.text_view_price);
            imageView = itemView.findViewById(R.id.image_view_upload);
          //  favourites_image = itemView.findViewById(R.id.favourites_image);
         //   add_to_cart = itemView.findViewById(R.id.add_to_cart);
            EditProduct=itemView.findViewById(R.id.edit_product);
            EditProductName=itemView.findViewById(R.id.edit_product_name);
            delete = itemView.findViewById(R.id.delete);
            make_sale=itemView.findViewById(R.id.sale);


          /*  favourites_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    addingToFavList();
                }
            });

            add_to_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  checkUserExisting();
                    addingToCartList();
                }
            });

           */

            EditProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onEditPriceClick(position);

                        }
                    }

                }
            });
            EditProductName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onEditNameClick(position);

                        }
                    }
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onDeleteClick(position);
                        }
                    }

                }
            });

            make_sale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onMakeSaleClick(position);

                        }
                    }

                }
            });


        }

    /*    public void addingToFavList() {

            favourites_image.setImageResource(R.drawable.ic_pink_heart);
            favourites_image.setTag(R.drawable.ic_pink_heart);
            DatabaseReference favListRef = FirebaseDatabase.getInstance().getReference("Favorite List");

            Upload upload = new Upload(textViewName.getText().toString().trim(), mUploads.get(getAdapterPosition()).getImageUrl().toString(),
                    textViewPrice.getText().toString());

            String uploadId = favListRef.push().getKey();
            DatabaseReference ref=favListRef.child(upload.getName());
            ref.setValue(upload);
            Toast.makeText(mContext, "Added Successfully to Favourites", Toast.LENGTH_SHORT).show();


        }


        public void addingToCartList() {
            AlertDialog.Builder builder= new AlertDialog.Builder(mContext);
            builder.setTitle("Enter the Quantity You need");
            final EditText quantity=new EditText(mContext);
            quantity.setInputType(InputType.TYPE_CLASS_TEXT);
            // Editable num=quantity.getText();

            builder.setView(quantity);
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myquatity=quantity.getText().toString();
                    if (TextUtils.isEmpty(myquatity)) {
                        quantity.setError("Enter the Quantity!");
                        return;
                    }
                    else {
                        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference("Cart List");
                        Cart upload = new Cart(textViewName.getText().toString().trim(), mUploads.get(getAdapterPosition()).getImageUrl().toString(),
                                textViewPrice.getText().toString(), myquatity);

                        String uploadId = cartListRef.push().getKey();
                        DatabaseReference ref = cartListRef.child(upload.getName());
                        ref.setValue(upload);
                        Toast.makeText(mContext, "Added Successfully to cart", Toast.LENGTH_SHORT).show();
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

     */

       /* public void EditProductsPrice(){
            AlertDialog.Builder builder= new AlertDialog.Builder(mContext);
            builder.setTitle("Edit the Product Price");
            final EditText price=new EditText(mContext);
            price.setInputType(InputType.TYPE_CLASS_TEXT);
            // Editable num=quantity.getText();

            builder.setView(price);
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    edited_price=price.getText().toString();
                    if (TextUtils.isEmpty(edited_price)) {
                        price.setError("Enter the Price!");
                        return;
                    }
                    else {
                        Query query = mDatabaseRef.orderByChild("name").equalTo(mUploads.get(getAdapterPosition()).getName());
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                mUploads.clear();
                                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                                    ds.child("price").getRef().setValue(edited_price +" ₪");
                                    textViewPrice.setText(edited_price +" ₪");
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
        public void EditProductsName(){
           AlertDialog.Builder builder= new AlertDialog.Builder(mContext);
            builder.setTitle("Edit the Product Name");
            final EditText name=new EditText(mContext);
            name.setInputType(InputType.TYPE_CLASS_TEXT);
            // Editable num=quantity.getText();

            builder.setView(name);
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    edited_name=name.getText().toString();
                    if (TextUtils.isEmpty(edited_name)) {
                        name.setError("Enter the Name!");
                        return;
                    }
                    else {
                        Query query = mDatabaseRef.orderByChild("name").equalTo(mUploads.get(getAdapterPosition()).getName());
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                mUploads.clear();
                                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                    ds.child("name").getRef().setValue(edited_name);
                                    textViewPrice.setText(edited_name);
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

        */
    }

    public interface onItemClickListener{
        void onDeleteClick(int position);
        void onEditPriceClick(int position);
        void onEditNameClick(int position);
        void onMakeSaleClick(int position);


    }
    public void setOnItemClickListener(AdminProductsAdapter.onItemClickListener listener) {
        mListener = listener;
    }
}





