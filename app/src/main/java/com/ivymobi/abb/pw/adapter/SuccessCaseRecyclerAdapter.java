package com.ivymobi.abb.pw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.network.response.SuccessCaseResponse;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by renguangkai on 2016/7/28.
 */
public class SuccessCaseRecyclerAdapter extends RecyclerView.Adapter<SuccessCaseRecyclerAdapter.MyViewHolder> {

    private Context context;
    private List<SuccessCaseResponse.Items> successCaseList;

    public SuccessCaseRecyclerAdapter(Context context, List<SuccessCaseResponse.Items> successCaseList) {
        this.context = context;
        this.successCaseList = successCaseList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_success_case, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SuccessCaseResponse.Data data = successCaseList.get(position).getData();
        holder.titleTv.setText(data.getTitle());
        ImageLoader.getInstance().displayImage(data.getCover().getFile(), holder.coverIv);
        holder.contentTv.setText(Html.fromHtml(data.getContent()));
    }

    @Override
    public int getItemCount() {
        return successCaseList != null ? successCaseList.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTv;
        ImageView coverIv;
        TextView contentTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            titleTv = (TextView) itemView.findViewById(R.id.tv_title);
            coverIv = (ImageView) itemView.findViewById(R.id.iv_cover);
            contentTv = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
