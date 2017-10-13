package com.nshane.avatar;

import android.app.Application;
import android.content.Context;

import rain.coder.photopicker.PhotoPick;

/**
 * Created by Administrator on 2017/5/3 0003.
 */
public class AvatarApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        PhotoPick.init(getApplicationContext());
    }
}
