package com.example.documentmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MylistAdapter extends RecyclerView.Adapter<MylistAdapter.ViewHolder> {

    private ArrayList<String> listdata;
    Context context;
    FragmentManager fragmentManager;

    public MylistAdapter(ArrayList<String> listdata,Context context){
        this.listdata=listdata;
        this.context=context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View listItem=layoutInflater.inflate(R.layout.folderfiles,parent,false);
        ViewHolder viewHolder=new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.textView.setText(listdata.get(position));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.FOLDER_NAME=listdata.get(position);
                context.startActivity(new Intent(context,Main2Activity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView=itemView.findViewById(R.id.text1);
        }
    }


}