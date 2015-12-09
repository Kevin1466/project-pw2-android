package com.ivymobi.abb.pw.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.util.PreferenceUtil;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class SettingMailSignActivity extends BaseActivity {

    @ViewById
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_mail_sign);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        PreferenceUtil.init(this);

        editText.setText(PreferenceUtil.getString("mailSign", null));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == android.R.id.home)
        {
            PreferenceUtil.commitString("mailSign", editText.getText().toString());

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
