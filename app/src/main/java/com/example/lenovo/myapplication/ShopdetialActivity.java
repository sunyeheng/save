package com.example.lenovo.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lenovo on 2018/5/25.
 */

public class ShopdetialActivity extends Activity {
    private String guanzhugStr;
    private String shoucangStr;
    private Button shareBtn;
    private Button likeBtn;
    private Button messageBtn;
    private Button shoucangBtn;

    //关注按钮

    public  String guanzhuByGet(String user_id,String business_id){

        //提交数据到服务器
        //拼装路径
        String path = null;
        try {
            path = Data.url+"GuanzhuButton?user_id="+user_id+"&business_id="+business_id
                    + URLEncoder.encode(user_id,"UTF-8") +  URLEncoder.encode(business_id,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request=new Request.Builder()
                .url(path)
                .build();

        Call call = okHttpClient.newCall(request);
        try {

            Response response=call.execute();
            guanzhugStr = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return guanzhugStr;
    }



    //收藏按钮
    public  String shoucangloginByGet(){

            //提交数据到服务器
            //拼装路径


        String   path = Data.url+"ShouCangGoods?user_id="+Data.user.getId()+"&goods_id="+Data.goods.getId();


        OkHttpClient okHttpClient = new OkHttpClient();
            Request request=new Request.Builder()
                    .url(path)
                    .build();

            Call call = okHttpClient.newCall(request);
        try {

            Response response=call.execute();
            shoucangStr=response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return shoucangStr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopdetail);
        Intent intent = getIntent();
        final Business business = (Business) intent.getSerializableExtra("business");
        ImageView guanzhubusiness = findViewById(R.id.guanzhubusiness);
        guanzhubusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = true;
                String [] b_id= Data.user.getBusiness_id().split(";");
                for (String s:b_id){
                    if (Integer.parseInt(s)==business.getId()){
                        flag=false;
                    }
                }
                if (flag){
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(Data.url + "GuanzhuButton?user_id=" + Data.user.getId() + "&business_id=" + business.getId())
                            .build();
                    Call call = okHttpClient.newCall(request);
                    try {
                        call.execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Data.user.setBusiness_id(Data.user.getBusiness_id()+business.getId()+";");
                    Data.businessList_data.add(business);
                    Toast.makeText(ShopdetialActivity.this,"关注成功！",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ShopdetialActivity.this,"已关注！",Toast.LENGTH_SHORT).show();
                }

            }
        });
        TextView phone_business = findViewById(R.id.phone_business);
        phone_business.setText(business.getPhone());

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Data.url+"GetBusinessGoods?business_id="+business.getId())
                .build();
        List<Goods> goodsList = new ArrayList<>();
        final Call call = okHttpClient.newCall(request);
        try {
            Response response=call.execute();
            String goodsListStr = response.body().string();

            Gson gson = new Gson();
            Type type = new TypeToken<List<Goods>>() {}.getType();
            goodsList = gson.fromJson(goodsListStr,type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ListView lv_shopdetial_list = findViewById(R.id.lv_shopdetial_list);     // 获取ListView控件
        ShopdetialAdapter customAdapter = new ShopdetialAdapter(// 创建Adapter对象
                this,       // 上下文环境
                goodsList, // 数据源
                R.layout.shopdetial_list // 列表项布局文件
        );
        lv_shopdetial_list.setAdapter(customAdapter); // 给ListView控件设置适配器
//        // 给ListView的列表项注册点击事件监听器
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
