package com.ivymobi.abb.pw.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.analytics.Analytics;
import com.joanzapata.pdfview.PDFView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.io.File;

import cz.msebera.android.httpclient.Header;

@EActivity
public class PDFActivity extends BaseActivity {

    ProgressDialog pDialog;
    PDFView pdfView;
    AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.activity_main);
        setContentView(R.layout.activity_pdf);

        Intent intent = getIntent();
        final String url = intent.getStringExtra("url");

        Log.d("pdf", "url " + url);

        loadPDF(url);

        Analytics.log(this, "user_action", "open_file", url, "1");
    }

    @AfterViews
    public void showDialog() {
        pDialog = new ProgressDialog(PDFActivity.this);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.setMessage(getResources().getString(R.string.loading));
        pDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                    pDialog.dismiss();

                    client.cancelAllRequests(true);
                    finish();
                }

                return false;
            }
        });
        pDialog.show();
    }

    private void loadPDF(final String url) {
        client.get(url, new FileAsyncHttpResponseHandler(this) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                pDialog.dismiss();
                Log.d("PDFActivity", "load pdf fail, url" + url);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {

                pDialog.dismiss();

                if (pdfView == null) {
                    pdfView = (PDFView) findViewById(R.id.pdfview);
                }

                pdfView.fromFile(file)
                        .defaultPage(1)
                        .showMinimap(true)
                        .enableSwipe(true)
                        .load();
            }
        });
    }
}
