package com.example.documentmanager;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MylistAdapter2 extends RecyclerView.Adapter<MylistAdapter2.ViewHolder> implements Filterable {

    ArrayList<String> mlist=new ArrayList<>();
    ArrayList<String> list2=new ArrayList<>();
    Context context;





    public MylistAdapter2(ArrayList<String> mlist,Context context){
        this.mlist=mlist;
        list2=new ArrayList<>(mlist);
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View listItem=layoutInflater.inflate(R.layout.filesview,parent,false);
        ViewHolder viewHolder=new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(mlist.get(position));


    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    @Override
    public Filter getFilter() {
        return filefilter;
    }

    private Filter filefilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<String> flist=new ArrayList<>();
            if (charSequence==null || charSequence.length()==0){
                flist.addAll(list2);
            }else {
                String filterpattren=charSequence.toString().toLowerCase().trim();
                for (String item : list2) {
                    if (item.toLowerCase().contains(filterpattren)) {
                        flist.add(item);
                    }
                }



            }
            FilterResults results=new FilterResults();
            results.values=flist;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mlist.clear();
            mlist.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        RelativeLayout relativeLayout1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.text2);
            relativeLayout1=itemView.findViewById(R.id.relativelayout1);
        }
    }
}
