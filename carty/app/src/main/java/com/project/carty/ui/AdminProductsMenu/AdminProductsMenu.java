package com.project.carty.ui.AdminProductsMenu;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.project.carty.AdminProducts.AdminProductsListActivity;
import com.project.carty.AdminProducts.AdminProductsListActivity2;
import com.project.carty.AdminProducts.AdminProductsListActivity3;
import com.project.carty.AdminProducts.AdminProductsListActivity4;
import com.project.carty.AdminProducts.AdminProductsListActivity5;
import com.project.carty.AdminProducts.AdminProductsListActivity6;
import com.project.carty.AdminProducts.AdminProductsListActivity7;
import com.project.carty.AdminProducts.AdminProductsListActivity8;
import com.project.carty.CustomerProducts.ProductsListActivity;
import com.project.carty.CustomerProducts.ProductsListActivity2;
import com.project.carty.CustomerProducts.ProductsListActivity3;
import com.project.carty.CustomerProducts.ProductsListActivity4;
import com.project.carty.CustomerProducts.ProductsListActivity5;
import com.project.carty.CustomerProducts.ProductsListActivity6;
import com.project.carty.CustomerProducts.ProductsListActivity7;
import com.project.carty.CustomerProducts.ProductsListActivity8;
import com.project.carty.R;
import com.project.carty.activities.AdminSalesListActivity;
import com.project.carty.activities.CartListActivity;
import com.project.carty.activities.CategoriesListActivity;
import com.project.carty.activities.FavListActivity;
import com.project.carty.activities.Home;
import com.project.carty.activities.LoginActivity;
import com.project.carty.activities.NavigationDrawerActivity;
import com.project.carty.activities.ViewCustomersOrdersActivity;
import com.project.carty.activities.adminDrawerActivity;
import com.project.carty.ui.Account.Logout.logout;
import com.project.carty.ui.Account.Profile.profile;
import com.project.carty.ui.Communication.ContactUs.Contactus;
import com.project.carty.ui.Home.HomeView;
import com.project.carty.ui.Language.Arabic.arabic;
import com.project.carty.ui.Language.English.english;
import com.project.carty.ui.Notifications.notifications;
import com.project.carty.ui.Orders.orders;
import com.project.carty.ui.ProductsMenu.ProductsMenu;
import com.project.carty.ui.Sales.Sales;

public class AdminProductsMenu extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;
    private AdminProductsMenuViewModel mViewModel;

    public static AdminProductsMenu newInstance() {
        return new AdminProductsMenu();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.admin_products_menu_fragment, container, false);
        ImageView fruits_image= (ImageView) view.findViewById(R.id.favourites_image);
        Button fuits_button= (Button)  view.findViewById(R.id.button);
        ImageView meat_image=(ImageView) view.findViewById(R.id.meat_image);
        Button meat_button= (Button)  view.findViewById(R.id.button2);
        ImageView sweet_image=(ImageView) view.findViewById(R.id.sweet_image);
        Button sweet_button= (Button)  view.findViewById(R.id.add_to_cart);
        ImageView cheese_image=(ImageView) view.findViewById(R.id.cheese_image);
        Button cheese_button= (Button)  view.findViewById(R.id.button4);
        ImageView frozen_image=(ImageView) view.findViewById(R.id.frozen_image);
        Button frozen_button= (Button)  view.findViewById(R.id.button5);
        ImageView clean_image=(ImageView) view.findViewById(R.id.clean_image);
        Button clean_button= (Button)  view.findViewById(R.id.button6);
        ImageView rice_image=(ImageView) view.findViewById(R.id.rice_image);
        Button rice_button= (Button)  view.findViewById(R.id.button7);
        ImageView drinks_image=(ImageView) view.findViewById(R.id.drinks_image);
        Button drinks_button= (Button)  view.findViewById(R.id.button8);

        drawer = (DrawerLayout) view.findViewById (R.id.drawer_layout);
        navigationView = (NavigationView) view.findViewById(R.id.nav_view);
        toolbar = (Toolbar)  view.findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawer, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        view.findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);



        fuits_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fruits();
            }
        });
        fruits_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fruits();
            }
        });

        meat_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Meat();
            }
        });
        meat_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Meat();
            }
        });

        sweet_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sweets();
            }
        });
        sweet_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sweets();
            }
        });

        cheese_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cheese();
            }
        });
        cheese_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cheese();
            }
        });

        frozen_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frozen();
            }
        });
        frozen_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frozen();
            }
        });

        clean_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clean();
            }
        });
        clean_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clean();
            }
        });

        rice_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rice();
            }
        });
        rice_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rice();
            }
        });

        drinks_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drinks();
            }
        });
        drinks_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drinks();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AdminProductsMenuViewModel.class);
        // TODO: Use the ViewModel
    }

    public void Fruits(){
        Intent intent = new Intent(getActivity(),  AdminProductsListActivity.class);
        startActivity(intent);
    }

    public void Meat(){
        Intent intent = new Intent(getActivity(),  AdminProductsListActivity2.class);
        startActivity(intent);
    }

    public void Sweets(){
        Intent intent = new Intent(getActivity(),  AdminProductsListActivity3.class);
        startActivity(intent);
    }

    public void cheese(){
        Intent intent = new Intent(getActivity(),  AdminProductsListActivity4.class);
        startActivity(intent);
    }

    public void frozen(){
        Intent intent = new Intent(getActivity(),  AdminProductsListActivity5.class);
        startActivity(intent);
    }

    public void clean(){
        Intent intent = new Intent(getActivity(),  AdminProductsListActivity6.class);
        startActivity(intent);
    }

    public void rice(){
        Intent intent = new Intent(getActivity(),  AdminProductsListActivity7.class);
        startActivity(intent);
    }

    public void drinks(){
        Intent intent = new Intent(getActivity(),  AdminProductsListActivity8.class);
        startActivity(intent);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_home:
                Intent intent8 =new Intent (getActivity(), Home.class);
                startActivity(intent8);

                break;
            case R.id.add_products:
                Intent intent1 = new Intent(getActivity(), CategoriesListActivity.class);
                startActivity(intent1);
                break;

            case R.id.nav_goods:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdminProductsMenu()).commit();
                break;


            case R.id.nav_sales:
                Intent intentt =new Intent (getActivity(), AdminSalesListActivity.class);
                startActivity(intentt);
                break;

            case R.id.nav_Customers:
                Intent intent4 =new Intent (getActivity(), ViewCustomersOrdersActivity.class);
                startActivity(intent4);
                break;


            case R.id.nav_logout:

                Intent intent5 =new Intent (getActivity(), LoginActivity.class);
                startActivity(intent5);


                break;

            case R.id.nav_arabic:

                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new arabic()).commit();
                break;


            case R.id.nav_english:

                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new english()).commit();

                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}