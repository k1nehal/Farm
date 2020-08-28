package com.example.farm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class Balance_Adapter extends BaseAdapter {

    private Context context;
    private LayoutInflater l;
    private ArrayList<String> name=new ArrayList<>();
    private ArrayList<Double> quantity=new ArrayList<>();
    private ArrayList<String> type = new ArrayList<>();
    public Balance_Adapter(Context context, ArrayList<String> name, ArrayList<Double> quantity, ArrayList<String> type) {
        this.context = context;
        this.name = name;
        this.quantity = quantity;
        this.type = type;
    }

    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       if(l==null)
        {
            l=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null)
        {
            convertView=l.inflate(R.layout.item_balance,null);
        }

        TextView textView =convertView.findViewById(R.id.nam);
        TextView textView1=convertView.findViewById(R.id.qua);
        TextView textView2=convertView.findViewById(R.id.typ);
        textView.setText(name.get(position));
        textView1.setText(quantity.get(position)+"");
        textView2.setText(type.get(position));

        return convertView;
    }
}