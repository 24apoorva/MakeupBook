package com.example.android.myproductslibrary.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.myproductslibrary.R;

public class SummeryAdapter extends RecyclerView.Adapter<SummeryAdapter.SummeryViewHolder>{
    Context context;

    public SummeryAdapter() {
    }

    public SummeryAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public SummeryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.products_summery_list,viewGroup,false);
        return new SummeryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SummeryViewHolder summeryViewHolder, int i) {
        String[] type = context.getResources().getStringArray(R.array.product_type);
        summeryViewHolder.type.setText(type[i]);
    }

    @Override
    public int getItemCount() {
        return context.getResources().getStringArray(R.array.product_type).length;
    }

    public class SummeryViewHolder extends RecyclerView.ViewHolder {
        TextView type;
        TextView number;
        public SummeryViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type_display_id);
            number = itemView.findViewById(R.id.count_id);
        }
    }
}
