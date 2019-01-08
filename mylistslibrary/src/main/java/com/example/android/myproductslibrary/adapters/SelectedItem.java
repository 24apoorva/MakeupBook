package com.example.android.myproductslibrary.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.myproductslibrary.Database.Item;
import com.example.android.myproductslibrary.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SelectedItem extends ListAdapter<Item,SelectedItem.SelectedItemViewHolder>{
Context mContext;
    public SelectedItem() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Item> DIFF_CALLBACK = new DiffUtil.ItemCallback<Item>() {
        @Override
        public boolean areItemsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getImage_link().equals(newItem.getImage_link()) &&
                    oldItem.getColorName().equals(newItem.getColorName());
        }
    };

    @NonNull
    @Override
    public SelectedItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_items_display,viewGroup,false);
        return new SelectedItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedItemViewHolder selectedItemViewHolder, int i) {

        Item item = getItem(i);
        String url = item.getImage_link();
        if(!url.contains("https")){
            url = url.replace("http","https");
        }

        Picasso.with(mContext)
                .load(url)
                .fit()
                .placeholder(R.drawable.loading)
                .into(selectedItemViewHolder.imageView);

        formatString(item.getName(),selectedItemViewHolder.name);

        String brand = item.getBrand();
        if(brand == null || brand.isEmpty() ){
            selectedItemViewHolder.brand.setVisibility(View.GONE);
        }else {
            selectedItemViewHolder.brand.setVisibility(View.VISIBLE);
            StringBuilder cap = new StringBuilder("By: ");
            cap.append(brand.substring(0, 1).toUpperCase() + brand.substring(1).trim());
            selectedItemViewHolder.brand.setText(cap);

        }

        String price = item.getPrice();
        if(price == null || price.isEmpty() ){
            price = "$"+"0.0";
        }else {
            selectedItemViewHolder.price.setVisibility(View.VISIBLE);
            price = "$"+price;
        }
        selectedItemViewHolder.brand.setText(price);

        String colorName = item.getColorName();
        String colorValue = item.getColorValue();
        if((colorName == null || colorName.isEmpty())&& colorValue == null || colorValue.isEmpty()){
            selectedItemViewHolder.colorLayout.setVisibility(View.GONE);
        }else{
            selectedItemViewHolder.colorLayout.setVisibility(View.VISIBLE);
            formatString(colorName, selectedItemViewHolder.colorName);
            selectedItemViewHolder.colorValue.setBackgroundColor(Color.parseColor(colorValue));
        }

        selectedItemViewHolder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void formatString(String string, TextView view){
        if(string == null || string.isEmpty() ){
            view.setVisibility(View.GONE);
        }else {
            view.setVisibility(View.VISIBLE);
            String cap = string.substring(0, 1).toUpperCase() + string.substring(1).trim();
            view.setText(cap);
        }
    }



    public class SelectedItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView name;
        private TextView brand;
        private TextView price;
        private LinearLayout colorLayout;
        private TextView colorName;
        private ImageView colorValue;
        private ImageView deleteImage;

        public SelectedItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.itemImage);
            name = itemView.findViewById(R.id.itemName);
            brand = itemView.findViewById(R.id.itemBrand);
            price = itemView.findViewById(R.id.itemPrice);
            colorLayout = itemView.findViewById(R.id.color_layout);
            colorName = itemView.findViewById(R.id.itemColor);
            colorValue = itemView.findViewById(R.id.displayItemColor);
            deleteImage = itemView.findViewById(R.id.deleteItemImage);
        }
    }
}