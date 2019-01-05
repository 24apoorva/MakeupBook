package com.example.android.makeupbook.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        displayUserData();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayoutProducts,prodTooldbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayoutProducts.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        // Create an adapter that knows which fragment should be shown on each page

        // Set the adapter onto the view pager

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.product_filter_menu, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if(id == R.id.filter_menuId){
//            filterClicked();
//        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_signOut:
                new NavigationBarClass(this).signOutOfApp();
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

    private void filterClicked(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProductsDisplayActivity.this);

       View view = getLayoutInflater().inflate(R.layout.filter_dialog,null);
//
        Spinner brandsSpinner = (Spinner)view.findViewById(R.id.brand_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProductsDisplayActivity.this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.brandsArray));
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        brandsSpinner.setAdapter(adapter);

        Spinner catSpinner = (Spinner)view.findViewById(R.id.category_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> catAdapter = new ArrayAdapter<String>(ProductsDisplayActivity.this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.brandsArray));
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        brandsSpinner.setAdapter(catAdapter);

        mBuilder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

        mBuilder.setView(view);
        mBuilder.create().show();
    }
}
