package com.nshane.avatar.utils;

import android.widget.Toast;

import com.nshane.avatar.AvatarApplication;



public class ToastUtil {

    private static Toast mToast;

    public static void showToast(String text) {
        if(AvatarApplication.context==null){
            LogUtil.e("AvatarApplication.context==null");
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(AvatarApplication.context, text, Toast.LENGTH_SHORT);
        }
        mToast.setText(text);
        mToast.show();
    }

    public static void showToast(int text) {
        if(AvatarApplication.context==null){
            LogUtil.e("SeaBottleApplication.context==null");
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(AvatarApplication.context, AvatarApplication.context.getString(text), Toast.LENGTH_SHORT);
        }
        mToast.setText(AvatarApplication.context.getString(text));
        mToast.show();
    }
}
