package com.ivymobi.abb.pw.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.activeandroid.query.Select;
import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.adapter.ListFavoriteAdapter;
import com.ivymobi.abb.pw.beans.Collection;
import com.ivymobi.abb.pw.beans.CollectionFile;
import com.ivymobi.abb.pw.beans.File;
import com.ivymobi.abb.pw.listener.OnFavoriteRecyclerListener;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.List;

@EFragment
public class FavoriteFragment extends Fragment implements OnFavoriteRecyclerListener {
    private View mView;
    private RecyclerView mRecyclerView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_favorite, container, false);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.cloud_list_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return mView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        updateData();
    }

    private void updateData() {
        List<Collection> collections = new Select().from(Collection.class).execute();

        mRecyclerView.setAdapter(new ListFavoriteAdapter(getActivity(), collections, FavoriteFragment.this));
    }

    @Override
    public void onItemRecyclerClicked(View v, Collection collection) {

        List<CollectionFile> collectionFiles = CollectionFile.findByCollection(collection);
        List<File> files = new ArrayList<>();
        for (CollectionFile cf : collectionFiles) {
            files.add(cf.file);
        }

        ListItemFragment listItemFragment = new ListItemFragment();
        listItemFragment.files = files;

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container_framelayout, listItemFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Click
    public void addButtonClicked() {
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setTitle(R.string.new_group);

        final EditText inputEditText = (EditText) promptsView.findViewById(R.id.input_edit_text);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(R.string.action_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Collection collection = new Collection();
                                collection.setName(inputEditText.getText().toString());
                                collection.save();

                                updateData();
                            }
                        })
                .setNegativeButton(R.string.action_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
