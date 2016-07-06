package com.akhil.threadpoolimageloader.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.akhil.threadpoolimageloader.R;
import com.akhil.threadpoolimageloader.imageloader.ImageLoader;
import com.akhil.threadpoolimageloader.imageloader.ImageLoadingTask;
import com.akhil.threadpoolimageloader.model.FlickrPhotoBean;


/**
 * Created by akhil on 03/07/16.
 */
public class PhotoViewHolder extends RecyclerView.ViewHolder {

    private int mWidthHeight = 300;

    private final ImageLoader mImageLoader;

    private final TextView mTitleTV;

    private final ImageView mPhotoIV;

    public PhotoViewHolder(View itemView, ImageLoader imageLoader) {
        super(itemView);
        mImageLoader = imageLoader;
        mPhotoIV = (ImageView) itemView.findViewById(R.id.iv_photo);
        mTitleTV = (TextView) itemView.findViewById(R.id.tv_photo_title);
        mWidthHeight  = itemView.getContext().getResources().getDimensionPixelOffset(R.dimen.image_width_height);
    }

    public void bind(FlickrPhotoBean flickrPhotoBean) {
        mPhotoIV.setImageResource(R.drawable.progress_image);
        final String imageUrl = flickrPhotoBean.getImageUrl();
        final String title = flickrPhotoBean.getTitle();
        mTitleTV.setText(title != null ? title : "");
        if (imageUrl != null) {
            mImageLoader.queueTask(new ImageLoadingTask(imageUrl, mWidthHeight, mWidthHeight, mPhotoIV));
        }
    }
}
