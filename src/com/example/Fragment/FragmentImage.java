package com.example.Fragment;

import java.util.List;
import com.example.Adapter.ImageAdapter;
import com.example.model.News;
import com.example.news_day.MainActivity;
import com.example.news_day.R;
import com.example.news_day.ShowNewsActivity;
import com.google.gson.Gson;
import com.litesuits.orm.log.Log;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
/**
 * Í¼Æ¬½çÃæ
 * */
public class FragmentImage extends Fragment implements OnItemClickListener{
	private ImageAdapter adapter;
	private ListView lv_img_news;
	private List<News> mList;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_image, null);
		lv_img_news = (ListView) view.findViewById(R.id.lv_img_news);
		lv_img_news.setOnItemClickListener(this);
		
		MainActivity activity = (MainActivity) getActivity();
		FragmentNews news = activity.fragmentnews;
		mList = news.mList;
		adapter = new ImageAdapter(mList,getActivity());
		lv_img_news.setAdapter(adapter);
		
		return view;
	}
	//¼àÌý
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		News news = (News) adapter.getItem(position);
		Gson gson=new Gson();
		String json = gson.toJson(news);
		Intent intent = new Intent(getActivity(),ShowNewsActivity.class);
		intent.putExtra("json", json);
		startActivity(intent);
	}

}
