package com.nshane.avatar.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by 0 on 2017-7-6.
 */
public class PathConstants {

    public static String getVoicePath(Context context){
        String voicePath = Environment.getExternalStorageDirectory() +"/" + context.getPackageName() + "/" + "voices/";
        checkDirs(voicePath);
        return voicePath;
    }

    public static String getImagePath(Context context){
        String imagePath = Environment.getExternalStorageDirectory() +"/" + context.getPackageName() + "/" + "images/";
        checkDirs(imagePath);
        return imagePath;
    }

    public static String getCompressImagePath(Context context){
        String imagePath = Environment.getExternalStorageDirectory() +"/" + context.getPackageName() + "/" + "images/.thumbnail/";
        checkDirs(imagePath);
        return imagePath;
    }

    public static String getCompressVideoPath(Context context){
        String imagePath = Environment.getExternalStorageDirectory() +"/" + context.getPackageName() + "/" + "video/";
        checkDirs(imagePath);
        return imagePath;
    }

    private static void checkDirs(String paths){
        File f = new File(paths);
        if(!f.exists()){
            f.mkdirs();
        }
    }

    public static String getFileName(String extendName){
        return System.currentTimeMillis()/1000 + extendName;
    }
}
