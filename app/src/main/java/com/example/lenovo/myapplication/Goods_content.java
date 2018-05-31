package com.example.lenovo.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import static com.example.lenovo.myapplication.Data.goodsListm;
import static com.example.lenovo.myapplication.Data.goodsList;

/**
 * Created by lenovo on 2018/5/26.
 */

public class Goods_content extends Activity {
    private TextView Goods_name;
    private ImageView Goods_image;
    private TextView Goods_info;
    private TextView Collet_num;
    private TextView Goods_price;
    private Button Delete_Goods;
    private ImageView return_business;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_layout);
        return_business=findViewById(R.id.return_business_index);
       Goods_image=findViewById(R.id.Goods_content_image);
       Goods_name=findViewById(R.id.Goods_content_name);
       Goods_price=findViewById(R.id.Goods_content_price);
       Goods_info=findViewById(R.id.Goods_content_info);
       Collet_num=findViewById(R.id.Goods_content_collect_num);
       Delete_Goods=findViewById(R.id.delete_goods);
        final Intent intent=getIntent();
        int id=intent.getIntExtra("id",0);
       Glide.with(Goods_content.this).load(Data.urlImageBusiness+goodsList.get(id).getImage())
       .into(Goods_image);
       Goods_name.setText(goodsList.get(id).getName());
       Goods_info.setText(goodsList.get(id).getInfo());
       Goods_price.setText(goodsList.get(id).getPrice().toString()+"元");
       Collet_num.setText(String.valueOf(goodsList.get(id).getCollect_num()));
       return_business.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent1=new Intent(Goods_content.this,BuinessActivity.class);
               startActivity(intent1);
           }
       });
       Delete_Goods.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(Goods_content.this,"删除成功",Toast.LENGTH_SHORT)
                       .show();
               Intent intent1=new Intent(Goods_content.this,BuinessActivity.class);
               startActivity(intent);
           }
       });
    }
}
