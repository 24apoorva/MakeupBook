package com.example.android.makeupbook.savedlistsui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.android.makeupbook.Database.ItemViewModel;
import com.example.android.makeupbook.R;

public class MyListsMainActivity extends AppCompatActivity {
    private ItemViewModel itemViewModel;
    public static final String LISTTYPE = "com.example.android.makeupbook.savedlistsui.listtype";
    private final int HAVEID = 1;
    private final int WANTID = 2;
    private String title;
    private int pageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_products);
        Intent intent = getIntent();
        if(intent != null){

         pageId = intent.getIntExtra(LISTTYPE,0);
        if(pageId== HAVEID){
            title = getResources().getString(R.string.havePage_title);
        }else {
            title = getResources().getString(R.string.wantPage_title);
        }
        Toolbar toolBar = findViewById(R.id.toolbar_userlists);
        toolBar.setTitle(title);
        setSupportActionBar(toolBar);
        if(pageId == HAVEID){
            getSupportFragmentManager().beginTransaction().replace(R.id.lists_frame, new HaveListFragment()).commit();
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.lists_frame, new WantListsFragment()).commit();

        }

    }}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.savedlistsmenu, menu);
        /* Return true so that the savedlistsmenu is displayed in the Toolbar */
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.delete_all){
            itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
            if(pageId == HAVEID){
                itemViewModel.removeAllHaveItems();
                return true;
            }else {
                itemViewModel.removeAllWantedItems();
                return true;
            }

        }
            return super.onOptionsItemSelected(item);
    }

}
