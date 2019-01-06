package com.example.android.makeupbook.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.makeupbook.R;
import com.example.android.makeupbook.objects.Colors;
import com.example.android.makeupbook.objects.Products;
import com.example.android.makeupbook.ui.ItemDetailsFragment;
import com.example.android.makeupbook.ui.ProductsFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnItemClick;

public class ProductsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Products> mProducts = new ArrayList<>();
    private Context mContext;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private boolean hasFooter;
    //declare interface
    private OnItemClicked onClick;

    //make interface like this
    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public ProductsRecyclerViewAdapter(Context context, ArrayList<Products> products, boolean hasFooter, OnItemClicked onClick){
        this.mContext = context;
        this.mProducts = products;
        this.hasFooter = hasFooter;
        this.onClick = onClick;
    }

    public ProductsRecyclerViewAdapter(boolean hasFooter){
        this.hasFooter = hasFooter;
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        public ImageView productImage;
        public TextView productName;
        public TextView brandDisplay;
        public TextView price;
        public CardView cardView;
        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = (ImageView)itemView.findViewById(R.id.listImage);
            productName = (TextView)itemView.findViewById(R.id.productName);
            brandDisplay = (TextView)itemView.findViewById(R.id.brandName);
            price = (TextView)itemView.findViewById(R.id.price);
            cardView = itemView.findViewById(R.id.card_recy_products);
        }
    }

    private class ProductsFooterViewHolder extends RecyclerView.ViewHolder {
        Button footerText;

        public ProductsFooterViewHolder(View view) {
            super(view);
            footerText = view.findViewById(R.id.loadmore_data);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if(viewType == TYPE_ITEM){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.products_list,viewGroup,false);
        return new ProductsViewHolder(v);
    }else {
            View footerView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.footer_view,viewGroup,false);
            return new ProductsFooterViewHolder(footerView);

        }}

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ProductsFooterViewHolder) {
            ProductsFooterViewHolder footerHolder = (ProductsFooterViewHolder) holder;
            if(hasFooter){
            footerHolder.footerText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onItemClick(mProducts.size());
                }
            });}else {
                footerHolder.footerText.setVisibility(View.GONE);
            }

        }else if (holder instanceof ProductsViewHolder) {
            final ProductsViewHolder itemViewHolder = (ProductsViewHolder) holder;
            Products currentProduct = mProducts.get(position);
            String url = currentProduct.getImageUrl();
            if(!url.contains("https")){
                url = url.replace("http","https");
            }
            Picasso.with(mContext)
                    .load(url)
                    .fit()
                    .placeholder(R.drawable.beauty_all_products)
                    .into(itemViewHolder.productImage);

            String name = currentProduct.getName();
            if(name == null){
                itemViewHolder.productName.setVisibility(View.INVISIBLE);
            }else {
                itemViewHolder.productName.setVisibility(View.VISIBLE);
                itemViewHolder.productName.setText(name.trim());
            }
            if(currentProduct.getBrand() == null){
                itemViewHolder.brandDisplay.setVisibility(View.INVISIBLE);
            }else {
                String brand = mContext.getResources().getString(R.string.by)+" "+currentProduct.getBrand().trim();
                itemViewHolder.brandDisplay.setVisibility(View.VISIBLE);
                itemViewHolder.brandDisplay.setText(brand);
            }
            String priceSign = "$";
            String price = priceSign+currentProduct.getPrice();
            if(currentProduct.getPrice() == null){
                itemViewHolder.price.setVisibility(View.INVISIBLE);
            }else {
                itemViewHolder.price.setVisibility(View.VISIBLE);
                itemViewHolder.price.setText(price);
            }
            itemViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    String end = Integer.toString(mProducts.get(position).getProductId())+".json";
                    String url = "https://makeup-api.herokuapp.com/api/v1/products/";
                    String sendUrl = url+end;
                    ItemDetailsFragment itemDetailsFragment = new ItemDetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(ItemDetailsFragment.DETAILSURL,sendUrl);
                    itemDetailsFragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.items_frame, itemDetailsFragment).addToBackStack(null).commit();

                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mProducts.size()) {
            return TYPE_FOOTER;
        } else
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        if(hasFooter){
            return mProducts.size()+1 ;
        }
        return mProducts.size();
    }



}
