package com.ivymobi.abb.pw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.EditText;
import android.widget.Toast;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.beans.File;
import com.ivymobi.abb.pw.util.PreferenceUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

@EActivity
public class ShareActivity extends AppCompatActivity {

    @ViewById
    EditText editTextReceiver;

    @ViewById
    EditText editTextContent;

    private List<File> fileList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        PreferenceUtil.init(this);

        receiveFile();
        initView();
    }

    private void receiveFile() {
        Intent intent = getIntent();
        ArrayList<String> uuidList = intent.getStringArrayListExtra("uuidList");
        for (String uuid : uuidList) {
            fileList.add(File.findByUuid(uuid));
        }
    }

    private void initView() {
        StringBuffer stringBuffer = new StringBuffer();
        for (File file : fileList) {
            stringBuffer.append(String.format("文件名：%s", file.getTitle()));
            stringBuffer.append("<br/><br/>");
            stringBuffer.append(String.format("%.2f MB", (float) file.getSize() / 1024 / 1024));
            stringBuffer.append("<br/><br/>");
            stringBuffer.append(String.format("<a href=\"%s\">下载链接</a>", file.getUrl()));
            stringBuffer.append("<br/><br/>");
        }

        // footer
        stringBuffer.append("分享自“ABB中国电力世界”APP。");
        stringBuffer.append("<br/><br/>");

        String mailSign = PreferenceUtil.getString("mailSign", null);
        if (mailSign != null) {
            stringBuffer.append(PreferenceUtil.getString("mailSign", null));
        }

        String receiver = PreferenceUtil.getString("receiver", null);
        if (receiver != null) {
            editTextReceiver.setText(receiver);
        }

        editTextContent.setText(Html.fromHtml(stringBuffer.toString()));
    }

    @Click
    public void actionCancelClicked() {
        finish();
    }

    @Click
    public void actionOkClicked() {
        if (editTextReceiver.getText().length() == 0) {
            Toast.makeText(this, R.string.warning_receiver, Toast.LENGTH_SHORT).show();
            return;
        } else if (editTextContent.getText().length() == 0) {
            Toast.makeText(this, R.string.warning_content, Toast.LENGTH_SHORT).show();
            return;
        }

        PreferenceUtil.commitString("receiver", editTextReceiver.getText().toString());

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.put("rcpt", editTextReceiver.getText());
        requestParams.put("type", "custom");
        requestParams.put("lang", "zh-CN");
        requestParams.put("data", String.format("{\"subject\":\"%s\",\"content\": \"%s\"}", getString(R.string.email_subject), Html.toHtml(editTextContent.getText()).replaceAll("\n", "").replaceAll("\"", "")));

        client.post("http://www.yangbentong.com/api/7a94881a-df96-429d-9e01-dece4f46fee2/share/email", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getString("status").equals("ok")) {
                        Toast.makeText(getBaseContext(), R.string.share_success, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getBaseContext(), R.string.share_fail, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        finish();
    }
}
