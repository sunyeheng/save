package com.example.lenovo.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.ViewTarget;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.example.lenovo.myapplication.Data.*;

import static com.example.lenovo.myapplication.Data.goodsList;

/**
 * Created by lenovo on 2018/5/23.
 */

public class BuinessActivity extends Activity {
    private String goodsString; //gson字符串
    private GridView gridView;  //商品列表
    private ImageView imageView; //商铺头像
    private TextView textView;//商铺名
    private ImageButton Add_goods;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buiness_layout);
        gridView=findViewById(R.id.Business_Goods_Gv);
        imageView=findViewById(R.id.Business_Image_top);
        Add_goods=findViewById(R.id.add_goods_button);
         Glide.with(BuinessActivity.this)
                .load(Data.urlImageBusiness + Data.business.getImage())
                .into(imageView);
        textView=findViewById(R.id.Business_top_name);
        textView.setText(Data.business.getShop_name());
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        int id=Data.business.getId();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request=new Request.Builder()
                .url(Data.url+"GetBusinessGoods?business_id="+id)
                .build();
        Call call=okHttpClient.newCall(request);
        try {
            Response response=call.execute();
            goodsString=response.body().string();
            Log.e("goodsString" ,goodsString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Goods>>(){}.getType();
        goodsList=gson.fromJson(goodsString,type);
        Business_Goods_Adapter adapter = new Business_Goods_Adapter(BuinessActivity.this,goodsList);
        gridView.setAdapter(adapter);

        Add_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BuinessActivity.this,Add_Goods.class);
                startActivity(intent);
            }
        });
    }
}
