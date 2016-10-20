package com.example.news_day;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Adapter.StartAdapter;
import com.example.database.UseDb;
import com.example.model.LoginuserBin;
import com.example.model.User;
import com.example.utils.Config;
import com.example.utils.MyDialog;
import com.example.utils.MySharedPreferences;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

/**
 * 引导页
 * */
public class StartActivity extends Activity {
	Handler mhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				 MyDialog.dismiss();
				Intent intent = new Intent(StartActivity.this,
						MainActivity.class);
				startActivity(intent);
				StartActivity.this.finish();
				User user = new User();
				user.setUser_name(username);
				user.setUser_pwd(userpwd);
				
//				UseDb ud=new UseDb(StartActivity.this);
//				ud.deleteWhere(LoginuserBin.class, "token=?", new String[]{"c0e42744f39cede3884024e41d99be04"});
				break;
			}
		};
	};
	ViewPager vp;
	private SharedPreferences sp;
	private ImageView start_img;
	int[] res = new int[] { R.drawable.small, R.drawable.bd,
			R.drawable.welcome, R.drawable.wy };
	private String username;
	private String userpwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		((MyApp)getApplication()).AddActivity(this);
		
		//初始化图片缓存组件
		 //加载image,用到imageload
		 DisplayImageOptions defaultOptions = new
		 DisplayImageOptions.Builder()
		 .cacheInMemory(true)
		 .cacheOnDisc(true)
		 .build();
		 ImageLoaderConfiguration config = new
		 ImageLoaderConfiguration.Builder(StartActivity.this)
		 .threadPoolSize(3) // default
		 .threadPriority(Thread.NORM_PRIORITY - 1) // default
		 .tasksProcessingOrder(QueueProcessingType.LIFO)
		 .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
		 .memoryCacheSizePercentage(13) // default
		 .defaultDisplayImageOptions(defaultOptions)
		 .writeDebugLogs() // Remove for release app
		 .build();
		 // Initialize ImageLoader with configuration.
		 ImageLoader.getInstance().init(config);

		 
		View decorView = getWindow().getDecorView();// 隐藏标头
		int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);// 隐藏标头
		setContentView(R.layout.start_activity);
		sp = getSharedPreferences("sp", Context.MODE_PRIVATE);
		initView();

	}

	private void initView() {
		// TODO Auto-generated method stub
		vp = (ViewPager) findViewById(R.id.start_vp);
		start_img = (ImageView) findViewById(R.id.start_img);
		// 判断是否第一次登录
		if (getisstart() == 0) {
			// 1
			vp.setVisibility(View.VISIBLE);
			start_img.setVisibility(View.GONE);
			List<View> views = loadViewpagerData();
			StartAdapter adapetr = new StartAdapter(views);
			vp.setAdapter(adapetr);
			View view = views.get(views.size() - 1);
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					mhandler.sendEmptyMessage(1);
				}
			});

			isstart();
		} else {
			MySharedPreferences msp = new MySharedPreferences(
					StartActivity.this);
			// 如果没点退出登录
			if (msp.getisexit()) {

				username = msp.getusername();
				userpwd = msp.getuserpwd();
				AsyncHttpClient ac = new AsyncHttpClient();
				RequestParams params = new RequestParams();
				params.put("uid", username);
				params.put("pwd", userpwd);
				params.put("ver", 1);
				params.put("device", 0);
				ac.get(Config.ip + Config.user_login, params,
						new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, Header[] arg1,
									byte[] arg2) {
								// TODO Auto-generated method stub
								String str = new String(arg2);
								try {
									JSONObject obj = new JSONObject(str);
									int i = obj.getInt("status");
									if (i == 0) {
										JSONObject jo = obj
												.getJSONObject("data");
										int result = jo.getInt("result");
										String token = jo.getString("token");
										System.out.println(token);
										String explain = jo
												.getString("explain");
										User user = new User();
										user.setUser_name(username);
										user.setUser_pwd(userpwd);
										user.setToken(token);
										MyApp myapp = (MyApp) StartActivity.this
												.getApplication();
										myapp.user = user;
										if (result == 0) {

											mhandler.postDelayed(
													new Runnable() {

														@Override
														public void run() {
															// TODO
															// Auto-generated
															// method stub

															mhandler.sendEmptyMessage(1);
														}
													}, 1000);
										} else {
											MyDialog.dismiss();
											Toast.makeText(StartActivity.this,
													explain, Toast.LENGTH_SHORT).show();
										}
									} else {
										MyDialog.dismiss();
										Toast.makeText(StartActivity.this,
												"用户名或密码错误", Toast.LENGTH_SHORT).show();
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

							@Override
							public void onFailure(int arg0, Header[] arg1,
									byte[] arg2, Throwable arg3) {
								// TODO Auto-generated method stub

							}
						});

				// 2
				// vp.setVisibility(View.GONE);
				// start_img.setVisibility(View.VISIBLE);
				// MyDialog.showDialog(this);

			} else {
				mhandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						mhandler.sendEmptyMessage(1);
					}
				}, 1000);
			}

		}
	}

	public int getisstart() {
		return sp.getInt("isstart", 0);
	}

	public void isstart() {
		Editor edit = sp.edit();
		edit.putInt("isstart", 1);
		edit.commit();
	}

	public List<View> loadViewpagerData() {
		List<View> views = new ArrayList<View>();
		for (int i = 0; i < res.length; i++) {
			ImageView img = new ImageView(this);
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			img.setLayoutParams(params);
			img.setScaleType(ScaleType.FIT_XY);
			img.setImageResource(res[i]);
			views.add(img);
		}
		return views;
	}
}
