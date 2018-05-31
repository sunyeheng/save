package com.example.lenovo.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lenovo on 2018/5/23.
 */

public class BuinessRegister extends Activity {
    private String businessStr;
    private static final int REQUEST_CODE = 1;
    private static final int RESULT_OK = -1;
    private static final int REQUEST_PERMISSION = 2;
    private static final String TAG = "app";
    private OkHttpClient okHttpClient;
    private TextView business_register_back;
    private ImageView business_image;
    private EditText business_name;
    private EditText business_name_1;
    private EditText business_password;
    private EditText business_comfirm_password;
    private EditText business_phone;
    private EditText business_address;
    private Button business_register;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buiness_registered_layout);
        okHttpClient=new OkHttpClient();
        business_register_back=findViewById(R.id.Buiness_register_back);
        business_image=findViewById(R.id.topImage);
        business_name=findViewById(R.id.Buiness_Register_username);
        business_name_1=findViewById(R.id.Buiness_name_1);
        business_password=findViewById(R.id.Buiness_Register_password);
        business_comfirm_password=findViewById(R.id.Buiness_confirm_password);
        business_phone=findViewById(R.id.Buiness_phone);
        business_address=findViewById(R.id.Buiness_address);
        business_register=findViewById(R.id.Buiness_register);
        business_comfirm_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (business_comfirm_password.getText().toString()
                            .equals(business_password.getText().toString())) {
                        Toast.makeText(BuinessRegister.this,"密码一致",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(BuinessRegister.this,"密码不相同或未输入，请重新输入！",Toast.LENGTH_SHORT).show();
                        business_comfirm_password.setText("");
                    }
                }
            }
        });
        //返回登录页
        business_register_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuinessRegister.this.finish();
            }
        });
        //上传头像
        business_image.setFocusable(true);
        business_image.setFocusableInTouchMode(true);
        business_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                business_name.setFocusableInTouchMode(false);

                //打开手机相册，动态申请权限；
                ActivityCompat.requestPermissions(BuinessRegister.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,},REQUEST_PERMISSION);
                Log.e("**************","*****************");

            }
        });
        business_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=business_name.getText().toString();
                String password=business_password.getText().toString();
                Request request=new Request.Builder().url(Data.url+"businessRegister?name="
                        +business_name.getText().toString()
                        +"&password="+business_password.getText().toString()+"&Image="+business_name.getText().toString()+".jpg" +
                        "" +
                        "" +
                        "")
                        .get().build();
                Call call=okHttpClient.newCall(request);
                try {
                    Response response=call.execute();
                    businessStr=response.body().string();
                    Log.e("businessRegieter",businessStr);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Gson gson=new Gson();
                Type type= new TypeToken<Business>(){}.getType();
                Business business=gson.fromJson(businessStr,type);
                if (business.getName().equals(username)){
                    Toast.makeText(BuinessRegister.this,"注册成功",
                            Toast.LENGTH_SHORT).show();
                    BuinessRegister.this.finish();
                }
            }
        });
    }
    /*
    上传方法
     */
    private void doUploadFile(File file)  {
        EditText business_name=findViewById(R.id.Buiness_Register_username);
        Toast.makeText(this,"上传成功",Toast.LENGTH_SHORT).show();
        RequestBody requestBody=RequestBody.create(MediaType.parse("image/*"),file);
        String imgname= null;
        try {
            imgname = URLDecoder.decode(business_name.getText().toString(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Request request = new Request.Builder().url(Data.url+"UploadBusinessFile?name="+imgname)
                .post(requestBody).build();
        Call call=okHttpClient.newCall(request);
        Response response = null;
        try {
            response = call.execute();
            Log.e("business_image",response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //相册界面返回之后回调方法

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取照片
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            //获取照片
            Uri uri = data.getData();
            Cursor cursor = getContentResolver().query(uri,null,null,null,null);
            cursor.moveToFirst();
            String column = MediaStore.Images.Media.DATA;
            int columIndex = cursor.getColumnIndex(column);
            String path = cursor.getString(columIndex);
            File file = new File(path);
            Glide.with(this).load(file).into(business_image);
            doUploadFile(file);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //打开手机相册
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        Log.e("text","text4");
        startActivityForResult(intent,REQUEST_CODE);
    }
}
