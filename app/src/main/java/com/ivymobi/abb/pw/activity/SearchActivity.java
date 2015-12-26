package com.ivymobi.abb.pw.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.SearchView;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.beans.File;
import com.ivymobi.abb.pw.fragment.ListItemFragment;
import com.ivymobi.abb.pw.fragment.ListItemFragment_;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EActivity
public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    @ViewById
    SearchView searchView;

    ListItemFragment listItemFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setupSearchView();
        showSearchViews();
    }

    private void setupSearchView() {
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
    }

    private void showSearchViews() {
        listItemFragment = ListItemFragment_.builder().build();
        listItemFragment.files = new ArrayList<>();

        Bundle args = new Bundle();
        args.putBoolean(getResources().getString(R.string.share_mode), false);
        listItemFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_framelayout, listItemFragment, ListItemFragment.FLAG);
        transaction.commit();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        showSearchResults(query);

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        showSearchResults(newText);

        return false;
    }

    private void showSearchResults(String query) {
        System.out.println("show search results");

        listItemFragment.files = File.getFilesByKeywords(query);
        listItemFragment.setAdapter();
    }
}
