package com.example.lenovo.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by dingin on 2018/3/19.
 */

public class mine_fragment extends Fragment {
    private View view;
    TextView qianming;
    TextView qiandao;


    // 单例(方法二)
    private static mine_fragment fc;
    public static mine_fragment getFragmentD() {
        if (fc == null) {
            fc = new mine_fragment();
        }
        return fc;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.menu_layout,container,false);

        List<Map<String,Object>> list=new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        map.put("src",R.drawable.service);
        map.put("name","联系客服");

        Map<String,Object> map1=new HashMap<>();
        map1.put("src",R.drawable.shoucang);
        map1.put("name","收藏");
        Map<String,Object> map2=new HashMap<>();
        map2.put("src",R.drawable.address);
        map2.put("name","此功能已关闭");
        Map<String,Object> map3=new HashMap<>();
        map3.put("src",R.drawable.coupon);
        map3.put("name","此功能正在维护中");
        Map<String,Object> map4=new HashMap<>();
        map4.put("src",R.drawable.message_center);
        map4.put("name","消息中心");

        Map<String,Object> map6=new HashMap<>();
        map6.put("src",R.drawable.setting);
        map6.put("name","设置");



        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);

        list.add(map6);

        list.add(map);
        MenuAdapter adapter=new MenuAdapter(
                getActivity(),
                R.layout.list_item_layout,list
        );
        ListView listView= view.findViewById(R.id.lv);
        listView.setAdapter(adapter);



        // 点击头像修改个人信息
        ImageView head_image = view.findViewById(R.id.image_user);
        head_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),UpdateActivity.class);

                startActivityForResult(intent,0);
            }
        });
        qianming = view.findViewById(R.id.qianming_user);
        qiandao = view.findViewById(R.id.qiandao_date);
        TextView name = view.findViewById(R.id.name_user);
        //获取用户姓名和个人信息
        Glide.with(getActivity())
                .load(Data.urlImage+Data.user.getImage())
                .into(head_image);


        name.setText(Data.user.getName());

        qianming.setText(Data.user.getQianming());
        qiandao.setText(Data.user.getQiandao()+"天");
        Button qiandaoButton = view.findViewById(R.id.qiandao_user);
        qiandaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long day = (new Date().getTime()-Data.user.getQiandao_date().getTime())/(1000*60*60*24);
                Log.d("时间差：", ""+day);
                if (day>1){
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(Data.url + "UserQianDao?user_id="+Data.user.getId())
                            .build();
                    Call call = okHttpClient.newCall(request);
                    try {
                        call.execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Data.user.setQiandao(Data.user.getQiandao()+1);
                    Data.user.setQiandao_date(new Date());
                }else {
                    Toast.makeText(getActivity(),"请明天再来！",Toast.LENGTH_SHORT).show();
                }
                qiandao.setText(Data.user.getQiandao()+"天");
            }
        });



        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        qianming.setText(Data.user.getQianming());
    }
}
