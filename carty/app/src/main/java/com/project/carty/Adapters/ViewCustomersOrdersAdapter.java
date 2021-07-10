package com.project.carty.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.carty.Models.Cart;
import com.project.carty.Models.Order;
import com.project.carty.Models.TotalPrice;
import com.project.carty.Models.Upload;
import com.project.carty.R;
import com.project.carty.activities.OrdersListActivity;
import com.project.carty.activities.ShowOrderedProductsActivity;
import com.project.carty.activities.UserActitvity;
import com.project.carty.activities.ViewCustomersOrdersActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ViewCustomersOrdersAdapter extends RecyclerView.Adapter<ViewCustomersOrdersAdapter.ImageViewHolder>{

        private Context mContext;
        private List<Order> mUploads;
        private List<TotalPrice> mUploads1;
        private String myquatity="1";
        private ViewCustomersOrdersAdapter.onItemClickListener mListener;
    public ViewCustomersOrdersAdapter(Context context, List<Order> uploads) {
            mContext = context;
            mUploads = uploads;

        }

        @Override
        public ViewCustomersOrdersAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.view_customers_orders, parent, false);
            return new ImageViewHolder(v);

        }

        @Override
        public void onBindViewHolder(final ImageViewHolder holder, final int position) {
            final Order uploadCurrent = mUploads.get(position);
            holder.Fullname.setText(uploadCurrent.getFullName());
            holder.Phone.setText(uploadCurrent.getPhoneNum());
            holder.Email.setText(uploadCurrent.getEmail());
            holder.City.setText(uploadCurrent.getCity());
            holder.Street.setText(uploadCurrent.getStreet());
            if(uploadCurrent.getPayment_method().equals("Cash")) {
                holder.paymentway.setText(uploadCurrent.getPayment_method());
            }
            else{
                holder.paymentway.setText(uploadCurrent.getPayment_method()+"\n& cardnum: "+uploadCurrent.getCardNum());
            }
            holder.Orderdate.setText(uploadCurrent.getOrderDate());
            holder.Ordertime.setText(uploadCurrent.getOrderTime());

            if(uploadCurrent.getTotalPrice().equals(uploadCurrent.getNewTotalPrice())) {
                holder.totalpay.setText(uploadCurrent.getTotalPrice());
            }
            else{
                holder.totalpay.setText(uploadCurrent.getNewTotalPrice());
            }

            holder.productsInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(mContext, ShowOrderedProductsActivity.class);
                     intent.putExtra("email", uploadCurrent.getEmail());
                    mContext.startActivity(intent);
                }

            });


        }

        @Override
        public int getItemCount() {

            return mUploads.size();
        }



        public class ImageViewHolder extends RecyclerView.ViewHolder {
            public TextView Fullname;
            public TextView Phone;
            public TextView Email;
            public TextView City;
            public TextView Street;
            public TextView totalpay;
            public TextView paymentway;
            public TextView Orderdate;
            public TextView Ordertime;
            public TextView productsInfo;
            public ImageView delete;

            public ImageViewHolder(final View itemView) {
                super(itemView);
                Fullname = itemView.findViewById(R.id.Fullname);
                Phone = itemView.findViewById(R.id.Phone);
                Email = itemView.findViewById(R.id.Email);
                City = itemView.findViewById(R.id.city);
                Street = itemView.findViewById(R.id.street);
                totalpay = itemView.findViewById(R.id.totalPay);
                paymentway = itemView.findViewById(R.id.PaymentWay);
                Orderdate = itemView.findViewById(R.id.orderdate);
                Ordertime = itemView.findViewById(R.id.ordertime);
                productsInfo = itemView.findViewById(R.id.productsInfo);
                delete = itemView.findViewById(R.id.delete);

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                mListener.onDeleteClick(position, mUploads.get(position).getEmail().toString());
                            }
                        }

                    }
                });



            }



        }

    public interface onItemClickListener{
        void onDeleteClick(int position, String email);


    }
    public void setOnItemClickListener(ViewCustomersOrdersAdapter.onItemClickListener listener) {
        mListener = listener;
    }

}


