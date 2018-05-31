package com.example.lenovo.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import static com.example.lenovo.myapplication.Data.BusinessUrlImage;

public class suosuoShopdetialAdapter extends BaseAdapter {
    private Activity context;
    private List<Goods> data;
    private int item_layout_id;

    public suosuoShopdetialAdapter (Activity context,List<Goods> data,  int item_layout_id){
        this.context = context;
        this.data= data;
        this.item_layout_id = item_layout_id;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.sousuoshopdetialactivity_list,null);
        }
        TextView txt_shopName = convertView.findViewById(R.id.shopName);
        ImageView imageView = convertView.findViewById(R.id.shopImg);
        final Goods goods=data.get(position);

        txt_shopName.setText(goods.getName());
        Glide.with(context)
                .load(BusinessUrlImage+goods.getImage())
                .into(imageView);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,GoodsdetialActivity.class);
                intent.putExtra("goods",goods);
                context.startActivity(intent);
            }
        });
        return convertView;

    }
}
