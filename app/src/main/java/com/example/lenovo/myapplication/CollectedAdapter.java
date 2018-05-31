package com.example.lenovo.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by dingin on 2018/5/27.
 */

public class CollectedAdapter extends BaseAdapter {
    private List<Goods> data = new ArrayList<Goods>();
    private Activity context;

    public CollectedAdapter(Activity context, List<Goods> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_collected_item, null);
        }
        ImageView imageView = convertView.findViewById(R.id.col_img);
        TextView name = convertView.findViewById(R.id.col_name);
        TextView price = convertView.findViewById(R.id.col_price);

        Glide.with(context)
                .load(Data.urlImage + data.get(position).getImage())
                .into(imageView);
        name.setText(data.get(position).getName().toString());
        price.setText(data.get(position).getPrice().toString());
        return convertView;
    }
}

