package com.akhil.threadpoolimageloader.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by akhil on 04/07/16.
 */
public class ImageLoadingTask implements Runnable {
    private String mUrl;
    private final int mWidth;
    private final int mHeight;
    private SoftReference<ImageView> mImageViewSoftReference;
    private FileCache mFileCache;
    private MemoryCache mMemoryCache;
    private Handler mHandler;
    Bitmap bitmap;

    public ImageLoadingTask(String url, int width, int height, ImageView imageView) {
        mUrl = url;
        mWidth = width;
        mHeight = height;
        imageView.setTag(url);
        mHandler = new Handler();
        mImageViewSoftReference = new SoftReference<>(imageView);
    }

    @Override
    public void run() {

        bitmap = mMemoryCache.get(mUrl);
        if (bitmap == null) {
            final File file = mFileCache.getFile(mUrl);
            if (file != null) {
                try {
                    bitmap = resizeImage(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bitmap == null) {
                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(mUrl).openConnection();
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    OutputStream os = new FileOutputStream(file);
                    mFileCache.copyStream(input, os);
                    os.close();
                    bitmap=resizeImage(file);
                    if (bitmap != null) {
                        mMemoryCache.put(mUrl, bitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        final ImageView imageView = mImageViewSoftReference.get();
        if (imageView != null && mUrl.equals(imageView.getTag())) {
            mHandler.post(() -> {
                if (mUrl.equals(imageView.getTag())) {
                    imageView.setImageBitmap(bitmap);
                }
            });
        }
    }

    private Bitmap resizeImage(File file) throws IOException {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(new FileInputStream(file),null,options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, mWidth,mHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(new FileInputStream(file),null,options);
    }


    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }



    public void setFileCache(FileCache fileCache) {
        mFileCache = fileCache;
    }

    public void seMemoryCache(MemoryCache memoryCache) {
        mMemoryCache = memoryCache;
    }

    public String getUrl() {
        return mUrl;
    }
}
