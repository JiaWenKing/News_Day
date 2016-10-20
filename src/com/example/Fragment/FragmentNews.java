package com.example.Fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Adapter.CommonAdapter;
import com.example.model.News;
import com.example.news_day.R;
import com.example.news_day.ShowNewsActivity;
import com.example.news_day.UserinfoActivity;
import com.example.utils.Config;
import com.example.utils.HttpRequestUtils;
import com.example.utils.MyDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 新闻
 * */
public class FragmentNews extends Fragment implements OnItemClickListener{
	Handler mhandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				MyDialog.dismiss();
				mList.clear();
				List<News> newslists = (List<News>) msg.obj;
				mList.addAll(newslists);
				
				adapter.notifyDataSetChanged();
				break;

			default:
				break;
			}
		};
	};
	private ListView listview_news;
	private CommonAdapter<News> adapter;
	public List<News> mList;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.fragment_news, null);
		listview_news = (ListView) view.findViewById(R.id.listview_news);
		listview_news.setOnItemClickListener(this);
		mList = new ArrayList<News>();
		adapter = new CommonAdapter<News>(getActivity(), mList, R.layout.item_news){
			@Override
			public void setViewData(View view, News item) {
				// TODO Auto-generated method stub
				//super.setViewData(currentView, item);
				TextView news_text_biaoti=CommonAdapter.get(view, R.id.news_text_biaoti);
				TextView news_text_neirong=CommonAdapter.get(view, R.id.news_text_neirong);
				ImageView news_img=CommonAdapter.get(view, R.id.news_img);
				news_text_biaoti.setText(item.getTitle());
				news_text_neirong.setText(item.getSummary().trim());
				//新闻图片
		        Picasso.with(getActivity()).load(item.getIcon()).into(news_img);

				
//				if (item.getTitle().length()>=15) {
//					news_text_biaoti.setText(item.getTitle().substring(0, 15)+"...");
//				}else {
//					news_text_biaoti.setText(item.getTitle());
//				}
//				if (item.getSummary().trim().length()>=17) {
//					news_text_neirong.setText(item.getSummary().trim().substring(0, 17)+"...");
//				}else {
//					news_text_neirong.setText(item.getSummary().trim());
//				}
			}
		};
		listview_news.setAdapter(adapter);
		RequestParams params=new RequestParams();
		//ver=0000000&subid=1    &dir=1&nid=1    &stamp=201609211 &cnt=20
		//ver=版本号    &subid=分类名&dir=1&nid=新闻id&stamp=20140321  &cnt=20
		params.put("ver", "0000000");
		params.put("subid", "1");
		params.put("dir", "1");
		params.put("nid", "1");
		params.put("stamp", "201609211");
		MyDialog.showDialog(getActivity());
		HttpRequestUtils.gethttprequest().getrequestarray(Config.news_list, params, mhandler, 1, new TypeToken<List<News>>(){});
		/*AsyncHttpClient ac=new AsyncHttpClient();//通过url请求数据
		ac.get(Config.ip+Config.news_list, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				String str = new String(arg2);
				Gson gson=new Gson();
				 
				try {
					JSONObject	js = new JSONObject(str);
					String string = js.getString("status");
					JSONArray data = js.getJSONArray("data");
					List<News> mList = gson.fromJson(String.valueOf(data), new TypeToken<List<News>>(){}.getType());
					Message msg =mhandler.obtainMessage();
					msg.obj = mList;
					msg.what=1;
					mhandler.sendMessage(msg);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				// TODO Auto-generated method stub
			}
		});*/
		return view;
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		News item = adapter.getItem(arg2);
		Gson gson=new Gson();
		String json = gson.toJson(item);
		Intent intent = new Intent(getActivity(),ShowNewsActivity.class);
		intent.putExtra("json", json);
		startActivity(intent);
		
	}
}
