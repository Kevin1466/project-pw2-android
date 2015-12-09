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
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.util.PreferenceUtil;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.util.List;
import java.util.Locale;

@EActivity
public class SettingActivity extends BaseActivity {

    @ViewById
    TextView currentVersion;

    @ViewById
    TextView currentLanguage;

    @ViewById
    TextView cacheSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.setting);
        setContentView(R.layout.activity_setting);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.icon_home);

        String language = PreferenceUtil.getString("language", "Chinese");
        PreferenceUtil.init(this);
        currentVersion.setText(getVersion());
        currentLanguage.setText(getResources().getIdentifier(language, "string", getPackageName()));

        getCacheSize();
    }

    @Click
    public void actionMailSignClicked() {
        Intent intent = new Intent(this, SettingMailSignActivity_.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        startActivity(intent);
    }

    private void getCacheSize() {
        File filePath = getExternalCacheDir();
        File imagePath = new File(getExternalCacheDir() + "/images");

        File[] files;
        Long size = 0l;

        if (filePath != null && filePath.exists()) {
            files = filePath.listFiles();

            for (File f : files) {
                size = size + f.length();
            }
        }

        if (imagePath.exists()) {
            files = imagePath.listFiles();
            for (File f : files) {
                size = size + f.length();
            }
        }

        cacheSize.setText(String.format("%.2f MB", (float) size / 1024 / 1024));
    }

    @Click
    public void actionCacheClearClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        builder.setMessage(R.string.dialog_title_clear_cache);
        builder.setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                File filePath = getExternalCacheDir();
                File imagePath = new File(getExternalCacheDir() + "/images");

                File[] files;
                if (filePath != null && filePath.exists()) {
                    files = filePath.listFiles();

                    for (File f : files) {
                        f.delete();
                    }
                }

                if (imagePath.exists()) {
                    files = imagePath.listFiles();
                    for (File f : files) {
                        f.delete();
                    }
                }

                List<com.ivymobi.abb.pw.beans.File> downloadedFiles = com.ivymobi.abb.pw.beans.File.getAllDownloadedFiles();
                for (com.ivymobi.abb.pw.beans.File f : downloadedFiles) {
                    f.setLocalPath(null);
                    f.save();
                }

                cacheSize.setText("0.00 MB");

                dialog.dismiss();
            }
        });

        builder.show();
    }


    @Click
    public void actionLanguageSettingClicked() {
        final String[] arrayFruit = new String[]{"中文", "English"};

        Dialog alertDialog = new AlertDialog.Builder(this).
                setTitle(R.string.label_language_setting)
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

        Intent intent = new Intent(this, SettingActivity_.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            finish();
            back();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void back() {
        Intent intent = new Intent(this, MainActivity_.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            back();
        }

        return super.onKeyDown(keyCode, event);
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