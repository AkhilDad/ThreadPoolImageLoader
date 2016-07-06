package com.akhil.threadpoolimageloader.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.akhil.threadpoolimageloader.R;
import com.akhil.threadpoolimageloader.imageloader.ImageLoader;
import com.akhil.threadpoolimageloader.model.FlickrPhotoBean;

import java.util.List;

/**
 * Created by akhil on 03/07/16.
 */
public class PhotosAdapter extends RecyclerView.Adapter<PhotoViewHolder> {

    private List<FlickrPhotoBean> mPhotoBeanList;
    private ImageLoader mImageLoader;

    public PhotosAdapter(List<FlickrPhotoBean> photoBeanList, ImageLoader imageLoader) {
        mPhotoBeanList = photoBeanList;
        mImageLoader = imageLoader;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_adapter, parent, false), mImageLoader);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        holder.bind(mPhotoBeanList.get(position));
    }

    @Override
    public int getItemCount() {
        return mPhotoBeanList != null ? mPhotoBeanList.size() : 0;
    }
}
