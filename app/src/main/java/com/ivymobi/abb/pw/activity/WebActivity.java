package com.ivymobi.abb.pw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ivymobi.abb.pw.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class WebActivity extends BaseActivity {

    @ViewById
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.activity_main);

        setContentView(R.layout.activity_web);

        Intent intent = getIntent();

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(intent.getStringExtra("url"));

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);

                return true;
            }
        });
    }
}
