package com.example.Adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
/**
 * Viewpager适配器
 * */
public class MyFragmentAdapter extends PagerAdapter {
	FragmentManager fm;
	List<Fragment> fragments;
	
	public MyFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
		super();
		this.fm = fm;
		this.fragments = fragments;
	}
	/**
	 * 数据展示
	 * */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		Fragment fragment = fragments.get(position);
		FragmentTransaction bt = fm.beginTransaction();//开启事务
		if (!fragment.isAdded()) {
			bt.add(fragment, fragment.getClass().getSimpleName());//添加事务
			bt.commit();//提交
			fm.executePendingTransactions();//立即执行
		}
		View view = fragment.getView();//得到布局
		if (view.getParent()==null) {
			container.addView(view);
		}
		return view;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragments.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}
	/**
	 * 销毁界面
	 * */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		//super.destroyItem(container, position, object);
		container.removeView((View)object);
	}

}
