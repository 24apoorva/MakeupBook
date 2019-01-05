package com.example.android.makeupbook.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.android.makeupbook.R;
import com.example.android.makeupbook.network.VolleySingleton;
import com.example.android.makeupbook.objects.ItemDetails;
import com.example.android.makeupbook.objects.Products;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemDetailsFragment extends Fragment {
    public static String DETAILSURL = "item details url";


    public ItemDetailsFragment() {
        // Required empty public constructor
    }

    private void loadDetails(String url){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                ItemDetails itemDetails = gson.fromJson(response,ItemDetails.class);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = VolleySingleton.getVolleySingleton(getContext()).getRequestQueue();
        queue.add(stringRequest);

    }

    private void displayDetails(ItemDetails itemDetails){

    }

}
