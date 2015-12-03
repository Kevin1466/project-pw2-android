package com.ivymobi.abb.pw.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.adapter.ListFolderAdapter;
import com.ivymobi.abb.pw.beans.Catalog;
import com.ivymobi.abb.pw.beans.File;
import com.ivymobi.abb.pw.listener.OnFolderRecyclerListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.androidannotations.annotations.EFragment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

import static java.lang.System.err;

@EFragment
public class CloudFragment extends Fragment implements OnFolderRecyclerListener {

    private View mView;

    public Catalog root;
    private RecyclerView mRecyclerView = null;
    private HashMap<String, File> filesMap = new HashMap<>();
    private ProgressDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_cloud, container, false);

            mRecyclerView = (RecyclerView) mView.findViewById(R.id.cloud_list_rv);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }

        return mView;
    }

    public void getCatalog() {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.setMessage("Buffering...");
        pDialog.show();

        final AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://yangbentong.com/api/7a94881a-df96-429d-9e01-dece4f46fee2/storage?bucket=catalog", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        File file = new File();
                        file.setTitle(jsonObject.getString("title"));
                        file.setCover(jsonObject.getString("cover"));
                        file.setEnTitle(jsonObject.getString("title_en"));
                        file.setSize(jsonObject.getInt("size"));
                        file.setThumbnail(jsonObject.getString("thumbnail"));
                        file.setUuid(jsonObject.getString("uuid"));
                        file.setTag(jsonObject.getJSONArray("tags").getString(0));

                        File old = File.findByUuid(jsonObject.getString("uuid"));

                        if (old == null) {
                            file.save();

                            filesMap.put(jsonObject.getString("uuid"), file);
                        } else {
                            filesMap.put(jsonObject.getString("uuid"), old);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    err.println(e);
                }

                // request catalog
                client.get("http://yangbentong.com/api/7a94881a-df96-429d-9e01-dece4f46fee2/category?bucket=catalog", new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        pDialog.dismiss();

                        try {
                            JSONObject list = response.getJSONObject(0);
                            root = new Catalog();
                            root.setName(list.getString("name"));

                            ArrayList<Catalog> catalogList = fetchCatalog(list.getJSONArray("subs"));
                            root.setChildren(catalogList);

                            mRecyclerView.setAdapter(new ListFolderAdapter(getActivity(), root, CloudFragment.this));

                        } catch (JSONException e) {
                            err.println(e);
                        }
                    }
                });


            }
        });
    }

    private ArrayList<Catalog> fetchCatalog(JSONArray jsonArray) throws JSONException {

        ArrayList<Catalog> catalogList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            Catalog catalog = new Catalog();
            catalog.setUuid(jsonObject.getString("uuid"));
            catalog.setName(jsonObject.getString("name"));
            catalog.setEnName(jsonObject.getString("name_en"));

            JSONArray files = jsonObject.getJSONArray("files");

            if (files.length() > 0) {
                for (int j = 0; j < files.length(); j++) {

                    File file;
                    if (null != (file = filesMap.get(files.getString(j)))) {
                        catalog.addFile(file);
                    }
                }
            }

            if (jsonObject.getJSONArray("subs").length() > 0) {
                catalog.setChildren(fetchCatalog(jsonObject.getJSONArray("subs")));
            }

            catalogList.add(catalog);
        }

        return catalogList;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (root == null) {
            getCatalog();
        } else if (root.hasChildren()) {
            mRecyclerView.setAdapter(new ListFolderAdapter(getActivity(), root, CloudFragment.this));
        }
    }

    @Override
    public void onFolderRecyclerClicked(View v, int position) {

        if (root.getChildren().get(position).hasFiles()) {
            System.out.println("has files");

            DownloadedFragment downloadedFragment = new DownloadedFragment();
            downloadedFragment.files = root.getChildren().get(position).getFiles();

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.container_framelayout, downloadedFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            CloudFragment cloudFragment = new CloudFragment();
            cloudFragment.root = root.getChildren().get(position);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.container_framelayout, cloudFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}