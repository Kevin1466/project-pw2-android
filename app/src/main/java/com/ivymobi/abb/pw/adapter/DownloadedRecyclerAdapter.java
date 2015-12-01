package com.ivymobi.abb.pw.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.beans.File;
import com.ivymobi.abb.pw.listener.OnFolderRecyclerListener;
import com.ivymobi.abb.pw.listener.OnItemRecyclerListener;
import com.ivymobi.abb.pw.listener.OnLocalItemRecyclerListener;
import com.ivymobi.abb.pw.util.PreferenceUtil;

import java.util.List;

import cn.trinea.android.common.service.impl.ImageCache;
import cn.trinea.android.common.util.CacheManager;

public class DownloadedRecyclerAdapter extends RecyclerView.Adapter<DownloadedRecyclerAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<File> mData;
    private OnLocalItemRecyclerListener mListener;
    private static final ImageCache IMAGE_CACHE = CacheManager.getImageCache();
    private String language;

    public DownloadedRecyclerAdapter(Context context, List<File> data, OnLocalItemRecyclerListener listener) {
        mInflater = LayoutInflater.from(context);
        mData = data;
        mListener = listener;
        PreferenceUtil.init(context);
        language = PreferenceUtil.getString("language", "Chinese");
    }

    @Override
    public DownloadedRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(mInflater.inflate(R.layout.item_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

        File file = mData.get(i);

        if (language.equals("English")) {
            viewHolder.nameTextView.setText(file.getEnTitle());
        } else {
            viewHolder.nameTextView.setText(file.getTitle());
        }

        if (!file.isDownload()) {
            viewHolder.ivDownloaded.setVisibility(View.GONE);
        } else {
            viewHolder.ivDownloaded.setVisibility(View.VISIBLE);
        }

        IMAGE_CACHE.setCacheFolder(mInflater.getContext().getExternalCacheDir() + "/images");
        IMAGE_CACHE.get(file.getThumbnail(), viewHolder.itemImageView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemRecyclerClicked(v, mData.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        ImageView itemImageView;
        ImageView ivDownloaded;
        ImageView ivLanguage;

        public ViewHolder(View itemView) {
            super(itemView);
            itemImageView = (ImageView) itemView.findViewById(R.id.list_item_image);
            nameTextView = (TextView) itemView.findViewById(R.id.list_item_text);
            ivDownloaded = (ImageView) itemView.findViewById(R.id.iv_downloaded);
            ivLanguage = (ImageView) itemView.findViewById(R.id.iv_language);
        }
    }
}