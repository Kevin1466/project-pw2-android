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
import com.ivymobi.abb.pw.adapter.SuccessCaseRecyclerAdapter;
import com.ivymobi.abb.pw.network.CachedFileEnum;
import com.ivymobi.abb.pw.network.response.SuccessCaseResponse;
import com.ivymobi.abb.pw.util.SerializationUtil;

import java.util.List;

public class CaseFragmentContent extends Fragment {

    private RecyclerView successCaseLv;
    private SuccessCaseRecyclerAdapter mAdapter;

    public static Fragment getInstance(Bundle bundle) {
        CaseFragmentContent fragment = new CaseFragmentContent();
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
        View view = inflater.inflate(R.layout.case_fragment_layout, container, false);
        successCaseLv = (RecyclerView) view.findViewById(R.id.success_case_lv);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SuccessCaseResponse response =SerializationUtil.getObject(getActivity(), CachedFileEnum.BUSINESS_CASE.getNameEtag());
        List<SuccessCaseResponse.Items> successCaseList = response.getItems();
        successCaseLv.setLayoutManager(new LinearLayoutManager(getContext()));
        successCaseLv.setAdapter(mAdapter = new SuccessCaseRecyclerAdapter(getContext(), successCaseList));
    }

}
