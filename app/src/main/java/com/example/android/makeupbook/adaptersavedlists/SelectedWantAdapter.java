package com.example.android.makeupbook.adaptersavedlists;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.transition.Fade;
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
import com.example.android.makeupbook.DetailsTransition;
import com.example.android.makeupbook.R;
import com.example.android.makeupbook.savedlistsui.SavedListDetailsFragment;
import com.example.android.makeupbook.ui.ItemDetailsFragment;
import com.squareup.picasso.Picasso;

public class SelectedWantAdapter extends ListAdapter<Item,SelectedWantAdapter.SelectedWantViewHolder> {
    private Context mContext;
    public SelectedWantAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Item> DIFF_CALLBACK = new DiffUtil.ItemCallback<Item>() {
        @Override
        public boolean areItemsTheSame(Item oldItem, Item newItem) {
           return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame( Item oldItem, Item newItem) {
            Boolean x =  oldItem.getId() == (newItem.getId())
                    &&(oldItem.getName() != null &&oldItem.getName().equals(newItem.getName()))
                    && (oldItem.getImage_link() != null && oldItem.getImage_link().equals(newItem.getImage_link()))
                    && (oldItem.getColorName() != null && oldItem.getColorName().equals(newItem.getColorName()));
            return x;
        }
    };

    @NonNull
    @Override
    public SelectedWantAdapter.SelectedWantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_items_display,viewGroup,false);
        return new SelectedWantViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final SelectedWantAdapter.SelectedWantViewHolder selectedItemViewHolder, int i) {
        final Item item = getItem(i);

        String url = item.getImage_link();
        if(!url.contains("https")){
            url = url.replace("http","https");
        }

        selectedItemViewHolder.imageView.setTransitionName(item.getName()+item.getId());
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
        selectedItemViewHolder.price.setText(price);

        String colorName = item.getColorName();
        String colorValue = item.getColorValue();
        if((colorName == null || colorName.isEmpty())){
            selectedItemViewHolder.colorLayout.setVisibility(View.GONE);
        }else{
            selectedItemViewHolder.colorLayout.setVisibility(View.VISIBLE);
            formatString(colorName, selectedItemViewHolder.colorName);
            if(!colorValue.contains("#")){
                colorValue = "#"+colorValue;
            }
            selectedItemViewHolder.colorValue.setBackgroundColor(Color.parseColor(colorValue));

        }

        selectedItemViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AppCompatActivity activity = (AppCompatActivity) v.getContext();
//                String end = Integer.toString(item.getProduct_id())+".json";
//                String url = "https://makeup-api.herokuapp.com/api/v1/products/";
//                String sendUrl = url+end;
//                ItemDetailsFragment itemDetailsFragment = new ItemDetailsFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString(ItemDetailsFragment.DETAILSURL,sendUrl);
//                itemDetailsFragment.setArguments(bundle);
//
//
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    itemDetailsFragment.setSharedElementEnterTransition(new DetailsTransition());
//                    itemDetailsFragment.setEnterTransition(new Fade());
//                    itemDetailsFragment.setExitTransition(new Fade());
//                    itemDetailsFragment.setSharedElementReturnTransition(new DetailsTransition());
//                }
//                activity.getSupportFragmentManager().beginTransaction()
//                        .addSharedElement(selectedItemViewHolder.imageView, mContext.getResources().getString(R.string.transition_photo_details))
//                        .replace(R.id.lists_frame, itemDetailsFragment).addToBackStack(null).commit();

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                SavedListDetailsFragment savedListDetailsFragment = new SavedListDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("savedItemDb",item);
                savedListDetailsFragment.setArguments(bundle);

                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.lists_frame,savedListDetailsFragment).addToBackStack(null).commit();


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

    public Item getItemAt(int position){
        return getItem(position);
    }



    public class SelectedWantViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView name;
        private TextView brand;
        private TextView price;
        private LinearLayout colorLayout;
        private TextView colorName;
        private ImageView colorValue;
        private CardView cardView;
        public SelectedWantViewHolder(@NonNull View itemView) {
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
