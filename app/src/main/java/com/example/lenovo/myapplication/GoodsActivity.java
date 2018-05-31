package com.example.lenovo.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2018/5/28.
 */

public class GoodsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);



//        // 准备数据源
//        List<Map<String, Object>> dataSource = new ArrayList<>();
//        Map<String, Object> itemData1 = new HashMap<>();
//        // 定义第1个数据项
//        itemData1.put("header", R.drawable.wanda);
//        itemData1.put("name", "万达");
//        dataSource.add(itemData1);
//
//        itemData1.put("header", R.drawable.wanda);
//        itemData1.put("name", "万达");
//        dataSource.add(itemData1);
//
//        itemData1.put("header", R.drawable.wanda);
//        itemData1.put("name", "万达");
//        dataSource.add(itemData1);
//
//        ListView lv_good_list = findViewById(R.id.lv_goods_list);     // 获取ListView控件
//        DiscoveryAdapter customAdapter = new DiscoveryAdapter(// 创建Adapter对象
//                this,       // 上下文环境
//                dataSource, // 数据源
//                R.layout.goods_list // 列表项布局文件
//        );
//        lv_good_list.setAdapter(customAdapter); // 给ListView控件设置适配器





        // 给ListView的列表项注册点击事件监听器
//        lv_discovery_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                Toast.makeText(getApplicationContext(),
//                        parent.getAdapter().getItem(position).toString(),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
    }

}
