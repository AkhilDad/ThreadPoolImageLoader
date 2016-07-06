package com.akhil.threadpoolimageloader.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by akhil on 03/07/16.
 */
public class FlickrPhotoBean {

    @SerializedName("id")
    private String mId;

    @SerializedName("owner")
    private String mOwner;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("width_sq")
    private int mWidth;

    @SerializedName("url_n")
    private String mImageUrl;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getOwner() {
        return mOwner;
    }

    public void setOwner(String owner) {
        mOwner = owner;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
