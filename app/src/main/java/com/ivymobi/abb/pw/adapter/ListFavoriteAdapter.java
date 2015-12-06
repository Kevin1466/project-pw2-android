package com.ivymobi.abb.pw.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

        viewHolder.nameTextView.setText(mData.get(i).getName());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemRecyclerClicked(v, mData.get(i));
            }
        });

        viewHolder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDeleteImageClicked(v, i);
            }
        });

        viewHolder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDeleteTvClicked(v, mData.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateItems(List<Collection> data) {
        this.mData = data;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public EditText nameTextView;
        public ImageView deleteImageView;
        public ImageView arrowImageView;
        public TextView deletebtn;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (EditText) itemView.findViewById(R.id.list_item_text);
            deleteImageView = (ImageView) itemView.findViewById(R.id.list_item_image);
            arrowImageView = (ImageView) itemView.findViewById(R.id.list_item_arrow_image);
            deletebtn = (TextView) itemView.findViewById(R.id.delete_button);
        }
    }

    public void SwitchToEditStyle(RecyclerView.ViewHolder holder, Context context) {

        ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.itemView.setClickable(false);
        viewHolder.deleteImageView.setVisibility(View.VISIBLE);
        viewHolder.arrowImageView.setVisibility(View.GONE);

        viewHolder.nameTextView.setEnabled(true);
        viewHolder.nameTextView.setFocusableInTouchMode(true);
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.bg_edittext);
        viewHolder.nameTextView.setBackground(drawable);

    }

    public void SwitchToNormalStyle(RecyclerView.ViewHolder holder, Context context) {

        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.itemView.setClickable(true);

        viewHolder.deleteImageView.setVisibility(View.GONE);
        viewHolder.arrowImageView.setVisibility(View.VISIBLE);

        viewHolder.nameTextView.setEnabled(false);
        viewHolder.nameTextView.setFocusableInTouchMode(false);
        Drawable drawable = ContextCompat.getDrawable(context, R.color.white);
        viewHolder.nameTextView.setBackground(drawable);

        viewHolder.deletebtn.setVisibility(View.GONE);
    }

    public void deleteImageClicked(RecyclerView.ViewHolder holder, Context context, boolean flag) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (flag == false) {
            viewHolder.deletebtn.setVisibility(View.GONE);
            return;
        }
        int vi = viewHolder.deletebtn.getVisibility();
        if (View.GONE == vi) {
            viewHolder.deletebtn.setVisibility(View.VISIBLE);
        } else {
            viewHolder.deletebtn.setVisibility(View.GONE);
        }
    }

    public void saveNewName(RecyclerView.ViewHolder holder,
                            Context context, Collection collection) {
        ViewHolder viewHolder = (ViewHolder) holder;
        String newName = viewHolder.nameTextView.getEditableText().toString();
        collection.setName(newName);
        collection.save();
    }
}