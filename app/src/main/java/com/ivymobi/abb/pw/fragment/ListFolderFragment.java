package com.ivymobi.abb.pw.fragment;

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
import com.ivymobi.abb.pw.app.MyApplication;
import com.ivymobi.abb.pw.beans.Catalog;
import com.ivymobi.abb.pw.listener.OnFolderRecyclerListener;

import org.androidannotations.annotations.EFragment;

@EFragment
public class ListFolderFragment extends Fragment implements OnFolderRecyclerListener {

    private View mView;

    public Catalog root;
    private RecyclerView mRecyclerView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_cloud, container, false);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.cloud_list_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new ListFolderAdapter(getActivity(), root, ListFolderFragment.this));

        return mView;
    }

    @Override
    public void onFolderRecyclerClicked(View v, int position) {

        if (root.getChildren().get(position).hasFiles()) {
            ListItemFragment listItemFragment = ListItemFragment_.builder().build();
            listItemFragment.files = root.getChildren().get(position).getFiles();

            Bundle args = new Bundle();

            boolean isShareMode = (boolean)((MyApplication) getActivity().getApplication()).getmData(
                    getResources().getString(R.string.share_mode));
            args.putBoolean(getResources().getString(R.string.share_mode),isShareMode);
            listItemFragment.setArguments(args);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.replace(R.id.container_framelayout, listItemFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            ListFolderFragment cloudFragment = ListFolderFragment_.builder().build();
            cloudFragment.root = root.getChildren().get(position);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.replace(R.id.container_framelayout, cloudFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}