package com.ivymobi.abb.pw.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.activity.PDFActivity_;
import com.ivymobi.abb.pw.activity.VideoActivity_;
import com.ivymobi.abb.pw.app.MyApplication;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.androidannotations.annotations.EFragment;

import java.util.Locale;

@EFragment
public class FragmentContent extends Fragment implements View.OnTouchListener {
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Locale locale;

    ImageView imageView;

    public static Fragment getInstance(Bundle bundle) {
        FragmentContent fragment = new FragmentContent();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            onLoad();
        } else {
            notLoad();
        }
    }

    protected void onLoad() {

        View view = View.inflate(getContext(), R.layout.fragment_layout, null);

        locale = getContext().getResources().getConfiguration().locale;
        imageView = (ImageView) view.findViewById(R.id.imageView);

        initView();
    }

    protected void notLoad() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final Rect rect = new Rect();
        v.getHitRect(rect);

        Integer position = getArguments().getInt("position");

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:

                switch (position) {
                    case 0:

                        if (locale == Locale.ENGLISH) {
                            if (MyApplication.clickHit(imageView, event, 0, 702, 0, 336)) {
                                Intent intent = new Intent(getActivity(), VideoActivity_.class);
                                intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/Video/ABB_Instruction.mp4");
                                startActivity(intent);
                            } else if (MyApplication.clickHit(imageView, event, 572, 702, 419, 469)) {
                                Intent intent = new Intent(getActivity(), PDFActivity_.class);
                                intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/PDF_file/abb_instruction/ABB%2bin%2bChina_EN_2014.pdf");
                                startActivity(intent);
                            }
                        } else {
                            if (MyApplication.clickHit(imageView, event, 0, 700, 0, 336)) {
                                Intent intent = new Intent(getActivity(), VideoActivity_.class);
                                intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/Video/ABB_Instruction.mp4");
                                startActivity(intent);
                            } else if (MyApplication.clickHit(imageView, event, 572, 702, 390, 440)) {
                                Intent intent = new Intent(getActivity(), PDFActivity_.class);
                                intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/PDF_file/abb_instruction/ABB%2bin%2bChina_CN_2014.pdf");
                                startActivity(intent);
                            }
                        }

                        break;

                    case 1:

                        if (locale == Locale.ENGLISH) {
                            if (MyApplication.clickHit(imageView, event, 0, 700, 0, 336)) {
                                Intent intent = new Intent(getActivity(), VideoActivity_.class);
                                intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/Video/ABB_Power.mp4");
                                startActivity(intent);
                            } else if (MyApplication.clickHit(imageView, event, 572, 702, 419, 469)) {
                                Intent intent = new Intent(getActivity(), PDFActivity_.class);
                                intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/PDF_file/abb_power/ABB_power_en.pdf");
                                startActivity(intent);
                            }
                        } else {
                            if (MyApplication.clickHit(imageView, event, 0, 700, 0, 336)) {
                                Intent intent = new Intent(getActivity(), VideoActivity_.class);
                                intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/Video/ABB_Power.mp4");
                                startActivity(intent);
                            } else if (MyApplication.clickHit(imageView, event, 572, 702, 390, 440)) {
                                Intent intent = new Intent(getActivity(), PDFActivity_.class);
                                intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/PDF_file/abb_power/ABB_power_cn.pdf");
                                startActivity(intent);
                            }
                        }

                        break;
                }


                break;

        }
        return true;
    }

    private void initView() {

        Integer position = getArguments().getInt("position");
        imageView.setOnTouchListener(this);

        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).imageScaleType(ImageScaleType.NONE).build();

        String file = "icon_dlywjs@2x.png";
        String file2 = "icon_zgjs@2x.png";

        if (locale == Locale.ENGLISH) {
            file = "EN_icon_dlywjs@2x.png";
            file2 = "EN_icon_zgjs@2x.png";
        }

        switch (position) {
            case 1:
                imageView.setImageResource(R.mipmap.dlywjs);

                imageLoader.displayImage("http://ivymobi-storage.qiniudn.com/abbpw/Business/" + file, imageView, options);

                break;
            default: // 0
                imageView.setImageResource(R.mipmap.zgjs);

                imageLoader.displayImage("http://ivymobi-storage.qiniudn.com/abbpw/Business/" + file2, imageView, options);
                break;
        }

    }
}
