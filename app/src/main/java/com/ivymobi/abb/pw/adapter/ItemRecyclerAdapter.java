package com.ivymobi.abb.pw.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.beans.File;
import com.ivymobi.abb.pw.listener.OnItemRecyclerListener;
import com.ivymobi.abb.pw.listener.OnMenuItemClickListener;
import com.ivymobi.abb.pw.util.PreferenceUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<File> mData;
    private OnItemRecyclerListener mListener;
    private OnMenuItemClickListener onMenuItemClickListener;
    private String language;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public ItemRecyclerAdapter(Context context, List<File> data, OnItemRecyclerListener listener) {
        mInflater = LayoutInflater.from(context);
        mData = data;
        mListener = listener;
        PreferenceUtil.init(context);
        language = PreferenceUtil.getString("language", "Chinese");
    }

    @Override
    public ItemRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(mInflater.inflate(R.layout.item_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

        File file = mData.get(i);

        if (language.equals("English")) {
            viewHolder.nameTextView.setText(file.getTitle());
        } else {
            viewHolder.nameTextView.setText(file.getEnTitle());
        }

        if (!file.isDownload()) {
            viewHolder.ivDownloaded.setVisibility(View.GONE);
        } else {
            viewHolder.ivDownloaded.setVisibility(View.VISIBLE);
        }

        viewHolder.file = file;

        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).build();
        imageLoader.displayImage(file.getThumbnail(), viewHolder.itemImageView, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                viewHolder.progressBar.setVisibility(View.VISIBLE);
                viewHolder.itemImageView.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                viewHolder.progressBar.setVisibility(View.GONE);
                viewHolder.itemImageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemRecyclerClicked(v, i);
            }
        });
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        TextView nameTextView;
        ImageView itemImageView;
        ImageView ivDownloaded;
        ImageView ivLanguage;
        ProgressBar progressBar;
        File file;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.list_item_text);
            itemImageView = (ImageView) itemView.findViewById(R.id.list_item_image);
            ivDownloaded = (ImageView) itemView.findViewById(R.id.iv_downloaded);
            ivLanguage = (ImageView) itemView.findViewById(R.id.iv_language);
            progressBar = (ProgressBar) itemView.findViewById(R.id.list_item_progress_bar);

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (onMenuItemClickListener != null) {
                onMenuItemClickListener.onMenuItemClick(item, file);
            }

            return false;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle(R.string.select_the_action);
            MenuItem menuItem1 = menu.add(0, 0, 0, R.string.action_download);
            MenuItem menuItem2 = menu.add(0, 1, 0, R.string.action_share);
            MenuItem menuItem3 = menu.add(0, 2, 0, R.string.action_collect);

            menuItem1.setOnMenuItemClickListener(this);
            menuItem2.setOnMenuItemClickListener(this);
            menuItem3.setOnMenuItemClickListener(this);
        }
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }
}