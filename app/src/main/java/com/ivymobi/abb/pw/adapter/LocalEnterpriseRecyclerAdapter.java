package com.ivymobi.abb.pw.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.network.response.ActivitiesResponse;
import com.ivymobi.abb.pw.network.response.LocalIntrosResponse;

import java.util.List;

/**
 * 本地企业分类列表的adapter
 * Created by renguangkai on 2016/7/28.
 */
public class LocalEnterpriseRecyclerAdapter extends RecyclerView.Adapter<LocalEnterpriseRecyclerAdapter.MyViewHolder> {

    private Context context;
    private int width, height;
    private List<LocalIntrosResponse.Item> enterpriseList;

    public LocalEnterpriseRecyclerAdapter(Context context, List<LocalIntrosResponse.Item> enterpriseList) {
        this.context = context;
        this.enterpriseList = enterpriseList;
        width = ((Activity)context).getWindowManager().getDefaultDisplay().getWidth();
        height = ((Activity)context).getWindowManager().getDefaultDisplay().getHeight();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_local_enterprise_category, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        LocalIntrosResponse.Data data = enterpriseList.get(position).getData();
        holder.categoryTv.setText(data.getCategory());
        holder.categoryRcylv.setLayoutManager(new GridLayoutManager(context, 2));
        List<LocalIntrosResponse.Data.Company> companies = data.getCompanies();
        int companyCount = companies.size();
        LinearLayout.LayoutParams llParmas = new LinearLayout.LayoutParams(width - 20, (int)((width- 20) * Math.ceil(companyCount) * 0.25));
        holder.categoryRcylv.setLayoutParams(llParmas);
        holder.categoryRcylv.setAdapter(new LocalEnterpriseCompanyAdapter(context, data.getCompanies()));
    }

    @Override
    public int getItemCount() {
        if (enterpriseList == null || (enterpriseList != null && enterpriseList.size() == 0))   return 0;
        else return enterpriseList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView categoryTv;
        private RecyclerView categoryRcylv;

        public MyViewHolder(View itemView) {
            super(itemView);
            categoryTv = (TextView) itemView.findViewById(R.id.tv_enterprise_category);
            categoryRcylv = (RecyclerView) itemView.findViewById(R.id.rcyclv_enterprise_category);
        }
    }
}
