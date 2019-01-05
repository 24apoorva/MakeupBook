package com.example.android.makeupbook.ui;

import android.graphics.drawable.Drawable;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.android.makeupbook.MainActivity;
import com.example.android.makeupbook.R;
import com.example.android.makeupbook.adapters.ProductsRecyclerViewAdapter;
import com.example.android.makeupbook.network.VolleySingleton;
import com.example.android.makeupbook.objects.Products;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductsFragment extends Fragment {
    private ArrayList<Products> mProductsData = new ArrayList<Products>();
    private ArrayList<Products> mAllProductsData = new ArrayList<Products>();
    public ProgressBar p;
    public static String PRODUCTSURL = "urlReq";
    public static String FULLURL = "full data url";
    private boolean isBrand = false;
    public static final String JUSTDISPLAY = "just display data";
    private String mainUrl;
    private RecyclerView mRecyclerView;



    public ProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products_tab, container, false);
        p = view.findViewById(R.id.progress_rec);
        if(getArguments() != null){
            if(getArguments().getBoolean(JUSTDISPLAY)){

            }else {
                String url = getArguments().getString(PRODUCTSURL);
                mainUrl = getArguments().getString(FULLURL);
                isBrand = getArguments().getBoolean(ItemsActivity.BRANDTYPE);
                loadData(url, view,0);
            }

        }

        return view;
    }


    private void displaySelectedProducts (ArrayList<Products> productsList, View view,
                                          boolean footer, ProductsRecyclerViewAdapter.OnItemClicked  onItemClicked){
        mRecyclerView = view.findViewById(R.id.eyeProducts_recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ProductsRecyclerViewAdapter mAdapter = new ProductsRecyclerViewAdapter(getContext(), productsList, footer, onItemClicked);
        mRecyclerView.setAdapter(mAdapter);
    }


    private void loadData(final String requestUrl, final View view, final int code ) {
        p.setVisibility(View.VISIBLE);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, requestUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responseArray) {
                p.setVisibility(View.INVISIBLE);


                if(responseArray == null | responseArray.length()==0){
                    loadData(mainUrl, view,1);
                }else{
                    List<Products> mProducts = new ArrayList<>();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();

                    if (responseArray.length() > 0) {
                        mProducts = Arrays.asList(gson.fromJson(responseArray.toString(), Products[].class));
                    }
                    mProductsData.addAll(mProducts);
                    if(isBrand){
                        displaySelectedProducts(mProductsData,view,false,null);
                    }else if(code == 1){
                        displaySelectedProducts(mProductsData,view,false,null);
                    }else {
                        displaySelectedProducts(mProductsData,view,true,new ProductsRecyclerViewAdapter.OnItemClicked() {
                            @Override
                            public void onItemClick(int position) {
                                p.setVisibility(View.VISIBLE);
                                mRecyclerView.setVisibility(View.INVISIBLE);
                                loadFullData(view);

                            }
                        });

                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = VolleySingleton.getVolleySingleton(getContext()).getRequestQueue();
        queue.add(jsonArrayRequest);


    }

    private void loadFullData(final View view ){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, mainUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responseArray) {
               // new ProductsRecyclerViewAdapter(true);
                p.setVisibility(View.INVISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
                List<Products> mProducts = new ArrayList<>();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();

                if (responseArray.length() > 0) {
                    mProducts = Arrays.asList(gson.fromJson(responseArray.toString(), Products[].class));
                }
                 mAllProductsData.addAll(mProducts);
                displaySelectedProducts(mAllProductsData,view,false,null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = VolleySingleton.getVolleySingleton(getContext()).getRequestQueue();
        queue.add(jsonArrayRequest);
    }



}
