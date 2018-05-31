package com.example.lenovo.myapplication;

/**
 * Created by lenovo on 2018/5/24.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dingin on 2018/3/19.
 */

public class nearby_fragment extends Fragment {

    private View view;
    // 单例(方法二)
    private static nearby_fragment fc;
    public static nearby_fragment getFragmentC() {
        if (fc == null) {
            fc = new nearby_fragment();
        }
        return fc;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_nearby,container,false);


        // 准备数据源
        List<Map<String, Object>> dataSource = new ArrayList<>();
        Map<String, Object> itemData1 = new HashMap<>();
        // 定义第1个数据项
        itemData1.put("header", R.drawable.nikelogo);
        itemData1.put("name", "NIKE万达专卖店");
        itemData1.put("price", "$300起");
        itemData1.put("position", "鞋服|裕华区");
        dataSource.add(itemData1);

        itemData1.put("header", R.drawable.nikelogo);
        itemData1.put("name", "NIKE万达专卖店");
        itemData1.put("price", "$300起");
        itemData1.put("position", "鞋服|裕华区");
        dataSource.add(itemData1);

        itemData1.put("header", R.drawable.nikelogo);
        itemData1.put("name", "NIKE万达专卖店");
        itemData1.put("price", "$300起");
        itemData1.put("position", "鞋服|裕华区");
        dataSource.add(itemData1);

        ListView lv_nearbylist =view.findViewById(R.id.lv_nearby_list);// 获取ListView控件

        NearbyAdapter nearbyAdapter = new NearbyAdapter(// 创建Adapter对象
                getActivity(),       // 上下文环境
                dataSource, // 数据源
                R.layout.nearby_list // 列表项布局文件
        );

        lv_nearbylist.setAdapter(nearbyAdapter); // 给ListView控件设置适配器
        return view;

    }
}
