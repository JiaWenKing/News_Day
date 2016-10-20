package com.example.Fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.Adapter.CommonAdapter;
import com.example.database.LiteDb;
import com.example.model.CollectionNews;
import com.example.model.News;
import com.example.news_day.MyApp;
import com.example.news_day.R;
import com.example.news_day.ShowNewsActivity;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 收藏
 * */
public class FragmentCollection extends Fragment implements OnItemLongClickListener,OnItemClickListener{
private CommonAdapter<CollectionNews> adapter;
private ListView lv_collection;
	//	private CommonAdapter<News> adapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.fragment_collection, null);
		lv_collection = (ListView) view.findViewById(R.id.lv_collection);
		lv_collection.setOnItemClickListener(this);
		lv_collection.setOnItemLongClickListener(this);
		app = (MyApp) getActivity().getApplication();
		list = new ArrayList<CollectionNews>();
		return view;
	}
	@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			getdata();
			
		}
	private void getdata() {
		litedb = new LiteDb(getActivity());
		if (app.user==null) {
			Toast.makeText(getActivity(), "请登录查看", Toast.LENGTH_SHORT).show();
			return;
		}
		list = litedb.getQueryByWhere(CollectionNews.class, "token=?", new String[]{app.user.getToken()});
		
		adapter = new CommonAdapter<CollectionNews>(getActivity(),list , R.layout.item_news){
			@Override
			public void setViewData(View view, CollectionNews item) {
				// TODO Auto-generated method stub
//				super.setViewData(currentView, item);
				TextView news_text_biaoti=CommonAdapter.get(view, R.id.news_text_biaoti);
				TextView news_text_neirong=CommonAdapter.get(view, R.id.news_text_neirong);
				ImageView news_img=CommonAdapter.get(view, R.id.news_img);
				news_text_biaoti.setText(item.getTitle());
				news_text_neirong.setText(item.getSummary().trim());
				//新闻图片
		        Picasso.with(getActivity()).load(item.getIcon()).into(news_img);
			}
		};
		lv_collection.setAdapter(adapter);
//		adapter.notifyDataSetChanged();
	}
	int index=-1;
	private List<CollectionNews> list;
	private LiteDb litedb;
	private MyApp app;
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
			
			index=position;
			
			Builder dialog = new AlertDialog.Builder(getActivity());
			dialog.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					// TODO Auto-generated method stub
				}
			});//点击空白处退出
			
			dialog.setTitle("提示")
			.setMessage("是否删除？")
			.setPositiveButton("是", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					CollectionNews news = list.get(index);
					litedb.deleteWhere(CollectionNews.class, "nid=? and token=?", new String[]{news.getNid()+"",app.user.getToken()});
					adapter.notifyDataSetChanged();
					getdata();
				}
			}).setNegativeButton("真是", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			AlertDialog createdialog = dialog.create();
			createdialog.show();
	
		return true;
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
			News item = adapter.getItem(position);
			Gson gson=new Gson();
			String json = gson.toJson(item);
			Intent intent = new Intent(getActivity(),ShowNewsActivity.class);
			intent.putExtra("json", json);
			startActivity(intent);
		
	}
}
