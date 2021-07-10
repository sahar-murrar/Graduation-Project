package com.project.carty.test;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.carty.Interface.ItemClickListener;
import com.project.carty.R;

public class FavViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView textViewName;
    public TextView textViewPrice;
    public ImageView imageView;
    public ImageView favourites_image, cart_image;
    public Button add_to_cart;
    private ItemClickListener itemClickListener;

    public FavViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewName = itemView.findViewById(R.id.text_view_name);
        textViewPrice=itemView.findViewById(R.id.text_view_price);
        imageView = itemView.findViewById(R.id.image_view_upload);
        favourites_image=itemView.findViewById(R.id.favourites_image);
        cart_image=itemView.findViewById(R.id.cart_image);
        add_to_cart=itemView.findViewById(R.id.add_to_cart);

    }

    @Override
    public void onClick(View view) {
       // long id=view.getId();
        itemClickListener.onClick(view, getAdapterPosition(),false);

    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }
}
