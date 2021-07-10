package com.project.carty.ui.Favourites;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.carty.R;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageView;
import android.widget.ProgressBar;

import com.project.carty.Adapters.ProductsAdapter;
import com.project.carty.Models.Upload;

import java.util.ArrayList;
import java.util.List;


public class Favourites extends Fragment {

    private FavouritesViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private ProductsAdapter favAdapter;
    private ProgressBar mProgressCircle;
    private ImageView image;
    private List<Upload> favItemsList=new ArrayList<>();

    public static Favourites newInstance() {
        return new Favourites();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.favourites_fragment, container, false);
        mRecyclerView = (RecyclerView) view. findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mProgressCircle = (ProgressBar) view.findViewById(R.id.progress_circle);
        loadData();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FavouritesViewModel.class);
        // TODO: Use the ViewModel
    }
 public void loadData(){
       favAdapter=new ProductsAdapter(getActivity(),favItemsList);
       mRecyclerView.setAdapter(favAdapter);
 }
}