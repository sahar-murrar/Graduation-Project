package com.project.carty.Adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.carty.Models.Cart;
import com.project.carty.Models.Upload;
import com.project.carty.R;

import java.util.List;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Cart> mUploads;
    private onItemClickListener mListener;
    public OrdersAdapter(Context context, List<Cart> uploads) {
        mContext = context;
        mUploads = uploads;

    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.orders_view, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position) {
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


      //  Intent intent =((Activity) mContext).getIntent();
      //  String first_name=intent.getStringExtra("Name");
      //  holder.name.setText(first_name);

     //   holder.phone.setText(uploadCurrent.getPhoneNum());


    }
    @Override
    public int getItemCount() {

        return mUploads.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView price;
        public TextView quantity;
      //  public TextView time;
        public ImageViewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            quantity=itemView.findViewById(R.id.quantity);
         //   time=itemView.findViewById(R.id.time);

        }

    }

    public interface onItemClickListener{
        void onDeleteClick(int position);


    }
    public void setOnItemClickListener(onItemClickListener listener) {
        mListener = listener;
    }



}
