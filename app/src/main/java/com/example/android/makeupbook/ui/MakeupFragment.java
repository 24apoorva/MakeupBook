package com.example.android.makeupbook.ui;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.makeupbook.R;
import com.example.android.makeupbook.adapters.MakeupItemsAdapter;
import com.example.android.makeupbook.objects.MakeupItems;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MakeupFragment extends Fragment {
//    ArrayList<> makeupItems = new ArrayList<MakeupItems>();

    public MakeupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        makeupItems.add(new MakeupItems(getResources().getString(R.string.eyes),getResources().getDrawable(R.drawable.ic_eyes)));
//        makeupItems.add(new MakeupItems(getResources().getString(R.string.face),getResources().getDrawable(R.drawable.ic_face)));
//        makeupItems.add(new MakeupItems(getResources().getString(R.string.lips),getResources().getDrawable(R.drawable.ic_lipstick)));
//        makeupItems.add(new MakeupItems(getResources().getString(R.string.nails),getResources().getDrawable(R.drawable.ic_nails)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_makeup, container, false);
        displayMakeupList(view);
        return view;
    }

    private void displayMakeupList(View view){
//        MakeupItemsAdapter adapter = new MakeupItemsAdapter(getContext(),makeupItems);
         ListView list = view.findViewById(R.id.makeup_list);
         ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),R.layout.list_makeup,R.id.makeupTitleview,getResources().getStringArray(R.array.MakeupArray));
//        // Using a ListView
//        list.setAdapter(adapter);
        list.setAdapter(arrayAdapter);
    }

}
