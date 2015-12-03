package com.ivymobi.abb.pw.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ivymobi.abb.pw.R;
import com.joanzapata.pdfview.PDFView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.androidannotations.annotations.EActivity;

import java.io.File;

import cz.msebera.android.httpclient.Header;

@EActivity
public class PDFActivity extends AppCompatActivity {

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.activity_main);
        setContentView(R.layout.activity_pdf);

        Intent intent = getIntent();
        final String url = intent.getStringExtra("url");

        pDialog = new ProgressDialog(PDFActivity.this);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        pDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new FileAsyncHttpResponseHandler(this) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                pDialog.dismiss();
                Log.d("PDFActivity", "load pdf fail, url" + url);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {

                pDialog.dismiss();
                PDFView pdfView = (PDFView) findViewById(R.id.pdfview);

                pdfView.fromFile(file)
                        .defaultPage(1)
                        .showMinimap(false)
                        .enableSwipe(true)
                        .load();
            }
        });


    }
}
