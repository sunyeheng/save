package com.example.lenovo.myapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;
import java.lang.String;
import com.example.lenovo.myapplication.User;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/8.
 */

public class StartActivty extends Activity {
	private static final int REQUEST_CODE = 1;
	private static final int REQUEST_PERMISSION = 2;
	private OkHttpClient okHttp;
	private static final String TAG = "app";
	private EditText username;
	private EditText password;
	private MyVideo myVideo;
	private String userString;
	private TextView Business;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout);
		Business=findViewById(R.id.enter_business_login);
		Business.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(StartActivty.this,LoginActivity.class);
				startActivity(intent);
			}
		});
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		//视频
		myVideo = (MyVideo) findViewById(R.id.myvideo);
		initView();
		username = findViewById(R.id.username);
		password = findViewById(R.id.password);

		Button subBtn1 = findViewById(R.id.subBtn1);
		subBtn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				OkHttpClient okHttpClient = new OkHttpClient();
				Request request = new Request.Builder()
						.url(Data.url + "userLogin?name=" + username.getText().toString() + "&password=" + password.getText().toString())
						.build();
				Call call = okHttpClient.newCall(request);
				try {
					Response response = call.execute();
					userString = response.body().string();
					Log.e("userLogin", userString.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
				Gson gson = new Gson();
				Type type=new TypeToken<User>(){}.getType();
				Data.user=gson.fromJson(userString,type);
				if(Data.user!=null && Data.user.getName().equals(username.getText().toString())){
					Intent intent=new Intent(StartActivty.this,MainActivity.class);
					intent.putExtra("id",Data.user.getId());
					startActivity(intent);
				}else {
					username.setText("");
					password.setText("");
					Toast.makeText(StartActivty.this,"please try again",Toast.LENGTH_SHORT).show();
				}
			}
		});
		Button subBtn2 = findViewById(R.id.subBtn2);
		subBtn2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(StartActivty.this,RegisterActivty.class);
				startActivity(intent);
			}
		});
	}
	public void initView(){
		myVideo=(MyVideo) findViewById(R.id.myvideo);
		//播放路径
		myVideo.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.guide));
		//播放
		myVideo.start();
		//循环播放
		myVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mediaPlayer) {
				myVideo.start();
			}
		});
	}
	@Override
	protected void onRestart() {
		//返回重新加载
		initView();
		super.onRestart();
	}


	@Override
	protected void onStop() {
		//防止锁屏或者弹出的时候，音乐在播放
		myVideo.stopPlayback();
		super.onStop();
	}
}


