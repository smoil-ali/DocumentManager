package com.example.documentmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int REQUEST_CODE=1;
    MenuItem search,popup,rate;
    public DrawerLayout drawerLayout;
    public Toolbar toolbar;
    Menu mymenu;
    static Bundle savedInstanceState;
    static ProgressDialog progressDialog;
    static NavigationView navigationView;
    static ArrayList<File> mylist=new ArrayList<File>();
    String L="MYLIST";
    String PREFERENCE="mypreference";
    static SharedPreferences sharedPreferences;
    StringBuilder stringBuilder;
    static String FOLDER_NAME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState=savedInstanceState;
        setContentView(R.layout.activity_main);

        sharedPreferences=getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        stringBuilder=new StringBuilder();
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        if (!CheckPermission()){
            RequestPermission();
        }else {
            getofflinedata();
        }
    }

    private void getofflinedata() {
        String val=sharedPreferences.getString(L,"");
        String[] mlist=val.split(",");
        mylist.clear();
        for (String list:mlist){
            mylist.add(new File(list));
        }
        if (savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MessageFragment()).commit();
            navigationView.setCheckedItem(R.id.folder);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.inflateMenu(R.menu.files_menu);
        toolbar.setTitle("All Documents");
        search=menu.findItem(R.id.search);
        popup=menu.findItem(R.id.popup);
        rate=menu.findItem(R.id.rate_menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    boolean CheckPermission(){
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            return true;
        }
        return false;
    }

    void RequestPermission(){
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE);
    }

    void DisplayMessage(String str, DialogInterface.OnClickListener listener){
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(str)
                .setPositiveButton("ok",  listener)
                .setNegativeButton("cancel",null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE:
                if (grantResults.length>0){
                    boolean accepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    if (accepted){
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                        GetData obj=new GetData(MainActivity.this,progressDialog,navigationView,getSupportFragmentManager(),
                                savedInstanceState,sharedPreferences);
                        obj.execute(Environment.getExternalStorageDirectory());
                    }else{
                        DisplayMessage("You have to give permission", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                RequestPermission();
                            }
                        });
                    }
                }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.folder:
                drawerLayout.closeDrawer(GravityCompat.START);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MessageFragment()).commit();
                break;
            case R.id.files:
                drawerLayout.closeDrawer(GravityCompat.START);
                MainActivity.FOLDER_NAME="All Documents";
                Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
                break;
            case R.id.recent:
                drawerLayout.closeDrawer(GravityCompat.START);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;
        }

        return true;
    }

}
