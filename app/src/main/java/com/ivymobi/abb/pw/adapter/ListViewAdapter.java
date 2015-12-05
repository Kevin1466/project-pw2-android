package com.ivymobi.abb.pw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.beans.Collection;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    private Context context;
    private List<Collection> beans;

    public List<Integer> selectedList;

    class ViewHolder {

        TextView tvName;
        CheckBox cb;
    }

    public ListViewAdapter(Context context, List<Collection> collections, List<Integer> selectedList) {
        this.beans = collections;
        this.context = context;
        this.selectedList = selectedList;
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public Object getItem(int position) {
        return beans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Collection bean = beans.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.collection_item, null);
            holder = new ViewHolder();
            holder.cb = (CheckBox) convertView.findViewById(R.id.checkBox);
            holder.tvName = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Integer collectionId = beans.get(position).getId().intValue();

        holder.tvName.setText(bean.getName());
        holder.cb.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if (selectedList.contains(collectionId)) {
                    selectedList.remove(collectionId);
                } else {
                    selectedList.add(collectionId);
                }
            }
        });

        holder.cb.setChecked(selectedList.contains(collectionId));
        return convertView;
    }

    public List<Integer> getSelectedList() {
        return selectedList;
    }
}
