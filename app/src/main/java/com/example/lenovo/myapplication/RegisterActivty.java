package com.example.lenovo.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/8.
 */

public class RegisterActivty extends Activity{
	private static final int REQUEST_CODE = 1;
	private static final int RESULT_OK = -1;
	private static final int REQUEST_PERMISSION = 2;
	private static final String TAG = "app";
	private EditText rusername;
	private EditText rpassword;
	private String Rpassword;
	private String Rusername;
	private Button button;
	private ImageView postImage;
	final OkHttpClient client = new OkHttpClient();

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registerlayout);

		rusername = findViewById(R.id.Rusername);
		rpassword = findViewById(R.id.Rpassword);
        button=findViewById(R.id.register);
		postImage=findViewById(R.id.postImage);
        postImage.setFocusable(true);
        postImage.setFocusable(true);

		postImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//打开手机相册，动态申请权限
              ActivityCompat.requestPermissions(RegisterActivty.this,
                     new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,},REQUEST_PERMISSION);
             Log.e("**************","*****************");

			}

		});
//shangchuan    name   password

        button.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
		        Rusername=rusername.getText().toString();
		        Rpassword=rpassword.getText().toString();
		        postRequest(Rusername,Rpassword);
	        }
        });
	}

	private void  postRequest(String name1,String pwd){
		RequestBody formBody=new FormBody.Builder()
				.add("name",name1)
				.add("password",pwd)
				.build();
		final Request request = new Request.Builder()
				.url(Data.url+"userRegister")
				.post(formBody)
				.build();

		//新建一个线程，用于得到服务器响应的参数
		new Thread(new Runnable() {
			@Override
			public void run() {
				Response response = null;
				try {
					//回调
					response = client.newCall(request).execute();
					if (response.isSuccessful()) {
						Intent intent = new Intent(RegisterActivty.this,StartActivty.class);
						startActivity(intent);

					} else {
						throw new IOException("Unexpected code:" + response);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	/**
	 * 上传文件
	 */
	private void doUploadFile(File file){
		//构建请求体
		final EditText username = findViewById(R.id.Rusername);
		RequestBody requestBody = RequestBody.create(MediaType.parse("images/*"),file);
		Request request = new Request.Builder()
				.url(Data.url+"UploadUserFile?name="+username.getText())
				.post(requestBody)
				.build();
		//创建call对象，并执行请求
		final Call call = client.newCall(request);
		//执行异步请求
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				Log.e(TAG,response.body().string());
			}
		});
	}
	//相册界面返回之后的回调方法
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE){
			//获取照片
			Uri uri = data.getData();
			Cursor cursor = getContentResolver().query(uri,null,null,null,null);
			cursor.moveToFirst();
			String column = MediaStore.Images.Media.DATA;
			int columIndex = cursor.getColumnIndex(column);
			String path = cursor.getString(columIndex);
			File file = new File(path);
			Glide.with(this).load(file).into(postImage);
			doUploadFile(file);
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		Log.e("text","text2");
//        if (requestCode == REQUEST_READ){
//            String path = Environment.getExternalStorageDirectory()+"/2.jpg";
//            File file = new File(path);
//            Log.d("GlideDemo",path);
//            Glide.with(this).load(file).into(imageView);
//            Log.e("text","text3");
		//打开手机相册
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_PICK);
		intent.setType("image/*");
		Log.e("text","text4");
		startActivityForResult(intent,REQUEST_CODE);

		//}

	}



}