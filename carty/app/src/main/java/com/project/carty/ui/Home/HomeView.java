package com.project.carty.ui.Home;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.navigation.NavigationView;
import com.project.carty.R;

public class HomeView extends Fragment {
  //  DrawerLayout drawer;
  //  NavigationView navigationView;
    private HomeViewViewModel mViewModel;

    public static HomeView newInstance() {
        return new HomeView();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_view_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewViewModel.class);

    }

/*
@Override
public void onViewCreated(View view, Bundle savedInstanceState){
    super.onViewCreated(view, savedInstanceState);

    drawer=view.findViewById(R.id.drawer_layout);
    navigationView=view.findViewById(R.id.nav_view);
    view.findViewById(R.id.imageMenu2).setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            drawer.openDrawer(GravityCompat.START);
        }


         });
}
*/
}

