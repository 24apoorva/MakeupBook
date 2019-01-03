package com.example.android.makeupbook.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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

public class ProductsFragment extends Fragment {
    private ArrayList<Products> mProductsData = new ArrayList<Products>();
    public ProgressBar p;
    public static String PRODUCTSURL = "urlReq";

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
            String url = getArguments().getString(PRODUCTSURL);
            loadData(url,view);
        }

        return view;
    }


    private void displayEyeProducts (ArrayList<Products> productsList,View view){
        RecyclerView mRecyclerView = view.findViewById(R.id.eyeProducts_recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ProductsRecyclerViewAdapter mAdapter = new ProductsRecyclerViewAdapter(getContext(), productsList);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void loadData(final String requestUrl, final View view) {
        p.setVisibility(View.VISIBLE);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, requestUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responseArray) {
                p.setVisibility(View.INVISIBLE);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                for(int i=0; i<responseArray.length(); i++){
                    try {
                         JSONObject response = responseArray.getJSONObject(i);
                        mProductsData.add(gson.fromJson(response.toString(),Products.class));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    displayEyeProducts(mProductsData,view);
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


}
