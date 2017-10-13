package com.nshane.avatar.utils;

import android.widget.Toast;

import com.nshane.avatar.AvatarApplication;


public class ToastUtil {

    private static Toast mToast;

    public static void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(AvatarApplication.context, text, Toast.LENGTH_SHORT);
        }
        mToast.setText(text);
        mToast.show();
    }
}
