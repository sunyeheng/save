package com.example.lenovo.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2018/5/23.
 */

public class DiscoveryAdapter extends BaseAdapter {

    private Activity context;        //上下文环境
    private List<Business> businessesList = new ArrayList<>();

    public DiscoveryAdapter(Activity context,List<Business> businessesList){
        this.context = context;
        this.businessesList = businessesList;
    }
    @Override
    public int getCount() {
        return businessesList.size();
    }

    @Override
    public Object getItem(int position) {
        return businessesList.get(position);
    }

    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (null == convertView){
            //加载布局文件
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.discovery_list,null);
        }

        ImageView img_locationShop = convertView.findViewById(R.id.img_locationShop);
        TextView txt_shopName = convertView.findViewById(R.id.txt_shopName);
        // 给数据项填充数据
        Glide.with(context)
                .load(Data.BusinessUrlImage+businessesList.get(position).getImage())
                .into(img_locationShop);
        txt_shopName.setText(businessesList.get(position).getShop_name());
       convertView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent =new Intent(context,ShopdetialActivity.class);//你要跳转的界面
               intent.putExtra("business",businessesList.get(position));
               context.startActivity(intent);

           }
       });
        return convertView;
        // 返回列表项
    }
}
