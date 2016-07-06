package com.akhil.threadpoolimageloader.network;

import com.akhil.threadpoolimageloader.model.FlickrResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akhil on 03/07/16.
 */
public class ResponseBean {

    @SerializedName("code")
    private int mResponseCode;

    @SerializedName("message")
    private String mErrorString;

    @SerializedName("stat")
    private String mStatus;

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

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }
}
