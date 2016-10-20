package com.example.Fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Adapter.CommonAdapter;
import com.example.model.CommentInfo;
import com.example.news_day.R;
import com.example.utils.Config;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentComment extends Fragment {
	Handler mhandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 9:
				list.clear();
				List<CommentInfo> ls=(List<CommentInfo>) msg.obj;
				list.addAll(ls);
				adapter.notifyDataSetChanged();
				break;

			default:
				break;
			}
		};
	};
	private CommonAdapter<CommentInfo> adapter;
	private List<CommentInfo> list;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v=inflater.inflate(R.layout.fragment_comment, null);
		ListView lv_comment=(ListView) v.findViewById(R.id.lv_comment);
	list = new ArrayList<CommentInfo>();
		adapter = new CommonAdapter<CommentInfo>(getActivity(), list, R.layout.item_news){
			@Override
			public void setViewData(View currentView, CommentInfo item) {
				// TODO Auto-generated method stub
				super.setViewData(currentView, item);
			ImageView news_img=	CommonAdapter.get(currentView, R.id.news_img);
			TextView news_text_biaoti=	CommonAdapter.get(currentView, R.id.news_text_biaoti);
			TextView news_text_neirong=	CommonAdapter.get(currentView, R.id.news_text_neirong);
		Picasso.with(getActivity()).load(item.getPortrait()).into(news_img);
		news_text_biaoti.setText(item.getUid());
		news_text_neirong.setText(item.getStamp());
			}
		};
		lv_comment.setAdapter(adapter);
		AsyncHttpClient ac=new AsyncHttpClient();
		RequestParams params=new RequestParams();
		//ver=1&nid=20&type=1&stamp=20160617&dir=0&cid=111
		params.put("ver", "1");
		params.put("nid", "20");
		params.put("type", "1");
		params.put("stamp", "20160617");
		params.put("dir", "0");
		params.put("cid", "111");
		ac.get(Config.ip+Config.cmt_list, params, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				try {
					JSONObject json=new JSONObject(new String(arg2));
					int status = json.getInt("status");
					if (status==0) {
						JSONArray jsonarray = json.getJSONArray("data");
						Type type=new TypeToken<List<CommentInfo>>(){}.getType();
						Gson gson=new Gson();
					List<CommentInfo> list=	gson.fromJson(jsonarray.toString(), type);
					Message message = mhandler.obtainMessage();
					message.obj=list;
					message.what=9;
					mhandler.sendMessage(message);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				// TODO Auto-generated method stub
				
			}
		});
		return v;
	}
	
	
}
