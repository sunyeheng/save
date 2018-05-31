package com.example.lenovo.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.myapplication.R;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2018/5/23.
 */

public class GoodsAdapter extends BaseAdapter {

    private Context context;        //上下文环境
    private List<Map<String,Object>> dataSource; //数据源
    private int goods_list;   //声明列表项的布局

    //声明列表项里的控件
    public GoodsAdapter(Context context, List<Map<String, Object>> dataSource, int goods_list) {
        this.context = context;
        this.dataSource = dataSource;
        this.goods_list = goods_list;
    }

    @Override
    public int getCount() {

        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView){
            //加载布局文件
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(goods_list,null);
        }

        ImageView img_goods= convertView.findViewById(R.id.img_goods);
        TextView txt_goods = convertView.findViewById(R.id.txt_goods);
        TextView txt_goodPrice = convertView.findViewById(R.id.txt_goodPrice);
        // 给数据项填充数据
        final Map<String, Object> mItemData = dataSource.get(position);
        img_goods.setImageResource((int)mItemData.get("header"));
        txt_goods.setText(mItemData.get("name").toString());
        txt_goodPrice.setText(mItemData.get("price").toString());
        // 给列表项中的控件注册事件监听器
//        img_locationFollow.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        return convertView; // 返回列表项
    }
}