package com.example.android.makeupbook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.example.android.makeupbook.accounts.SigningActivity;
import com.example.android.makeupbook.accounts.User;
import com.example.android.makeupbook.ui.HomeFragment;
import com.example.android.makeupbook.ui.MakeupFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.main_toolbar)
    Toolbar toolBar;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.main_drawer)
    DrawerLayout drawerLayout;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        auth = FirebaseAuth.getInstance();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolBar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState == null){
            displayHomeScreen();
        }
        displayUserData();

        navigation.setOnNavigationItemSelectedListener(mOnBottomNavigationItemSelectedListener);
        navigationView.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private NavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            int id = menuItem.getItemId();
            switch (id){
                case R.id.nav_home:
                    displayHomeScreen();
                    break;
                case R.id.nav_eyes:
                    displayMakeupScreen(getResources().getString(R.string.eyes),R.drawable.eyemakeup_background);
                    break;
                case R.id.nav_brands:
                    displayMakeupScreen(getResources().getString(R.string.brands),R.drawable.makeup_products);
                    break;
                case R.id.nav_face:
                    displayMakeupScreen(getResources().getString(R.string.face),R.drawable.beauty_all_products);
                    break;
                case R.id.nav_lips:
                    displayMakeupScreen(getResources().getString(R.string.lips),R.drawable.lips);
                    break;
                case R.id.nav_nails:
                    displayMakeupScreen(getResources().getString(R.string.nails),R.drawable.nailpolish_beauty);
                    break;
                case R.id.share:
                    shareThisApp();
                    break;
                case R.id.nav_signOut:
                    signOutOfApp();
                    break;
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
    };

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }


    //for Bottom navigation
    private BottomNavigationView.OnNavigationItemSelectedListener mOnBottomNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_fav:
                    return true;
                case R.id.navigation_iWant:
                    return true;
                case R.id.navigation_iHave:
                    return true;
            }
            return false;
        }
    };

    private void displayHomeScreen(){
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new HomeFragment()).commit();
    }

    private void displayMakeupScreen(String makeUp, int image){
        Bundle bundle = new Bundle();
        bundle.putString("makeupItem",makeUp);
        bundle.putInt("image",image);
        MakeupFragment makeupFragment = new MakeupFragment();
        makeupFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,makeupFragment).commit();
    }

    public void signOutOfApp() {
        auth = FirebaseAuth.getInstance();
        auth.signOut();
        startActivity(new Intent(MainActivity.this, SigningActivity.class));
    }

    private void displayUserData(){

        String id = auth.getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("users/" + id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User userData = dataSnapshot.getValue(User.class);
                String name = userData.getUserName();
                name = name.substring(0,1).toUpperCase() + name.substring(1);
                String email = userData.getEmail();
                View headerView = navigationView.getHeaderView(0);
                TextView userName = headerView.findViewById(R.id.nav_userName_display);
                TextView userEmail = headerView.findViewById(R.id.nav_email_display);
                userName.setText(name);
                userEmail.setText(email.trim());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void shareThisApp(){
        startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(MainActivity.this)
                .setType(getString(R.string.share_type))
                .setText(getString(R.string.shareText))
                .getIntent(), getString(R.string.shareMakeupBook)));
    }
}
