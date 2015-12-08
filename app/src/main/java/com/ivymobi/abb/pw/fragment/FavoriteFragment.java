package com.ivymobi.abb.pw.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.adapter.ListFavoriteAdapter;
import com.ivymobi.abb.pw.beans.Collection;
import com.ivymobi.abb.pw.beans.CollectionFile;
import com.ivymobi.abb.pw.beans.File;
import com.ivymobi.abb.pw.listener.OnFavoriteRecyclerListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment
public class FavoriteFragment extends Fragment implements OnFavoriteRecyclerListener {
    protected View mView;
    protected RecyclerView mRecyclerView = null;

    @ViewById(R.id.add_button)
    protected ImageView addButton;

    @ViewById(R.id.edit_button)
    protected ImageView editButton;

    @ViewById(R.id.complete_button)
    protected ImageView completeTV;

    protected ListFavoriteAdapter adapter = null;
    protected List<Collection> collections;
    protected List<Collection> collectionsToDelete;

    protected boolean isEditMode = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_favorite, container, false);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.cloud_list_rv);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return mView;
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        updateData();
//    }

    @AfterViews
    public void setEditMode() {
        isEditMode = false;

        collectionsToDelete = new ArrayList<>();

        updateData();

    }

    private void updateData() {
        collections = new Select().from(Collection.class).execute();

        adapter = new ListFavoriteAdapter(getActivity(), collections, FavoriteFragment.this);

        adapter.setHasStableIds(true);

        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemRecyclerClicked(View v, Collection collection) {

        if (isEditMode) {
            return;
        }
        List<CollectionFile> collectionFiles = CollectionFile.findByCollection(collection);
        List<File> files = new ArrayList<>();
        for (CollectionFile cf : collectionFiles) {
            files.add(cf.file);
        }

        ListItemFragment listItemFragment = ListItemFragment_.builder().build();
        listItemFragment.files = files;

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container_framelayout, listItemFragment, ListItemFragment.FLAG);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void onDeleteImageClicked(View v, int position) {

        for (int i = 0; i < adapter.getItemCount(); i++) {
            RecyclerView.ViewHolder viewHolder = mRecyclerView.findViewHolderForItemId(i);
            boolean flag = false;
            if (position == i) {
                flag = true;
            }
            adapter.deleteImageClicked(viewHolder, flag);
        }
    }

    @Override
    public void onDeleteTvClicked(View v, Collection collection, String newName) {
        collectionsToDelete.add(collection);
        collections.remove(collection);
        adapter.updateItems(collections);
        adapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);
    }

    @Click
    public void addButtonClicked() {
        if (isEditMode) {
            return;
        }

        addButton.setSelected(true);
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setTitle(R.string.new_group);

        final EditText inputEditText = (EditText) promptsView.findViewById(R.id.input_edit_text);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(R.string.action_ok, null)
                .setNegativeButton(R.string.action_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                addButton.setSelected(false);
                                dialog.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (inputEditText.getText().length() == 0) {
                    Toast.makeText(getContext(), R.string.dialog_title_empty_group_name, Toast.LENGTH_SHORT).show();
                } else {

                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
                    }

                    alertDialog.dismiss();

                    Collection collection = new Collection();
                    collection.setName(inputEditText.getText().toString());
                    collection.save();

                    addButton.setSelected(false);

                    updateData();

                }
            }
        });
    }

    @Click
    public void editButtonClicked() {
        for (int i = 0; i < adapter.getItemCount(); i++) {
            RecyclerView.ViewHolder viewHolder = mRecyclerView.findViewHolderForItemId(i);
            adapter.SwitchToEditStyle(viewHolder, this.getContext());
        }
        completeTV.setVisibility(View.VISIBLE);
        isEditMode = true;

        editButton.setSelected(true);
    }

    @Click
    public void completeButtonClicked() {
        completeTV.setVisibility(View.GONE);
        isEditMode = false;
        editButton.setSelected(false);
        for (int i = 0; i < adapter.getItemCount(); i++) {
            RecyclerView.ViewHolder viewHolder = mRecyclerView.findViewHolderForItemId(i);
            //恢复到非编辑模式
            adapter.SwitchToNormalStyle(viewHolder, this.getContext());
            //处理预重命名元素
            adapter.saveNewName(viewHolder, collections.get(i));
        }
        //处理预删除元素,如果文件夹下含文件时,需要先删除关联关系。
        for (Collection c : collectionsToDelete) {
            List<CollectionFile> collectionFiles = CollectionFile.findByCollection(c);
            for (CollectionFile f : collectionFiles) {
                f.collection = null;
                f.delete();
            }
            c.delete();
        }
    }
}
