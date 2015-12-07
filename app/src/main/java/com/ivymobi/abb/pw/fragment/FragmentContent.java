package com.ivymobi.abb.pw.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
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
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        locale = getContext().getResources().getConfiguration().locale;
        imageView = (ImageView) view.findViewById(R.id.imageView);

        Integer position = getArguments().getInt("position");
        imageView.setOnTouchListener(this);


        loadView(position);

        // initView();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final Rect rect = new Rect();
        v.getHitRect(rect);

        Integer position = getArguments().getInt("position");

        float scale = getContext().getResources().getDisplayMetrics().density;

        if (scale == 3.0) {
            scale = 1.5f;
        } else {
            scale = 1.0f;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:

                switch (position) { // 1st image
                    case 0:

                        if (locale == Locale.ENGLISH) {
                            if (MyApplication.clickHit(imageView, event, 0, 702, 0, 336, scale)) {
                                Intent intent = new Intent(getActivity(), VideoActivity_.class);
                                intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/Video/ABB_Instruction.mp4");
                                startActivity(intent);
                            } else if (MyApplication.clickHit(imageView, event, 572, 702, 419, 469, scale)) {
                                Intent intent = new Intent(getActivity(), PDFActivity_.class);
                                intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/PDF_file/abb_instruction/ABB%2bin%2bChina_EN_2014.pdf");
                                startActivity(intent);
                            }
                        } else {
                            if (MyApplication.clickHit(imageView, event, 0, 700, 0, 336, scale)) { // 1st area
                                Intent intent = new Intent(getActivity(), VideoActivity_.class);
                                intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/Video/ABB_Instruction.mp4");
                                startActivity(intent);
                            } else if (MyApplication.clickHit(imageView, event, 570, 700, 390, 440, scale)) { // 2nd area
                                Intent intent = new Intent(getActivity(), PDFActivity_.class);
                                intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/PDF_file/abb_instruction/ABB%2bin%2bChina_CN_2014.pdf");
                                startActivity(intent);
                            }
                        }

                        break;

                    case 1: // 2nd image

                        if (locale == Locale.ENGLISH) {
                            if (MyApplication.clickHit(imageView, event, 0, 700, 0, 336, scale)) {
                                Intent intent = new Intent(getActivity(), VideoActivity_.class);
                                intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/Video/ABB_Power.mp4");
                                startActivity(intent);
                            } else if (MyApplication.clickHit(imageView, event, 572, 702, 419, 469, scale)) {
                                Intent intent = new Intent(getActivity(), PDFActivity_.class);
                                intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/PDF_file/abb_power/ABB_power_en.pdf");
                                startActivity(intent);
                            }
                        } else {
                            if (MyApplication.clickHit(imageView, event, 0, 700, 0, 336, scale)) {
                                Intent intent = new Intent(getActivity(), VideoActivity_.class);
                                intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/Video/ABB_Power.mp4");
                                startActivity(intent);
                            } else if (MyApplication.clickHit(imageView, event, 572, 702, 390, 440, scale)) {
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

    private void loadView(int position) {
        if (position == 0) {
            imageView.setImageResource(R.mipmap.zgjs);
        } else {
            imageView.setImageResource(R.mipmap.dlywjs);
        }
    }

    private void initView(int position) {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(100))
                .build();
        ImageLoadingListener imageLoadingListener = new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                System.out.println("loadedImage.getWidth();" + loadedImage.getWidth());
                System.out.println("loadedImage.getHeight();" + loadedImage.getHeight());
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        };

        String file = "icon_dlywjs@2x.png";
        String file2 = "icon_zgjs@2x.png";

        if (locale == Locale.ENGLISH) {
            file = "EN_icon_dlywjs@2x.png";
            file2 = "EN_icon_zgjs@2x.png";
        }

        switch (position) {
            case 1:
                imageView.setImageResource(R.mipmap.dlywjs);
                imageLoader.displayImage("http://ivymobi-storage.qiniudn.com/abbpw/Business/" + file, imageView, options, imageLoadingListener);

                break;
            default: // 0
                imageView.setImageResource(R.mipmap.zgjs);

                imageLoader.displayImage("http://ivymobi-storage.qiniudn.com/abbpw/Business/" + file2, imageView, options, imageLoadingListener);
                break;
        }

    }
}
