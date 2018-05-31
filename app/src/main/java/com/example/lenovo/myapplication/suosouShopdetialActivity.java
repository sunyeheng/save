package com.example.lenovo.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class suosouShopdetialActivity extends Activity{
    public List<Goods> goodsList;
    String goodsListStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sousuoshopdetialactivity);


        Intent getIntent = getIntent();
        goodsListStr = getIntent.getStringExtra("goodsListStr");
        Gson gson = new Gson();
        Type type = new TypeToken<List<Goods>>(){}.getType();

        goodsList = gson.fromJson(goodsListStr,type);


        ListView lv = findViewById(R.id.lvShop);
        suosuoShopdetialAdapter shopdetialAdapter = new suosuoShopdetialAdapter(suosouShopdetialActivity.this,
             goodsList ,R.layout.sousuoshopdetialactivity_list  );
        lv.setAdapter(shopdetialAdapter);




        //返回按钮
        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
