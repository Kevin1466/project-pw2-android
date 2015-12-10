package com.ivymobi.abb.pw.listener;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuLayout;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.activity.CollectionActivity_;
import com.ivymobi.abb.pw.activity.DownloadActivity;
import com.ivymobi.abb.pw.activity.ShareActivity_;
import com.ivymobi.abb.pw.analytics.Analytics;
import com.ivymobi.abb.pw.beans.File;
import com.ivymobi.abb.pw.fragment.ListItemFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.ThinDownloadManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.Copy;
import cn.sharesdk.onekeyshare.Email;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import cz.msebera.android.httpclient.Header;


public class OnSwipeMenuItemClickListener implements SwipeMenuListView.OnMenuItemClickListener {
    List<File> files;
    ListItemFragment fragment;

    public OnSwipeMenuItemClickListener(ListItemFragment fragment, List<File> files) {
        this.files = files;
        this.fragment = fragment;
    }

    @Override
    public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
        File file = files.get(position);

        switch (index) {
            case 0:
                if (!file.isDownload() && !file.isDownloading()) {
                    file.setDownloading(true);
                    downloadFile(file, position);
                }

                break;
            case 1:
                shareFile(file);
                break;

            case 2:
                collectionFile(file, position);
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

                    final String fileUrl = response.getString("url");
                    file.setUrl(fileUrl);
                    file.save();

                    ShareSDK.initSDK(fragment.getContext());
                    OnekeyShare oks = new OnekeyShare();
                    oks.disableSSOWhenAuthorize();
                    oks.addHiddenPlatform("Email");
                    oks.setTitle(file.getTitle());
                    oks.setText(file.getTitle());
                    oks.setUrl(fileUrl);
                    oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
                        @Override
                        public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                            if (platform.getName().equals(Email.NAME)) {
                                ArrayList<String> uuidList = new ArrayList<>();
                                uuidList.add(file.getUuid());

                                Intent intent = new Intent(fragment.getContext(), ShareActivity_.class);
                                intent.putStringArrayListExtra("uuidList", uuidList);
                                fragment.startActivity(intent);
                            } else if (platform.getName().equals(Copy.NAME)) {
                                ClipboardManager cmb = (ClipboardManager) fragment.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                                cmb.setText(fileUrl);
                                Toast.makeText(fragment.getContext(), R.string.copy_success, Toast.LENGTH_SHORT).show();
                            } else if (platform.getName().equals(Wechat.NAME)) {
                                Analytics.log(fragment.getContext(), "user_action", "share_app", "by_wechat_friend", "1");
                            } else if (platform.getName().equals(WechatMoments.NAME)) {
                                Analytics.log(fragment.getContext(), "user_action", "share_app", "by_wechat_moment", "1");
                            }
                        }
                    });
                    oks.show(fragment.getContext());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void collectionFile(final File file, final int position) {
        Intent intent = new Intent(fragment.getContext(), CollectionActivity_.class);
        intent.putExtra("uuid", file.getUuid());
        intent.putExtra("position", position);

        DownloadActivity.listItemFragment = fragment;

        fragment.startActivityForResult(intent, 1);
    }

    private void downloadFile(final File file, int position) {

        Analytics.log(fragment.getContext(), "user_action", "download_file_started", file.getUuid(), "1");

        final SwipeMenuLayout layout = (SwipeMenuLayout) fragment.listView.getChildAt(position);
        LinearLayout linearLayout = (LinearLayout) layout.getMenuView().getChildAt(0);
        final ImageView downloadImageView = (ImageView) linearLayout.getChildAt(0);

        final CircularProgressBar progressBar = (CircularProgressBar) layout.findViewById(R.id.list_item_download_progress);
        progressBar.setVisibility(View.GONE);

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

                                    file.setLocalPath(fileName);
                                    file.setDownloading(false);
                                    file.save();

                                    downloadImageView.setImageResource(R.mipmap.icon_download_gray);

                                    progressBar.setVisibility(View.GONE);

                                    Analytics.log(fragment.getContext(), "user_action", "download_file_finished", file.getUuid(), "1");

//                                    Toast.makeText(fragment.getContext(), R.string.download_success, Toast.LENGTH_SHORT).show();
                                    fragment.refreshData();
                                }

                                @Override
                                public void onDownloadFailed(int id, int errorCode, String errorMessage) {
                                    Toast.makeText(fragment.getContext(), R.string.download_failed, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onProgress(int id, long totalBytes, long downlaodedBytes, int progress) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    progressBar.setProgress(progress);
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
