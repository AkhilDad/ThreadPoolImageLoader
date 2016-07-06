package com.akhil.threadpoolimageloader.presenter;

import android.content.res.Resources;

import com.akhil.threadpoolimageloader.R;
import com.akhil.threadpoolimageloader.model.FlickrResponse;
import com.akhil.threadpoolimageloader.network.NetworkRequest;
import com.akhil.threadpoolimageloader.network.RequestBean;
import com.akhil.threadpoolimageloader.view.View;

/**
 * Created by akhil on 03/07/16.
 */
public class Presenter {

    public static final String FLICKR_PHOTO_URL = "https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&format=json&extras=url_n&api_key=";
    private View mView;
    private Resources mResources;

    public Presenter(View view, Resources resources) {
        mView = view;
        mResources = resources;
    }

    public void fetchPhotos() {
        mView.showProgress(true);
        final RequestBean requestBean = new RequestBean(FLICKR_PHOTO_URL +mResources.getString(R.string.api_key));
        NetworkRequest networkRequest = new NetworkRequest(requestBean, responseBean -> {
            mView.showProgress(false);
            final FlickrResponse flickrResponse = responseBean.getResults();
            if (flickrResponse != null) {
                mView.presentPhotos(flickrResponse.getPhotoBeanList());
            }
        });
        networkRequest.execute();
    }
}
