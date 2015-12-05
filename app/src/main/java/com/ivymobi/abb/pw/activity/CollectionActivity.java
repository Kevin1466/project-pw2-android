package com.ivymobi.abb.pw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.adapter.ListViewAdapter;
import com.ivymobi.abb.pw.beans.Collection;
import com.ivymobi.abb.pw.beans.CollectionFile;
import com.ivymobi.abb.pw.beans.File;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;
import java.util.List;

@EActivity
public class CollectionActivity extends AppCompatActivity {

    private ListView listView;
    private ListViewAdapter adapter;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.groups));
        setContentView(R.layout.activity_collection);

        Intent intent = getIntent();
        file = File.findByUuid(intent.getStringExtra("uuid"));

        setupList();
    }

    private void setupList() {
        List<Collection> collections = new Select().from(Collection.class).execute();

        List<Integer> selectedList = new ArrayList<>();
        List<CollectionFile> collectionFiles = CollectionFile.findByFile(file);
        for (CollectionFile collectionFile : collectionFiles) {
            selectedList.add(collectionFile.collection.getId().intValue());
        }

        listView = (ListView) findViewById(R.id.listView);
        adapter = new ListViewAdapter(this, collections, selectedList);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Click
    public void buttonCancelClicked() {
        finish();
    }

    @Click
    public void buttonOkClicked() {

        // 已有处理
        List<CollectionFile> collectionFiles = CollectionFile.findByFile(file);
        for (CollectionFile cf : collectionFiles) {
            cf.delete();
        }

        for (Integer id : adapter.getSelectedList()) {
            Collection collection = new Select().from(Collection.class).where("id = ?", id).executeSingle();

            if (collection != null) {
                CollectionFile newCollectionFile = new CollectionFile();
                newCollectionFile.collection = collection;
                newCollectionFile.file = file;
                newCollectionFile.save();
            }

        }

        finish();
    }
}
