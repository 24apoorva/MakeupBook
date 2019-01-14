package com.example.android.makeupbook.network;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.android.makeupbook.objects.Colors;
import com.example.android.makeupbook.objects.Products;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NetworkingService extends IntentService {
    private static final int STATUS_RUNNING = 24;
    private static final int STATUS_FINISHED = 25;
    private static final int STATUS_ERROR = 26;
    public NetworkingService() {
        super("NetworkingService");
    }

    protected void onHandleIntent(Intent intent) {
        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        String command = intent.getStringExtra("command");
        String url = intent.getStringExtra("urlvaluereciver");
        Bundle b = new Bundle();
        if(command.equals("query")) {
            receiver.send(STATUS_RUNNING, Bundle.EMPTY);
            try {
                loadFullData(url,b,receiver);
            } catch(Exception e) {
                b.putString(Intent.EXTRA_TEXT, e.toString());
                receiver.send(STATUS_ERROR, b);
            }
        }
    }

    private void loadFullData(String url, final Bundle b, final ResultReceiver receiver) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responseArray) {
                List<Products> makeupData = new ArrayList<>();
                if (responseArray.length() > 0) {
                    for (int i = 0; i < responseArray.length(); i++) {
                        JSONObject currentProduct = responseArray.optJSONObject(i);
                        int id =currentProduct.optInt("id");
                        String brand = currentProduct.optString("brand");
                        String name = currentProduct.optString("name");
                        String price = currentProduct.optString("price");
                        String imagelink = currentProduct.optString("image_link");
                        String producttype = currentProduct.optString("product_type");
                        String productLink = currentProduct.optString("product_link");
                        String description = currentProduct.optString("description");
                        double rating = currentProduct.optDouble("rating");
                        String category = currentProduct.optString("category");
                        ArrayList<String> tags = new ArrayList<>();
                        try {
                            JSONArray tagValues = currentProduct.getJSONArray("tag_list");
                            for(int k=0; k<tagValues.length();k++){
                                tags.add(tagValues.getString(k));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ArrayList<Colors> col = new ArrayList<>();
                        try {
                            JSONArray colors = currentProduct.getJSONArray("product_colors");
                            for (int j=0; j<colors.length();j++ ){
                                JSONObject currentcol = colors.optJSONObject(j);
                                String colName =currentcol.optString("colour_name");
                                String colValue = currentcol.optString("hex_value");
                                Colors colors1 =new Colors(colName,colValue);
                                col.add(colors1);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Products data = new Products(id,brand,name,price,imagelink,productLink,producttype,description,(float) rating,category,col,tags);
                        makeupData.add(data);


                    }
                }
                b.putParcelableArrayList("results", (ArrayList<? extends Parcelable>) makeupData);
                receiver.send(STATUS_FINISHED, b);




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = VolleySingleton.getVolleySingleton(this).getRequestQueue();
        queue.add(jsonArrayRequest);
    }

}
