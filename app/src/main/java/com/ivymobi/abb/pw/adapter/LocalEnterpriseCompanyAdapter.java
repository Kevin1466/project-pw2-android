package com.ivymobi.abb.pw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.network.response.LocalIntrosResponse;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 本地企业某一分类下企业列表的adapter
 * Created by renguangkai on 2016/7/29.
 */
public class LocalEnterpriseCompanyAdapter extends RecyclerView.Adapter<LocalEnterpriseCompanyAdapter.MyViewHolder> {

    private Context context;
    private List<LocalIntrosResponse.Data.Company> companyList;

    public LocalEnterpriseCompanyAdapter(Context context, List<LocalIntrosResponse.Data.Company> companyList) {
        this.context = context;
        this.companyList = companyList;
        System.out.println("---LocalEnterpriseCompanyAdapter---" + companyList.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(context).inflate(R.layout.item_local_enterprise, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.iv_company_title.setText(companyList.get(position).getTitle());
        ImageLoader.getInstance().displayImage(companyList.get(position).getCover().getFile(), holder.iv_company_company);
    }

    @Override
    public int getItemCount() {
        System.out.println("---getItemCount---" + companyList.size());
        if (companyList == null || (companyList != null && companyList.size() == 0))    return 0;
        else return companyList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView iv_company_title;
        private ImageView iv_company_company;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_company_title = (TextView) itemView.findViewById(R.id.iv_company_title);
            iv_company_company = (ImageView) itemView.findViewById(R.id.tv_company_picture);
        }
    }
}
