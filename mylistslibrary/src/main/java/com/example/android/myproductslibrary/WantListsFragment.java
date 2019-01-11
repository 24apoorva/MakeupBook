package com.example.android.myproductslibrary;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.myproductslibrary.Database.Item;
import com.example.android.myproductslibrary.Database.ItemViewModel;
import com.example.android.myproductslibrary.adapters.SelectedWantAdapter;
import com.example.android.myproductslibrary.adapters.SummeryAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WantListsFragment extends Fragment {

    private ItemViewModel itemViewModel;
    private int count;

    public WantListsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_want, container, false);
                displayWantData(view);

        return view;
    }

    private void displayWantData(final View view) {

        final LinearLayout layout = view.findViewById(R.id.notAddedLayout);
        Button addProd = view.findViewById(R.id.addItems);

        final TextView countText = view.findViewById(R.id.youWantTextview);
        RecyclerView numRecyclerView = view.findViewById(R.id.typeNum);
        RecyclerView.LayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        numRecyclerView.setLayoutManager(layoutManager);
        final SummeryAdapter summeryAdapter = new SummeryAdapter();
        numRecyclerView.setAdapter(summeryAdapter);

        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);


        RecyclerView list = view.findViewById(R.id.wantList);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        final SelectedWantAdapter listAdapter = new SelectedWantAdapter();
        list.setAdapter(listAdapter);

        itemViewModel.loadWantList().observe(getViewLifecycleOwner(), new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                listAdapter.submitList(items);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                itemViewModel.removeItem(listAdapter.getItemAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(list);

        itemViewModel.getWantCount().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer integer) {

                        if (integer == 0) {
                            layout.setVisibility(View.VISIBLE);
                        } else {
                            layout.setVisibility(View.GONE);
                            String countString = getContext().getResources().getString(R.string.youWant)+" "+integer+" "+getContext().getResources().getString(R.string.products);
                            countText.setText(countString);
                        }
                    }
                }
        );




            itemViewModel.getWantCountProductsList().observe(getViewLifecycleOwner(), new Observer<List<ProductCount>>() {
                @Override
                public void onChanged(@Nullable List<ProductCount> productCounts) {
                    summeryAdapter.submitList(productCounts);
                }
            });

        addProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = "Top Rated Products";
                String urlOne ="http://makeup-api.herokuapp.com/api/v1/products.json?rating_greater_than=4.99";
                Intent intent = null;
                try {
                    intent = new Intent(getContext(),Class.forName("com.example.android.makeupbook.MainActivity"));
                    getContext().startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });


    }


}
