package com.example.documentmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity {


    EditText name,password;
    Button save_btn,get_btn;
    TextView name_text,pass_text,list_txt;
    SharedPreferences sharedPreferences;
    List<String> list=new ArrayList();
    String Name="Name";
    String Password="Password";
    String List="List";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        list.add("hey");
        list.add("I");
        list.add("am");
        list.add("ali");

        final StringBuilder stringBuilder=new StringBuilder();

        for (int i=0;i<list.size();i++){
            stringBuilder.append(list.get(i));
            stringBuilder.append(",");
        }


        name=findViewById(R.id.name);
        password=findViewById(R.id.password);

        save_btn=findViewById(R.id.save_btn);
        get_btn=findViewById(R.id.getdata);

        name_text=findViewById(R.id.name_text);
        pass_text=findViewById(R.id.password_text);
        list_txt=findViewById(R.id.list_text);


        sharedPreferences=getSharedPreferences("mypref", Context.MODE_PRIVATE);
        if (sharedPreferences.contains(Name)){
            name_text.setText(sharedPreferences.getString(Name,""));
        }
        if (sharedPreferences.contains(Password)){
            pass_text.setText(sharedPreferences.getString(Password,""));
        }
        if(sharedPreferences.contains(List)){
            list_txt.setText(stringBuilder);
        }

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String naam=name.getText().toString();
                String pass=password.getText().toString();
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString(Name,naam);
                editor.putString(Password,pass);
                editor.putString(List, String.valueOf(stringBuilder));
                editor.commit();
            }
        });

        get_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.contains(Name)){
                    name_text.setText(sharedPreferences.getString(Name,""));
                }
                if (sharedPreferences.contains(Password)){
                    pass_text.setText(sharedPreferences.getString(Password,""));
                }
                if (sharedPreferences.contains(List)){
                    list_txt.setText(sharedPreferences.getString(List,""));
                    getList(sharedPreferences.getString(List,""));
                }
            }
        });

    }

    public void getList(String val){
        String[] mylist=val.split(",");
        list=new ArrayList<>();
        for (String mlist:mylist){
            list.add(mlist);
        }

        Toast.makeText(this, String.valueOf(list.size()), Toast.LENGTH_SHORT).show();
    }
}
