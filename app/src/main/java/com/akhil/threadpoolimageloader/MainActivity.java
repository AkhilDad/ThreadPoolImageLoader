package com.akhil.threadpoolimageloader;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.akhil.threadpoolimageloader.adapter.PhotosAdapter;
import com.akhil.threadpoolimageloader.imageloader.ImageLoader;
import com.akhil.threadpoolimageloader.model.FlickrPhotoBean;
import com.akhil.threadpoolimageloader.network.NetworkUtils;
import com.akhil.threadpoolimageloader.presenter.Presenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements com.akhil.threadpoolimageloader.view.View {

    private static final int VISIBLE_THRESHOLD = 3;
    private Presenter mPresenter;

    private ProgressDialog mProgressDialog;

    private RecyclerView mPhotosRV;

    private TextView mErrorMsgTV;

    private Button mRetryBtn;

    private List<FlickrPhotoBean> mFlickrPhotoBeanList = new ArrayList<>();
    private PhotosAdapter mAdapter;
    private RecyclerView.OnScrollListener mOnScrollListener;
    private boolean mFetchingMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPhotosRV = (RecyclerView) findViewById(R.id.rv_photos);
        mRetryBtn = (Button) findViewById(R.id.btn_retry);
        mErrorMsgTV = (TextView) findViewById(R.id.tv_error_message);


        mPresenter = new Presenter(this, getResources());
        mPhotosRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new PhotosAdapter(mFlickrPhotoBeanList, ImageLoader.getInstance(getApplicationContext()));
        mPhotosRV.setAdapter(mAdapter);
        mOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = linearLayoutManager.getItemCount();
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!mFetchingMore && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {
                    fetchNextPhotos();
                }
            }
        };
        mPhotosRV.addOnScrollListener(mOnScrollListener);
        fetchPhotos();
        mRetryBtn.setOnClickListener(v -> fetchPhotos());
    }

    private void fetchNextPhotos() {
        if (NetworkUtils.isNetworkConnected(getApplicationContext())) {
            mPresenter.fetchNextPhotos();
        }
    }

    private void fetchPhotos() {
        if (NetworkUtils.isNetworkConnected(getApplicationContext())) {
            mPresenter.fetchPhotos();
        } else {
            showError(getString(R.string.err_no_internet));
        }
    }

    @Override
    public void showFetchingMoreProgress(boolean show) {
        mFetchingMore = show;
        mAdapter.setIsFetchingMore(show);
    }

    @Override
    public void noMorePaginatedData() {
        mPhotosRV.removeOnScrollListener(mOnScrollListener);
        showSnackError(getString(R.string.err_no_more_data));
    }

    @Override
    public void showSnackError(String errorString) {
        Snackbar.make(mPhotosRV, errorString, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void presentPhotos(List<FlickrPhotoBean> flickrPhotoBeanList) {
        mPhotosRV.setVisibility(View.VISIBLE);
        int range = mFlickrPhotoBeanList.size();
        mFlickrPhotoBeanList.addAll(flickrPhotoBeanList);
        mAdapter.notifyItemRangeChanged(range, flickrPhotoBeanList.size());
    }

    @Override
    public void showProgress(boolean isShowProgress) {
        mRetryBtn.setVisibility(View.GONE);
        mErrorMsgTV.setVisibility(View.GONE);
        mPhotosRV.setVisibility(View.GONE);
        if (isShowProgress) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(getString(R.string.text_waiting_message));
            mProgressDialog.show();
        } else {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            mProgressDialog = null;
        }
    }

    @Override
    public void showError(String error) {
        mPhotosRV.setVisibility(View.GONE);
        mRetryBtn.setVisibility(View.VISIBLE);
        mErrorMsgTV.setVisibility(View.VISIBLE);
        mErrorMsgTV.setText(error);
    }
}
