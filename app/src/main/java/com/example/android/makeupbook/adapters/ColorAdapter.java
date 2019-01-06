package com.example.android.makeupbook.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.android.makeupbook.R;
import com.example.android.makeupbook.objects.Colors;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ColorAdapter extends ArrayAdapter<Colors> {
    private Context mContext;
    private ArrayList<Colors> mColors;
    private LayoutInflater inflater;

    public ColorAdapter(Context c, ArrayList<Colors> colors) {
        super(c, R.layout.display_color, colors);
        mContext = c;
        this.mColors = colors;
        inflater = LayoutInflater.from(c);
    }

    // Create a new ImageView for each item referenced by the Adapter
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.display_color, parent, false);
        }
        ImageView iv = convertView.findViewById(R.id.color_image_back);
        iv.setBackgroundColor(Color.parseColor(mColors.get(position).getHex_value()));
        return convertView;
    }

}
