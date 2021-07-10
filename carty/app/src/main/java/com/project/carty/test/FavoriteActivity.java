package com.project.carty.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.carty.Adapters.ProductsAdapter;
import com.project.carty.R;
import com.squareup.picasso.Picasso;

public class FavoriteActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_list);
        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

   /* public void onStart() {
        super.onStart();

        final DatabaseReference favListRef= FirebaseDatabase.getInstance().getReference().child("Favorite List");
        FirebaseRecyclerOptions<Upload> options= new FirebaseRecyclerOptions.Builder<Upload>().setQuery(favListRef.child("User View"), Upload.class).build();
        FirebaseRecyclerAdapter<Upload, FavViewHolder> adapter = new FirebaseRecyclerAdapter<Upload, FavViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FavViewHolder favViewHolder, int i, @NonNull Upload upload) {
                favViewHolder.textViewName.setText(upload.getName());
                favViewHolder.textViewPrice.setText(upload.getPrice());
                Picasso.get()
                        .load(upload.getImageUrl())
                        .fit()
                        .centerCrop()
                        .into(favViewHolder.imageView);

            }

            @NonNull
            @Override
            public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_favorite_list, parent, false);
                FavViewHolder holder = new FavViewHolder(view);
                return holder;

            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
        recyclerView.setVisibility(View.VISIBLE);

    }*/
}