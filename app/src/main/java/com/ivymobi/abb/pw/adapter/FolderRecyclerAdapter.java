package com.ivymobi.abb.pw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.beans.Catalog;
import com.ivymobi.abb.pw.listener.OnFolderRecyclerListener;
import com.ivymobi.abb.pw.util.PreferenceUtil;

import java.util.ArrayList;
import java.util.Locale;

public class FolderRecyclerAdapter extends RecyclerView.Adapter<FolderRecyclerAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private Catalog mData;
    private OnFolderRecyclerListener mListener;
    private Locale locale;

    public FolderRecyclerAdapter(Context context, Catalog data, OnFolderRecyclerListener listener) {
        mInflater = LayoutInflater.from(context);
        mData = data;
        mListener = listener;

        locale = context.getResources().getConfiguration().locale;
    }

    @Override
    public FolderRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(mInflater.inflate(R.layout.folder_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

        ArrayList<Catalog> children = mData.getChildren();

        if (locale == Locale.ENGLISH) {
            viewHolder.nameTextView.setText(children.get(i).getEnName());
        } else {
            viewHolder.nameTextView.setText(children.get(i).getName());
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFolderRecyclerClicked(v, i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.getChildren().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.list_item_text);
        }
    }
}