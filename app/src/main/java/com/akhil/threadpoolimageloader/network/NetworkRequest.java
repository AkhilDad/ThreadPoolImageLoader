package com.akhil.threadpoolimageloader.network;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by akhil on 03/07/16.
 */
public class NetworkRequest extends AsyncTask<Void, Void, ResponseBean> {

    private static final int TIMEOUT_MILLIS = 30 * 1000; // 30 seconds
    private NetworkResponseListener mNetworkResponseListener;
    private RequestBean mRequestBean;
    public NetworkRequest(RequestBean requestBean,NetworkResponseListener networkResponseListener) {
        mRequestBean = requestBean;
        mNetworkResponseListener = networkResponseListener;
    }

    @Override
    protected ResponseBean doInBackground(Void... params) {
        String url = mRequestBean.getUrl();
        ResponseBean responseBean = new ResponseBean();
        HttpURLConnection respObj = sendGetRequest(url);
        InputStream inputStream;
        if (respObj != null) {
            try {
                int statusCode = respObj.getResponseCode();
                responseBean.setResponseCode(statusCode);
                if (statusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = respObj.getInputStream();
                    if (inputStream != null) {
                        responseBean = processResponse(NetworkUtils.readAsString(inputStream),responseBean.getClass());
                        inputStream.close();
                    }

                } else {
                    responseBean.setResponseCode(statusCode);
                    inputStream = respObj.getErrorStream();
                    if (inputStream != null) {
                        responseBean.setErrorString(NetworkUtils.readAsString(inputStream));
                        inputStream.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                responseBean.setResponseCode(-1);
                responseBean.setErrorString("No Internet");
            }

        }
        return responseBean;
    }

    @Override
    protected void onPostExecute(ResponseBean responseBean) {
        super.onPostExecute(responseBean);
        mNetworkResponseListener.onResponse(responseBean);
    }

    private <T> T processResponse(String response, Class<T> typeOfObject) {
        response = response.replace("jsonFlickrApi(", "");
        response = response.substring(0,response.length()-1);
        return new Gson().fromJson(response, typeOfObject);
    }

    public static HttpURLConnection sendGetRequest(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) (new URL(url).openConnection());
            connection.setConnectTimeout(TIMEOUT_MILLIS);
            connection.setReadTimeout(TIMEOUT_MILLIS);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-type", "application/json");
            return connection;

        }  catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
