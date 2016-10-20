package com.example.Adapter;

import java.util.List;

import com.example.model.User;
import com.example.news_day.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LoginbinAdapter extends BaseAdapter {
	LayoutInflater layout;
	List<User> list;

	public LoginbinAdapter(List<User> list ,Context context) {
		super();
		this.list = list;
		layout = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View v, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View view = layout.inflate(R.layout.log_item, null);
		TextView tv_log_user=(TextView) view.findViewById(R.id.tv_log_user);
		String user_name = list.get(arg0).getUser_name();
		tv_log_user.setText(user_name);
		return v;
	}

}
