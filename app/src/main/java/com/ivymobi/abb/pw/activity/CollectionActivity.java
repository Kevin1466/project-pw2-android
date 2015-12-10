package com.ivymobi.abb.pw.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.adapter.ListViewAdapter;
import com.ivymobi.abb.pw.analytics.Analytics;
import com.ivymobi.abb.pw.beans.Collection;
import com.ivymobi.abb.pw.beans.CollectionFile;
import com.ivymobi.abb.pw.beans.File;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;
import java.util.List;

@EActivity
public class CollectionActivity extends BaseActivity {

    private ListView listView;
    private ListViewAdapter adapter;
    private File file;
    private Integer position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.groups));

        setContentView(R.layout.activity_collection);

        Intent intent = getIntent();
        file = File.findByUuid(intent.getStringExtra("uuid"));
        position = intent.getIntExtra("position", 0);

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
    public void imageViewPlusClicked() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.new_group_line);
        linearLayout.setVisibility(View.VISIBLE);
    }

    @Click
    public void newGroupButtonClicked() {
        TextView textView = (TextView) findViewById(R.id.new_group_name);

        if (textView.getText().length() == 0) {
            Toast.makeText(this, R.string.dialog_title_empty_group_name, Toast.LENGTH_SHORT).show();
        } else {
            Collection collection = new Collection();
            collection.setName(textView.getText().toString());
            collection.save();

            textView.setText(null);
            setupList();

            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.new_group_line);
            linearLayout.setVisibility(View.GONE);

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            }
        }
    }

    @Click
    public void buttonOkClicked() {

        // 已有处理
        List<CollectionFile> collectionFiles = CollectionFile.findByFile(file);
        for (CollectionFile cf : collectionFiles) {
            Analytics.log(this, "user_action", "fav_file", cf.file.getUuid(), "1");

            cf.delete();
        }

        for (Integer id : adapter.getSelectedList()) {
            Collection collection = new Select().from(Collection.class).where("id = ?", id).executeSingle();

            if (collection != null) {
                CollectionFile newCollectionFile = new CollectionFile();
                newCollectionFile.collection = collection;
                newCollectionFile.file = file;
                newCollectionFile.save();

                Analytics.log(this, "user_action", "fav_file", file.getUuid(), "1");
            }

        }

        setResult(RESULT_OK, getIntent());
        finish();
    }
}
