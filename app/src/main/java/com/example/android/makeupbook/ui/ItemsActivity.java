package com.example.android.makeupbook.ui;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.example.android.makeupbook.R;

import java.util.Arrays;
import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemsActivity extends AppCompatActivity {
    @BindView(R.id.items_toolbar)
    Toolbar itemToolBar;
    public static String ITEMTYPE = "item type";
    public static String BRANDTYPE = "is brand";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String page = intent.getStringExtra(ITEMTYPE);
        Boolean isBrandName = intent.getBooleanExtra(BRANDTYPE,false);
        itemToolBar.setTitle(page);
        setSupportActionBar(itemToolBar);
        // Create an adapter that knows which fragment should be shown on each page
        ProductsFragment productsFragment = new ProductsFragment();
        Bundle bundle = new Bundle();

        String urlFirstPart = "https://makeup-api.herokuapp.com/api/v1/products.json";
        String url;
        if(isBrandName){
            String brandsType = "?brand=";
            url = urlFirstPart + brandsType +page;
        }else {
            String productType = "?product_type=";
            url = urlFirstPart + productType +page;
        }
        bundle.putString(ProductsFragment.PRODUCTSURL, url);
        productsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.items_frame,productsFragment).commit();


    }

}
