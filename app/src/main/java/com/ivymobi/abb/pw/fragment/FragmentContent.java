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
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.Locale;

public class FragmentContent extends Fragment implements View.OnTouchListener {
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Locale locale;

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

        initView(view);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final Rect rect = new Rect();
        v.getHitRect(rect);

        Integer position = getArguments().getInt("position");

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:

                switch (position) {
                    case 0:

                        if (x > 0 && x < 700 && y > 0 && y < 336) {
                            Intent intent = new Intent(getActivity(), VideoActivity_.class);
                            intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/Video/ABB_Instruction.mp4");
                            startActivity(intent);
                        } else if (x > 572 && x < 702 && y > 390 && y < 440) {
                            Intent intent = new Intent(getActivity(), PDFActivity_.class);
                            intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/PDF_file/abb_instruction/ABB%2bin%2bChina_CN_2014.pdf");
                            startActivity(intent);
                        }


                        break;

                    case 1:

                        if (x > 0 && x < 700 && y > 0 && y < 336) {
                            Intent intent = new Intent(getActivity(), VideoActivity_.class);
                            intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/Video/ABB_Power.mp4");
                            startActivity(intent);
                        } else if (x > 572 && x < 702 && y > 390 && y < 440) {
                            Intent intent = new Intent(getActivity(), PDFActivity_.class);
                            intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/PDF_file/abb_power/ABB_power_cn.pdf");
                            startActivity(intent);
                        }


                        break;
                }


                break;

        }
        return true;
    }

    private void initView(View view) {

        Integer position = getArguments().getInt("position");
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setOnTouchListener(this);

        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).imageScaleType(ImageScaleType.NONE).build();

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
