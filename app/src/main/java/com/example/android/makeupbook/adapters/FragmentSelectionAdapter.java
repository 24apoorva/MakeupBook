package com.example.android.makeupbook.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.android.makeupbook.R;
import com.example.android.makeupbook.network.VolleySingleton;
import com.example.android.makeupbook.objects.Products;
import com.example.android.makeupbook.ui.EyebrowFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FragmentSelectionAdapter extends FragmentPagerAdapter {
    public Context mContext;
    private int mTabs;
    private ArrayList<Products> mProducts = new ArrayList<Products>();
    private String urlFirstPart = "http://makeup-api.herokuapp.com/api/v1/products.json?product_type=";
    private String path ;


    public FragmentSelectionAdapter(FragmentManager fm, Context context, int tabs) {
        super(fm);
        mContext = context;
        mTabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        if (mTabs == 4) {
            return displayEyeMakeUp(position);
        }
        else if (mTabs == 3) {
            return displayFaceMakeUp(position);
        } else if (mTabs == 2) {
            return displayLipMakeUp(position);
        } else {
            return displayNailsMakeUp(position);
        }

    }

    @Override
    public int getCount() {
        return mTabs;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if (mTabs == 4) {
            return displayEyeMakeNames(position);
        }
        else if (mTabs == 3) {
            return displayFaceMakeNames(position);
        } else if (mTabs == 2) {
            return displayLipMakeNames(position);
        } else{
            return displayNailsMakeNames(position);
        }
    }

    private Fragment setUp(String url){
        Bundle bundle = new Bundle();
        bundle.putString("urlReq",url);
        EyebrowFragment eyebrowFragment = new EyebrowFragment();
        eyebrowFragment.setArguments(bundle);
        return eyebrowFragment;
    }

    private Fragment displayEyeMakeUp(int position) {
        switch (position) {
            case 0:

                path = urlFirstPart + mContext.getResources().getString(R.string.eyebrow);
                return setUp(path);
            case 1:
                path = urlFirstPart + mContext.getResources().getString(R.string.eyeliner);
                return setUp(path);

            case 2:
                path = urlFirstPart + mContext.getResources().getString(R.string.mascara);
                return setUp(path);

            default:
                path = urlFirstPart + mContext.getResources().getString(R.string.eyeshadow);
                return setUp(path);

        }
    }

//    private Fragment loadData(String url, Context context) {
//        final EyebrowFragment eyebrowFragment = new EyebrowFragment();
//        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                GsonBuilder gsonBuilder = new GsonBuilder();
//                Gson gson = gsonBuilder.create();
//                Type type = new TypeToken<ArrayList<Products>>() {
//                }.getType();
//                mProducts = gson.fromJson(response, type);
//                Bundle bundle = new Bundle();
//                bundle.putParcelableArrayList("impData", mProducts);
//                eyebrowFragment.setArguments(bundle);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        RequestQueue queue = VolleySingleton.getVolleySingleton(context).getRequestQueue();
//        queue.add(request);
//        return eyebrowFragment;
//    }

    private String displayEyeMakeNames(int position) {
        switch (position) {
            case 0:
                return ("EyeBrows");
            case 1:
                return ("Eye Liner");
            case 2:
                return ("Mascara");
            default:
                return ("Eye Shadows");
        }
    }

    private Fragment displayLipMakeUp(int position) {
        switch (position) {
            case 0:
                path = urlFirstPart + mContext.getResources().getString(R.string.lipstick);
                return setUp(path);
            default:
                path = urlFirstPart + mContext.getResources().getString(R.string.lip_liner);
                return setUp(path);


        }
    }

    private String displayLipMakeNames(int position) {
        switch (position) {
            case 0:
                return ("Lip Sticks");
            default:
                return ("Lip Liners");
        }
    }

    private Fragment displayFaceMakeUp(int position) {
        switch (position) {
            case 0:
                path = urlFirstPart + mContext.getResources().getString(R.string.foundation);
                return setUp(path);

            case 1:
                path = urlFirstPart + mContext.getResources().getString(R.string.bronzer);
                return setUp(path);

            default:
                path = urlFirstPart + mContext.getResources().getString(R.string.blush);
                return setUp(path);


        }
    }

    private String displayFaceMakeNames(int position) {
        switch (position) {
            case 0:
                return ("Foundation");
            case 1:
                return ("Bronzer");
            default:
                return ("Blush");
        }
    }

    private Fragment displayNailsMakeUp(int position) {
        switch (position) {
            default:
                path = urlFirstPart + mContext.getResources().getString(R.string.nailPolish);
                return setUp(path);


        }
    }

    private String displayNailsMakeNames(int position) {
                return ("Nail Polish");
    }
}

