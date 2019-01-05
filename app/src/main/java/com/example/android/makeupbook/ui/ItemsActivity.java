package com.example.android.makeupbook.ui;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

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
        Log.i("ProductType::",page);
        Boolean isBrandName = intent.getBooleanExtra(BRANDTYPE,false);
        itemToolBar.setTitle(page);
        setSupportActionBar(itemToolBar);
        // Create an adapter that knows which fragment should be shown on each page
        ProductsFragment productsFragment = new ProductsFragment();
        Bundle bundle = new Bundle();

        String urlFirstPart = "https://makeup-api.herokuapp.com/api/v1/products.json?";
        String url;
        String allUrl = null;
        if((isBrandName)){
            String brandsType = "brand=";
            page = page.toLowerCase().trim();
            url = urlFirstPart + brandsType +page;
            Log.i("ProductTypeurl::",url);

        }else {
            String productType = "rating_greater_than=4.5&product_type=";
            String allProduct = "product_type=";
            page = page.toLowerCase().trim();
            url = urlFirstPart + productType +page;
            allUrl= urlFirstPart+allProduct+page;
            Log.i("ProductTypeurl::",url);
        }
        bundle.putString(ProductsFragment.PRODUCTSURL, url);
        bundle.putBoolean(BRANDTYPE,isBrandName);
        bundle.putString(ProductsFragment.FULLURL,allUrl);
        productsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.items_frame,productsFragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //Title bar back press triggers onBackPressed()
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Both navigation bar back press and title bar back press will trigger this method
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ) {
            getFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }

}
