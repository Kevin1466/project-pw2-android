package com.ivymobi.abb.pw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.adapter.ListViewAdapter;
import com.ivymobi.abb.pw.beans.Collection;
import com.ivymobi.abb.pw.beans.File;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import java.util.List;

@EActivity
public class CollectionActivity extends AppCompatActivity {

    private ListView listView;
    private ListViewAdapter adapter;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        Intent intent = getIntent();
        file = File.findByUuid(intent.getStringExtra("uuid"));

        System.out.println(file.getTitle());

        setupList();
    }

    private void setupList() {
        List<Collection> collections = new Select().from(Collection.class).execute();

        listView = (ListView) findViewById(R.id.listView);
        adapter = new ListViewAdapter(this, collections);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Click
    public void buttonCancelClicked() {
        finish();
    }

    @Click
    public void buttonOkClicked() {
        for (Integer id : adapter.getSelectedList()) {
            Collection collection = new Select().from(Collection.class).where("id = ?", id).executeSingle();

            if (collection != null) {
                file.setCollection(collection);
                file.save();
            }

        }

        Toast.makeText(this, "已收藏", Toast.LENGTH_SHORT).show();
        finish();
    }
}
