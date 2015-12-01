package com.ivymobi.abb.pw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.query.Select;
import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.activity.LocalPDFActivity_;
import com.ivymobi.abb.pw.activity.PDFActivity_;
import com.ivymobi.abb.pw.adapter.DownloadedRecyclerAdapter;
import com.ivymobi.abb.pw.beans.File;
import com.ivymobi.abb.pw.listener.OnLocalItemRecyclerListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.androidannotations.annotations.EFragment;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;


@EFragment
public class DownloadedFragment extends Fragment implements OnLocalItemRecyclerListener {
    private View mView;
    private RecyclerView mRecyclerView = null;
    public List<File> files;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_downloaded, container, false);

            mRecyclerView = (RecyclerView) mView.findViewById(R.id.cloud_list_rv);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }

        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (files == null) {
            files = new Select().from(File.class).execute();
        }

        mRecyclerView.setAdapter(new DownloadedRecyclerAdapter(getActivity(), files, DownloadedFragment.this));
    }

    @Override
    public void onItemRecyclerClicked(View v, File file) {

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
}
