package com.example.android.makeupbook.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.makeupbook.R;
import com.example.android.makeupbook.objects.Colors;
import com.example.android.makeupbook.objects.Products;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductsRecyclerViewAdapter extends RecyclerView.Adapter<ProductsRecyclerViewAdapter.ProductsViewHolder> {
    private ArrayList<Products> mProducts = new ArrayList<>();
    private Context mContext;

    public ProductsRecyclerViewAdapter(Context context, ArrayList<Products> products){
        mContext = context;
        mProducts = products;
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        public ImageView productImage;
        public TextView productName;
        public TextView brandDisplay;
        public TextView price;
        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = (ImageView)itemView.findViewById(R.id.listImage);
            productName = (TextView)itemView.findViewById(R.id.productName);
            brandDisplay = (TextView)itemView.findViewById(R.id.brandName);
            price = (TextView)itemView.findViewById(R.id.price);
        }
    }
    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.products_list,viewGroup,false);
        return new ProductsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder productsViewHolder, int position) {
        Products currentProduct = mProducts.get(position);
        String url = currentProduct.getImageUrl();
        if(!url.contains("https")){
            url = url.replace("http","https");
        }
        Picasso.with(mContext)
                .load(url)
                .fit()
                .placeholder(R.drawable.beauty_all_products)
                .into(productsViewHolder.productImage);

        String name = currentProduct.getName();
        if(name == null){
            productsViewHolder.productName.setVisibility(View.INVISIBLE);
        }else {
            productsViewHolder.productName.setVisibility(View.VISIBLE);
            productsViewHolder.productName.setText(name.trim());
        }
        if(currentProduct.getBrand() == null){
            productsViewHolder.brandDisplay.setVisibility(View.INVISIBLE);
        }else {
            String brand = mContext.getResources().getString(R.string.by)+" "+currentProduct.getBrand().trim();
            productsViewHolder.brandDisplay.setVisibility(View.VISIBLE);
            productsViewHolder.brandDisplay.setText(brand);
        }
        String priceSign = "$";
        String price = priceSign+currentProduct.getPrice();
        if(currentProduct.getPrice() == null){
            productsViewHolder.price.setVisibility(View.INVISIBLE);
        }else {
            productsViewHolder.price.setVisibility(View.VISIBLE);
            productsViewHolder.price.setText(price);
        }

    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }


}
