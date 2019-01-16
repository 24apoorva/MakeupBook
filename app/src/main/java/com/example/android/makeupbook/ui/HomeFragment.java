package com.example.android.makeupbook.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.makeupbook.R;
import com.example.android.makeupbook.accounts.User;
import com.example.android.makeupbook.adapters.TagsRecyAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private FirebaseAuth auth;
    @BindView(R.id.home_name)
    TextView welcomeName;
    @BindView(R.id.tags_recy)
    RecyclerView myList;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        auth = FirebaseAuth.getInstance();
        displayTagsDetails();
        displayUserData(welcomeName);

        return view;
    }

    private void displayUserData(final TextView view){

        String id = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            id = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        }
        FirebaseDatabase.getInstance().getReference("users/" + id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User userData = dataSnapshot.getValue(User.class);
                assert userData != null;
                String name = userData.getUserName();
                name = name.substring(0,1).toUpperCase() + name.substring(1);
                String displayText = "Hello "+name+" !";
                view.setText(displayText);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void displayTagsDetails(){
        RecyclerView.LayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        myList.setLayoutManager(layoutManager);
        TagsRecyAdapter adapter = new TagsRecyAdapter(getContext());
        myList.setAdapter(adapter);
    }

    @OnClick(R.id.toprated_home)
    public void displayTopRatedProducts(){
        String item = "Top Rated Products";
        String urlOne ="https://makeup-api.herokuapp.com/api/v1/products.json?rating_greater_than=4.99&product_tags=Natural";
        String urltwo ="https://makeup-api.herokuapp.com/api/v1/products.json?rating_greater_than=4.99";
        Intent intent = new Intent(getContext(),ItemsActivity.class);
        intent.putExtra(ItemsActivity.ITEMTYPE,item);
        intent.putExtra(ItemsActivity.ISBRAND,false);
        intent.putExtra(ItemsActivity.ONEURL,urlOne);
        intent.putExtra(ItemsActivity.TWOURL,urltwo);
        getContext().startActivity(intent);
    }

}
