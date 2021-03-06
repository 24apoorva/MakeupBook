package com.example.android.makeupbook;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.example.android.makeupbook.accounts.SigningActivity;
import com.example.android.makeupbook.accounts.User;
import com.example.android.makeupbook.ui.AboutFragment;
import com.example.android.makeupbook.ui.HomeFragment;
import com.example.android.makeupbook.ui.MakeupFragment;
import com.example.android.makeupbook.savedlistsui.MyListsMainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.main_toolbar)
    Toolbar toolBar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.main_drawer)
    DrawerLayout drawerLayout;
    private FirebaseAuth auth;
    private boolean isTablet;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        auth = FirebaseAuth.getInstance();
        isTablet = getResources().getBoolean(R.bool.isTablet);
        if(!isTablet){
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolBar,
                    R.string.navigation_drawer_open,R.string.navigation_drawer_close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
        }

        if(savedInstanceState == null){
            displayHomeScreen();
        }
        displayUserData();
        navigationView.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private final NavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            int id = menuItem.getItemId();
            switch (id){
                case R.id.nav_home:
                    displayHomeScreen();
                    break;
                case R.id.nav_eyes:
                    displayMakeupScreen(getResources().getString(R.string.eyes));
                    break;
                case R.id.nav_brands:
                    displayMakeupScreen(getResources().getString(R.string.brands));
                    break;
                case R.id.nav_face:
                    displayMakeupScreen(getResources().getString(R.string.face));
                    break;
                case R.id.nav_lips:
                    displayMakeupScreen(getResources().getString(R.string.lips));
                    break;
                case R.id.nav_nails:
                    displayMakeupScreen(getResources().getString(R.string.nails));
                    break;
                case R.id.share:
                    shareThisApp();
                    break;
                case R.id.about:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new AboutFragment()).commit();
                    break;
                case R.id.nav_signOut:
                    signOutOfApp();
                    break;
            }
            if(!isTablet) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            return true;
        }
    };

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            if(!isTablet) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        }else {
            if (checkNavigationMenuItem() != 0)
            {
                navigationView.setCheckedItem(R.id.nav_home);
                HomeFragment fragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment).commit();
            }
            else
                super.onBackPressed();
        }
    }
    private int checkNavigationMenuItem() {
        Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            if (menu.getItem(i).isChecked())
                return i;
        }
        return -1;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        /* Return true so that the savedlistsmenu is displayed in the Toolbar */
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.navigation_iWant) {
            //Title bar back press triggers onBackPressed()
            displayIwantFragment();
            return true;
        }else if(id == R.id.navigation_iHave){
            displayIhaveFragment();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayIwantFragment(){
        Intent intent = new Intent(MainActivity.this,MyListsMainActivity.class);
        intent.putExtra(MyListsMainActivity.LISTTYPE,2);
        startActivity(intent);
    }

    private void displayIhaveFragment(){
        Intent intent = new Intent(MainActivity.this,MyListsMainActivity.class);
        intent.putExtra(MyListsMainActivity.LISTTYPE,1);
        startActivity(intent);
    }


    private void displayHomeScreen(){
        navigationView.setCheckedItem(R.id.nav_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new HomeFragment()).commit();
    }

    private void displayMakeupScreen(String makeUp){
        Bundle bundle = new Bundle();
        bundle.putString("makeupItem",makeUp);
        MakeupFragment makeupFragment = new MakeupFragment();
        makeupFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,makeupFragment).commit();
    }

    public void signOutOfApp() {
        auth = FirebaseAuth.getInstance();
        auth.signOut();
        startActivity(new Intent(MainActivity.this, SigningActivity.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void displayUserData(){

        String id = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        FirebaseDatabase.getInstance().getReference("users/" + id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User userData = dataSnapshot.getValue(User.class);
                assert userData != null;
                String name = Objects.requireNonNull(userData).getUserName();
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
