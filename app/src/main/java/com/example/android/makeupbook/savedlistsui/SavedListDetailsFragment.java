package com.example.android.makeupbook.savedlistsui;


import android.content.Intent;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.android.makeupbook.Database.Item;
import com.example.android.makeupbook.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SavedListDetailsFragment extends Fragment {

    @BindViews({R.id.savedName_text,R.id.savedBrand_text,R.id.savedPrice_text,R.id.savedColor_Text,
            R.id.saved_type_product,R.id.saved_description})List<TextView> savedDataTextViews;
    @BindView(R.id.savedRatingBar)
    RatingBar mRating;
    @BindView(R.id.itemImage_view)
    ImageView mImage;
    @BindView(R.id.savedColor_grid)
    View viewColor;
    private Item itemDetails;
    @BindView(R.id.saved_linearlayout)
    LinearLayout savedLinearLayout;


    public SavedListDetailsFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saved_list_details, container, false);
        ButterKnife.bind(this,view);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).hide();

        if(getArguments() !=null){
            savedLinearLayout.setVisibility(View.VISIBLE);
            itemDetails = getArguments().getParcelable("savedItemDb");
            displayData();
        }else{
            savedLinearLayout.setVisibility(View.GONE);
        }
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
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void displayData(){
        String name = itemDetails.getName();
        if(name == null || name.isEmpty()){
            savedDataTextViews.get(0).setVisibility(View.GONE);
        }else {
            String cap = name.substring(0, 1).toUpperCase() + name.substring(1);
            Objects.requireNonNull(((MyListsMainActivity) getActivity()).getSupportActionBar()).setTitle(cap);
            savedDataTextViews.get(0).setVisibility(View.VISIBLE);
            savedDataTextViews.get(0).setText(cap.trim());
        }

        String brand = itemDetails.getBrand();
        if(brand == null || brand.isEmpty()){
            savedDataTextViews.get(1).setVisibility(View.GONE);
        }else {
            String cap = brand.substring(0, 1).toUpperCase() + brand.substring(1);
            brand = "By "+cap.trim();
            savedDataTextViews.get(1).setVisibility(View.VISIBLE);
            savedDataTextViews.get(1).setText(brand);
        }

        String price = itemDetails.getPrice();
        if(price == null){
            price = "$"+"0.0";
            savedDataTextViews.get(2).setText(price);
        }else {
            price = "$"+price;
            savedDataTextViews.get(2).setText(price);
        }

        mRating.setRating(itemDetails.getRating());

        String desc = itemDetails.getDescription();
        if(desc == null || desc.isEmpty()){
            savedDataTextViews.get(5).setVisibility(View.GONE);
        }else {
            desc = desc.trim().replaceAll(" +", " ");
            savedDataTextViews.get(5).setVisibility(View.VISIBLE);
            savedDataTextViews.get(5).setText(desc);
        }

        String type = itemDetails.getCategory();
        if(type == null || type.isEmpty()){
            savedDataTextViews.get(4).setVisibility(View.GONE);
        }else {
            savedDataTextViews.get(4).setVisibility(View.VISIBLE);
            String category = "Category: "+type.substring(0, 1).toUpperCase() + type.substring(1).trim();
            savedDataTextViews.get(4).setText(category);
        }

        String url = itemDetails.getImage_link();
        if(!url.contains("https")){
            url = url.replace("http","https");
        }

        Picasso.with(getContext())
                .load(url)
                .fit()
                .placeholder(R.color.white)
                .into(mImage);

        String colorName = itemDetails.getColorName();
        if(colorName == null || colorName.isEmpty()){
            savedDataTextViews.get(3).setVisibility(View.GONE);
            viewColor.setVisibility(View.GONE);
        }else {
            savedDataTextViews.get(3).setVisibility(View.VISIBLE);
            String cName= "Color: "+colorName.substring(0, 1).toUpperCase() + colorName.substring(1).trim();
            savedDataTextViews.get(3).setText(cName);
            String colorValue = itemDetails.getColorValue();
            if(colorValue != null) {
                viewColor.setVisibility(View.VISIBLE);
                viewColor.setBackgroundColor(Color.parseColor(colorValue));
            }else {
                viewColor.setVisibility(View.INVISIBLE);
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick(R.id.share_product_image)
    public void shareThisProduct(){
        String shareText = "Share "+itemDetails.getName();
        startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(Objects.requireNonNull(getActivity()))
                .setType(getString(R.string.share_type))
                .setText(itemDetails.getProduct_link())
                .getIntent(), shareText));
    }

}
