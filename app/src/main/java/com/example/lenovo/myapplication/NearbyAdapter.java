package com.example.lenovo.myapplication;

import android.content.Context;
import android.content.Intent;
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

public class NearbyAdapter extends BaseAdapter {

    private Context context;        //上下文环境
    private List<Map<String,Object>> dataSource; //数据源
    private int nearby_list;   //声明列表项的布局

    //声明列表项里的控件
    public NearbyAdapter(Context context, List<Map<String, Object>> dataSource, int nearby_list) {
        this.context = context;
        this.dataSource = dataSource;
        this.nearby_list = nearby_list;
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
            convertView = mInflater.inflate(nearby_list,null);
        }

        ImageView img_nearybylogo = convertView.findViewById(R.id.img_nearbylogo);
        TextView txt_nearbyName = convertView.findViewById(R.id.txt_nearbyName);
        TextView txt_nearbyPrice = convertView.findViewById(R.id.txt_nearbyPrice);
        TextView txt_nearbyPosition = convertView.findViewById(R.id.txt_nearbyPosition);
        // 给数据项填充数据
        final Map<String, Object> mItemData = dataSource.get(position);
        img_nearybylogo.setImageResource((int)mItemData.get("header"));
        txt_nearbyName.setText(mItemData.get("name").toString());
        txt_nearbyPrice.setText(mItemData.get("price").toString());
        txt_nearbyPosition.setText(mItemData.get("position").toString());
        // 给列表项中的控件注册事件监听器
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(context,ShopdetialActivity.class);//你要跳转的界面
                context.startActivity(intent);

            }
        });
        return convertView; // 返回列表项
    }
}
