package com.ivymobi.abb.pw.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.beans.File;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;
import java.util.Locale;

public class ListItemAdapter extends BaseAdapter {

    private Context context;
    private List<File> beans;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Locale locale;

    public ListItemAdapter(Context context, List<File> files) {
        this.beans = files;
        this.context = context;
        this.locale = context.getResources().getConfiguration().locale;
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public Object getItem(int position) {
        return beans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        File file = beans.get(position);

        System.out.println(file.getTitle());

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (locale == Locale.ENGLISH) {
            viewHolder.nameTextView.setText(file.getEnTitle());
        } else {
            viewHolder.nameTextView.setText(file.getTitle());
        }

        if (!file.isDownload()) {
            viewHolder.ivDownloaded.setVisibility(View.GONE);
        } else {
            viewHolder.ivDownloaded.setVisibility(View.VISIBLE);
        }

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

        return convertView;
    }

    class ViewHolder {
        TextView nameTextView;
        ImageView itemImageView;
        ImageView ivDownloaded;
        ImageView ivLanguage;
        ProgressBar progressBar;

        public ViewHolder(View view) {
            nameTextView = (TextView) view.findViewById(R.id.list_item_text);
            itemImageView = (ImageView) view.findViewById(R.id.list_item_image);
            ivDownloaded = (ImageView) view.findViewById(R.id.iv_downloaded);
            ivLanguage = (ImageView) view.findViewById(R.id.iv_language);
            progressBar = (ProgressBar) view.findViewById(R.id.list_item_progress_bar);
        }
    }
}
