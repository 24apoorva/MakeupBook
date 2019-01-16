package com.example.android.makeupbook.adaptersavedlists;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.makeupbook.R;
import com.example.android.makeupbook.adapters.ProductsRecyclerViewAdapter;
import com.example.android.makeupbook.objects.ProductCount;

public class SummeryHaveAdapter extends ListAdapter<ProductCount,SummeryHaveAdapter.SummeryViewHolder> {
    public SummeryHaveAdapter() {
        super(DIFF_CALLBACK);
    }
    private static final DiffUtil.ItemCallback<ProductCount> DIFF_CALLBACK = new DiffUtil.ItemCallback<ProductCount>() {
        @Override
        public boolean areItemsTheSame(@NonNull ProductCount oldItem, @NonNull ProductCount newItem) {
            return oldItem.getType().contains(newItem.getType());

        }

        @Override
        public boolean areContentsTheSame(@NonNull ProductCount oldItem, @NonNull ProductCount newItem) {
            return  oldItem.getType().equals(newItem.getType()) && oldItem.getCount() == newItem.getCount();
        }
    };

    @NonNull
    @Override
    public SummeryHaveAdapter.SummeryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.products_summery_list,viewGroup,false);
        return new SummeryHaveAdapter.SummeryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final SummeryHaveAdapter.SummeryViewHolder summeryViewHolder, int i) {
        final ProductCount productCount = getItem(i);
        final String productType = productCount.getType();
        String value = productCount.getType().trim() +" "+String.valueOf(productCount.getCount());
        String cap = value.substring(0, 1).toUpperCase() + value.substring(1);
        summeryViewHolder.type.setText(cap);
    }

    class SummeryViewHolder extends RecyclerView.ViewHolder {
        final TextView type;
        SummeryViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type_display_id);
        }
    }
}
