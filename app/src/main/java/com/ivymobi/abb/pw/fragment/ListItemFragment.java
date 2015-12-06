package com.ivymobi.abb.pw.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.activity.LocalPDFActivity_;
import com.ivymobi.abb.pw.activity.PDFActivity_;
import com.ivymobi.abb.pw.adapter.ListItemAdapter;
import com.ivymobi.abb.pw.beans.CollectionFile;
import com.ivymobi.abb.pw.beans.File;
import com.ivymobi.abb.pw.listener.OnSwipeMenuItemClickListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.androidannotations.annotations.EFragment;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;


@EFragment
public class ListItemFragment extends Fragment {
    private View mView;
    private SwipeMenuListView listView = null;
    public List<File> files;
    public Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_list_item, container, false);

        listView = (SwipeMenuListView) mView.findViewById(R.id.listView);

        if (files == null) {
            files = File.getAllDownloadedFiles();
        }

        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                File file = null;
                if (menu.getViewType() >= 0) {
                    file = files.get(menu.getViewType());
                }

                List<CollectionFile> collectionFiles = CollectionFile.findByFile(file);

                SwipeMenuItem downloadItem = new SwipeMenuItem(getContext());
                downloadItem.setBackground(new ColorDrawable(Color.parseColor("#f2f2f2")));
                downloadItem.setWidth(120);

                if (file != null && file.isDownload()) {
                    downloadItem.setIcon(R.mipmap.icon_download_gray);
                } else {
                    downloadItem.setIcon(R.mipmap.icon_download_blue);
                }
                menu.addMenuItem(downloadItem);

                SwipeMenuItem shareItem = new SwipeMenuItem(getContext());
                shareItem.setBackground(new ColorDrawable(Color.parseColor("#f2f2f2")));
                shareItem.setWidth(120);
                shareItem.setIcon(R.mipmap.icon_share);
                menu.addMenuItem(shareItem);

                SwipeMenuItem favoriteItem = new SwipeMenuItem(getContext());
                favoriteItem.setBackground(new ColorDrawable(Color.parseColor("#f2f2f2")));
                favoriteItem.setWidth(120);

                if (collectionFiles != null && !collectionFiles.isEmpty()) {
                    favoriteItem.setIcon(R.mipmap.icon_heart);
                } else {
                    favoriteItem.setIcon(R.mipmap.icon_heart_empty);
                }
                menu.addMenuItem(favoriteItem);
            }
        };

        listView.setMenuCreator(creator);
        listView.setAdapter(new ListItemAdapter(getContext(), files));
        listView.setOnMenuItemClickListener(new OnSwipeMenuItemClickListener(this, files));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemClicked(position);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        refreshData();
    }

    private void itemClicked(int position) {

        File file = files.get(position);

        if (file.getLocalPath() == null) {
            final AsyncHttpClient client = new AsyncHttpClient();
            client.get("http://yangbentong.com/api/7a94881a-df96-429d-9e01-dece4f46fee2/storage/" + file.getUuid(), new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {

                        String fileUrl = response.getString("url");

                        Intent intent = new Intent(getActivity(), PDFActivity_.class);
                        intent.putExtra("url", fileUrl);

                        startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            Intent intent = new Intent(getActivity(), LocalPDFActivity_.class);
            intent.putExtra("fileName", file.getLocalPath());

            startActivity(intent);
        }
    }

    public void refreshData() {
        listView.setAdapter(new ListItemAdapter(getContext(), files));
        listView.invalidateViews();
    }


}
