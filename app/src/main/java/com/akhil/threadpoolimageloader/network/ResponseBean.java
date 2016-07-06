package com.akhil.threadpoolimageloader.network;

import com.akhil.threadpoolimageloader.model.FlickrResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akhil on 03/07/16.
 */
public class ResponseBean {

    private int mResponseCode;
    private String mErrorString;

    @SerializedName("photos")
    private FlickrResponse mResults;

    public int getResponseCode() {
        return mResponseCode;
    }

    public void setResponseCode(int responseCode) {
        mResponseCode = responseCode;
    }

    public String getErrorString() {
        return mErrorString;
    }

    public void setErrorString(String errorString) {
        mErrorString = errorString;
    }

    public FlickrResponse getResults() {
        return mResults;
    }

    public void setResults(FlickrResponse results) {
        mResults = results;
    }
}
