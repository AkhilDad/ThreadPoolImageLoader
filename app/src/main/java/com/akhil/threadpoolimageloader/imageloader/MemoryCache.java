package com.akhil.threadpoolimageloader.imageloader;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by akhil on 04/07/16.
 */
public class MemoryCache {

    public static final int PHOTO_SIZE_IN_MB = 1024 * 1024 * 4 ; //1/2 MB's and lets assume 2 mb for each photo
    // of memory available
    private LruCache<String,Bitmap > mLruCache;
    public MemoryCache(){
        final int min = (int) Math.max(Math.min(Runtime.getRuntime().maxMemory() / PHOTO_SIZE_IN_MB, 20), 8);
        mLruCache = new LruCache<>(min);
    }


    public Bitmap get(String url){
        return mLruCache.get(url);
    }

    public void put(String url, Bitmap bitmap){
        mLruCache.put(url, bitmap);
    }

    public void clear() {
        mLruCache.evictAll();
    }
}