package com.example.lenovo.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dingin on 2018/5/27.
 */

public class CollectedActivity extends Activity{
    String goodsListStr;
    private List<Goods> goodsList;
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collected);
        ImageView col_back =findViewById(R.id.col_back);
        col_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        OkHttpClient okHttpClient = new OkHttpClient();

        final Request request = new Request.Builder()
                .url(Data.url + "ShouCangYeMian?user_id="+Data.user.getId())
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                goodsListStr = response.body().string();
                Gson gson = new Gson();
                Type type = new TypeToken<List<Goods>>(){}.getType();
                goodsList = gson.fromJson(goodsListStr,type);
                Data.goodsList_data = goodsList;

                CollectedActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CollectedAdapter goodsListAdapter = new CollectedAdapter(CollectedActivity.this, goodsList);
                        ListView listView = findViewById(R.id.lv_collected_list);
                        listView.setAdapter(goodsListAdapter);
                    }
                });

            }
        });
//        Response response = null;
//        try {
//            response = call.execute();
//            goodsListStr = response.body().string();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Gson gson = new Gson();
//        Type type = new TypeToken<List<Goods>>(){}.getType();
//        List<Goods> goodsList = gson.fromJson(goodsListStr,type);
//        Data.goodsList_data = goodsList;
//        CollectedAdapter goodsListAdapter = new CollectedAdapter(CollectedActivity.this, goodsList);
//        ListView listView = findViewById(R.id.lv_collected_list);
//        listView.setAdapter(goodsListAdapter);
//
//


    }
}
