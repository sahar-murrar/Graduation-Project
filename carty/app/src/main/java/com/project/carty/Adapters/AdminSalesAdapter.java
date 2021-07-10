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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.project.carty.CustomerProducts.ProductsListActivity;
import com.project.carty.Models.Cart;
import com.project.carty.Models.Order;
import com.project.carty.Models.Sales;
import com.project.carty.R;
import com.project.carty.Models.Upload;
import com.project.carty.activities.ShowOrderedProductsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminSalesAdapter extends RecyclerView.Adapter<AdminSalesAdapter.ImageViewHolder>{
    private Context mContext;
    private List<Sales> mUploads;
    private DatabaseReference mDatabaseRef;

    private AdminSalesAdapter.onItemClickListener mListener;
    public AdminSalesAdapter(Context context, List<Sales> uploads/*, ArrayList<String> fav*/) {
        mContext = context;
        mUploads = uploads;

    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.admin_sales_view, parent, false);
        return new ImageViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, final int position) {
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
        public ImageView imageView;
        public TextView SalePrice;
        public TextView SalePercentage;
        public ImageView delete;
        public TextView edit_sale;

        public ImageViewHolder(final View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewPrice = itemView.findViewById(R.id.oldPrice);
            imageView = itemView.findViewById(R.id.image_view_upload);
            SalePrice= itemView.findViewById(R.id.newPrice);
            SalePercentage= itemView.findViewById(R.id.sale_precentage);
            delete = itemView.findViewById(R.id.delete);
            edit_sale=itemView.findViewById(R.id.editsale);


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onDeleteClick(position, mUploads.get(position).getcategory());
                        }
                    }

                }
            });

            edit_sale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onEditSaleClick(position, mUploads.get(position).getcategory());

                        }
                    }

                }
            });


        }


    }

    public interface onItemClickListener{
        void onDeleteClick(int position, String category);
        void onEditSaleClick(int position, String category);


    }
    public void setOnItemClickListener(AdminSalesAdapter.onItemClickListener listener) {
        mListener = listener;
    }
}





