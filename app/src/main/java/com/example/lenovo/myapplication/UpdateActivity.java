package com.example.lenovo.myapplication;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdateActivity extends Activity {
    ImageView update_image;
    EditText name,newQianming;
    private String qianming;
    RadioButton radioButton;
    String sex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_update);

        name = findViewById(R.id.new_name);
        newQianming = findViewById(R.id.new_qianming);
        update_image = findViewById(R.id.update_touxiang);
        Glide.with(UpdateActivity.this)
                .load(Data.urlImage+Data.user.getImage())
                .into(update_image);


        final RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton)findViewById(checkedId);
                sex = (String) radioButton.getText();
                Log.e("radioButton", (String) radioButton.getText());
                OkHttpClient okHttpClientsex = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(Data.url+"GengGaiSex?user_id="+Data.user.getId()+"&sex="+sex)
                        .build();
                Call call = okHttpClientsex.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        Log.e("onFailure","修改Failure");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.e("sex","修改sex");
                    }
                });
            }
        });
        name.setHint(Data.user.getName());
        newQianming.setHint(Data.user.getQianming());
        Button baocun = findViewById(R.id.baocun);
        baocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                qianming = newQianming.getText().toString();



                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(Data.url+"GengGaiQianMing?user_id="+Data.user.getId()+"&qianming="+qianming)
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        Log.e("onFailure","修改Failure");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.e("onResponse","修改成功");



                    }
                });

                Data.user.setQianming(qianming);
                Toast toast =Toast.makeText(UpdateActivity.this,"修改成功",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM,0,0);
                toast.show();
                finish();
            }
        });
        ImageView update_back= findViewById(R.id.update_back);

        update_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateActivity.this.finish();
            }
        });




    }
}
