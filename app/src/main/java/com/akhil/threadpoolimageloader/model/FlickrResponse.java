package com.akhil.threadpoolimageloader.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by akhil on 03/07/16.
 */
public class FlickrResponse {

    @SerializedName("page")
    private int mPage;

    @SerializedName("pages")
    private int mPages;

    @SerializedName("perpage")
    private int mPerPage;

    @SerializedName("total")
    private long mTotal;

    @SerializedName("photo")
    private List<FlickrPhotoBean> mPhotoBeanList;

    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        mPage = page;
    }

    public int getPages() {
        return mPages;
    }

    public void setPages(int pages) {
        mPages = pages;
    }

    public int getPerPage() {
        return mPerPage;
    }

    public void setPerPage(int perPage) {
        mPerPage = perPage;
    }

    public long getTotal() {
        return mTotal;
    }

    public void setTotal(long total) {
        mTotal = total;
    }

    public List<FlickrPhotoBean> getPhotoBeanList() {
        return mPhotoBeanList;
    }

    public void setPhotoBeanList(List<FlickrPhotoBean> photoBeanList) {
        mPhotoBeanList = photoBeanList;
    }
}
