package com.example.android.myproductslibrary.adapters;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.myproductslibrary.ProductCount;
import com.example.android.myproductslibrary.R;

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
    public void onBindViewHolder(@NonNull SummeryHaveAdapter.SummeryViewHolder summeryViewHolder, int i) {
        ProductCount productCount = getItem(i);
        String value = productCount.getType() +" "+String.valueOf(productCount.getCount());
        summeryViewHolder.type.setText(value);
       // summeryViewHolder.number.setText(String.valueOf(productCount.getCount()));
    }

    public class SummeryViewHolder extends RecyclerView.ViewHolder {
        TextView type;
        TextView number;
        public SummeryViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type_display_id);
           // number = itemView.findViewById(R.id.count_id);
        }
    }
}
