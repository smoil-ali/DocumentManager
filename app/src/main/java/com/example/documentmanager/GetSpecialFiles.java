package com.example.documentmanager;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

import static com.example.documentmanager.ProfileFragment.emergency_layout;
import static com.example.documentmanager.ProfileFragment.emergency_msg;
import static com.example.documentmanager.ProfileFragment.mylist;
import static com.example.documentmanager.ProfileFragment.mylistAdapter2;
import static com.example.documentmanager.ProfileFragment.recyclerView;


public class GetSpecialFiles {

    Context context;
    FragmentManager fragmentManager;
    public GetSpecialFiles(FragmentManager fragmentManager){
        this.fragmentManager=fragmentManager;
    }

    public GetSpecialFiles() {

    }

    public void show(ArrayList<String> mlist){
        if (mlist.size()>0){
            recyclerView.setVisibility(View.VISIBLE);
            emergency_msg.setVisibility(View.GONE);
            mylistAdapter2=new MylistAdapter2(mlist,context);
            recyclerView.setAdapter(mylistAdapter2);
        }else {
            recyclerView.setVisibility(View.GONE);
            emergency_msg.setVisibility(View.VISIBLE);
        }

    }



    public void special_files(String name){
        ArrayList<String> mlist=new ArrayList<>();
        System.out.println(String.valueOf(mylist.size()));
        String str;
        mlist.clear();
        if (TextUtils.equals(name,"PDF")){
            for (int i=0;i<mylist.size();i++){
                str=mylist.get(i);
                if (str.endsWith(".pdf")){
                    mlist.add(str);
                }
            }
            show(mlist);
        }else if (name.equals("DOC")){
            for (int i=0;i<mylist.size();i++){
                str=mylist.get(i);
                if (str.endsWith(".doc")){
                    mlist.add(str);
                }
            }
            show(mlist);
        }else if (name.equals("XLS")){
            for (int i=0;i<mylist.size();i++){
                str=mylist.get(i);
                if (str.endsWith(".xls")){
                    mlist.add(str);
                }
            }
            show(mlist);
        }else if (name.equals("JPEG")){
            for (int i=0;i<mylist.size();i++){
                str=mylist.get(i);
                if (str.endsWith(".jpeg")){
                    mlist.add(str);
                }
            }
            show(mlist);
        }else if (name.equals("PPT")){
            for (int i=0;i<mylist.size();i++){
                str=mylist.get(i);
                if (str.endsWith(".ppt")){
                    mlist.add(str);
                }
            }
             show(mlist);
        }else if (name.equals("TXT")){
            for (int i=0;i<mylist.size();i++){
                str=mylist.get(i);
                if (str.endsWith(".txt")){
                    mlist.add(str);
                }
            }
        }
        show(mlist);
    }
}
