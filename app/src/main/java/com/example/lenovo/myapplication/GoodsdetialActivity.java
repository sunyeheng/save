package com.example.lenovo.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by lenovo on 2018/5/28.
 */

public class GoodsdetialActivity extends Activity {


    private String getReviewStr;
    ListView listView;


    public  String getReviewByGet(String goods_id){
        String path = null;
        try {
            path = Data.url+"GetGoodsPinglun?goods_id="+goods_id
                    + URLEncoder.encode(goods_id,"UTF-8");
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
            getReviewStr = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return getReviewStr;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsdetial);

        Intent intent = getIntent();
        final Goods goods = (Goods) intent.getSerializableExtra("goods");
        TextView goods_price_detail = findViewById(R.id.goods_price_detail);
        goods_price_detail.setText("￥"+goods.getPrice());
        TextView goods_name_detail = findViewById(R.id.goods_name_detail);
        goods_name_detail.setText(goods.getName());
        TextView goods_info = findViewById(R.id.goods_info);
        goods_info.setText(goods.getInfo());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TextView starttime = findViewById(R.id.starttime);
        starttime.setText(df.format(goods.getStarttime()));
        TextView endtime = findViewById(R.id.endtime);
        endtime.setText(df.format(goods.getEndtime()));
        final EditText edit_bg = findViewById(R.id.pinglun_content);
        Button review_submit = findViewById(R.id.review_submit);

        ImageView guanzhushangpin = findViewById(R.id.guanzhubusiness);
        guanzhushangpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = true;
                String [] c_id= Data.user.collection_id.split(";");
                for (String s:c_id){
                    if (Integer.parseInt(s)==goods.getId()){
                        flag=false;
                    }
                }
                if (flag){
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(Data.url + "ShouCangGoods?user_id="+Data.user.getId()+"&goods_id="+goods.getId())
                            .build();
                    Call call = okHttpClient.newCall(request);
                    try {
                        call.execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Data.user.setCollection_id(Data.user.getCollection_id()+goods.getId()+";");
                    Toast.makeText(GoodsdetialActivity.this,"收藏成功！",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(GoodsdetialActivity.this,"已收藏！",Toast.LENGTH_SHORT).show();
                }

            }
        });
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Data.url + "GetGoodsPinglun?goods_id="+goods.getId())
                .build();
        Call call = okHttpClient.newCall(request);
        String pinglunStr="";
        try {
            Response response = call.execute();
            pinglunStr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Type type=new TypeToken<List<Pinglun>>(){}.getType();
        final List<Pinglun> pinglun=gson.fromJson(pinglunStr,type);
        listView = findViewById(R.id.lv_goodsreview_list);
        listView.setAdapter(new goodsdetialpinglunAdapter(this,pinglun));

        review_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pinglun_content = edit_bg.getText().toString();
                Pinglun pinglun1 = new Pinglun();
                pinglun1.setBusiness_id(goods.getBusiness_id());
                pinglun1.setContent(pinglun_content);
                pinglun1.setGoods_id(goods.getId());
                pinglun1.setUser_id(Data.user.getId());
                pinglun1.setImage("morentouxiang.jpg");
                pinglun.add(pinglun1);
                Gson gson = new Gson();
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = RequestBody.create(
                        MediaType.parse("text/x-markdown; charset=utf-8"),gson.toJson(pinglun1));
                Request request = new Request.Builder()
                        .url(Data.url+"TianJiaPingLun")
                        .post(requestBody)
                        .build();
                Call call = okHttpClient.newCall(request);
                try {
                    call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                edit_bg.setText("");
                listView.setAdapter(new goodsdetialpinglunAdapter(GoodsdetialActivity.this,pinglun));
                Toast.makeText(GoodsdetialActivity.this,"添加成功",Toast.LENGTH_SHORT).show();

            }
        });


    }

    public class goodsdetialpinglunAdapter extends BaseAdapter{
        Activity activity;
        List<Pinglun> data;

        goodsdetialpinglunAdapter(Activity activity,List<Pinglun> data){
            this.activity = activity;
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                LayoutInflater inflater = LayoutInflater.from(activity);
                convertView = inflater.inflate(R.layout.pinglun_item,null);
            }
            ImageView img_user = convertView.findViewById(R.id.image_pinglun);
            TextView userName = convertView.findViewById(R.id.txt_userName);
            TextView txt_review = convertView.findViewById(R.id.txt_review);
            Glide.with(activity)
                    .load(Data.urlImage+data.get(position).getImage())
                    .into(img_user);
            userName.setText(data.get(position).getUsername());
            txt_review.setText(data.get(position).getContent());
            return convertView;
        }
    }
}
