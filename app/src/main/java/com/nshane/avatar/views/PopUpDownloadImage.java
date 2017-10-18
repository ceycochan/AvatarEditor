package com.nshane.avatar.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.nshane.avatar.R;
import com.nshane.avatar.utils.FileUtil;
import com.nshane.avatar.utils.LogUtil;
import com.nshane.avatar.utils.PathConstants;
import com.nshane.avatar.utils.ToastUtil;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.io.FileNotFoundException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 0 on 2017-9-14.
 */
public class PopUpDownloadImage implements View.OnClickListener {
    Context mContext;
    String path;
    String TAG =PopUpDownloadImage.class.getSimpleName();
    PopupWindow mPopWindow;
    public PopUpDownloadImage(Context context, String path){
        this.mContext = context;
        this.path = path;
    }

    public void show(View v){
        View mPopView = LayoutInflater.from(mContext).inflate(R.layout.layout_popup_saveimage, null);
//        int width = Utils.getScreenWidth(mContext);
//        int height = com.seabottle.utils.Utils.dip2px(mContext,170);
        mPopWindow = new PopupWindow(mPopView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);
        /** set */
        mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        /** 这个很重要 ,获取弹窗的长宽度 */
        mPopView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//                        int popupWidth = mPopView.getMeasuredWidth();
//                        int popupHeight = mPopView.getMeasuredHeight();
        /** 获取父控件的位置 */
//                        int[] location = new int[2];
//                        v.getLocationOnScreen(location);
        /** 显示位置 */
        mPopWindow.setOutsideTouchable(true);
        mPopView.findViewById(R.id.tv_item_1).setOnClickListener(this);
        mPopView.findViewById(R.id.tv_item_2).setOnClickListener(this);
        mPopView.findViewById(R.id.view_extra).setOnClickListener(this);
        mPopWindow.showAtLocation(v, Gravity.CENTER,0,0);
    }

    @Override
    public void onClick(View v) {
        if(R.id.tv_item_2==v.getId()){
        }else if(R.id.view_extra==v.getId()){
        }
        else
        {
            downloadFromGlide();
        }
        if(mPopWindow!=null)
            mPopWindow.dismiss();


    }

    private class SaveSubscriber implements Subscriber<String> {
        @Override
        public void onError(Throwable e) {
//            Log.i(getClass().getSimpleName(), e.toString());
//            Toast.makeText(getApplicationContext(), "保存失败——> " + e.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete() {

        }

        @Override
        public void onSubscribe(Subscription s) {

        }

        @Override
        public void onNext(String s) {
//            Toast.makeText(getApplicationContext(), "保存路径为：-->  " + s, Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileName(){
        String prefix = path.contains(".jpg")?".jpg":".png";
        String result = String.valueOf(path.hashCode()).replace("-","") + prefix;
        return result;
    }
    private void downloadFromGlide(){
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
//                    e.onNext(path);
                File f = Glide.with(mContext)
                        .load(path)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
                String outPath = PathConstants.getImagePath(mContext)+getFileName();
                FileUtil.copyFile(f.getAbsolutePath(),outPath);
                e.onNext(outPath);
//                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String filePath) throws Exception {
                        //这里接收数据项
                        LogUtil.d(TAG,"Consumer accept"+filePath);
                        try {
                            MediaStore.Images.Media.insertImage(mContext.getContentResolver(),filePath, getFileName(), null);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        ToastUtil.showToast(mContext.getString(R.string.image_save_at_s,filePath));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //这里接收onError
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //这里接收onComplete。
                        LogUtil.d(TAG,"Consumer Action");

                    }
                });
    }
}
