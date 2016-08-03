package com.ivymobi.abb.pw.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.adapter.LocalEnterpriseRecyclerAdapter;
import com.ivymobi.abb.pw.network.CachedFileEnum;
import com.ivymobi.abb.pw.network.response.LocalIntrosResponse;
import com.ivymobi.abb.pw.util.SerializationUtil;

import org.androidannotations.annotations.EFragment;

@EFragment
public class LocalFragmentContent extends Fragment {

    private RecyclerView localEnterpriseLv;
    private LocalEnterpriseRecyclerAdapter mAdapter;

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
        View view = inflater.inflate(R.layout.local_fragment_layout, container, false);
        localEnterpriseLv = (RecyclerView) view.findViewById(R.id.local_enterprise_lv);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LocalIntrosResponse response = SerializationUtil.getObject(getActivity(), CachedFileEnum.BUSINESS_LOCAL.getNameEtag());
        localEnterpriseLv.setLayoutManager(new LinearLayoutManager(getContext()));
        localEnterpriseLv.setAdapter(mAdapter = new LocalEnterpriseRecyclerAdapter(getContext(), response.getItems()));
    }
}
