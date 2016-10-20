package com.example.news_day;

import java.util.List;

import com.example.database.LiteDb;
import com.example.model.CollectionNews;
import com.example.model.News;
import com.example.model.User;
import com.example.utils.MyDialog;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
/**
 * ��ʾ������Ϣ
 * */
public class ShowNewsActivity extends Activity implements OnClickListener{
	private WebView web_news;
	private LiteDb litedb;
	private News news;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		((MyApp)getApplication()).AddActivity(this);
		setContentView(R.layout.show_news_activity);
		litedb = new LiteDb(getApplicationContext());
		initHead();
		initView();
	}
	private void initView() {
		
		web_news = (WebView) findViewById(R.id.web_news);
		String json = getIntent().getStringExtra("json");
		Gson gson= new Gson();
		news = gson.fromJson(json, News.class);
		web_news.loadUrl(news.getLink());
	}
	private void initHead() {
		// TODO Auto-generated method stub
		ImageButton head_left=(ImageButton) findViewById(R.id.head_left);
		head_left.setVisibility(View.VISIBLE);
		TextView head_text = (TextView) findViewById(R.id.head_text);
		head_text.setText("��ѯ");
		ImageButton head_right=(ImageButton) findViewById(R.id.head_right);
		head_right.setVisibility(View.VISIBLE);
		head_right.setBackgroundResource(R.drawable.plus);
		head_left.setOnClickListener(this);
		head_right.setOnClickListener(this);
		
		
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0.getId()==R.id.head_left) {
			this.finish();
		}else if (arg0.getId()==R.id.head_right) {
			CollectionNews cn=new CollectionNews();
			cn.setIcon(news.getIcon());
			cn.setStamp(news.getStamp());
			cn.setNid(news.getNid());
			cn.setTitle(news.getTitle());
			cn.setSummary(news.getSummary());
			cn.setLink(news.getLink());
			MyApp app = (MyApp) getApplication();
			User user = app.user;
			if (user==null) {
				Toast.makeText(this, "���¼",Toast.LENGTH_SHORT).show();
				return;
			}
			cn.setToken(user.getToken());
			List<CollectionNews> list = litedb.getQueryByWhere(CollectionNews.class, "nid = ? and token=?", new String[]{news.getNid()+"",user.getToken()});
			if (list!=null&&list.size()>0) {
				Toast.makeText(this, "���ղ� �����ظ��ղ�",Toast.LENGTH_SHORT).show();
			}else{
				litedb.Add(cn);
				Toast.makeText(this, "�ղسɹ�",Toast.LENGTH_SHORT).show();
			}
		}
	}
}
