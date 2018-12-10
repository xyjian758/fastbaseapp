package app.qingtingfm.com.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;

public class GlideSimpleTarget implements Target<Bitmap> {
    @Override
    public void onLoadStarted(Drawable placeholder) {
        LogJoneUtil.d("当前专辑的图片 onLoadStarted <<thread>>"+Thread.currentThread().getName());
    }

    @Override
    public void onLoadCleared(Drawable placeholder) {
        LogJoneUtil.d("当前专辑的图片 onLoadCleared");
    }

    @Override
    public void getSize(SizeReadyCallback cb) {
        LogJoneUtil.d("当前专辑的图片 getSize");
    }

    @Override
    public void setRequest(Request request) {
        LogJoneUtil.d("当前专辑的图片 setRequest");
    }

    @Override
    public void onLoadFailed(Exception e, Drawable errorDrawable) {
        LogJoneUtil.d("当前专辑的图片 onLoadFailed");
    }

    @Override
    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
        LogJoneUtil.d("当前专辑的图片 onResourceReady");
    }

    @Override
    public Request getRequest() {
        LogJoneUtil.d("当前专辑的图片 getRequest");
        return null;
    }

    @Override
    public void onStart() {
        LogJoneUtil.d("当前专辑的图片 onStart");
    }

    @Override
    public void onStop() {
        LogJoneUtil.d("当前专辑的图片 onStop");
    }

    @Override
    public void onDestroy() {
        LogJoneUtil.d("当前专辑的图片 onDestroy");
    }
}
