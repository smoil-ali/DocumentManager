package com.example.documentmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MessageFragment extends Fragment {

    ArrayList<String> mylist=new ArrayList<>();
    ProgressDialog progressDialog;
    View view;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    GetData obj=new GetData(MainActivity.sharedPreferences);
    FragmentManager fragmentManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_message,container,false);


        swipeRefreshLayout=view.findViewById(R.id.swipelayout);
        recyclerView=view.findViewById(R.id.recyclerview);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final MylistAdapter mylistAdapter=new MylistAdapter(getdadfiles(),getContext());
        recyclerView.setAdapter(mylistAdapter);
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

    public ArrayList<String> getdadfiles(){
        mylist.add("All Documents");
        for (int i=0;i<MainActivity.mylist.size();i++){
            if (!mylist.contains(MainActivity.mylist.get(i).getParentFile().getName())){
                mylist.add(MainActivity.mylist.get(i).getParentFile().getName());
            }
        }
        return mylist;
    }

    public static <T> ArrayList<T> convertSetToList(Set<T> set)
    {

        ArrayList<T> list = new ArrayList<>();

        for (T t : set)
            list.add(t);

        return list;
    }

    public class sub_task extends AsyncTask<File,String,String>{


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
            MylistAdapter mylistAdapter=new MylistAdapter(getdadfiles(),getContext());
            recyclerView.setAdapter(mylistAdapter);
        }
    }

}
