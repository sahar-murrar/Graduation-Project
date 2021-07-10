package com.project.carty.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.project.carty.Models.Cart;
import com.project.carty.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShowOrderedProductsAdapter extends RecyclerView.Adapter<ShowOrderedProductsAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Cart> mUploads;

    public ShowOrderedProductsAdapter(Context context, List<Cart> uploads) {
        mContext = context;
        mUploads = uploads;

    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_products_info_layout, parent, false);
        return new ImageViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, int position) {

        Cart uploadCurrent = mUploads.get(position);
        holder.name.setText(uploadCurrent.getName());
        holder.quantity.setText(uploadCurrent.getQuantity());

        String price[]=uploadCurrent.getPrice().split(" ");
        String quantity=uploadCurrent.getQuantity();
        double pr=Double.parseDouble(price[0].toString());
        int qua=Integer.parseInt(quantity.toString());
        double tot=pr*qua;
        String total=String.valueOf(tot);

        holder.price.setText(" " +total +" ₪" );


    }

    @Override
    public int getItemCount() {

        return mUploads.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView price;
        public TextView quantity;

        public ImageViewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            quantity=itemView.findViewById(R.id.quantity);

        }

    }
}

