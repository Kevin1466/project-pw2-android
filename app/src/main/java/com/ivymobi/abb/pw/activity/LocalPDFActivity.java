package com.ivymobi.abb.pw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ivymobi.abb.pw.R;
import com.joanzapata.pdfview.PDFView;

import org.androidannotations.annotations.EActivity;

import java.io.File;

@EActivity
public class LocalPDFActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.activity_main);

        setContentView(R.layout.activity_pdf);

        Intent intent = getIntent();
        String fileName = intent.getStringExtra("fileName");
        String fullPath = getExternalCacheDir().toString() + "/" + fileName;

        Log.d("pdf", "url " + fullPath);

        PDFView pdfView = (PDFView) findViewById(R.id.pdfview);

        pdfView.fromFile(new File(fullPath))
                .defaultPage(1)
                .showMinimap(false)
                .enableSwipe(true)
                .load();
    }
}
