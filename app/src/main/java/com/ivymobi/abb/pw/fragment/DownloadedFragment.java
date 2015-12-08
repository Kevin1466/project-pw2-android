package com.ivymobi.abb.pw.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.beans.File;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;


@EFragment
public class DownloadedFragment extends Fragment {
//    private View mView;
//    public List<File> files;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        mView = inflater.inflate(R.layout.fragment_downloaded, container, false);

//        files = File.getAllDownloadedFiles();

//        return mView;
        return inflater.inflate(R.layout.fragment_downloaded, container, false);
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        ListItemFragment listItemFragment = new ListItemFragment();
//        listItemFragment.files = files;
//
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.replace(R.id.container_framelayout, listItemFragment);
//        transaction.commit();
//    }

    @AfterViews
    public void showDownloadedFragmentListItems(){
        ListItemFragment listItemFragment = ListItemFragment_.builder().build();
        listItemFragment.files = File.getAllDownloadedFiles();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.container_framelayout, listItemFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
