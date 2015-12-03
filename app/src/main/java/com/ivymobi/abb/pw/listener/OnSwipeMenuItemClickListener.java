package com.ivymobi.abb.pw.listener;

import android.content.ClipboardManager;
import android.content.Context;
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
import com.umeng.socialize.bean.CustomPlatform;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;


public class OnSwipeMenuItemClickListener implements SwipeMenuListView.OnMenuItemClickListener {
    List<File> files;
    DownloadedFragment fragment;
    UMSocialService umSocialService = UMServiceFactory.getUMSocialService("com.umeng.share");

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

                    UMWXHandler wxHandler = new UMWXHandler(fragment.getContext(), "wx508bd5b1879c3b8e", "f0c9758ef8a2775f21c6e977aa06e5a3");
                    wxHandler.addToSocialSDK();

                    UMWXHandler wxCircleHandler = new UMWXHandler(fragment.getContext(), "wx508bd5b1879c3b8e", "f0c9758ef8a2775f21c6e977aa06e5a3");
                    wxCircleHandler.setToCircle(true);
                    wxCircleHandler.addToSocialSDK();

                    UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(fragment.getActivity(), "100424468", "c7394704798a158208a74ab60104f0ba");
                    qqSsoHandler.addToSocialSDK();

                    CustomPlatform customPlatform = new CustomPlatform("复制链接", R.mipmap.icon_albums);
                    customPlatform.mClickListener = new SocializeListeners.OnSnsPlatformClickListener() {
                        @Override
                        public void onClick(Context context, SocializeEntity socializeEntity, SocializeListeners.SnsPostListener snsPostListener) {
                            ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                            cmb.setText("xxxxx");
                        }
                    };

                    umSocialService.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.EMAIL);
                    umSocialService.getConfig().addCustomPlatform(customPlatform);
                    umSocialService.setShareContent(file.getTitle());
                    umSocialService.openShare(fragment.getActivity(), false);
                    umSocialService.registerListener(new SocializeListeners.SnsPostListener() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onComplete(SHARE_MEDIA share_media, int i, SocializeEntity socializeEntity) {

                        }
                    });

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
