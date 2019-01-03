package com.example.android.makeupbook.ui;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.makeupbook.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MakeupFragment extends Fragment {
    private boolean isBrand = false;

    public MakeupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_makeup, container, false);
        if(getArguments() != null){
            String selected = getArguments().getString("makeupItem");
            int image = getArguments().getInt("image");
            ImageView imageView = view.findViewById(R.id.image_makeup);
            imageView.setImageResource(image);
            if(selected == getContext().getResources().getString(R.string.brands)){
                isBrand = true;
            }else{
                isBrand = false;
            }
            displayMakeupList(view,selected);
        }
        return view;
    }

    private void displayMakeupList(View view, String string){
//        MakeupItemsAdapter adapter = new MakeupItemsAdapter(getContext(),makeupItems);
         ListView list = view.findViewById(R.id.makeup_list);
         String[] display = getArrayofMakeup(string);
        // String [] nailPolish = new String[0];
         final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),R.layout.list_makeup,R.id.makeupTitleview);
         if(display == null){
             arrayAdapter.add(getContext().getResources().getString(R.string.nailPolishTitle));
         }else {
             arrayAdapter.addAll(display);
         }

        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),ItemsActivity.class);
                String item = arrayAdapter.getItem(position);
                intent.putExtra(ItemsActivity.ITEMTYPE,item);
                intent.putExtra(ItemsActivity.BRANDTYPE,isBrand);
                getContext().startActivity(intent);
            }
        });
    }

    private String[] getArrayofMakeup(String string){
        if(string == getContext().getResources().getString(R.string.brands)){
            return getContext().getResources().getStringArray(R.array.brandsArray);
        }else if(string == getContext().getResources().getString(R.string.eyes)){
            return getContext().getResources().getStringArray(R.array.categoryEyesArray);
        }else if(string == getContext().getResources().getString(R.string.lips)){
            return getContext().getResources().getStringArray(R.array.categoryLipsArray);
        }else if(string == getContext().getResources().getString(R.string.face)){
            return getContext().getResources().getStringArray(R.array.categoryFaceArray);
        }else {
            return null;
        }
    }

}
