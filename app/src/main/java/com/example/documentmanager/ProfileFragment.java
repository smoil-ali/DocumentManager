package com.example.documentmanager;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.io.File;
import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    static RecyclerView recyclerView;
    static RelativeLayout emergency_layout;
    static MylistAdapter2 mylistAdapter2;
    static ArrayList<String> mylist=new ArrayList<>();
    String val=MainActivity.FOLDER_NAME;
    SwipeRefreshLayout swipeRefreshLayout;
    static TextView emergency_msg;
    GetData obj=new GetData(MainActivity.sharedPreferences);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_profile,container,false);

        swipeRefreshLayout=view.findViewById(R.id.swipelayout2);
        recyclerView=view.findViewById(R.id.recyclerview2);
        recyclerView.hasFixedSize();
        emergency_layout=view.findViewById(R.id.emergency_layout);
        emergency_msg=view.findViewById(R.id.emergency_text);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mylistAdapter2=new MylistAdapter2(getchildfiles(),getContext());
        recyclerView.setAdapter(mylistAdapter2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MainActivity.mylist.clear();
                mylist.clear();
                new sub_task().execute(Environment.getExternalStorageDirectory());
            }
        });

        return view;
    }
    public ArrayList<String> getchildfiles(){
        mylist.clear();
        if(TextUtils.equals(val,"All Documents")){
            for (int i=0;i<MainActivity.mylist.size();i++){
                mylist.add(MainActivity.mylist.get(i).getName());
            }
        }else{
            for (int i=0;i<MainActivity.mylist.size();i++){
                if (TextUtils.equals(val,MainActivity.mylist.get(i).getParentFile().getName())){
                    mylist.add(MainActivity.mylist.get(i).getName());
                }
            }
        }
        return mylist;
    }



    public class sub_task extends AsyncTask<File,String,String> {


        @Override
        protected String doInBackground(File... files) {
            obj.getdoc(files[0]);
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            obj.savedata();
            swipeRefreshLayout.setRefreshing(false);
            MylistAdapter2 mylistAdapter=new MylistAdapter2(getchildfiles(),getContext());
            recyclerView.setAdapter(mylistAdapter);
        }
    }

}
