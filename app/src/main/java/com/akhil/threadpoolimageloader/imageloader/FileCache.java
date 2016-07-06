package com.akhil.threadpoolimageloader.imageloader;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by akhil on 04/07/16.
 */
public class FileCache {

    private File cacheDir;

    public FileCache(Context context) {
        if (isExternalStorageWritable()) {
            cacheDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        } else {
            cacheDir = context.getFilesDir();
        }
        if (!cacheDir.exists())
            cacheDir.mkdirs();
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    public File getFile(String url) {
        String filename = String.valueOf(url.hashCode());
        File file = new File(cacheDir, filename);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;

    }

    public void copyStream(InputStream is, OutputStream os) {
        final int bufferSize = 1024 * 2;
        try {
            byte[] bytes = new byte[bufferSize];
            int count = is.read(bytes, 0, bufferSize);
            while (count != -1) {
                os.write(bytes, 0, count);
                count = is.read(bytes, 0, bufferSize);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        File[] files = cacheDir.listFiles();
        if (files == null)
            return;
        for (File f : files)
            f.delete();
    }

}