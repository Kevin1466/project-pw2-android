package com.ivymobi.abb.pw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.activity.ImageActivity_;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.androidannotations.annotations.EFragment;

@EFragment
public class LocalFragmentContent extends Fragment {

    private ImageLoader imageLoader = ImageLoader.getInstance();

    public static Fragment getInstance(Bundle bundle) {
        LocalFragmentContent fragment = new LocalFragmentContent();
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
        return inflater.inflate(R.layout.local_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView LocalImageView1 = (ImageView) view.findViewById(R.id.abb_jwjs_bdqy_gy_1);

        LocalImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageActivity_.class);
                intent.putExtra("image", "abb_jwjs_bdqy_gy_p1");
                startActivity(intent);

            }
        });

        ImageView LocalImageView2 = (ImageView) view.findViewById(R.id.abb_jwjs_bdqy_gy_2);

        LocalImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageActivity_.class);
                intent.putExtra("image", "abb_jwjs_bdqy_gy_p2");
                startActivity(intent);

            }
        });

        ImageView LocalImageView3 = (ImageView) view.findViewById(R.id.abb_jwjs_bdqy_gy_3);

        LocalImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageActivity_.class);
                intent.putExtra("image", "abb_jwjs_bdqy_gy_p3");
                startActivity(intent);

            }
        });

        ImageView LocalImageView4 = (ImageView) view.findViewById(R.id.abb_jwjs_bdqy_gy_4);

        LocalImageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageActivity_.class);
                intent.putExtra("image", "abb_jwjs_bdqy_gy_p4");
                startActivity(intent);

            }
        });

        ImageView LocalImageView5 = (ImageView) view.findViewById(R.id.abb_jwjs_bdqy_gy_5);

        LocalImageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageActivity_.class);
                intent.putExtra("image", "abb_jwjs_bdqy_gy_p5");
                startActivity(intent);

            }
        });

        ImageView LocalImageView6 = (ImageView) view.findViewById(R.id.abb_jwjs_bdqy_gy_6);

        LocalImageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageActivity_.class);
                intent.putExtra("image", "abb_jwjs_bdqy_gy_p6");
                startActivity(intent);

            }
        });

        ImageView LocalImageView7 = (ImageView) view.findViewById(R.id.abb_jwjs_bdqy_zy_1);

        LocalImageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageActivity_.class);
                intent.putExtra("image", "abb_jwjs_bdqy_zy_p1");
                startActivity(intent);

            }
        });

        ImageView LocalImageView8 = (ImageView) view.findViewById(R.id.abb_jwjs_bdqy_zy_2);

        LocalImageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageActivity_.class);
                intent.putExtra("image", "abb_jwjs_bdqy_zy_p2");
                startActivity(intent);

            }
        });

        ImageView LocalImageView9 = (ImageView) view.findViewById(R.id.abb_jwjs_bdqy_zy_3);

        LocalImageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageActivity_.class);
                intent.putExtra("image", "abb_jwjs_bdqy_zy_p3");
                startActivity(intent);

            }
        });

        ImageView LocalImageView10 = (ImageView) view.findViewById(R.id.abb_jwjs_bdqy_zy_4);

        LocalImageView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageActivity_.class);
                intent.putExtra("image", "abb_jwjs_bdqy_zy_p4");
                startActivity(intent);

            }
        });

        ImageView LocalImageView11 = (ImageView) view.findViewById(R.id.abb_jwjs_bdqy_zy_5);

        LocalImageView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageActivity_.class);
                intent.putExtra("image", "abb_jwjs_bdqy_zy_p5");
                startActivity(intent);

            }
        });

        ImageView LocalImageView12 = (ImageView) view.findViewById(R.id.abb_jwjs_bdqy_zy_6);

        LocalImageView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageActivity_.class);
                intent.putExtra("image", "abb_jwjs_bdqy_zy_p6");
                startActivity(intent);

            }
        });

        ImageView LocalImageView13 = (ImageView) view.findViewById(R.id.abb_jwjs_bdqy_by_1);

        LocalImageView13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageActivity_.class);
                intent.putExtra("image", "abb_jwjs_bdqy_by_p1");
                startActivity(intent);

            }
        });

        ImageView LocalImageView14 = (ImageView) view.findViewById(R.id.abb_jwjs_bdqy_by_2);

        LocalImageView14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageActivity_.class);
                intent.putExtra("image", "abb_jwjs_bdqy_by_p2");
                startActivity(intent);

            }
        });

        ImageView LocalImageView15 = (ImageView) view.findViewById(R.id.abb_jwjs_bdqy_by_3);

        LocalImageView15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageActivity_.class);
                intent.putExtra("image", "abb_jwjs_bdqy_by_p3");
                startActivity(intent);

            }
        });

        ImageView LocalImageView16 = (ImageView) view.findViewById(R.id.abb_jwjs_bdqy_by_4);

        LocalImageView16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageActivity_.class);
                intent.putExtra("image", "abb_jwjs_bdqy_by_p4");
                startActivity(intent);

            }
        });

        ImageView LocalImageView17 = (ImageView) view.findViewById(R.id.abb_jwjs_bdqy_by_5);

        LocalImageView17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageActivity_.class);
                intent.putExtra("image", "abb_jwjs_bdqy_by_p5");
                startActivity(intent);

            }
        });

        ImageView LocalImageView18 = (ImageView) view.findViewById(R.id.abb_jwjs_bdqy_dw_1);

        LocalImageView18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageActivity_.class);
                intent.putExtra("image", "abb_jwjs_bdqy_dw_p1");
                startActivity(intent);

            }
        });

        ImageView LocalImageView19 = (ImageView) view.findViewById(R.id.abb_jwjs_bdqy_dw_2);

        LocalImageView19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageActivity_.class);
                intent.putExtra("image", "abb_jwjs_bdqy_dw_p2");
                startActivity(intent);

            }
        });

        ImageView LocalImageView20 = (ImageView) view.findViewById(R.id.abb_jwjs_bdqy_fd_1);

        LocalImageView20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageActivity_.class);
                intent.putExtra("image", "abb_jwjs_bdqy_fd_p1");
                startActivity(intent);

            }
        });
    }
}
