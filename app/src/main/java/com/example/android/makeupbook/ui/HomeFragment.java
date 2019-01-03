package com.example.android.makeupbook.ui;


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

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    FirebaseAuth auth;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        auth = FirebaseAuth.getInstance();
        TextView welcomeName = view.findViewById(R.id.home_name);
        displayUserData(welcomeName);
        displayTagsDetails(view);
        return view;
    }

    private void displayUserData(final TextView view){

        String id = auth.getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("users/" + id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User userData = dataSnapshot.getValue(User.class);
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

    private void displayTagsDetails(View view){
        RecyclerView.LayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView myList = (RecyclerView)view.findViewById(R.id.tags_recy);
        myList.setLayoutManager(layoutManager);
        TagsRecyAdapter adapter = new TagsRecyAdapter(getContext());
        myList.setAdapter(adapter);
    }

}
