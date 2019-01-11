package com.example.android.makeupbook.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.FrameLayout;

import com.example.android.makeupbook.R;
import com.example.android.makeupbook.objects.Products;
import com.example.android.makeupbook.ui.ProductsFragment;

import java.util.ArrayList;

public class FragmentSelectionAdapter extends FragmentPagerAdapter {
    public Context mContext;
    private String title;
    private boolean isBrand;


    public FragmentSelectionAdapter(FragmentManager fm, Context context, String title) {
        super(fm);
        this.mContext = context;
        this.title = title;
    }

    private String[] getMakeupArray(String string, Context context){
        if(string.contains(context.getResources().getString(R.string.brands))){
            isBrand = true;
            return context.getResources().getStringArray(R.array.brandsArray);
        }else if(string.contains(context.getResources().getString(R.string.eyes))){
            isBrand = false;
            return context.getResources().getStringArray(R.array.categoryEyesArray);
        }else if(string.contains(context.getResources().getString(R.string.lips))){
            isBrand = false;
            return context.getResources().getStringArray(R.array.categoryLipsArray);
        }else{
            isBrand = false;
            return context.getResources().getStringArray(R.array.categoryFaceArray);
        }
    }

    @Override
    public Fragment getItem(int position) {
        String item = getMakeupArray(title,mContext)[position];
        String urlFirstPart = "https://makeup-api.herokuapp.com/api/v1/products.json?";
        String productType = "rating_greater_than=4.5&product_type=";
        String allProduct = "product_type=";
        String brandsType = "brand=";
        String urlOne;
        String urlTwo;
        if(isBrand){
            urlOne = urlFirstPart+brandsType+item;
            urlTwo = urlOne;
        }else {
            urlOne= urlFirstPart+productType+item;
            urlTwo=urlFirstPart+allProduct+item;
        }
        return setUp(urlOne,urlTwo);

    }

    @Override
    public int getCount() {
        return getMakeupArray(title,mContext).length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return getMakeupArray(title,mContext)[position];
    }

    private Fragment setUp(String url1, String url2){
        ProductsFragment productsFragment = new ProductsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ProductsFragment.PRODUCTSURL, url1);
        bundle.putBoolean(ProductsFragment.BRANDTYPE,isBrand);
        bundle.putString(ProductsFragment.FULLURL,url2);
        productsFragment.setArguments(bundle);
        return productsFragment;
           }

}

