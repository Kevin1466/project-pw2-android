package com.ivymobi.abb.pw.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.TextView;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.util.PreferenceUtil;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Locale;

@EActivity
public class SettingActivity extends AppCompatActivity {

    @ViewById
    TextView currentVersion;

    @ViewById
    TextView currentLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        PreferenceUtil.init(this);
        currentVersion.setText(getVersion());
        currentLanguage.setText(PreferenceUtil.getString("language", "Chinese"));
    }

    @Click
    public void actionMailSignClicked() {
        startActivity(new Intent(this, SettingMailSignActivity_.class));
    }


    @Click
    public void actionLanguageSettingClicked() {
        final String[] arrayFruit = new String[]{"中文", "English"};

        Dialog alertDialog = new AlertDialog.Builder(this).
                setTitle(R.string.label_chinese)
                .setItems(arrayFruit, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0:
                                switchLanguage("Chinese");
                                break;
                            default:
                                switchLanguage("English");
                                break;
                        }
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
        alertDialog.show();
    }

    public void switchLanguage(String language) {
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();

        if (language.equals("Chinese")) {
            config.locale = Locale.SIMPLIFIED_CHINESE;
        } else {
            config.locale = Locale.ENGLISH;
        }

        resources.updateConfiguration(config, dm);

        PreferenceUtil.commitString("language", language);

        finish();

        Intent intent = new Intent(this, MainActivity_.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            return "-";
        }
    }
}