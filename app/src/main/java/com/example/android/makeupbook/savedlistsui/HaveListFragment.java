package com.example.android.makeupbook.savedlistsui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
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
import com.example.android.makeupbook.adaptersavedlists.SelectedHaveItem;
import com.example.android.makeupbook.adaptersavedlists.SummeryHaveAdapter;

import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class HaveListFragment extends Fragment {
    private ItemViewModel itemViewModel;


    public HaveListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_have_list, container, false);
        displayHaveData(view);
        return view;
    }

    private void displayHaveData(final View view) {

        final LinearLayout layout = view.findViewById(R.id.notAddedLayoutHave);
        Button addProd = view.findViewById(R.id.addItemsHave);

        final TextView countText = view.findViewById(R.id.youHaveTextview);
        RecyclerView numHaveRecyclerView = view.findViewById(R.id.typeNumHave);
        RecyclerView.LayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        numHaveRecyclerView.setLayoutManager(layoutManager);
        final SummeryHaveAdapter summeryHaveAdapter = new SummeryHaveAdapter();
        numHaveRecyclerView.setAdapter(summeryHaveAdapter);

        RecyclerView listHave = view.findViewById(R.id.haveList);
        listHave.setLayoutManager(new LinearLayoutManager(getContext()));
        final SelectedHaveItem listHaveAdapter = new SelectedHaveItem();
        listHave.setAdapter(listHaveAdapter);
        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

        itemViewModel.loadHaveList().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                listHaveAdapter.submitList(items);

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                itemViewModel.removeItem(listHaveAdapter.getItemAt(viewHolder.getAdapterPosition()));

            }
        }).attachToRecyclerView(listHave);

        itemViewModel.getHaveCount().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {

                if (integer != 0) {
                    layout.setVisibility(View.GONE);
                    String countString = getContext().getResources().getString(R.string.youHave)+" "+integer+" "+getContext().getResources().getString(R.string.products);
                    countText.setText(countString);
                } else {
                    layout.setVisibility(View.VISIBLE);
                }
            }
        });

        itemViewModel.getHaveCountProductsList().observe(getViewLifecycleOwner(), new Observer<List<ProductCount>>() {
            @Override
            public void onChanged(@Nullable List<ProductCount> productCounts) {
                summeryHaveAdapter.submitList(productCounts);
            }
        });

        addProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   Intent intent = new Intent(getContext(),MainActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Objects.requireNonNull(getContext()).startActivity(intent);
                }
            }
        });


    }

}
