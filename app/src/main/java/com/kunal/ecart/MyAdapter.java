package com.kunal.ecart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<String> {

  ArrayList<String>   name ;
    Context context;

    public MyAdapter(@NonNull Context context, int resource, ArrayList<String> name) {
        super(context, resource , name);
        this.context = context;
        this.name = name;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.item1 , parent , false);
        TextView tv1 ;
        tv1 = convertView.findViewById(R.id.tv1);

        tv1.setText(name.get(position));
        return convertView;


    }
}
