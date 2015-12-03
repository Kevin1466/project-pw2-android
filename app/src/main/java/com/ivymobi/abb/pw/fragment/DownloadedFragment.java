package com.ivymobi.abb.pw.fragment;

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
public class DownloadedFragment extends Fragment {
    private View mView;
    private SwipeMenuListView listView = null;
    public List<File> files;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_downloaded, container, false);

        listView = (SwipeMenuListView) mView.findViewById(R.id.listView);

        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (files == null) {
            files = File.getAllDownloadedFiles();
        }

        System.out.println("xxxxx");
        System.out.println(files.size());

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(getActivity().getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(120);
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity().getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(120);
                // set a icon
                deleteItem.setIcon(R.mipmap.icon_downloaded);
                // add to menu
                menu.addMenuItem(deleteItem);
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
}
