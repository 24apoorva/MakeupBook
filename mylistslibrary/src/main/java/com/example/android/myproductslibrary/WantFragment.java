package com.example.android.myproductslibrary;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.myproductslibrary.Database.Item;
import com.example.android.myproductslibrary.Database.ItemViewModel;
import com.example.android.myproductslibrary.adapters.SelectedItem;
import com.example.android.myproductslibrary.adapters.SummeryAdapter;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WantFragment extends Fragment {

    private ItemViewModel itemViewModel;
    private int count;

    public WantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_want, container, false);

        itemViewModel = ViewModelProviders.of(getActivity()).get(ItemViewModel.class);
        displayData(view);
        return view;
    }

    private void displayData(final View view) {
        final LinearLayout layout = view.findViewById(R.id.notAddedLayout);

        final TextView countText = view.findViewById(R.id.youHaveTextview);
        RecyclerView numRecyclerView = view.findViewById(R.id.typeNum);
        RecyclerView.LayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        numRecyclerView.setLayoutManager(layoutManager);
        SummeryAdapter summeryAdapter = new SummeryAdapter(getContext());
        numRecyclerView.setAdapter(summeryAdapter);

        RecyclerView list = view.findViewById(R.id.wantList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(mLayoutManager);
        list.setHasFixedSize(true);
        final SelectedItem listAdapter = new SelectedItem();
        list.setAdapter(listAdapter);
        itemViewModel.getWantCount().observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer integer) {

                        if (integer == 0) {
                            layout.setVisibility(View.VISIBLE);
                        } else {
                            layout.setVisibility(View.GONE);
                            String countString = getContext().getResources().getString(R.string.youHave)+" "+integer+getContext().getResources().getString(R.string.products);
                            countText.setText(countString);
                        }
                    }
                }
        );

        itemViewModel.loadWantList().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                listAdapter.submitList(items);
            }
        });

//        String[] type = getContext().getResources().getStringArray(R.array.product_type);
//        for(int i=0;i<type.length; i++){
//            itemViewModel.getHaveProductsCount(type[i]).observe(this, new Observer<Integer>() {
//                @Override
//                public void onChanged(@Nullable Integer integer) {
//
//                }
//            });
//        }



    }
}
