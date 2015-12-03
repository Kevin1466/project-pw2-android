package com.ivymobi.abb.pw.listener;

import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.activity.CollectionActivity_;
import com.ivymobi.abb.pw.beans.File;
import com.ivymobi.abb.pw.fragment.DownloadedFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.ThinDownloadManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;


public class OnSwipeMenuItemClickListener implements SwipeMenuListView.OnMenuItemClickListener {
    List<File> files;
    DownloadedFragment fragment;

    public OnSwipeMenuItemClickListener(DownloadedFragment fragment, List<File> files) {
        this.files = files;
        this.fragment = fragment;
    }

    @Override
    public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
        File file = files.get(position);

        switch (index) {
            case 0:
                if (!file.isDownload()) {
                    downloadFile(file);
                }

                break;
            case 1:
                shareFile(file);
                break;

            case 2:
                collectionFile(file);
                break;
        }

        return false;
    }

    private void shareFile(final File file) {
        final AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://yangbentong.com/api/7a94881a-df96-429d-9e01-dece4f46fee2/storage/" + file.getUuid(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {

                    String fileUrl = response.getString("url");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void collectionFile(final File file) {
        Intent intent = new Intent(fragment.getContext(), CollectionActivity_.class);
        intent.putExtra("uuid", file.getUuid());
        fragment.startActivity(intent);
    }

    private void downloadFile(final File file) {
        final AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://yangbentong.com/api/7a94881a-df96-429d-9e01-dece4f46fee2/storage/" + file.getUuid(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {

                    String fileUrl = response.getString("url");
                    final String fileName = response.getString("file_key");

                    Uri downloadUri = Uri.parse(fileUrl);
                    Uri destinationUri = Uri.parse(fragment.getContext().getExternalCacheDir().toString() + "/" + fileName);
                    DownloadRequest downloadRequest = new DownloadRequest(downloadUri)
                            .setRetryPolicy(new DefaultRetryPolicy())
                            .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.HIGH)
                            .setDownloadListener(new DownloadStatusListener() {

                                @Override
                                public void onDownloadComplete(int id) {
                                    Toast.makeText(fragment.getContext(), R.string.download_success, Toast.LENGTH_SHORT).show();

                                    file.setLocalPath(fileName);
                                    file.save();

                                    fragment.files = files;
                                    fragment.refreshData();
                                }

                                @Override
                                public void onDownloadFailed(int id, int errorCode, String errorMessage) {
                                    Toast.makeText(fragment.getContext(), R.string.download_failed, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onProgress(int id, long totalBytes, long downlaodedBytes, int progress) {
                                }
                            });

                    ThinDownloadManager downloadManager = new ThinDownloadManager();
                    int downloadId = downloadManager.add(downloadRequest);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
