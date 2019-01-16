package com.example.android.makeupbook.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.makeupbook.R;
import com.example.android.makeupbook.adapters.ColorAdapter;
import com.example.android.makeupbook.objects.Colors;
import com.example.android.makeupbook.Database.Item;
import com.example.android.makeupbook.Database.ItemViewModel;
import com.example.android.makeupbook.objects.Products;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemDetailsFragment extends Fragment {
    @BindViews({ R.id.itemName_text, R.id.itemBrand_text,
            R.id.itemprice_text,R.id.description_item_textview , R.id.type_product,R.id.color_Text, R.id.tags_tv})
    List<TextView> detailsTextViews;
    @BindView(R.id.itemImage_view)
    ImageView image_itemView;
    @BindView(R.id.color_grid)
    GridView gridview;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView( R.id.adv_frame)
    AdView mAdView;
    private Products details;
    private ItemViewModel itemViewModel;
    private Colors selectedColor;
    private Animation anim;
    @BindView(R.id.details_linearlayout)
    LinearLayout linearLayout;
    public static final String MAKEUPITEMDETAILS = "com.example.android.makeupbook.ui.makeupItemDetails";
    private static final String MAKEUPITEMSAVING = "com.example.android.makeupbook.ui.saveitem";


    public ItemDetailsFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_details, container, false);
        ButterKnife.bind(this, view);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).hide();
        if(savedInstanceState != null){
            if(savedInstanceState.containsKey(MAKEUPITEMSAVING)){
                details = savedInstanceState.getParcelable(MAKEUPITEMSAVING);
            }
        }else{
            linearLayout.setVisibility(View.VISIBLE);
            if(getArguments()!=null){
                loadAdd();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    itemViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(ItemViewModel.class);
                }
                details = getArguments().getParcelable(MAKEUPITEMDETAILS);
            }else {
                hidePage();
            }}
        displayDetails(details);

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).hide();
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onStop() {
        super.onStop();
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MAKEUPITEMSAVING,details);
    }

    private void hidePage(){
        linearLayout.setVisibility(View.GONE);
    }

    private void loadAdd(){
        MobileAds.initialize(getContext(),"ca-app-pub-3940256099942544~3347511713");
        AdRequest adRequest= new AdRequest.Builder().
                addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("19C5BDCAE5E5F1A7A997B6A2115679CD").build();
        mAdView.loadAd(adRequest);
    }

    private void displayDetails(final Products itemDetails){
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
                    anim = AnimationUtils.loadAnimation(getContext(),R.anim.fadein);
                    detailsTextViews.get(5).startAnimation(anim);
                    v.setAnimation(anim);

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
        String url = details.getProduct_link();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @OnClick(R.id.share_product_image)
    public void shareThisProduct(){
        String shareText = "Share "+details.getName();
        startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(getActivity())
                .setType(getString(R.string.share_type))
                .setText(details.getProduct_link())
                .getIntent(), shareText));
    }

    private Item addItem(Products product,String nameColor,String valueColor,Boolean inHaveList,Boolean inWantList){
        return new Item(product.getProduct_id(),product.getBrand(),product.getName(),product.getPrice()
                ,product.getImage_link(),product.getProduct_type(),nameColor
                ,valueColor,product.getDescription(),product.getRating(),product.getCategory(),product.getProduct_link(),inHaveList,inWantList);
    }

    @OnClick(R.id.iHave_button)
    public void iHaveButtonClicked(){
        Item item;
        if(details.getProduct_colors() == null || details.getProduct_colors().isEmpty()){
            item = addItem(details,null,null,true,false);

        }else{
            if(selectedColor.getColour_name() ==null){
                selectedColor.setColour_name("color");
            }
            item = addItem(details,selectedColor.getColour_name(),selectedColor.getHex_value(),true,false);
        }

        itemViewModel.insertItem(item);
        Toast.makeText(getContext(),"Item added to have list",Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.iWant_button)
    public void iWantButtonClicked(){
        Item item;
        if(details.getProduct_colors() == null  || details.getProduct_colors().isEmpty()){
            item = addItem(details,null,null,false,true);

        }else{
            item = addItem(details,selectedColor.getColour_name(),selectedColor.getHex_value(),false,true);
        }

        itemViewModel.insertItem(item);
        Toast.makeText(getContext(),"Item added to want list",Toast.LENGTH_SHORT).show();


    }
    //calculating grid height
    private void setDynamicHeight(GridView gridView) {
        ListAdapter gridViewAdapter = gridView.getAdapter();
        if (gridViewAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight;
        int items = gridViewAdapter.getCount();
        int rows;

        View listItem = gridViewAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        totalHeight = listItem.getMeasuredHeight();

        float x;
        if( items > 9 ){
            x = items/9;
            rows = (int) (x + 1);
            totalHeight *= rows;
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);
    }


}
