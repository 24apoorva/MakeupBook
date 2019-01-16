package com.example.android.makeupbook.savedlistsui;


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

import com.example.android.makeupbook.Database.Item;
import com.example.android.makeupbook.Database.ItemViewModel;
import com.example.android.makeupbook.MainActivity;
import com.example.android.makeupbook.R;
import com.example.android.makeupbook.objects.ProductCount;
import com.example.android.makeupbook.adaptersavedlists.SelectedWantAdapter;
import com.example.android.makeupbook.adaptersavedlists.SummeryAdapter;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WantListsFragment extends Fragment {

    private ItemViewModel itemViewModel;

    public WantListsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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

                        if (integer != 0) {
                            layout.setVisibility(View.GONE);
                            String countString = getContext().getResources().getString(R.string.youWant)+" "+integer+" "+getContext().getResources().getString(R.string.products);
                            countText.setText(countString);
                        } else {
                            layout.setVisibility(View.VISIBLE);
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
                Intent intent = new Intent(getContext(),MainActivity.class);
                getContext().startActivity(intent);

            }
        });


    }


}
