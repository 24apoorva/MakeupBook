package com.example.android.makeupbook;

import android.content.Context;
import android.content.Intent;
import com.example.android.makeupbook.accounts.SigningActivity;
import com.example.android.makeupbook.accounts.User;
import com.example.android.makeupbook.ui.ProductsDisplayActivity;
import com.google.firebase.auth.FirebaseAuth;

public class NavigationBarClass {
    private FirebaseAuth auth;
    private Context mContext;


    public NavigationBarClass(Context context){
        mContext = context;
    }

    public void signOutOfApp() {
        auth = FirebaseAuth.getInstance();
        auth.signOut();
        mContext.startActivity(new Intent(mContext, SigningActivity.class));
    }


    public void displayEyeProducts() {
        Intent intent = new Intent(mContext, ProductsDisplayActivity.class);
        intent.putExtra("tabs", 4);
        mContext.startActivity(intent);

    }

    public void displayFaceProducts() {
        Intent intent = new Intent(mContext, ProductsDisplayActivity.class);
        intent.putExtra("tabs", 3);
        mContext.startActivity(intent);

    }

    public void displayLipProducts() {
        Intent intent = new Intent(mContext, ProductsDisplayActivity.class);
        intent.putExtra("tabs", 2);
        mContext.startActivity(intent);

    }

    public void displayNailsProducts() {
        Intent intent = new Intent(mContext, ProductsDisplayActivity.class);
        intent.putExtra("tabs", 1);
        mContext.startActivity(intent);
    }


    public void displayHomeScreen() {
        Intent intent = new Intent(mContext, MainActivity.class);
        mContext.startActivity(intent);
    }
}
