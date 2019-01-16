package com.example.android.makeupbook.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.makeupbook.R;
import com.example.android.makeupbook.ui.ItemsActivity;
import com.squareup.picasso.Picasso;

public class TagsRecyAdapter extends RecyclerView.Adapter<TagsRecyAdapter.TagsViewHolder> {
    private final Context mContext;
    public TagsRecyAdapter(Context context){
        mContext = context;
    }


    @NonNull
    @Override
    public TagsRecyAdapter.TagsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.grid_tag_list,viewGroup,false);
        return new TagsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final TagsViewHolder tagsViewHolder, int i) {
        final String name = mContext.getResources().getStringArray(R.array.tagsArray)[i];
        tagsViewHolder.tagText.setText(name);
        String url = mContext.getResources().getStringArray(R.array.tagsImageArray)[i];
        Picasso.with(mContext)
                .load(url)
                .fit()
                .placeholder(R.color.white)
                .into(tagsViewHolder.tagImage);

        tagsViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String urlOne ="https://makeup-api.herokuapp.com/api/v1/products.json?product_tags="+name;
                Intent intent = new Intent(mContext,ItemsActivity.class);
                intent.putExtra(ItemsActivity.ITEMTYPE,name);
                intent.putExtra(ItemsActivity.ISBRAND,true);
                intent.putExtra(ItemsActivity.ONEURL,urlOne);
                intent.putExtra(ItemsActivity.TWOURL,urlOne);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mContext.getResources().getStringArray(R.array.tagsArray).length;
    }

    class TagsViewHolder extends RecyclerView.ViewHolder {
          final TextView tagText;
          final ImageView tagImage;
        final CardView cardView;
        TagsViewHolder(@NonNull View itemView) {
            super(itemView);
            tagText = itemView.findViewById(R.id.tag_names);
            tagImage = itemView.findViewById(R.id.tag_images);
            cardView = itemView.findViewById(R.id.tag_card);
        }
    }
}
