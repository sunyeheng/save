package com.example.lenovo.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class sousuoActivity extends Activity {
    private List<Goods> goodsList;
    private String goodsListStr;
    EditText etSousuo;
    String name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sousuoye);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        etSousuo = findViewById(R.id.etSousuoye);
        name = etSousuo.getText().toString();
        ImageView imageView = findViewById(R.id.ivsousuoye);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient okHttpClient1 = new OkHttpClient();

                name=etSousuo.getText().toString();
                Request request1 = new Request.Builder()
                        .url(Data.sousuoGoods+name)
                        .build();
                Call call1 = okHttpClient1.newCall(request1);
                try {
                    Response response = call1.execute();
                    goodsListStr = response.body().string();
                    Log.e("goodsListStr",goodsListStr);

                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Goods>>(){}.getType();
                    goodsList = gson.fromJson(goodsListStr,type);
                    if (goodsList.size()==0){
                            viewTextImg(v,etSousuo);
                            etSousuo.setText("");
                    }else{


                        //通过intent注入信息，带到目标页  intent.putExtra("name",name);
                        Log.e("goodsList",goodsList.get(1).getName());
                        Intent intent = new Intent();
                        intent.putExtra("goodsListStr",goodsListStr);
                        intent.setClass(sousuoActivity.this,suosouShopdetialActivity.class);
                        startActivity(intent);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void viewTextImg(View view, TextView tv){
        Toast toast = new Toast(this);
        TextView textView= new TextView(this);
        ImageView imageView = new ImageView(this);
        imageView.setMaxWidth(500);
        imageView.setMaxHeight(900);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        textView.setTextSize(25);
        textView.setText("你搜索的'"+tv.getText().toString()+"'没有，去换个东西试试");
        imageView.setImageResource(R.drawable.bg);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.addView(imageView);
        linearLayout.addView(textView);
        toast.setView(linearLayout);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

}
