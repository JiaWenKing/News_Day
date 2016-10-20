package com.example.Adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

public class ImagePagerAdapter extends PagerAdapter {
	private ArrayList<ImageView> viewlist;
	public ImagePagerAdapter(ArrayList<ImageView> viewlist) {
		super();
		this.viewlist = viewlist;
	}
	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		//对viewpager页号求模取出view列表中要显示的项
		position %=viewlist.size();
		if (position<0) {
			position=viewlist.size()+position;
		}
		ImageView view = viewlist.get(position);
		ViewParent vp = view.getParent();
		if (vp!=null) {
			ViewGroup parent=(ViewGroup) vp;
			parent.removeView(view);
		}
		return view;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}
	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		super.destroyItem(container, position, object);
	}

}
