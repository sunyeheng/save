package com.example.lenovo.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2018/5/23.
 */

public class FollowAdapter extends BaseAdapter {

    private Context context;        //上下文环境
    private List<Business> dataSource; //数据源

    //声明列表项里的控件
    public FollowAdapter(Context context, List<Business> dataSource ) {
        this.context = context;
        this.dataSource = dataSource;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (null == convertView){
            //加载布局文件
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.follow_list,null);
        }

        ImageView img_locationFollow = convertView.findViewById(R.id.img_locationfollow);
        TextView txt_followName = convertView.findViewById(R.id.txt_followName);
        // 给数据项填充数据
        Glide.with(context)
                .load(Data.BusinessUrlImage + dataSource.get(position).getImage())
                .into(img_locationFollow);
        txt_followName.setText(dataSource.get(position).getShop_name());
        // 给列表项中的控件注册事件监听器
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(context,ShopdetialActivity.class);//你要跳转的界面
                intent.putExtra("business",dataSource.get(position));
                context.startActivity(intent);

            }
        });


        return convertView; // 返回列表项
    }
}
