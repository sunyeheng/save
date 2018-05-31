package com.example.lenovo.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.lenovo.myapplication.Data.business;

/**
 * Created by lenovo on 2018/5/23.
 */

public class LoginActivity extends Activity {
    private EditText buiness_name;
    private EditText buiness_password;
    private Button buiness_login;
    private Button buiness_register;
    private TextView NotLogin;
    private String userString;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangjiadenglu);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        buiness_name=findViewById(R.id.Buiness_name);
        buiness_password=findViewById(R.id.Buiness_password);
        buiness_login=findViewById(R.id.Buiness_login);
        buiness_register=findViewById(R.id.Buiness_registered);
        NotLogin=findViewById(R.id.not_Login);
        buiness_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OkHttpClient okHttpClient=new OkHttpClient();
                Request request=new Request.Builder()
                        .url(Data.url+"businessLogin?name="
                        +buiness_name.getText().toString()
                        +"&password=" +buiness_password.getText().toString())
                        .build();
                Call call=okHttpClient.newCall(request);
                try {
                    Response response=call.execute();
                    userString=response.body().string();
                    Log.e("userLogin",userString.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Gson gson=new Gson();
                Type type =new TypeToken<Business>(){}.getType();
                business=gson.fromJson(userString,type);
                if (business!=null&&business.getName().equals(buiness_name.getText().toString())){
                    Intent intent=new Intent(LoginActivity.this,BuinessActivity.class);
                    startActivity(intent);
                }else {
                    buiness_name.setText("");
                    buiness_password.setText("");
                    Toast.makeText(LoginActivity.this,"用户名或密码错误，请重新输入！！",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        buiness_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,BuinessRegister.class);
                startActivity(intent);
            }
        });
    }
}
