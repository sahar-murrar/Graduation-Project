package com.project.carty.Adapters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.carty.Models.Cart;
import com.project.carty.Models.Sales;
import com.project.carty.Models.Upload;
import com.project.carty.R;
import com.project.carty.activities.CartListActivity;
import com.project.carty.activities.UserInformationActivity;
import com.squareup.picasso.Picasso;
import java.util.List;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class CustomerSalesAdapter extends RecyclerView.Adapter<CustomerSalesAdapter.ImageViewHolder>{
    // private static final int FOOTER_TYPE=1;
    // private static final int LIST_TYPE=0;
    private Context mContext;
    private String myquatity="1";
    private List<Sales> mUploads;


    public CustomerSalesAdapter(Context context, List<Sales> uploads) {
        mContext = context;
        mUploads = uploads;

    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_sales_view, parent, false);
        return new ImageViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder( final ImageViewHolder holder,  int position) {


        Sales uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        holder.textViewPrice.setText(uploadCurrent.getPrice());
        holder.textViewPrice.setPaintFlags(holder.textViewPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.SalePrice.setText(uploadCurrent.getSalePrice());
        holder.SalePercentage.setText(uploadCurrent.getSalePercentage()+" OFF");

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
        public TextView SalePrice;
        public TextView SalePercentage;
        public ImageView imageView;
        public ImageView favourites_image;
        public Button add_to_cart;

        public ImageViewHolder(final View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewPrice = itemView.findViewById(R.id.oldPrice);
            SalePrice= itemView.findViewById(R.id.newPrice);
            SalePercentage= itemView.findViewById(R.id.sale_precentage);
            imageView = itemView.findViewById(R.id.image_view_upload);
            favourites_image = itemView.findViewById(R.id.favourites_image);
            add_to_cart = itemView.findViewById(R.id.add_to_cart);

            favourites_image.setOnClickListener(new View.OnClickListener() {
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


        }

        public void addingToFavList() {

            favourites_image.setImageResource(R.drawable.ic_pink_heart);
            favourites_image.setTag(R.drawable.ic_pink_heart);
            // DatabaseReference favListRef = FirebaseDatabase.getInstance().getReference("Favorite List");
            DatabaseReference favListRef =  FirebaseDatabase.getInstance().getReference("Users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Favorite List");



            Upload upload = new Upload(textViewName.getText().toString().trim(), mUploads.get(getAdapterPosition()).getImageUrl().toString(),
                    SalePrice.getText().toString()+" **"+SalePercentage.getText().toString()+"**",mUploads.get(getAdapterPosition()).getcategory().toString(),mUploads.get(getAdapterPosition()).getSalePrice().toString());


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
                        //  DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference("Cart List");
                        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Cart List");

                        Cart upload = new Cart(textViewName.getText().toString().trim(), mUploads.get(getAdapterPosition()).getImageUrl().toString(),
                                SalePrice.getText().toString()+" **"+SalePercentage.getText().toString()+"**", myquatity);

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

    }


}
