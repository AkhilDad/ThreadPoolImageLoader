package com.akhil.threadpoolimageloader.imageloader;

import android.content.Context;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by akhil on 04/07/16.
 */
public class ImageLoader {

    private static ImageLoader sImageLoader;
    private static Context mContext;
    private ThreadPoolExecutor mExecutor;
    private boolean mIsMemoryCache;
    private boolean mIsFileCache;
    private FileCache mFileCache;
    private MemoryCache mMemoryCache;

    public static ImageLoader getInstance(Context context) {
        if (sImageLoader == null) {
            sImageLoader = new ImageLoader(context);
        }
        return sImageLoader;
    }

    private ImageLoader(Context context) {
        mContext = context;
        mMemoryCache = new MemoryCache();
        mFileCache =  new FileCache(context);
        final LinkedBlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue<>();
        mExecutor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, linkedBlockingQueue);
    }

    public ImageLoader setIsFileCache(boolean isFileCache) {
        mIsFileCache = isFileCache;
        return sImageLoader;
    }

    public ImageLoader setIsMemoryCache(boolean isMemoryCache) {
        mIsMemoryCache = isMemoryCache;
        return sImageLoader;
    }

    public void queueTask(ImageLoadingTask imageLoadingTask) {
        imageLoadingTask.setFileCache(mFileCache);
        imageLoadingTask.seMemoryCache(mMemoryCache);
        mExecutor.execute(imageLoadingTask);
    }
}
