package com.project.carty.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.project.carty.Models.Cart;
import com.project.carty.Models.Notifications;
import com.project.carty.R;
import com.project.carty.activities.NavigationDrawerActivity;
import com.project.carty.activities.NotificationsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NotificationsAdapter  extends RecyclerView.Adapter<NotificationsAdapter.ImageViewHolder>{
    // private static final int FOOTER_TYPE=1;
    // private static final int LIST_TYPE=0;
    private Context mContext;
    private List<Notifications> mUploads;
    private NotificationsAdapter.onItemClickListener mListener;

    public NotificationsAdapter(Context context, List<Notifications> uploads) {
        mContext = context;
        mUploads = uploads;

    }
    @Override
    public NotificationsAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_view, parent, false);
        return new NotificationsAdapter.ImageViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(final NotificationsAdapter.ImageViewHolder holder, int position) {


        Notifications uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getCustomerName());
        holder.textViewRecommendedProducts.setText(uploadCurrent.getProductsNames());
        holder.textViewDoYouWant.setText("Do you need one of these products?");
        holder.categoryname.setText(uploadCurrent.getcategory());
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
        public TextView textViewDoYouWant;
        public ImageView delete;
        public TextView textViewRecommendedProducts;
        public CardView cardview;
        public ImageView imageView;
        public TextView categoryname;

        public ImageViewHolder(final View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewDoYouWant = itemView.findViewById(R.id.DoYouWant);
            delete = itemView.findViewById(R.id.delete);
            textViewRecommendedProducts = itemView.findViewById(R.id.RecommendedProducts);
            cardview= itemView.findViewById(R.id.cardview);
            imageView=itemView.findViewById(R.id.image_view);
            categoryname=itemView.findViewById(R.id.categoryname);

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

            cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onNotificationClick(position,mUploads.get(position).getcategory());
                        }
                    }

                }
            });



        }

    }

    public interface onItemClickListener{
        void onDeleteClick(int position);
        void onNotificationClick(int position,String category);


    }
    public void setOnItemClickListener(NotificationsAdapter.onItemClickListener listener) {
        mListener = listener;
    }


}
