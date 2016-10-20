package com.example.news_day;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Adapter.CommonAdapter;
import com.example.database.UseDb;
import com.example.model.LoginlogBin;
import com.example.model.LoginuserBin;
import com.example.model.News;
import com.example.model.Userlog;
import com.example.model.Userlogbin;
import com.example.utils.Config;
import com.example.utils.HttpRequestUtils;
import com.example.utils.Imageutils;
import com.example.utils.MyDialog;
import com.example.utils.MySharedPreferences;
import com.example.utils.imageutils.CropImageActivity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.litesuits.orm.LiteOrm;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * 用户界面
 * */
public class UserinfoActivity extends Activity implements OnClickListener,
		OnLongClickListener {
	private Userlog log;
	private String portrait;
	public static final String IMAGE_PATH = "Jokeep";
	private static String localTempImageFileName = "";
	private static final int FLAG_CHOOSE_IMG = 5;
	private static final int FLAG_CHOOSE_PHONE = 6;
	private static final int FLAG_MODIFY_FINISH = 7;
	public static final File FILE_SDCARD = Environment
			.getExternalStorageDirectory();
	public static final File FILE_LOCAL = new File(FILE_SDCARD, IMAGE_PATH);
	public static final File FILE_PIC_SCREENSHOT = new File(FILE_LOCAL,
			"images/screenshots");
	Handler mhandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				log = (Userlog) msg.obj;
				List<Userlogbin> userlogbin = log.getloginlog();
				tv_username.setText(log.getUid());
				tv_jifen2.setText(log.getIntegration() + "");
				tv_gentie.setText(log.getComnum() + "");
				// 初始化完毕，开始加载图片
				ImageLoader.getInstance().displayImage(log.getPortrait(),
						img_head);
				// 加载图片
				// portrait = log.getPortrait();
				// Picasso.with(UserinfoActivity.this).load(portrait).into(img_head);

				// 存值 切换用户使用
				LoginuserBin lb = new LoginuserBin();// 值
				lb.setUser_name(((MyApp) getApplication()).user.getUser_name());
				lb.setUser_pwd(((MyApp) getApplication()).user.getUser_pwd());
				lb.setToken(((MyApp) getApplication()).user.getToken());
				lb.setIcon(log.getPortrait());
				UseDb ud = new UseDb(UserinfoActivity.this);// 数据库
				List<LoginuserBin> where = ud.getQueryByWhere(
						LoginuserBin.class, "token=?",
						new String[] { ((MyApp) getApplication()).user
								.getToken() });
				if (where.size() == 0) {
					ud.Add(lb);
				}

				mList.clear();
				mList.addAll(userlogbin);
				adapter.notifyDataSetChanged();
				break;
			case 2:
				Toast.makeText(UserinfoActivity.this, explain,
						Toast.LENGTH_SHORT).show();
				MyDialog.dismiss();
				String path = (String) msg.obj;
				Bitmap b = BitmapFactory.decodeFile(path);
				img_head.setImageBitmap(b);
				LoginuserBin lb1 = new LoginuserBin();// 值
				lb1.setUser_name(((MyApp) getApplication()).user.getUser_name());
				lb1.setUser_pwd(((MyApp) getApplication()).user.getUser_pwd());
				lb1.setToken(((MyApp) getApplication()).user.getToken());
				lb1.setIcon(path);
				UseDb ud1 = new UseDb(UserinfoActivity.this);// 数据库
				List<LoginuserBin> where1 = ud1.getQueryByWhere(
						LoginuserBin.class, "token=?",
						new String[] { ((MyApp) getApplication()).user
								.getToken() });
				if (where1.size() == 0) {
					ud1.Add(lb1);
				}
				break;
			default:
				break;
			}
		};
	};
	private TextView tv_username;
	private TextView tv_jifen2;
	private TextView tv_gentie;
	private String uid;
	private int integration;
	private int comnum;
	private ListView lv_logmessage;
	private List<Userlogbin> mList;
	private CommonAdapter<Userlogbin> adapter;
	private com.example.View.CircularImage img_head;
	private Dialog dialog;
	private String explain;
	private String path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		((MyApp) getApplication()).AddActivity(this);
		setContentView(R.layout.user_info_activity);
		initHead();
		initView();
		Home();
	}

	private void initView() {
		img_head = (com.example.View.CircularImage) findViewById(R.id.img_head);
		img_head.setOnClickListener(this);
		img_head.setOnLongClickListener(this);

		tv_username = (TextView) findViewById(R.id.tv_username);
		tv_jifen2 = (TextView) findViewById(R.id.tv_jifen2);
		tv_gentie = (TextView) findViewById(R.id.tv_gentie);
		lv_logmessage = (ListView) findViewById(R.id.lv_logmessage);

		findViewById(R.id.bt_exitlog).setOnClickListener(this);

	}

	private void Home() {
		mList = new ArrayList<Userlogbin>();
		adapter = new CommonAdapter<Userlogbin>(this, mList,
				R.layout.userinfo_item) {
			@Override
			public void setViewData(View view, Userlogbin item) {
				// TODO Auto-generated method stub
				// super.setViewData(currentView, item);
				TextView tv_didian = CommonAdapter.get(view, R.id.tv_didian);
				TextView tv_time = CommonAdapter.get(view, R.id.tv_time);
				tv_didian.setText(item.getAddress() + "-");
				tv_time.setText(item.getTime());
			}
		};

		lv_logmessage.setAdapter(adapter);
		// ver=版本号&imei=手机标识符&token =用户令牌
		MyApp myapp = (MyApp) this.getApplication();
		String token = myapp.user.getToken();
		String Imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
				.getDeviceId();
		RequestParams params = new RequestParams();
		params.put("token", token);
		params.put("ver", 0000000);
		params.put("imei", Imei);
		AsyncHttpClient ac = new AsyncHttpClient();
		// 封装调用
		HttpRequestUtils.gethttprequest().getrequestdata(Config.user_home,
				params, mhandler, 1, Userlog.class);
		/*
		 * ac.get(Config.ip+Config.user_home, params, new
		 * AsyncHttpResponseHandler() {
		 * 
		 * @Override public void onSuccess(int arg0, Header[] arg1, byte[] arg2)
		 * { // TODO Auto-generated method stub String str=new String(arg2); try
		 * { JSONObject jo=new JSONObject(str); int i = jo.getInt("status"); if
		 * (i==0) { JSONObject obj = jo.getJSONObject("data"); //gson解析 Type
		 * type = new TypeToken<Userlog>(){}.getType(); Gson gson=new Gson();
		 * Userlog userlog = gson.fromJson(obj.toString(), type); Message
		 * message = mhandler.obtainMessage(); message.obj=userlog;
		 * message.what=1; mhandler.sendMessage(message); } } catch
		 * (JSONException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } }
		 * 
		 * @Override public void onFailure(int arg0, Header[] arg1, byte[] arg2,
		 * Throwable arg3) { // TODO Auto-generated method stub
		 * 
		 * } });
		 */

	}

	private void initHead() {
		// TODO Auto-generated method stub
		ImageButton head_left = (ImageButton) findViewById(R.id.head_left);
		head_left.setVisibility(View.VISIBLE);
		TextView head_text = (TextView) findViewById(R.id.head_text);
		head_text.setText("个人中心");
		ImageButton head_right = (ImageButton) findViewById(R.id.head_right);
		head_right.setVisibility(View.GONE);
		head_left.setOnClickListener(this);

	}

	public void jump(Class c) {
		Intent intent = new Intent(this, c);
		startActivity(intent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == 4) {
			UserinfoActivity.this.finish();
			jump(MainActivity.class);
		}
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0.getId() == R.id.head_left) {
			UserinfoActivity.this.finish();
			// jump(MainActivity.class);
		} else if (arg0.getId() == R.id.bt_exitlog) {
			MyApp app = (MyApp) getApplication();
			app.user = null;
			MySharedPreferences msp = new MySharedPreferences(
					UserinfoActivity.this);
			msp.setisexit(false);// 退出程序
			Toast.makeText(UserinfoActivity.this, "已下线", Toast.LENGTH_SHORT)
					.show();
			UserinfoActivity.this.finish();
			// jump(LogActivity.class);
		} else if (arg0.getId() == R.id.img_head) {
			MyDialog.showimageDialog(UserinfoActivity.this, log.getPortrait());
		} else if (arg0.getId() == R.id.bt_photograph) {
			dialog.dismiss();
			OppenCamera();
		} else if (arg0.getId() == R.id.bt_album) {
			dialog.dismiss();
			OppenPhoto();
		} else if (arg0.getId() == R.id.bt_cancel) {
			dialog.dismiss();
		}
	}

	@Override
	public boolean onLongClick(View arg0) {
		// TODO Auto-generated method stub

		imageselect();

		return true;
	}

	public void imageselect() {
		View view = getLayoutInflater().inflate(R.layout.user_image_select,
				null);
		// view.getBackground().setAlpha(100);

		view.findViewById(R.id.bt_photograph).setOnClickListener(this);
		view.findViewById(R.id.bt_album).setOnClickListener(this);
		view.findViewById(R.id.bt_cancel).setOnClickListener(this);
		dialog = new Dialog(this, R.style.MyDialog);

		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = new LayoutParams();
		lp.width = LayoutParams.MATCH_PARENT;
		lp.height = LayoutParams.MATCH_PARENT;
		lp.gravity = Gravity.CENTER;
		dialog.setContentView(view, lp);
		dialog.show();
	}

	// 打开相机
	public void OppenCamera() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {

			try {
				localTempImageFileName = "";
				localTempImageFileName = String.valueOf((new Date()).getTime())
						+ ".png";
				File filePath = FILE_PIC_SCREENSHOT;
				if (!filePath.exists()) {
					filePath.mkdirs();
				}
				Intent intent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				File f = new File(filePath, localTempImageFileName);
				// localTempImgDir和localTempImageFileName是自己定义的名字
				Uri u = Uri.fromFile(f);
				intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
				startActivityForResult(intent, FLAG_CHOOSE_PHONE);

			} catch (Exception e) {
				//
			}
		}

	}

	// 打开相册
	public void OppenPhoto() {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, FLAG_CHOOSE_IMG);

	}

	// 回调方法
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == FLAG_CHOOSE_IMG && resultCode == RESULT_OK) {

			if (data != null) {
				Uri uri = data.getData();
				if (!TextUtils.isEmpty(uri.getAuthority())) {
					Cursor cursor = getContentResolver().query(uri,
							new String[] { MediaStore.Images.Media.DATA },
							null, null, null);
					if (null == cursor) {

						return;
					}
					cursor.moveToFirst();
					String path = cursor.getString(cursor
							.getColumnIndex(MediaStore.Images.Media.DATA));
					cursor.close();

					Intent intent = new Intent(this, CropImageActivity.class);
					intent.putExtra("path", path);
					startActivityForResult(intent, FLAG_MODIFY_FINISH);
				} else {

					Intent intent = new Intent(this, CropImageActivity.class);
					intent.putExtra("path", uri.getPath());
					startActivityForResult(intent, FLAG_MODIFY_FINISH);
				}
			}
		} else if (requestCode == FLAG_CHOOSE_PHONE && resultCode == RESULT_OK) {
			File f = new File(FILE_PIC_SCREENSHOT, localTempImageFileName);

			Intent intent = new Intent(this, CropImageActivity.class);
			intent.putExtra("path", f.getAbsolutePath());
			startActivityForResult(intent, FLAG_MODIFY_FINISH);
		} else if (requestCode == FLAG_MODIFY_FINISH && resultCode == RESULT_OK) {
			if (data != null) {
				path = data.getStringExtra("path");

				// 上传头像数据
				MyDialog.showDialog(UserinfoActivity.this);
				MyApp app = (MyApp) getApplication();
				AsyncHttpClient ac = new AsyncHttpClient();
				RequestParams params = new RequestParams();
				params.put("token", app.user.getToken());// 用户令牌
				try {
					params.put("portrait", new File(path));// 头像的路径
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ac.post(Config.ip + Config.user_image, params,
						new AsyncHttpResponseHandler() {

							@Override
							public void onSuccess(int arg0, Header[] arg1,
									byte[] arg2) {
								// TODO Auto-generated method stub
								String str = new String(arg2);
								try {
									JSONObject json = new JSONObject(str);
									int status = json.getInt("status");
									if (status == 0) {
										JSONObject obj = json
												.getJSONObject("data");
										int result = obj.getInt("result");
										explain = obj.getString("explain");
										if (result == 0) {
											Message message = mhandler
													.obtainMessage();
											message.obj = path;
											message.what = 2;
											mhandler.sendMessage(message);
										} else {
											MyDialog.dismiss();
											Toast.makeText(
													UserinfoActivity.this,
													explain, Toast.LENGTH_SHORT)
													.show();
										}
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
				// img_head.setImageBitmap(b);

			}
		}
	}
}
