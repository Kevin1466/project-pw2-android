package com.ivymobi.abb.pw.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.app.MyApplication;
import com.ivymobi.abb.pw.beans.File;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;


@EFragment
public class DownloadedFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_downloaded, container, false);
    }

    @AfterViews
    public void showDownloadedFragmentListItems(){
        ListItemFragment listItemFragment = ListItemFragment_.builder().build();
        listItemFragment.files = File.getAllDownloadedFiles();

        Bundle args = new Bundle();
        boolean isShareMode = (boolean)((MyApplication) getActivity().getApplication()).getmData(
                getResources().getString(R.string.share_mode));
        args.putBoolean(getResources().getString(R.string.share_mode),isShareMode);
        args.putBoolean("canDeleted", true);
        listItemFragment.setArguments(args);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.container_framelayout, listItemFragment, ListItemFragment.FLAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
