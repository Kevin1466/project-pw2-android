package com.ivymobi.abb.pw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.beans.Collection;
import com.ivymobi.abb.pw.listener.OnFavoriteRecyclerListener;

import java.util.List;

public class ListFavoriteAdapter extends RecyclerView.Adapter<ListFavoriteAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<Collection> mData;
    private OnFavoriteRecyclerListener mListener;

    public ListFavoriteAdapter(Context context, List<Collection> data, OnFavoriteRecyclerListener listener) {
        mInflater = LayoutInflater.from(context);
        mData = data;
        mListener = listener;
    }

    @Override
    public ListFavoriteAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(mInflater.inflate(R.layout.folder_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

        viewHolder.nameTextView.setText(mData.get(i).getName());

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

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.list_item_text);
        }
    }
}