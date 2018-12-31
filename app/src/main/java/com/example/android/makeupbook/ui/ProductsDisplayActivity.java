package com.example.android.makeupbook.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.android.makeupbook.NavigationBarClass;
import com.example.android.makeupbook.R;
import com.example.android.makeupbook.accounts.User;
import com.example.android.makeupbook.adapters.FragmentSelectionAdapter;
import com.example.android.makeupbook.adapters.ProductsRecyclerViewAdapter;
import com.example.android.makeupbook.network.VolleySingleton;
import com.example.android.makeupbook.objects.Products;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsDisplayActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    @BindView(R.id.products_toolbar)
    android.support.v7.widget.Toolbar prodTooldbar;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.prod_draw)
    DrawerLayout drawerLayoutProducts;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_display);
        ButterKnife.bind(this);
        setSupportActionBar(prodTooldbar);
        auth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        //    private ArrayList<Products> mProducts = new ArrayList<>();
        //    private String partTwo;
        int tabNumber = intent.getIntExtra("tabs", 0);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayoutProducts,prodTooldbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayoutProducts.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        // Create an adapter that knows which fragment should be shown on each page
        FragmentSelectionAdapter adapter = new FragmentSelectionAdapter(getSupportFragmentManager(), this, tabNumber);

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }
    @Override
    public void onBackPressed() {
        if(drawerLayoutProducts.isDrawerOpen(GravityCompat.START)){
            drawerLayoutProducts.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

//    private void getJsonData(final View view){
//        String url = urlFirstPart+partTwo;
//        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                GsonBuilder gsonBuilder = new GsonBuilder();
//                Gson gson = gsonBuilder.create();
//                Type type = new TypeToken<ArrayList<Products>>(){
//                }.getType();
//                mProducts = gson.fromJson(response,type);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        RequestQueue queue = VolleySingleton.getVolleySingleton(getApplicationContext()).getRequestQueue();
//        queue.add(request);
//    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.product_filter_menu, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_signOut:
                new NavigationBarClass(this).signOutOfApp();
                break;

            case R.id.nav_home:
                new NavigationBarClass(this).displayHomeScreen();
                break;

            case R.id.nav_eyes:
                new NavigationBarClass(this).displayEyeProducts();
                break;
            case R.id.nav_face:
                new NavigationBarClass(this).displayFaceProducts();
                break;
            case R.id.nav_lips:
                new NavigationBarClass(this).displayLipProducts();
                break;
            case R.id.nav_nails:
                new NavigationBarClass(this).displayNailsProducts();
                break;
        }
        drawerLayoutProducts.closeDrawer(GravityCompat.START);
        return true;
    }
    private void displayUserData(){

        String id = auth.getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("users/" + id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User userData = dataSnapshot.getValue(User.class);
                String name = userData.getUserName();
                name = name.substring(0,1).toUpperCase() + name.substring(1);
                String email = userData.getEmail();
                View headerView = navigationView.getHeaderView(0);
                TextView userName = headerView.findViewById(R.id.nav_userName_display);
                TextView userEmail = headerView.findViewById(R.id.nav_email_display);
                userName.setText(name);
                userEmail.setText(email.trim());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
