package com.ivymobi.abb.pw.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.activity.PDFActivity_;
import com.ivymobi.abb.pw.activity.VideoActivity_;
import com.ivymobi.abb.pw.app.MyApplication;
import com.ivymobi.abb.pw.network.CachedFileEnum;
import com.ivymobi.abb.pw.network.response.ABBChinaIntroduceResponse;
import com.ivymobi.abb.pw.network.response.ABBChinaPowerIntroduceResponse;
import com.ivymobi.abb.pw.util.PreferenceUtil;
import com.ivymobi.abb.pw.util.SerializationUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.w3c.dom.Text;

import java.util.Locale;

@EFragment
public class FragmentContent extends Fragment implements View.OnTouchListener {
    private ABBChinaIntroduceResponse abbIntroresponse;
    private ABBChinaPowerIntroduceResponse abbPowerIntroResponse;
    private Locale locale;

    private TextView tvTitle;
    private TextView tvSubtitle;
    private TextView tvVideoMore;
    private TextView tvContent;
    private ImageView ivVideoCover;

    public static Fragment getInstance(Bundle bundle) {
        FragmentContent fragment = new FragmentContent();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 加载数据
        abbIntroresponse = SerializationUtil.getObject(getContext(), CachedFileEnum.BUSINESS_ABB_INTRO.getNameEtag());
        abbPowerIntroResponse = SerializationUtil.getObject(getContext(), CachedFileEnum.BUSINESS_ABB_POWER_INTRO.getNameEtag());
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
        ivVideoCover = (ImageView) view.findViewById(R.id.iv_video_cover);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvSubtitle = (TextView) view.findViewById(R.id.tv_subtitle);
        tvContent = (TextView) view.findViewById(R.id.tv_content);
        tvVideoMore = (TextView) view.findViewById(R.id.tv_video_more);

        Integer position = getArguments().getInt("position");
        ivVideoCover.setOnTouchListener(this);

        loadView(position);

        // initView();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final Rect rect = new Rect();
        v.getHitRect(rect);

        Integer position = getArguments().getInt("position");

        float scale = getContext().getResources().getDisplayMetrics().density;

        if (scale > 2) {
            scale /= 2;
        } else {
            scale = 1.0f;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:

                switch (position) { // 1st image
                    case 0:

                        if (locale == Locale.ENGLISH) {
                            if (MyApplication.clickHit(ivVideoCover, event, 0, 702, 0, 336, scale)) {
                                Intent intent = new Intent(getActivity(), VideoActivity_.class);
                                intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/Video/ABB_Instruction.mp4");
                                startActivity(intent);
                            } else if (MyApplication.clickHit(ivVideoCover, event, 572, 702, 419, 469, scale)) {
                                Intent intent = new Intent(getActivity(), PDFActivity_.class);
                                intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/PDF_file/abb_instruction/ABB%2bin%2bChina_EN_2014.pdf");
                                startActivity(intent);
                            }
                        } else {
                            if (MyApplication.clickHit(ivVideoCover, event, 0, 700, 0, 336, scale)) { // 1st area
                                Intent intent = new Intent(getActivity(), VideoActivity_.class);
                                intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/Video/ABB_Instruction.mp4");
                                startActivity(intent);
                            } else if (MyApplication.clickHit(ivVideoCover, event, 570, 700, 390, 440, scale)) { // 2nd area
                                Intent intent = new Intent(getActivity(), PDFActivity_.class);
                                intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/PDF_file/abb_instruction/ABB%2bin%2bChina_CN_2014.pdf");
                                startActivity(intent);
                            }
                        }

                        break;

                    case 1: // 2nd image

                        if (locale == Locale.ENGLISH) {
                            if (MyApplication.clickHit(ivVideoCover, event, 0, 700, 0, 336, scale)) {
                                Intent intent = new Intent(getActivity(), VideoActivity_.class);
                                intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/Video/ABB_Power.mp4");
                                startActivity(intent);
                            } else if (MyApplication.clickHit(ivVideoCover, event, 572, 702, 419, 469, scale)) {
                                Intent intent = new Intent(getActivity(), PDFActivity_.class);
                                intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/PDF_file/abb_power/ABB_power_en.pdf");
                                startActivity(intent);
                            }
                        } else {
                            if (MyApplication.clickHit(ivVideoCover, event, 0, 700, 0, 336, scale)) {
                                Intent intent = new Intent(getActivity(), VideoActivity_.class);
                                intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/Video/ABB_Power.mp4");
                                startActivity(intent);
                            } else if (MyApplication.clickHit(ivVideoCover, event, 572, 702, 390, 440, scale)) {
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
            // 标记“ABB中国介绍”为“已读”
            PreferenceUtil.commitBoolean(CachedFileEnum.BUSINESS_ABB_INTRO.getNameRead(), false);
            ABBChinaIntroduceResponse.Item item = abbIntroresponse.getItems().get(0);
            String coverPicUrl = item.getData().getVideo().getCover().getFile();
            String title = item.getData().getTitle();
            String subTitle = item.getData().getTitle_sub();
            String content = item.getData().getContent();
            ABBChinaIntroduceResponse.Item.Data.Video videoMore = item.getData().getMore();
            ImageLoader.getInstance().displayImage(coverPicUrl, ivVideoCover);
            tvTitle.setText(title);
            tvSubtitle.setText(subTitle);
            tvContent.setText(Html.fromHtml(content));
            tvVideoMore.setVisibility(videoMore != null ? View.VISIBLE : View.INVISIBLE);
        } else {
            // 标记“ABB中国电力业务介绍”为“已读”
            PreferenceUtil.commitBoolean(CachedFileEnum.BUSINESS_ABB_POWER_INTRO.getNameRead(), false);
            ABBChinaPowerIntroduceResponse.Item item = abbPowerIntroResponse.getItems().get(0);
            String coverPicUrl = item.getData().getVideo().getCover().getFile();
            String title = item.getData().getTitle();
            String subTitle = item.getData().getTitle_sub();
            String content = item.getData().getContent();
            ABBChinaPowerIntroduceResponse.Item.Data.More videoMore = item.getData().getMore();
            ImageLoader.getInstance().displayImage(coverPicUrl, ivVideoCover);
            tvTitle.setText(title);
            tvSubtitle.setText(subTitle);
            tvContent.setText(Html.fromHtml(content));
            tvVideoMore.setVisibility(videoMore != null ? View.VISIBLE : View.INVISIBLE);
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

        /*switch (position) {
            case 1:
                imageView.setImageResource(R.mipmap.dlywjs);
                imageLoader.displayImage("http://ivymobi-storage.qiniudn.com/abbpw/Business/" + file, imageView, options, imageLoadingListener);

                break;
            default: // 0
                imageView.setImageResource(R.mipmap.zgjs);

                imageLoader.displayImage("http://ivymobi-storage.qiniudn.com/abbpw/Business/" + file2, imageView, options, imageLoadingListener);
                break;
        }*/

    }
}
