package com.example.android.makeupbook.adaptersavedlists;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.android.makeupbook.Database.Item;
import com.example.android.makeupbook.R;
import com.example.android.makeupbook.savedlistsui.MyListsMainActivity;
import com.example.android.makeupbook.savedlistsui.SavedListDetailsFragment;
import com.example.android.makeupbook.ui.ItemsActivity;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class SelectedHaveItem extends ListAdapter<Item,SelectedHaveItem.SelectedItemViewHolder>{
private Context mContext;

    public SelectedHaveItem() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Item> DIFF_CALLBACK = new DiffUtil.ItemCallback<Item>() {
        @Override
        public boolean areItemsTheSame(Item oldItem, Item newItem) {
            return  (oldItem.getId() == newItem.getId());
        }

        @Override
        public boolean areContentsTheSame( Item oldItem, Item newItem) {
            return oldItem.getId() == (newItem.getId())
                    && (oldItem.getName() !=null && oldItem.getName().equals(newItem.getName()))
                    && (oldItem.getImage_link() != null && oldItem.getImage_link().equals(newItem.getImage_link()))
                    && (oldItem.getColorName() !=null && oldItem.getColorName().equals(newItem.getColorName()));

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
    public void onBindViewHolder(@NonNull final SelectedItemViewHolder selectedItemViewHolder, int i) {
        final Item item = getItem(i);

        String url = item.getImage_link();
        if(!url.contains("https")){
            url = url.replace("http","https");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            selectedItemViewHolder.imageView.setTransitionName(item.getName()+item.getId());
        }

        Picasso.with(mContext)
                .load(url)
                .fit()
                .placeholder(R.color.white)
                .into(selectedItemViewHolder.imageView);

        formatString(item.getName(),selectedItemViewHolder.name);

        String brand = item.getBrand();
        if(brand == null || brand.isEmpty() ){
            selectedItemViewHolder.brand.setVisibility(View.GONE);
        }else {
            selectedItemViewHolder.brand.setVisibility(View.VISIBLE);
            StringBuilder cap = new StringBuilder("By: ");
            cap.append(brand.substring(0, 1).toUpperCase()).append(brand.substring(1).trim());
            selectedItemViewHolder.brand.setText(cap);

        }

        String price = item.getPrice();
        if(price == null || price.isEmpty() ){
            price = "$"+"0.0";
        }else {
            selectedItemViewHolder.price.setVisibility(View.VISIBLE);
            price = "$"+price;
        }
        selectedItemViewHolder.price.setText(price);

        String colorName = item.getColorName();
        String colorValue = item.getColorValue();
        if((colorName == null || colorName.isEmpty())){
            selectedItemViewHolder.colorLayout.setVisibility(View.GONE);
        }else{
            selectedItemViewHolder.colorLayout.setVisibility(View.VISIBLE);
            formatString(colorName, selectedItemViewHolder.colorName);
            selectedItemViewHolder.colorValue.setBackgroundColor(Color.parseColor(colorValue));

        }

        selectedItemViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
               AppCompatActivity activity = (AppCompatActivity) v.getContext();
                SavedListDetailsFragment savedListDetailsFragment = new SavedListDetailsFragment();
                Bundle bundle = new Bundle();
                 bundle.putParcelable("savedItemDb",item);
                savedListDetailsFragment.setArguments(bundle);

                Boolean isTablet = ItemsActivity.getIsTablet();
                if(isTablet){
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.lists_frame_right,savedListDetailsFragment).commit();
                }else {

                activity.getSupportFragmentManager().beginTransaction()
                        .hide(Objects.requireNonNull(activity.getSupportFragmentManager().findFragmentByTag(MyListsMainActivity.FRAGMENTUSERSAVEDLIST)))
                        .add(R.id.lists_frame,savedListDetailsFragment).addToBackStack(null).commit();

            }}
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

    public Item getItemAt(int position){
        return getItem(position);
    }



    class SelectedItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView name;
        private final TextView brand;
        private final TextView price;
        private final LinearLayout colorLayout;
        private final TextView colorName;
        private final ImageView colorValue;
        private final CardView cardView;
        SelectedItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.itemImage);
            name = itemView.findViewById(R.id.itemName);
            brand = itemView.findViewById(R.id.itemBrand);
            price = itemView.findViewById(R.id.itemPrice);
            colorLayout = itemView.findViewById(R.id.color_layout);
            colorName = itemView.findViewById(R.id.itemColor);
            colorValue = itemView.findViewById(R.id.displayItemColor);
            cardView = itemView.findViewById(R.id.card_recy_products);
        }
    }
}