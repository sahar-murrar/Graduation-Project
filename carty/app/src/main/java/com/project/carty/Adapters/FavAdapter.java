package com.project.carty.Adapters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.carty.CustomerProducts.ProductsListActivity;
import com.project.carty.CustomerProducts.ProductsListActivity2;
import com.project.carty.CustomerProducts.ProductsListActivity3;
import com.project.carty.CustomerProducts.ProductsListActivity4;
import com.project.carty.CustomerProducts.ProductsListActivity5;
import com.project.carty.CustomerProducts.ProductsListActivity6;
import com.project.carty.CustomerProducts.ProductsListActivity7;
import com.project.carty.CustomerProducts.ProductsListActivity8;
import com.project.carty.Models.Cart;
import com.project.carty.Models.Notifications;
import com.project.carty.Models.User;
import com.project.carty.R;
import com.project.carty.Models.Upload;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.app.NotificationManager.IMPORTANCE_HIGH;
import static com.google.firebase.storage.FirebaseStorage.getInstance;
import static com.project.carty.SplashScreen.SplashSleep.CHANNEL_1_ID;
import static com.project.carty.SplashScreen.SplashSleep.CHANNEL_2_ID;
import static com.project.carty.SplashScreen.SplashSleep.CHANNEL_3_ID;
import static com.project.carty.SplashScreen.SplashSleep.CHANNEL_4_ID;
import static com.project.carty.SplashScreen.SplashSleep.CHANNEL_5_ID;
import static com.project.carty.SplashScreen.SplashSleep.CHANNEL_6_ID;
import static com.project.carty.SplashScreen.SplashSleep.CHANNEL_7_ID;
import static com.project.carty.SplashScreen.SplashSleep.CHANNEL_8_ID;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;
    private List<Upload> mUploads1 = new ArrayList<>();
    private List<Cart> cartlist;
    private onItemClickListener mListener;
    private String myquatity;
    private String name0;
    private String name1;
    private String name2;
    private String name3;
    private String currentUserName[];

    private NotificationManagerCompat notificationManager;
    public FavAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;

    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_favorite_list, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position) {
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


    public class ImageViewHolder extends RecyclerView.ViewHolder  {
        public TextView textViewName;
        public TextView textViewPrice;
        public ImageView imageView;
        public ImageView favourites_image;
        public Button add_to_cart;
       // public TextView textViewQuantity;
        public ImageViewHolder(final View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewPrice=itemView.findViewById(R.id.text_view_price);
           // textViewQuantity=itemView.findViewById(R.id.text_view_quatity);
            imageView = itemView.findViewById(R.id.image_view_upload);
            favourites_image=itemView.findViewById(R.id.favourites_image);
            add_to_cart = itemView.findViewById(R.id.add_to_cart);
            favourites_image.setOnClickListener(new View.OnClickListener() {
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

            add_to_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  checkUserExisting();
                    addingToCartList();
                }
            });
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
                        String email=FirebaseAuth.getInstance().getCurrentUser().getEmail();
                        String newemail=email.replace('.','-');
                        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Cart List");
                        Cart upload = new Cart(textViewName.getText().toString().trim(), mUploads.get(getAdapterPosition()).getImageUrl().toString(),
                                textViewPrice.getText().toString(), myquatity);

                        String uploadId = cartListRef.push().getKey();
                        DatabaseReference ref = cartListRef.child(upload.getName());
                        ref.setValue(upload);
                        Toast.makeText(mContext, "Added Successfully to cart", Toast.LENGTH_SHORT).show();
                        final String orginal_category=mUploads.get(getAdapterPosition()).getcategory().toString();
                        final String orginal_name=mUploads.get(getAdapterPosition()).getName().toString();
                        final DatabaseReference products = FirebaseDatabase.getInstance().getReference(orginal_category);
                        notificationManager= NotificationManagerCompat.from(mContext);
                        DatabaseReference username=FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile Info");
                        username.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                User user = dataSnapshot.getValue(User.class);
                                currentUserName=user.getName().toString().split(" ");

                                products.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        mUploads1.clear();
                                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                            Upload upload = postSnapshot.getValue(Upload.class);
                                            mUploads1.add(upload);
                                        }
                            /*    int randomNum=new Random().nextInt(mUploads1.size());
                                int randomNum1=new Random().nextInt(mUploads1.size()-randomNum)+randomNum;
                                String rand=String.valueOf(randomNum);
                                String rand1=String.valueOf(randomNum1);
                                String zft=String.valueOf(mUploads1.size());
                                Toast.makeText(mContext,rand , Toast.LENGTH_SHORT).show();
                                Toast.makeText(mContext,rand1 , Toast.LENGTH_SHORT).show();
                                Toast.makeText(mContext,zft , Toast.LENGTH_SHORT).show();

                             */

                                        for(int i=0;i<4;i++) {
                                            Upload up = getRandomElement(mUploads1);
                                            // String name = up.getName().toString();
                                            if (i == 0) {
                                                name0 = up.getName().toString();
                                            }
                                            if (i == 1) {
                                                name1 = up.getName().toString();
                                            }
                                            if (i == 2) {
                                                name2 = up.getName().toString();
                                            }
                                            if (i == 3) {
                                                name3 = up.getName().toString();
                                            }
                                        }
                                        // String category=up.getcategory().toString();
/////////////////////////////////////////when all 4 name variables not null/////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////Fruits and Vegetables///////////////////////////////////////////////////////////////////////////////
                                        if (!name0.equals(orginal_name) &&!name0.equals(name1) &&!name0.equals(name2)
                                                && !name0.equals(name3) && !name1.equals(orginal_name)&& !name1.equals(name2)
                                                && !name1.equals(name3)&& !name2.equals(orginal_name)&& !name2.equals(name3)
                                                && !name3.equals(orginal_name) &&  orginal_category.equals("Fruits and Vegetables")) {
                                            //Toast.makeText(mContext, "do you want to buy " + name0+" " +name1+" "+name2+" "+name3 + " ?", Toast.LENGTH_SHORT).show();
                                            Intent activityIntent = new Intent(mContext, ProductsListActivity.class);
                                            PendingIntent contentIntent = PendingIntent.getActivity(mContext,
                                                    0, activityIntent, 0);

                                            Bitmap largeIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.fruits);
                                            Notification notification = new NotificationCompat.Builder(mContext, CHANNEL_1_ID)
                                                    .setSmallIcon(R.drawable.ic_notifications)
                                                    .setContentTitle("Products Recommendation")
                                                    .setContentText("Do you need one of these products? " +"\n"+ name0+", " +name1+", "+name2+" and "+name3)
                                                    .setLargeIcon(largeIcon)
                                                    .setStyle(new NotificationCompat.BigTextStyle()
                                                            .bigText("Do you need one of these products? " +"\n"+ name0+", " +name1+", "+name2+" and "+name3)
                                                            .setBigContentTitle("Dear "+currentUserName[0]+",")
                                                            .setSummaryText("Recommended Products"))
                                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                                    .setColor(0xd56d92)
                                                    .setContentIntent(contentIntent)
                                                    .setAutoCancel(true)
                                                    // .setOnlyAlertOnce(true)
                                                    .setPriority(IMPORTANCE_HIGH)
                                                    // .addAction(R.mipmap.ic_launcher, "Go To Buy", actionIntent)
                                                    .build();
                                            notificationManager.notify(1, notification);
                                            DatabaseReference notificationRef=FirebaseDatabase.getInstance().getReference("Users")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Notifications");
                                            Notifications noty=new Notifications(name0+", "+name1+", "+name2+" and "+name3, orginal_category,"Dear "+currentUserName[0]+", ",
                                                    "https://fyi.extension.wisc.edu/safefood/files/2019/04/CDC_produce.png");
                                            notificationRef.child(orginal_category).setValue(noty);
                                        }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


                                        /////////////////////////////////////////when all 4 name variables not null/////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////Meat, Poultry and Fish///////////////////////////////////////////////////////////////////////////////

                                        else if (!name0.equals(orginal_name) &&!name0.equals(name1) &&!name0.equals(name2)
                                                && !name0.equals(name3) && !name1.equals(orginal_name)&& !name1.equals(name2)
                                                && !name1.equals(name3)&& !name2.equals(orginal_name)&& !name2.equals(name3)
                                                && !name3.equals(orginal_name) && orginal_category.equals("Meat, Poultry and Fish")) {
                                            //  Toast.makeText(mContext, "do you want to buy " + name0+" " +name1+" "+name2+" "+name3 + " ?", Toast.LENGTH_SHORT).show();
                                            Intent activityIntent = new Intent(mContext, ProductsListActivity2.class);
                                            PendingIntent contentIntent = PendingIntent.getActivity(mContext,
                                                    0, activityIntent, 0);
                                            Bitmap largeIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.meat);
                                            Notification notification2 = new NotificationCompat.Builder(mContext, CHANNEL_2_ID)
                                                    .setSmallIcon(R.drawable.ic_notifications)
                                                    .setContentTitle("Products Recommendation")
                                                    .setContentText("Do you need one of these products? " +"\n"+ name0+", " +name1+", "+name2+" and "+name3)
                                                    .setLargeIcon(largeIcon)
                                                    .setStyle(new NotificationCompat.BigTextStyle()
                                                            .bigText("Do you need one of these products? " +"\n"+ name0+", " +name1+", "+name2+" and "+name3)
                                                            .setBigContentTitle("Dear "+currentUserName[0]+",")
                                                            .setSummaryText("Recommended Products"))


                                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                                    .setColor(0xd56d92)
                                                    .setContentIntent(contentIntent)
                                                    .setAutoCancel(true)
                                                    // .setOnlyAlertOnce(true)
                                                    .setPriority(IMPORTANCE_HIGH)
                                                    // .addAction(R.mipmap.ic_launcher, "Go To Buy", actionIntent)
                                                    .build();
                                            notificationManager.notify(2, notification2);
                                            DatabaseReference notificationRef=FirebaseDatabase.getInstance().getReference("Users")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Notifications");
                                            Notifications noty=new Notifications(name0+", "+name1+", "+name2+" and "+name3, orginal_category,"Dear "+currentUserName[0]+", ",
                                                    "https://thumbs.dreamstime.com/b/assortment-meat-seafood-beef-chicken-fish-pork-assortment-meat-seafood-149111241.jpg");
                                            notificationRef.child(orginal_category).setValue(noty);
                                        }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


/////////////////////////////////////////when all 4 name variables not null/////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////Bakery and Sweets///////////////////////////////////////////////////////////////////////////////


                                        else if (!name0.equals(orginal_name) &&!name0.equals(name1) &&!name0.equals(name2)
                                                && !name0.equals(name3) && !name1.equals(orginal_name)&& !name1.equals(name2)
                                                && !name1.equals(name3)&& !name2.equals(orginal_name)&& !name2.equals(name3)
                                                && !name3.equals(orginal_name) && orginal_category.equals("Bakery and Sweets")) {
                                            //  Toast.makeText(mContext, "do you want to buy " + name0+" " +name1+" "+name2+" "+name3 + " ?", Toast.LENGTH_SHORT).show();
                                            Intent activityIntent = new Intent(mContext, ProductsListActivity3.class);
                                            PendingIntent contentIntent = PendingIntent.getActivity(mContext,
                                                    0, activityIntent, 0);
                                            Bitmap largeIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.sweet);
                                            Notification notification3 = new NotificationCompat.Builder(mContext, CHANNEL_3_ID)
                                                    .setSmallIcon(R.drawable.ic_notifications)
                                                    .setContentTitle("Products Recommendation")
                                                    .setContentText("Do you need one of these products? " +"\n"+ name0+", " +name1+", "+name2+" and "+name3)
                                                    .setLargeIcon(largeIcon)
                                                    .setStyle(new NotificationCompat.BigTextStyle()
                                                            .bigText("Do you need one of these products? " +"\n"+ name0+", " +name1+", "+name2+" and "+name3)
                                                            .setBigContentTitle("Dear "+currentUserName[0]+",")
                                                            .setSummaryText("Recommended Products"))


                                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                                    .setColor(0xd56d92)
                                                    .setContentIntent(contentIntent)
                                                    .setAutoCancel(true)
                                                    // .setOnlyAlertOnce(true)
                                                    .setPriority(IMPORTANCE_HIGH)
                                                    // .addAction(R.mipmap.ic_launcher, "Go To Buy", actionIntent)
                                                    .build();
                                            notificationManager.notify(3, notification3);
                                            DatabaseReference notificationRef=FirebaseDatabase.getInstance().getReference("Users")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Notifications");
                                            Notifications noty=new Notifications(name0+", "+name1+", "+name2+" and "+name3, orginal_category,"Dear "+currentUserName[0]+", ",
                                                    "https://pngimage.net/wp-content/uploads/2018/06/indian-bakery-items-png-4.png");
                                            notificationRef.child(orginal_category).setValue(noty);
                                        }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


/////////////////////////////////////////when all 4 name variables not null/////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////Cheese and Dairy///////////////////////////////////////////////////////////////////////////////


                                        else if (!name0.equals(orginal_name) &&!name0.equals(name1) &&!name0.equals(name2)
                                                && !name0.equals(name3) && !name1.equals(orginal_name)&& !name1.equals(name2)
                                                && !name1.equals(name3)&& !name2.equals(orginal_name)&& !name2.equals(name3)
                                                && !name3.equals(orginal_name) && orginal_category.equals("Cheese and Dairy")) {
                                            //  Toast.makeText(mContext, "do you want to buy " + name0+" " +name1+" "+name2+" "+name3 + " ?", Toast.LENGTH_SHORT).show();
                                            Intent activityIntent = new Intent(mContext, ProductsListActivity4.class);
                                            PendingIntent contentIntent = PendingIntent.getActivity(mContext,
                                                    0, activityIntent, 0);
                                            Bitmap largeIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.cheese);
                                            Notification notification4 = new NotificationCompat.Builder(mContext, CHANNEL_4_ID)
                                                    .setSmallIcon(R.drawable.ic_notifications)
                                                    .setContentTitle("Products Recommendation")
                                                    .setContentText("Do you need one of these products? " +"\n"+ name0+", " +name1+", "+name2+" and "+name3)
                                                    .setLargeIcon(largeIcon)
                                                    .setStyle(new NotificationCompat.BigTextStyle()
                                                            .bigText("Do you need one of these products? " +"\n"+ name0+", " +name1+", "+name2+" and "+name3)
                                                            .setBigContentTitle("Dear "+currentUserName[0]+",")
                                                            .setSummaryText("Recommended Products"))


                                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                                    .setColor(0xd56d92)
                                                    .setContentIntent(contentIntent)
                                                    .setAutoCancel(true)
                                                    // .setOnlyAlertOnce(true)
                                                    .setPriority(IMPORTANCE_HIGH)
                                                    // .addAction(R.mipmap.ic_launcher, "Go To Buy", actionIntent)
                                                    .build();
                                            notificationManager.notify(4, notification4);
                                            DatabaseReference notificationRef=FirebaseDatabase.getInstance().getReference("Users")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Notifications");
                                            Notifications noty=new Notifications(name0+", "+name1+", "+name2+" and "+name3, orginal_category,"Dear "+currentUserName[0]+", ",
                                                    "https://www.donaldson.com/content/dam/donaldson/shared-assets/stock-imagery/industries-markets/food-beverage/Dairy-Products-Milk-Cheese-Cream-Eggs-Yogurt-Butter.jpg/jcr:content/renditions/cq5dam.web.1280.1280.jpeg");
                                            notificationRef.child(orginal_category).setValue(noty);
                                        }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


/////////////////////////////////////////when all 4 name variables not null/////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////Frozen Food///////////////////////////////////////////////////////////////////////////////

                                        else if (!name0.equals(orginal_name) &&!name0.equals(name1) &&!name0.equals(name2)
                                                && !name0.equals(name3) && !name1.equals(orginal_name)&& !name1.equals(name2)
                                                && !name1.equals(name3)&& !name2.equals(orginal_name)&& !name2.equals(name3)
                                                && !name3.equals(orginal_name) && orginal_category.equals("Frozen Food")) {
                                            //  Toast.makeText(mContext, "do you want to buy " + name0+" " +name1+" "+name2+" "+name3 + " ?", Toast.LENGTH_SHORT).show();
                                            Intent activityIntent = new Intent(mContext, ProductsListActivity5.class);
                                            PendingIntent contentIntent = PendingIntent.getActivity(mContext,
                                                    0, activityIntent, 0);
                                            Bitmap largeIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.frozen);
                                            Notification notification5 = new NotificationCompat.Builder(mContext, CHANNEL_5_ID)
                                                    .setSmallIcon(R.drawable.ic_notifications)
                                                    .setContentTitle("Products Recommendation")
                                                    .setContentText("Do you need one of these products? " +"\n"+ name0+", " +name1+", "+name2+" and "+name3)
                                                    .setLargeIcon(largeIcon)
                                                    .setStyle(new NotificationCompat.BigTextStyle()
                                                            .bigText("Do you need one of these products? " +"\n"+ name0+", " +name1+", "+name2+" and "+name3)
                                                            .setBigContentTitle("Dear "+currentUserName[0]+",")
                                                            .setSummaryText("Recommended Products"))


                                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                                    .setColor(0xd56d92)
                                                    .setContentIntent(contentIntent)
                                                    .setAutoCancel(true)
                                                    // .setOnlyAlertOnce(true)
                                                    .setPriority(IMPORTANCE_HIGH)
                                                    // .addAction(R.mipmap.ic_launcher, "Go To Buy", actionIntent)
                                                    .build();
                                            notificationManager.notify(5, notification5);
                                            DatabaseReference notificationRef=FirebaseDatabase.getInstance().getReference("Users")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Notifications");
                                            Notifications noty=new Notifications(name0+", "+name1+", "+name2+" and "+name3, orginal_category,"Dear "+currentUserName[0]+", ",
                                                    "https://static.toiimg.com/thumb/77315077.cms?width=680&height=512&imgsize=234326");
                                            notificationRef.child(orginal_category).setValue(noty);
                                        }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/////////////////////////////////////////when all 4 name variables not null/////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////Cleaning Needs///////////////////////////////////////////////////////////////////////////////


                                        else if (!name0.equals(orginal_name) &&!name0.equals(name1) &&!name0.equals(name2)
                                                && !name0.equals(name3) && !name1.equals(orginal_name)&& !name1.equals(name2)
                                                && !name1.equals(name3)&& !name2.equals(orginal_name)&& !name2.equals(name3)
                                                && !name3.equals(orginal_name) && orginal_category.equals("Cleaning Needs")) {
                                            //  Toast.makeText(mContext, "do you want to buy " + name0+" " +name1+" "+name2+" "+name3 + " ?", Toast.LENGTH_SHORT).show();
                                            Intent activityIntent = new Intent(mContext, ProductsListActivity6.class);
                                            PendingIntent contentIntent = PendingIntent.getActivity(mContext,
                                                    0, activityIntent, 0);
                                            Bitmap largeIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.clean);
                                            Notification notification6 = new NotificationCompat.Builder(mContext, CHANNEL_6_ID)
                                                    .setSmallIcon(R.drawable.ic_notifications)
                                                    .setContentTitle("Products Recommendation")
                                                    .setContentText("Do you need one of these products? " +"\n"+ name0+", " +name1+", "+name2+" and "+name3)
                                                    .setLargeIcon(largeIcon)
                                                    .setStyle(new NotificationCompat.BigTextStyle()
                                                            .bigText("Do you need one of these products? " +"\n"+ name0+", " +name1+", "+name2+" and "+name3)
                                                            .setBigContentTitle("Dear "+currentUserName[0]+",")
                                                            .setSummaryText("Recommended Products"))


                                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                                    .setColor(0xd56d92)
                                                    .setContentIntent(contentIntent)
                                                    .setAutoCancel(true)
                                                    // .setOnlyAlertOnce(true)
                                                    .setPriority(IMPORTANCE_HIGH)
                                                    // .addAction(R.mipmap.ic_launcher, "Go To Buy", actionIntent)
                                                    .build();
                                            notificationManager.notify(6, notification6);
                                            DatabaseReference notificationRef=FirebaseDatabase.getInstance().getReference("Users")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Notifications");
                                            Notifications noty=new Notifications(name0+", "+name1+", "+name2+" and "+name3, orginal_category,"Dear "+currentUserName[0]+", ",
                                                    "https://image.shutterstock.com/image-photo/cleaning-supplies-isolated-on-white-260nw-129640784.jpg");
                                            notificationRef.child(orginal_category).setValue(noty);
                                        }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/////////////////////////////////////////when all 4 name variables not null/////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////Rice, Pasta and Legumes///////////////////////////////////////////////////////////////////////////////

                                        else if (!name0.equals(orginal_name) &&!name0.equals(name1) &&!name0.equals(name2)
                                                && !name0.equals(name3) && !name1.equals(orginal_name)&& !name1.equals(name2)
                                                && !name1.equals(name3)&& !name2.equals(orginal_name)&& !name2.equals(name3)
                                                && !name3.equals(orginal_name) && orginal_category.equals("Rice, Pasta and Legumes")) {
                                            //  Toast.makeText(mContext, "do you want to buy " + name0+" " +name1+" "+name2+" "+name3 + " ?", Toast.LENGTH_SHORT).show();
                                            Intent activityIntent = new Intent(mContext, ProductsListActivity7.class);
                                            PendingIntent contentIntent = PendingIntent.getActivity(mContext,
                                                    0, activityIntent, 0);
                                            Bitmap largeIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.rice);
                                            Notification notification7 = new NotificationCompat.Builder(mContext, CHANNEL_7_ID)
                                                    .setSmallIcon(R.drawable.ic_notifications)
                                                    .setContentTitle("Products Recommendation")
                                                    .setContentText("Do you need one of these products? " +"\n"+ name0+", " +name1+", "+name2+" and "+name3)
                                                    .setLargeIcon(largeIcon)
                                                    .setStyle(new NotificationCompat.BigTextStyle()
                                                            .bigText("Do you need one of these products? " +"\n"+ name0+", " +name1+", "+name2+" and "+name3)
                                                            .setBigContentTitle("Dear "+currentUserName[0]+",")
                                                            .setSummaryText("Recommended Products"))


                                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                                    .setColor(0xd56d92)
                                                    .setContentIntent(contentIntent)
                                                    .setAutoCancel(true)
                                                    // .setOnlyAlertOnce(true)
                                                    .setPriority(IMPORTANCE_HIGH)
                                                    // .addAction(R.mipmap.ic_launcher, "Go To Buy", actionIntent)
                                                    .build();
                                            notificationManager.notify(7, notification7);
                                            DatabaseReference notificationRef=FirebaseDatabase.getInstance().getReference("Users")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Notifications");
                                            Notifications noty=new Notifications(name0+", "+name1+", "+name2+" and "+name3, orginal_category,"Dear "+currentUserName[0]+", ",
                                                    "https://homemaderecipes.com/wp-content/uploads/2015/06/Pasta-and-Grains-OPT.jpg");
                                            notificationRef.child(orginal_category).setValue(noty);
                                        }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/////////////////////////////////////////when all 4 name variables not null/////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////Drinks///////////////////////////////////////////////////////////////////////////////

                                        else if (!name0.equals(orginal_name) &&!name0.equals(name1) &&!name0.equals(name2)
                                                && !name0.equals(name3) && !name1.equals(orginal_name)&& !name1.equals(name2)
                                                && !name1.equals(name3)&& !name2.equals(orginal_name)&& !name2.equals(name3)
                                                && !name3.equals(orginal_name) && orginal_category.equals("Drinks")) {
                                            //  Toast.makeText(mContext, "do you want to buy " + name0+" " +name1+" "+name2+" "+name3 + " ?", Toast.LENGTH_SHORT).show();
                                            Intent activityIntent = new Intent(mContext, ProductsListActivity8.class);
                                            PendingIntent contentIntent = PendingIntent.getActivity(mContext,
                                                    0, activityIntent, 0);
                                            Bitmap largeIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.drinks);
                                            Notification notification8 = new NotificationCompat.Builder(mContext, CHANNEL_8_ID)
                                                    .setSmallIcon(R.drawable.ic_notifications)
                                                    .setContentTitle("Products Recommendation")
                                                    .setContentText("Do you need one of these products? " +"\n"+ name0+", " +name1+", "+name2+" and "+name3)
                                                    .setLargeIcon(largeIcon)
                                                    .setStyle(new NotificationCompat.BigTextStyle()
                                                            .bigText("Do you need one of these products? " +"\n"+ name0+", " +name1+", "+name2+" and "+name3)
                                                            .setBigContentTitle("Dear "+currentUserName[0]+",")
                                                            .setSummaryText("Recommended Products"))


                                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                                    .setColor(0xd56d92)
                                                    .setContentIntent(contentIntent)
                                                    .setAutoCancel(true)
                                                    // .setOnlyAlertOnce(true)
                                                    .setPriority(IMPORTANCE_HIGH)
                                                    // .addAction(R.mipmap.ic_launcher, "Go To Buy", actionIntent)
                                                    .build();
                                            notificationManager.notify(8, notification8);
                                            DatabaseReference notificationRef=FirebaseDatabase.getInstance().getReference("Users")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Notifications");
                                            Notifications noty=new Notifications(name0+", "+name1+", "+name2+" and "+name3, orginal_category,"Dear "+currentUserName[0]+", ",
                                                    "https://lh3.googleusercontent.com/proxy/vVgGX4J0jHmaA4RUQtNWbM9g_vh59cd2RqqktZpTn26bBCDV78KPenjxunOl3aOsJN0DZupd7704KDrDGYFs6OWRoN4A-mde466M-DsVd-RCCkMze0X2xw");
                                            notificationRef.child(orginal_category).setValue(noty);
                                        }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


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

        public Upload getRandomElement(List<Upload> list)
        {
            Random rand = new Random();
            return list.get(rand.nextInt(list.size()));
        }

    }

    public interface onItemClickListener{
        void onDeleteClick(int position);

    }
    public void setOnItemClickListener(onItemClickListener listener) {
        mListener = listener;
    }






}
