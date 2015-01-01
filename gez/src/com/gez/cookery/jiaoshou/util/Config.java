package com.gez.cookery.jiaoshou.util;

import android.os.Environment;

import java.io.File;

import com.gez.cookery.jiaoshou.activity.ActivityGlobal;

public final class Config {
    public static final String ROOT = getWritableFolder() + "/.cookery.jiaoshou/";
    public static final String IMAGE_CACHE_DIR = ROOT + ".IMAGE/";

    private static String getWritableFolder() {
        final File directory = Environment.getExternalStorageDirectory();
        if (!(directory.isDirectory() && directory.canWrite())) {
            return ActivityGlobal.getContext().getFilesDir().getAbsolutePath() + "/";
        }
        return directory.getAbsolutePath() + "/";
    }

    static {
        File dir = new File(IMAGE_CACHE_DIR);
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
    }
}
