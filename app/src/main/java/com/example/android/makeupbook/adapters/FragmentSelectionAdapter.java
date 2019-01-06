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
    private int mPosition;
    private ArrayList<Products> mProducts = new ArrayList<Products>();
    private String urlFirstPart = "https://makeup-api.herokuapp.com/api/v1/products.json?product_type=";
    private String urlPartTwo = "&product_category=";


    public FragmentSelectionAdapter(FragmentManager fm, Context context, int position) {
        super(fm);
        this.mContext = context;
        this.mPosition = position;
    }

    private String[] getType(int position, Context context){
        switch (position) {
            case 0:
                return null;
            case 1:
                return context.getResources().getStringArray(R.array.eyelinerArray);
            case 2:
                return context.getResources().getStringArray(R.array.eyeShadowArray);
            case 3:
                return null;
            case 4:
                return context.getResources().getStringArray(R.array.blushArray);
            case 5:
                return null;
            case 6:
                return context.getResources().getStringArray(R.array.foundationArray);
            case 7:
                return null;
            case 8:
                return context.getResources().getStringArray(R.array.lipstickArray);
                default:
                    return null;

        }
    }

    @Override
    public Fragment getItem(int position) {
        String typemode = mContext.getResources().getStringArray(R.array.MakeupArray)[mPosition];
        String path;
        if(getType(mPosition,mContext) == null){
            path = urlFirstPart+typemode;
        }else{
            String string =  getType(mPosition,mContext)[position];
            path = urlFirstPart+typemode+urlPartTwo+string;
        }

            return setUp(path);

    }

    @Override
    public int getCount() {
        if(getType(mPosition,mContext) == null){
            return 1;
        }
        return getType(mPosition,mContext).length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(getType(mPosition,mContext) == null){
            if(mPosition == 0 || position == 7){
                return mContext.getResources().getString(R.string.pencil);
            }else if (mPosition == 3){
                return mContext.getResources().getString(R.string.mascara);
            }else if(mPosition == 5){
                return mContext.getResources().getString(R.string.powder);
            }else{
                return "Polish";            }
        }
        return getType(mPosition,mContext)[position];
    }

    private Fragment setUp(String url){
        Bundle bundle = new Bundle();
        bundle.putString(ProductsFragment.PRODUCTSURL,url);
        ProductsFragment productsFragment = new ProductsFragment();
        productsFragment.setArguments(bundle);
        return productsFragment;
    }

}

