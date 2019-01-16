package com.example.android.makeupbook.ui;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import com.example.android.makeupbook.R;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.makeupbook.ui.ProductsFragment.BRANDTYPE;

public class ItemsActivity extends AppCompatActivity{
    @BindView(R.id.items_toolbar)
    Toolbar itemToolBar;
    public static final String ITEMTYPE = "com.example.android.makeupbook.ui.itemtype";
    public static final String ONEURL = "com.example.android.makeupbook.ui.oneurl";
    public static final String TWOURL = "com.example.android.makeupbook.ui.twourl";
    public static final String ISBRAND = "com.example.android.makeupbook.ui.isbrand";
    private static boolean isTablet;
    public static final String PRODUCTFRAGMENTTAG = "productsDisplayFragments";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        ButterKnife.bind(this);
            Intent intent = getIntent();
            String page = intent.getStringExtra(ITEMTYPE);
            Log.i("ProductType::",page);
            Boolean isBrandName = intent.getBooleanExtra(ISBRAND,false);
            String urlOne = intent.getStringExtra(ONEURL);
            String urlAll = intent.getStringExtra(TWOURL);
            itemToolBar.setTitle(page);
            setSupportActionBar(itemToolBar);

            ProductsFragment productsFragment = openFragment(urlOne,urlAll,isBrandName);

        if( findViewById(R.id.items_frame_left) != null){
            isTablet = true;
            getSupportFragmentManager().beginTransaction().add(R.id.items_frame_left, productsFragment).add(R.id.items_frame_right, new ItemDetailsFragment()).commit();

        }else{
            isTablet = false;
            getSupportFragmentManager().beginTransaction().replace(R.id.items_frame, productsFragment,PRODUCTFRAGMENTTAG).commit();

        }
        }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    private ProductsFragment openFragment(String url1,String url2, Boolean isBrand){
        ProductsFragment productsFragment = new ProductsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ProductsFragment.PRODUCTSURL, url1);
        bundle.putBoolean(BRANDTYPE,isBrand);
        bundle.putString(ProductsFragment.FULLURL,url2);
        productsFragment.setArguments(bundle);
        return productsFragment;
    }

    public static boolean getIsTablet(){
        return isTablet;
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
