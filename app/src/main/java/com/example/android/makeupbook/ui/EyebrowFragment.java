package com.example.android.makeupbook.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.android.makeupbook.R;
import com.example.android.makeupbook.adapters.ProductsRecyclerViewAdapter;
import com.example.android.makeupbook.network.VolleySingleton;
import com.example.android.makeupbook.objects.Products;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class EyebrowFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ProductsRecyclerViewAdapter mAdapter;
    private ArrayList<Products> mProductsData = new ArrayList<Products>();
    public ContentLoadingProgressBar p;

    public EyebrowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_eyes, container, false);
        p = view.findViewById(R.id.progress_rec);
        if(getArguments() != null){
            String url = getArguments().getString("urlReq");
            loadData(url,view);
        }

        return view;
    }


    private void displayEyeProducts (ArrayList<Products> productsList,View view){
        mRecyclerView = view.findViewById(R.id.eyeProducts_recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ProductsRecyclerViewAdapter(getContext(), productsList);
        mRecyclerView.setAdapter(mAdapter);
    }

        private void loadData(String url, final View view) {
        p.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                p.setVisibility(View.INVISIBLE);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Type type = new TypeToken<ArrayList<Products>>() {
                }.getType();
                mProductsData = gson.fromJson(response, type);
                displayEyeProducts(mProductsData,view);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = VolleySingleton.getVolleySingleton(getContext()).getRequestQueue();
        queue.add(request);
    }


}
