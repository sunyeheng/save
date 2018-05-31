package com.example.lenovo.myapplication;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.VideoView;

public class MyVideo extends VideoView {
	public MyVideo(Context context) {
		super(context);
	}

	public MyVideo(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyVideo(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//从新写入高度
		int width = getDefaultSize(0, widthMeasureSpec);//得到默认的大小（0，宽度测量规范）
		int height = getDefaultSize(0, heightMeasureSpec);//得到默认的大小（0，高度度测量规范）
		//设置测量尺寸,将高和宽放进去
		setMeasuredDimension(width, height);
	}
	@Override
	public void setOnPreparedListener(MediaPlayer.OnPreparedListener l) {
		super.setOnPreparedListener(l);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}
}
