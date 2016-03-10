package com.ivymobi.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.artifex.mupdfdemo.MuPDFCore;
import com.artifex.mupdfdemo.R;



public class HorizontalScrollViewAdapter
{

	public interface IBitmapGetterDelegate{
		Bitmap getBitmap(int pos);
	}

	private IBitmapGetterDelegate mBitmapDelegate;

	public void setBitmapDelegate(IBitmapGetterDelegate bitmapGetterDelegate){
		mBitmapDelegate = bitmapGetterDelegate;
	}

	private Context mContext;
	private LayoutInflater mInflater;
	private List<Integer> mDatas;

	private int mCount;

	MyHorizontalScrollView mScrollView;

	public void setScrollView(MyHorizontalScrollView view){
		mScrollView = view;
	}

	MuPDFCore mCore;

	int mFirstIndex;

	public HorizontalScrollViewAdapter(Context context, MuPDFCore core, int firstIndex)
	{
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		this.mCore = core;
		this.mFirstIndex = firstIndex;
		this.mCount = mCore.countPages();
	}

	public HorizontalScrollViewAdapter(Context context, int count)
	{
		this.mContext = context;
		this.mCount = count;
		mInflater = LayoutInflater.from(context);
	}

//	public HorizontalScrollViewAdapter(Context context, List<Integer> mDatas)
//	{
//		this.mContext = context;
//		mInflater = LayoutInflater.from(context);
//		this.mDatas = mDatas;
//	}

	public int getCount()
	{
		return mCount;
	}

	public Object getItem(int position)
	{
		return position;
	}

	public long getItemId(int position)
	{
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder = null;
		if (convertView == null)
		{
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(
					R.layout.activity_index_gallery_item, parent, false);
			viewHolder.mImg = (ImageView) convertView
					.findViewById(R.id.id_index_gallery_item_image);
			viewHolder.mText = (TextView) convertView
					.findViewById(R.id.id_index_gallery_item_text);

			convertView.setTag(viewHolder);
		} else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.mImg.setImageBitmap(mBitmapDelegate.getBitmap(position));

//		viewHolder.mImg.setImageResource(mDatas.get(position));

		String text = "第" + Integer.toString(position + 1) + "页";
		viewHolder.mText.setText(text);


		if(mScrollView.getSelIndex() == position){
			mScrollView.setSelView(convertView);
		}

//		convertView.setBackgroundColor(Color.parseColor("#21212178"));

		return convertView;
	}

	private class ViewHolder
	{
		ImageView mImg;
		TextView mText;
	}

//	Bitmap getBitmap(int pos){
//		int w = mContext.getResources().getDimensionPixelOffset(R.dimen.thumnail_default_width);
//		int h = mContext.getResources().getDimensionPixelSize(R.dimen.thumnail_default_height);
//
//		Bitmap b = mCore.drawPage(pos, w, h, 0, 0, w, h);
//
//		return b;
//	}

}
