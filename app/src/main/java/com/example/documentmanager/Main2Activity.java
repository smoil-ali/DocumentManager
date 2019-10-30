package com.example.documentmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    MylistAdapter2 mylistAdapter2;
    String val=MainActivity.FOLDER_NAME;
    PopupMenu popupMenu;
    View view;
    GetSpecialFiles obj=new GetSpecialFiles(getSupportFragmentManager());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        toolbar=findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        view=findViewById(R.id.popup);






        drawerLayout=findViewById(R.id.drawer_layout2);
        NavigationView navigationView=findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2,
                    new ProfileFragment()).commit();
            navigationView.setCheckedItem(R.id.files);
        }

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.folder:
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent newIntent = new Intent(Main2Activity.this,MainActivity.class);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(newIntent);
                break;
            case R.id.files:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.recent:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.setTitle(MainActivity.FOLDER_NAME);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.files_menu2, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        MenuItem down_btn=menu.findItem(R.id.popup);
        final ImageView popup_btn=(ImageView) down_btn.getActionView();
        popup_btn.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
        popup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                        popupMenu=new PopupMenu(Main2Activity.this,toolbar,Gravity.START,0,R.style.PopupMenuMoreCentralized);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                switch (menuItem.getItemId()){
                                    case R.id.pdf:
                                        obj.special_files(menuItem.getTitle().toString());
                                        return true;
                                    case R.id.doc:
                                        obj.special_files(menuItem.getTitle().toString());
                                        return true;
                                    case R.id.xls:
                                        obj.special_files(menuItem.getTitle().toString());
                                        return true;
                                    case R.id.jpeg:
                                        obj.special_files(menuItem.getTitle().toString());
                                        return true;
                                    case R.id.ppt:
                                        obj.special_files(menuItem.getTitle().toString());
                                        return true;
                                    case R.id.txt:
                                        obj.special_files(menuItem.getTitle().toString());
                                        return true;
                                }

                                return true;
                            }
                        });
                    }
                }
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                popupMenu.show();
            }
        });

        SearchView searchView=(SearchView) searchItem .getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ProfileFragment.mylistAdapter2.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }


}
