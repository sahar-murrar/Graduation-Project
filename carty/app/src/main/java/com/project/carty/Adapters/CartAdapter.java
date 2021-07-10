package com.project.carty.Adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.carty.Models.Cart;
import com.project.carty.Models.Upload;
import com.project.carty.R;
import com.project.carty.activities.CartListActivity;
import com.project.carty.activities.UserInformationActivity;
import com.squareup.picasso.Picasso;
import java.util.List;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ImageViewHolder>{
   // private static final int FOOTER_TYPE=1;
   // private static final int LIST_TYPE=0;
    private Context mContext;
    private List<Cart> mUploads;
    private onItemClickListener mListener;

    public CartAdapter(Context context, List<Cart> uploads) {
        mContext = context;
        mUploads = uploads;

    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_view, parent, false);
            return new ImageViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder( final ImageViewHolder holder,  int position) {


           Cart uploadCurrent = mUploads.get(position);
           holder.textViewName.setText(uploadCurrent.getName());
           holder.textViewPrice.setText(uploadCurrent.getPrice());
           holder.textViewQuantity.setText("Quantity= "+uploadCurrent.getQuantity());

           String[] price=holder.textViewPrice.getText().toString().split(" ");
           String quantity=holder.textViewQuantity.getText().toString().substring(10);
           double pr=Double.parseDouble(price[0].toString());
           int qua=Integer.parseInt(quantity.toString());
           double tot=pr*qua;
           String total=String.valueOf(tot);

           holder.textViewTotalPrice.setText("Total Price: " +total +" ₪" );

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
        public ImageView delete;
        public TextView textViewQuantity;
        public TextView textViewTotalPrice;

        public ImageViewHolder(final View itemView) {
            super(itemView);
                textViewName = itemView.findViewById(R.id.text_view_name);
                textViewPrice = itemView.findViewById(R.id.text_view_price);
                imageView = itemView.findViewById(R.id.image_view_upload);
                delete = itemView.findViewById(R.id.delete);
                textViewQuantity = itemView.findViewById(R.id.text_view_quatity);
                textViewTotalPrice = itemView.findViewById(R.id.text_view_total_price);

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



        }

    }

    public interface onItemClickListener{
        void onDeleteClick(int position);


    }
    public void setOnItemClickListener(onItemClickListener listener) {
        mListener = listener;
    }

  /*  private int TotalPrice(List<Upload> items){

        int totalPrice = 0;
        for(int i = 0 ; i < items.size(); i++) {
            String[] total= items.get(i).getPrice().split(" ");
            int tot=Integer.parseInt(total[0].toString()) ;
            totalPrice=totalPrice+tot;
        }

        return totalPrice;
    }

    @Override
    public int getItemViewType(int position) {
       // return (position == mUploads.size()) ? R.layout.footer : R.layout.cart_view;
        if (isPositionItem(position))
            return LIST_TYPE;
        return FOOTER_TYPE;
    }
    private boolean isPositionItem(int position) {
        return position != getItemCount()-1; // last position
    }

   */
}
