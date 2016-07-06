package com.akhil.threadpoolimageloader.view;

import com.akhil.threadpoolimageloader.model.FlickrPhotoBean;

import java.util.List;

/**
 * Created by akhil on 03/07/16.
 */
public interface View {
    void presentPhotos(List<FlickrPhotoBean> flickrPhotoBeanList);
    void showProgress(boolean isShowProgress);
    void showError(String error);
}
