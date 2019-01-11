package com.example.android.makeupbook.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.android.makeupbook.MainActivity;
import com.example.android.makeupbook.R;
import com.example.android.makeupbook.adapters.ColorAdapter;
import com.example.android.makeupbook.network.VolleySingleton;
import com.example.android.makeupbook.objects.Colors;
import com.example.android.makeupbook.objects.ItemDetails;
import com.example.android.myproductslibrary.Database.Item;
import com.example.android.myproductslibrary.Database.ItemViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemDetailsFragment extends Fragment {
    public static String DETAILSURL = "item details url";
    @BindViews({ R.id.itemName_text, R.id.itemBrand_text,
            R.id.itemprice_text,R.id.description_item_textview , R.id.type_product,R.id.color_Text, R.id.tags_tv})
    List<TextView> detailsTextViews;
    @BindView(R.id.itemImage_view)
    ImageView image_itemView;
    @BindView(R.id.color_grid)
    GridView gridview;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    private ItemDetails itemDetails;
    private ItemViewModel itemViewModel;
    private Colors selectedColor;


    public ItemDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_details, container, false);
        ButterKnife.bind(this, view);
        if(getArguments()!=null){
            itemViewModel = ViewModelProviders.of(getActivity()).get(ItemViewModel.class);
            String url = getArguments().getString(DETAILSURL);
            loadDetails(url);
        }

        return view;
    }

    private void loadDetails(String url){

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                 itemDetails = gson.fromJson(response.toString(),ItemDetails.class);
                displayDetails(itemDetails);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });





        RequestQueue queue = VolleySingleton.getVolleySingleton(getContext()).getRequestQueue();
        queue.add(jsonObjectRequest);

    }

    private void displayDetails(final ItemDetails itemDetails){

        String name = itemDetails.getName();
        if(name == null || name.isEmpty()){
            detailsTextViews.get(0).setVisibility(View.GONE);
        }else {
            String cap = name.substring(0, 1).toUpperCase() + name.substring(1);
            detailsTextViews.get(0).setVisibility(View.VISIBLE);
            detailsTextViews.get(0).setText(cap.trim());
        }

        String brand = itemDetails.getBrand();
        if(brand == null || brand.isEmpty()){
            detailsTextViews.get(1).setVisibility(View.GONE);
        }else {
            String cap = brand.substring(0, 1).toUpperCase() + brand.substring(1);
            brand = "By "+cap.trim();
            detailsTextViews.get(1).setVisibility(View.VISIBLE);
            detailsTextViews.get(1).setText(brand);
        }

        String price = itemDetails.getPrice();
        if(price == null){
            price = "$"+"0.0";
            detailsTextViews.get(2).setText(price);
        }else {
            price = "$"+price;
            detailsTextViews.get(2).setText(price);
        }

        ratingBar.setRating(itemDetails.getRating());

        String desc = itemDetails.getDescription();
        if(desc == null || desc.isEmpty()){
            detailsTextViews.get(3).setVisibility(View.GONE);
        }else {
            desc = desc.trim().replaceAll(" +", " ");
            detailsTextViews.get(3).setVisibility(View.VISIBLE);
            detailsTextViews.get(3).setText(desc);
        }

        String type = itemDetails.getCategory();
        if(type == null || type.isEmpty()){
            detailsTextViews.get(4).setVisibility(View.GONE);
        }else {
            detailsTextViews.get(4).setVisibility(View.VISIBLE);
            String category = "Category: "+type.substring(0, 1).toUpperCase() + type.substring(1).trim();
            detailsTextViews.get(4).setText(category);
        }

        String url = itemDetails.getImage_link();
        if(!url.contains("https")){
            url = url.replace("http","https");
        }

        Picasso.with(getContext())
                .load(url)
                .fit()
                .placeholder(R.color.white)
                .into(image_itemView);

        if(itemDetails.getProduct_colors().size() != 0){
            gridview.setVisibility(View.VISIBLE);
            detailsTextViews.get(5).setVisibility(View.VISIBLE);
            gridview.setAdapter(new ColorAdapter(getContext(),itemDetails.getProduct_colors()));
            setDynamicHeight(gridview);

            String color = "Color: "+itemDetails.getProduct_colors().get(0).getColour_name();
            selectedColor = new Colors(color, itemDetails.getProduct_colors().get(0).getHex_value());
            detailsTextViews.get(5).setText(color);

            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    String color = "Color: "+itemDetails.getProduct_colors().get(position).getColour_name();
                    selectedColor = new Colors(color, itemDetails.getProduct_colors().get(position).getHex_value());
                    detailsTextViews.get(5).setText(color);
                }
            });

        }else {
            gridview.setVisibility(View.GONE);
            detailsTextViews.get(5).setVisibility(View.GONE);
        }

        ArrayList<String> tags = itemDetails.getTag_list();
        if(tags.size() != 0){
            StringBuilder sb=new StringBuilder("Tags: ");
            for(int i=0; i<tags.size();i++){
                sb.append(tags.get(i));
                if(i !=tags.size()-1) {
                    sb.append(", ");
                }
            }
            detailsTextViews.get(6).setVisibility(View.VISIBLE);
            detailsTextViews.get(6).setText(sb);
        }else {
            detailsTextViews.get(6).setVisibility(View.GONE);
        }


    }

    @OnClick(R.id.buy_button)
    public void buyProductLink(){
        String url = itemDetails.getProduct_link();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @OnClick(R.id.share_product_image)
    public void shareThisProduct(){
        String shareText = "Share "+itemDetails.getName();
        startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(getActivity())
                .setType(getString(R.string.share_type))
                .setText(itemDetails.getProduct_link())
                .getIntent(), shareText));
    }

    @OnClick(R.id.iHave_button)
    public void iHaveButtonClicked(){
        Item item;
        if(itemDetails.getProduct_colors() == null || itemDetails.getProduct_colors().isEmpty()){
            item= new Item(itemDetails.getProduct_id(),itemDetails.getBrand(),itemDetails.getName(),itemDetails.getPrice(),itemDetails.getImage_link(),
                    itemDetails.getProduct_type(),null,null,true,false);
        }else{
            if(selectedColor.getColour_name() ==null){
                selectedColor.setColour_name("color");
            }
            item = new Item(itemDetails.getProduct_id(),itemDetails.getBrand(),itemDetails.getName(),itemDetails.getPrice(),itemDetails.getImage_link(),
                    itemDetails.getProduct_type(),selectedColor.getColour_name(),selectedColor.getHex_value(),true,false);
        }

        itemViewModel.insertItem(item);
        Toast.makeText(getContext(),"Item added to have list",Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.iWant_button)
    public void iWantButtonClicked(){
        Item item;
        if(itemDetails.getProduct_colors() == null  || itemDetails.getProduct_colors().isEmpty()){
            item= new Item(itemDetails.getProduct_id(),itemDetails.getBrand(),itemDetails.getName(),itemDetails.getPrice(),itemDetails.getImage_link(),
                    itemDetails.getProduct_type(),null,null,false,true);
        }else{
            item = new Item(itemDetails.getProduct_id(),itemDetails.getBrand(),itemDetails.getName(),itemDetails.getPrice(),itemDetails.getImage_link(),
                    itemDetails.getProduct_type(),selectedColor.getColour_name(),selectedColor.getHex_value(),false,true);
        }

        itemViewModel.insertItem(item);
        Toast.makeText(getContext(),"Item added to want list",Toast.LENGTH_SHORT).show();


    }

    private void setDynamicHeight(GridView gridView) {
        ListAdapter gridViewAdapter = gridView.getAdapter();
        if (gridViewAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int items = gridViewAdapter.getCount();
        int rows = 0;

        View listItem = gridViewAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        totalHeight = listItem.getMeasuredHeight();
        int number = gridView.getNumColumns();

        float x = 1;
        if( items > number ){
            x = items/number;
            rows = (int) (x + 1);
            totalHeight *= rows;
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);
    }

}
