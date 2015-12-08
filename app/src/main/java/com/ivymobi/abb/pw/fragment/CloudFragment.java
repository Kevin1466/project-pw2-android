package com.ivymobi.abb.pw.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
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

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
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
    private HashMap<String, File> filesMap = new HashMap<>();
    private ProgressDialog pDialog;

    public static String FLAG = "CLOUD_FRAGMENT";

    public Catalog root;
    private RecyclerView mRecyclerView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_cloud, container, false);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.cloud_list_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return mView;
    }

    @AfterViews
    protected void afterViews() {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.setMessage(getResources().getString(R.string.loading));
        pDialog.show();

        getCatalog();
    }

    public void getCatalog() {

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

                        updateFragmentUI(response);
                    }
                });


            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("ffffffffkkkkkkkkk");
    }

    @UiThread
    public void updateFragmentUI(JSONArray response) {
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
    public void onFolderRecyclerClicked(View v, int position) {

        if (root.getChildren().get(position).hasFiles()) {
            ListItemFragment listItemFragment = ListItemFragment_.builder().build();
            listItemFragment.files = root.getChildren().get(position).getFiles();

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.replace(R.id.container_framelayout, listItemFragment, ListItemFragment.FLAG);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            ListFolderFragment cloudFragment = ListFolderFragment_.builder().build();
            cloudFragment.root = root.getChildren().get(position);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.replace(R.id.container_framelayout, cloudFragment, CloudFragment.FLAG);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}