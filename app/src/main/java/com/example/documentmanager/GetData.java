package com.example.documentmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;

import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.ArrayList;

import static com.example.documentmanager.MainActivity.mylist;

public class GetData extends AsyncTask<File,String,String > {

    Context context;
    ProgressDialog progressDialog;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    Bundle savedInstanceState;
    StringBuilder stringBuilder=new StringBuilder();
    String L="MYLIST";
    SharedPreferences sharedPreferences;

    public GetData(SharedPreferences sharedPreferences)
    {
        this.sharedPreferences=sharedPreferences;
    }

    public GetData(Context context, ProgressDialog progressDialog,NavigationView navigationView,FragmentManager fragmentManager,
                   Bundle savedInstanceState,SharedPreferences sharedPreferences) {
        this.context = context;
        this.progressDialog = progressDialog;
        this.navigationView=navigationView;
        this.fragmentManager=fragmentManager;
        this.savedInstanceState=savedInstanceState;
        this.sharedPreferences=sharedPreferences;

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Files are loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(File... files) {
        getdoc(files[0]);
        return "Done";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        savedata();
        progressDialog.dismiss();
        if (savedInstanceState==null) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container,
                    new MessageFragment()).commit();
            navigationView.setCheckedItem(R.id.folder);
        }
    }

    public void getdoc(File file){
        File[] files=file.listFiles();
        if(files.length>0){
            for (File mfile:files){
                if (mfile.isDirectory()){
                    getdoc(mfile);
                }else {
                    if (mfile.isFile()){
                        String strfile=String.valueOf(mfile);
                        if (strfile.endsWith(".pdf") || strfile.endsWith(".doc") ||
                                strfile.endsWith(".xls") || strfile.endsWith(".ppt") || strfile.endsWith(".jpeg") || strfile.endsWith(".txt")){
                            mylist.add(mfile);
                        }

                    }
                }
            }
        }
    }

    public StringBuilder list_to_string(ArrayList<File> mlist){
        for (int i = 0; i<mlist.size(); i++){
            stringBuilder.append(mlist.get(i).toString());
            stringBuilder.append(",");
        }
        return stringBuilder;
    }

    public void savedata() {
        StringBuilder val=list_to_string(mylist);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.putString(L, String.valueOf(val));
        editor.commit();
    }
}
