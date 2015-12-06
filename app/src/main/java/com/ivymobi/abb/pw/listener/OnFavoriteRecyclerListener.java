package com.ivymobi.abb.pw.listener;

import android.view.View;

import com.ivymobi.abb.pw.beans.Collection;

public interface OnFavoriteRecyclerListener {
    void onItemRecyclerClicked(View v, Collection collection);
    void onDeleteImageClicked(View v, int i);
    void onDeleteTvClicked(View v, Collection collection,String newName);
}
