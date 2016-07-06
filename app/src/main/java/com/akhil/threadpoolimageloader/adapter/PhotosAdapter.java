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
public class PhotosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int PHOTO_ITEM = 0;
    private static final int LOADING_MORE_RESULTS = 1;
    private List<FlickrPhotoBean> mPhotoBeanList;
    private ImageLoader mImageLoader;
    private boolean mIsFetchingMore;

    public PhotosAdapter(List<FlickrPhotoBean> photoBeanList, ImageLoader imageLoader) {
        mPhotoBeanList = photoBeanList;
        mImageLoader = imageLoader;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case PHOTO_ITEM:
                return new PhotoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_adapter, parent, false), mImageLoader);
            case LOADING_MORE_RESULTS:
                return new LoadingMoreViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_more, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PhotoViewHolder) {
            ((PhotoViewHolder) holder).bind(mPhotoBeanList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mPhotoBeanList == null ? 0 : mIsFetchingMore ? mPhotoBeanList.size() + 1 : mPhotoBeanList.size();
    }

    public void setIsFetchingMore(boolean isFetchingMore) {
        if (mIsFetchingMore == isFetchingMore) {
            return;
        }
        mIsFetchingMore = isFetchingMore;
        if (mIsFetchingMore) {
            notifyItemInserted(getItemCount());
        } else {
            notifyItemRemoved(getItemCount());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mPhotoBeanList.size()) {
            return PHOTO_ITEM;
        } else if (position == mPhotoBeanList.size()) {
            return LOADING_MORE_RESULTS;
        }
        return RecyclerView.NO_POSITION;
    }
}
