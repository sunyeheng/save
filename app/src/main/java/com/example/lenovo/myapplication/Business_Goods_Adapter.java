package com.example.lenovo.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2018/5/25.
 */

public class Business_Goods_Adapter extends BaseAdapter {
    private Activity content;
    private List<Goods> goodsList=new ArrayList<>();
    Business_Goods_Adapter(Activity context,List<Goods> list){
        this.content=context;
        this.goodsList=list;
    }
    @Override
    public int getCount() {
        return goodsList.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView= content.getLayoutInflater().inflate(R.layout.business_item_layout,null);
        }
        ImageView imageView=convertView.findViewById(R.id.Business_topImage);
        TextView textView=convertView.findViewById(R.id.index_Goods_name);
        textView.setText(goodsList.get(position).getName());
        Glide.with(content).load(
                Data.urlImageBusiness+goodsList.get(position).getImage()
        ).into(imageView);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(content,Goods_content.class);
                intent.putExtra("id",position);
                content.startActivity(intent);
            }
        });
        return convertView;
    }
}
