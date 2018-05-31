package com.example.lenovo.myapplication;

/**
 * Created by lenovo on 2018/5/24.
 */

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dingin on 2018/3/19.
 */

public class follow_fragment extends Fragment {

    private List <String> images = new ArrayList<String>();

    private View view;
    // 单例(方法二)
    private static follow_fragment fa;
    public static follow_fragment getFragmentA() {
        if (fa == null) {
            fa = new follow_fragment();
        }
        return fa;
    }

    String businessListStr;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_follow,container,false);


        //轮播

        //初始化
        images.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3643251844,1782349203&fm=27&gp=0.jpg");
        images.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3643251844,1782349203&fm=27&gp=0.jpg");
        images.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3643251844,1782349203&fm=27&gp=0.jpg");
        images.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3643251844,1782349203&fm=27&gp=0.jpg");

        Banner banner = (Banner) view.findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        banner.start();




        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(Data.url + "Guanzhu_index?user_id="+Data.user.getId())
                .build();
        Call call = okHttpClient.newCall(request);

        Response response = null;
        try {
            response = call.execute();
            businessListStr = response.body().string();
            Log.e("businessListStr",businessListStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Business>>(){}.getType();
        List<Business> businessList = gson.fromJson(businessListStr, type);
        Data.businessList_data = businessList;
        FollowAdapter businessListAdapter = new FollowAdapter(getActivity(), businessList);

        ListView listView = view.findViewById(R.id.lv_follow_list);
        listView.setAdapter(businessListAdapter);


        return view;

    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
