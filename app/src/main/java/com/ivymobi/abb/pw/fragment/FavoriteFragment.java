package com.ivymobi.abb.pw.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.ivymobi.abb.pw.listener.OnFavoriteRecyclerListener;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import java.util.List;

@EFragment
public class FavoriteFragment extends Fragment implements OnFavoriteRecyclerListener {
    private View mView;
    private RecyclerView mRecyclerView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_favorite, container, false);

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

        updateData();
    }

    private void updateData() {
        List<Collection> collections = new Select().from(Collection.class).execute();

        mRecyclerView.setAdapter(new ListFavoriteAdapter(getActivity(), collections, FavoriteFragment.this));
    }

    @Override
    public void onItemRecyclerClicked(View v, Collection collection) {
        ListItemFragment listItemFragment = new ListItemFragment();
        listItemFragment.files = collection.files();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container_framelayout, listItemFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Click
    public void addButtonClicked() {
        LayoutInflater li = LayoutInflater.from(getActivity().getApplicationContext());
        View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity().getApplicationContext());
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setTitle("新建分组");

        final EditText inputEditText = (EditText) promptsView.findViewById(R.id.input_edit_text);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Collection collection = new Collection();
                                collection.setName(inputEditText.getText().toString());
                                collection.save();

                                updateData();
                            }
                        })
                .setNegativeButton("取消",
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
