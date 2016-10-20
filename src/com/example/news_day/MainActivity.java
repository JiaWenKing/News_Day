package com.example.news_day;

import java.util.ArrayList;
import java.util.List;

import com.example.Adapter.MyFragmentAdapter;
import com.example.Fragment.FragmentCollection;
import com.example.Fragment.FragmentComment;
import com.example.Fragment.FragmentImage;
import com.example.Fragment.FragmentNews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener{
	ViewPager vp;
	private List<Fragment> fragments;
	private RadioGroup home_rg;
	private FragmentManager fm;
	private MyFragmentAdapter adapter;
	private TextView head_text;
	String[] str=new String[]{"新闻","收藏","跟帖","图片"};
	public FragmentNews fragmentnews;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((MyApp)getApplication()).AddActivity(this);
		fragmentnews = new FragmentNews();
		setContentView(R.layout.activity_main);
		getFragments();
		initHead();
		initView();
	}
	private void initHead() {
		// TODO Auto-generated method stub
		ImageButton head_left=(ImageButton) findViewById(R.id.head_left);
		head_left.setVisibility(View.GONE);
		head_text = (TextView) findViewById(R.id.head_text);
		ImageButton head_right=(ImageButton) findViewById(R.id.head_right);
		head_right.setOnClickListener(this);
		
	}
	private void initView() {
		// TODO Auto-generated method stub
		vp=(ViewPager) findViewById(R.id.home_vp);
		home_rg = (RadioGroup) findViewById(R.id.home_rg);
		home_rg.check(R.id.rb_news);
		vp.setOnPageChangeListener(new OnPageChangeListener() {
			/**
			 * viewpager监听
			 * */
			@Override
			public void onPageSelected(int index) {
				// TODO Auto-generated method stub
				if (index==0) {
					home_rg.check(R.id.rb_news);
				}else if (index==1) {
					home_rg.check(R.id.rb_collection);
				}else if (index==2) {
					home_rg.check(R.id.rb_comment);
				}else if (index==3) {
					home_rg.check(R.id.rb_image);
				}
				head_text.setText(str[index]);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		home_rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			/**
			 * radiogroup监听
			 * */
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				if (arg1==R.id.rb_news) {
					vp.setCurrentItem(0);//设置viewpager展示布局
					head_text.setText(str[0]);
				}else if (arg1==R.id.rb_collection) {
					vp.setCurrentItem(1);
					head_text.setText(str[1]);
				}else if (arg1==R.id.rb_comment) {
					vp.setCurrentItem(2);
					head_text.setText(str[2]);
				}else if (arg1==R.id.rb_image) {
					vp.setCurrentItem(3);
					head_text.setText(str[3]);
				}
			}
		});
		
		fm = getSupportFragmentManager();
		adapter = new MyFragmentAdapter(fm, fragments);
		vp.setAdapter(adapter);
	}
	public List<Fragment> getFragments(){
		fragments = new ArrayList<Fragment>();
		fragments.add(fragmentnews);
		fragments.add(new FragmentCollection());
		fragments.add(new FragmentComment());
		fragments.add(new FragmentImage());
		return fragments;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId()==R.id.head_right) {
			MyApp app=(MyApp)getApplication();
			if (app.user==null) {
				jump(LogActivity.class);//当用户等于空可以进去输入
			}else{
				jump(UserinfoActivity.class);
			}
		}
	}
	public void jump(Class c){
		Intent intent=new Intent(this,c);
		startActivity(intent);
	} 
	
	long i=0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if (keyCode == 4) {      
			if (System.currentTimeMillis()-i>1500) {
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				i=System.currentTimeMillis();
			}else {
				((MyApp)getApplication()).OutActivity();
			}
	    }    
		return true;
	}
}
