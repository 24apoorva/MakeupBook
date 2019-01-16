package com.example.android.makeupbook.adapters;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.transition.Fade;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.makeupbook.DetailsTransition;
import com.example.android.makeupbook.R;
import com.example.android.makeupbook.objects.Colors;
import com.example.android.makeupbook.objects.Products;
import com.example.android.makeupbook.ui.ItemDetailsFragment;
import com.example.android.makeupbook.Database.Item;
import com.example.android.makeupbook.ui.ItemsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ArrayList<Products> mProducts;
    private final Context mContext;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private final boolean hasFooter;
    //declare interface
    private final OnItemClicked onClick;

    public interface OnItemClicked {
        void onFooterClick();
        void imageClick(Item item);

    }

    public ProductsRecyclerViewAdapter(Context context, ArrayList<Products> products, boolean hasFooter, OnItemClicked onClick){
        this.mContext = context;
        this.mProducts = products;
        this.hasFooter = hasFooter;
        this.onClick = onClick;
    }

    class ProductsViewHolder extends RecyclerView.ViewHolder {
        final ImageView productImage;
         final TextView productName;
        final TextView brandDisplay;
        final TextView price;
        final CardView cardView;
        private final ImageView haveImage;
        private final ImageView wantImage;
        ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.listImage);
            productName = itemView.findViewById(R.id.productName);
            brandDisplay =itemView.findViewById(R.id.brandName);
            price = itemView.findViewById(R.id.price);
            cardView = itemView.findViewById(R.id.card_recy_products);
            haveImage = itemView.findViewById(R.id.have_this);
            wantImage=itemView.findViewById(R.id.want_this);
        }
    }

    private class ProductsFooterViewHolder extends RecyclerView.ViewHolder {
        final Button footerText;

        ProductsFooterViewHolder(View view) {
            super(view);
            footerText = view.findViewById(R.id.loadmore_data);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //Checking if there is a footer
        if(viewType == TYPE_ITEM){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.products_list,viewGroup,false);
        return new ProductsViewHolder(v);
    }else {
            View footerView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.footer_view,viewGroup,false);
            return new ProductsFooterViewHolder(footerView);

        }}

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final Boolean isTablet = ItemsActivity.getIsTablet();
        if (holder instanceof ProductsFooterViewHolder) {
            ProductsFooterViewHolder footerHolder = (ProductsFooterViewHolder) holder;
            if(hasFooter){
            footerHolder.footerText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onFooterClick();
                }
            });}else {
                footerHolder.footerText.setVisibility(View.GONE);
            }

        }else if (holder instanceof ProductsViewHolder) {
            final ProductsViewHolder itemViewHolder = (ProductsViewHolder) holder;
            final Products currentProduct = mProducts.get(position);
            String url = currentProduct.getImage_link();
            if(!url.contains("https")){
                url = url.replace("http","https");
            }
            Picasso.with(mContext)
                    .load(url)
                    .fit()
                    .placeholder(R.color.white)
                    .noFade()
                    .into(itemViewHolder.productImage);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                itemViewHolder.productImage.setTransitionName(currentProduct.getName()+position);
            }
            String name = currentProduct.getName();
            if(name == null){
                itemViewHolder.productName.setVisibility(View.INVISIBLE);
            }else {
                name = name.trim();
                itemViewHolder.productName.setVisibility(View.VISIBLE);
                itemViewHolder.productName.setText(name);
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

                    ItemDetailsFragment itemDetailsFragment = new ItemDetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(ItemDetailsFragment.MAKEUPITEMDETAILS,currentProduct);
                    itemDetailsFragment.setArguments(bundle);

                    if(isTablet){

                        activity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.items_frame_right, itemDetailsFragment).commit();
                    }else{

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            itemDetailsFragment.setSharedElementEnterTransition(new DetailsTransition());
                            itemDetailsFragment.setEnterTransition(new Fade());
                            itemDetailsFragment.setExitTransition(new Fade());
                            itemDetailsFragment.setSharedElementReturnTransition(new DetailsTransition());
                        }
                        activity.getSupportFragmentManager().beginTransaction()
                                .hide(activity.getSupportFragmentManager().findFragmentByTag(ItemsActivity.PRODUCTFRAGMENTTAG))
                                 .addSharedElement(itemViewHolder.productImage,mContext.getResources().getString(R.string.transition_photo_details))
                                .add(R.id.items_frame, itemDetailsFragment).addToBackStack(null).commit();

                    }
                   }
            });

            if(isTablet){
                //if it is a tablet images will be hidden
                itemViewHolder.wantImage.setVisibility(View.GONE);
                itemViewHolder.haveImage.setVisibility(View.GONE);

            }else {
                itemViewHolder.wantImage.setVisibility(View.VISIBLE);
                itemViewHolder.haveImage.setVisibility(View.VISIBLE);
            itemViewHolder.wantImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentProduct.getProduct_colors().isEmpty() || currentProduct.getProduct_colors() == null){
                        Item item =   addItem(currentProduct,null,null,false,true);

                        onClick.imageClick(item);
                    }else {
                        displayDialog(currentProduct.getProduct_colors(),currentProduct,false);
                    }

                }
            });

            itemViewHolder.haveImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentProduct.getProduct_colors().isEmpty() || currentProduct.getProduct_colors() == null){
                        Item item =   addItem(currentProduct,null,null,true,false);
                        onClick.imageClick(item);
                    }else {
                        displayDialog(currentProduct.getProduct_colors(),currentProduct,true);
                    }
                }
            });
        }}
    }

    /**
     * This method adds product data to item
     * @param product - current product
     * @param nameColor - color name
     * @param valueColor - color value
     * @param inHaveList - is it added to have list
     * @param inWantList - is it in want list
     * @return an item
     */
    private Item addItem(Products product,String nameColor,String valueColor,Boolean inHaveList,Boolean inWantList){
        return new Item(product.getProduct_id(),product.getBrand(),product.getName(),product.getPrice()
                ,product.getImage_link(),product.getProduct_type(),nameColor
                ,valueColor,product.getDescription(),product.getRating(),product.getCategory(),product.getProduct_link(),inHaveList,inWantList);
    }

    /**
     * This method displays dialog box with colors lists
     * @param colours Array list of Colours
     * @param currentProduct selected product
     * @param isHave is it a have list or want list
     */
    private void displayDialog(final ArrayList<Colors> colours, final Products currentProduct, final boolean isHave){
        final String[] listItems = getColorNamesArry(colours);
        final String[] col = getColorvalueArry(colours);
        final Colors[] color = {new Colors()};
        String title;
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        if(isHave){
            title = mContext.getResources().getString(R.string.chooseHave);
        }else{
            title = mContext.getResources().getString(R.string.chooseWant);
        }

        builder.setTitle(title)
                .setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        color[0] = new Colors(listItems[which],col[which]);
                    }
                })
        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(color[0].getColour_name() == null){
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.selectColor),Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else{
               Colors mColor = new Colors(color[0].getColour_name(),color[0].getHex_value());
                Item item;
               if(isHave) {
                   item = addItem(currentProduct,mColor.getColour_name(),mColor.getHex_value(),true,false);

               }else {
                   item = addItem(currentProduct,mColor.getColour_name(),mColor.getHex_value(),false,true);

               }
               onClick.imageClick(item);
            }}
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * used to get colour values from Colors object
     * @param colors ArrayList of colors
     * @return string array
     */
    private String[] getColorNamesArry(ArrayList<Colors> colors){
        String[] name = new String[colors.size()];
        for(int i=0; i<colors.size(); i++){
            if(colors.get(i).getColour_name() == null || colors.get(i).getColour_name().isEmpty())
            {
                name[i] = mContext.getResources().getString(R.string.color);
            }else{
                name[i] = colors.get(i).getColour_name();
            }
        }
        return name;
    }

    /**
     * used to get colour values from Colors object
     * @param colors ArrayList of colors
     * @return string array
     */
    private String[] getColorvalueArry(ArrayList<Colors> colors){
        String[] name = new String[colors.size()];
        for(int i=0; i<colors.size(); i++){
            name[i] = colors.get(i).getHex_value();
        }
        return name;
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
