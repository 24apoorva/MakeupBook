package com.example.android.makeupbook.ui;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.android.makeupbook.R;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.makeupbook.ui.ProductsFragment.BRANDTYPE;

public class ItemsActivity extends AppCompatActivity {
    @BindView(R.id.items_toolbar)
    Toolbar itemToolBar;
    public static String ITEMTYPE = "item type";
    public static String ONEURL = "one url";
    public static String TWOURL = "two url";
    public static String ISBRAND = "com.example.android.makeupbook.ui.isbrand";
    private Fragment mfragment;


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
            // Create an adapter that knows which fragment should be shown on each page
        ProductsFragment productsFragment = new ProductsFragment();
            Bundle bundle = new Bundle();
            bundle.putString(ProductsFragment.PRODUCTSURL, urlOne);
            bundle.putBoolean(BRANDTYPE,isBrandName);
            bundle.putString(ProductsFragment.FULLURL,urlAll);
            productsFragment.setArguments(bundle);


        getSupportFragmentManager().beginTransaction().replace(R.id.items_frame, productsFragment).commit();
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_menu, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
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
