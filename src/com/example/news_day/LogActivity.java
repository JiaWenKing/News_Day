package com.example.news_day;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Adapter.CommonAdapter;
import com.example.database.UseDb;
import com.example.model.LoginuserBin;
import com.example.model.User;
import com.example.utils.Config;
import com.example.utils.HttpRequestUtils;
import com.example.utils.MyDialog;
import com.example.utils.MySharedPreferences;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

/**
 * 登录界面
 */
public class LogActivity extends Activity implements OnClickListener {
	Handler mhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				MyDialog.dismiss();
				Toast.makeText(LogActivity.this, explain, Toast.LENGTH_SHORT)
						.show();
				LogActivity.this.finish();
				Intent intent = new Intent(LogActivity.this,
						UserinfoActivity.class);
				startActivity(intent);
				break;
			case 1:
				MyDialog.dismiss();
				Toast.makeText(LogActivity.this, explain, Toast.LENGTH_SHORT)
						.show();
				ll_log.setVisibility(View.VISIBLE);
				ll_regist.setVisibility(View.GONE);
				ll_forgetpwd.setVisibility(View.GONE);
				head_text.setText("登录");
				break;
			case 2:
				MyDialog.dismiss();
				Toast.makeText(LogActivity.this, explain, Toast.LENGTH_SHORT)
						.show();
				ll_log.setVisibility(View.VISIBLE);
				ll_regist.setVisibility(View.GONE);
				ll_forgetpwd.setVisibility(View.GONE);
				head_text.setText("登录");
				break;
			default:
				break;
			}
		};
	};
	private EditText et_username;
	private EditText et_userpwd;
	private String username;
	private String userpwd;
	private String explain;
	private String message;
	private View ll_log;
	private View ll_regist;
	private TextView head_text;
	int state = 0;
	private CheckBox cb_regist;
	private EditText regist_email;
	private EditText regist_name;
	private EditText regist_userpwd;
	private View ll_forgetpwd;
	private EditText et_forgetyx;
	private ImageSwitcher imgbt_username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		((MyApp) getApplication()).AddActivity(this);
		setContentView(R.layout.log_activity);
		initHead();
		initView();
	}

	private void initHead() {
		// TODO Auto-generated method stub
		ImageButton head_left = (ImageButton) findViewById(R.id.head_left);
		head_left.setVisibility(View.VISIBLE);
		head_text = (TextView) findViewById(R.id.head_text);
		head_text.setText("登录");
		ImageButton head_right = (ImageButton) findViewById(R.id.head_right);
		head_right.setVisibility(View.GONE);
		head_left.setOnClickListener(this);

	}

	private void initView() {
		ll_log = findViewById(R.id.ll_log);
		ll_regist = findViewById(R.id.ll_regist);
		ll_forgetpwd = findViewById(R.id.ll_forgetpwd);

		et_username = (EditText) findViewById(R.id.et_username);
		et_userpwd = (EditText) findViewById(R.id.et_userpwd);
		imgbt_username = (ImageSwitcher) findViewById(R.id.imgbt_username);

		regist_email = (EditText) findViewById(R.id.regist_email);
		regist_name = (EditText) findViewById(R.id.regist_name);
		regist_userpwd = (EditText) findViewById(R.id.regist_userpwd);
		cb_regist = (CheckBox) findViewById(R.id.cb_regist);

		et_forgetyx = (EditText) findViewById(R.id.et_forgetyx);

		findViewById(R.id.bt_log).setOnClickListener(this);
		findViewById(R.id.bt_regist).setOnClickListener(this);
		findViewById(R.id.bt_regist1).setOnClickListener(this);
		findViewById(R.id.bt_forgetpwd).setOnClickListener(this);
		findViewById(R.id.bt_okpwd).setOnClickListener(this);
		findViewById(R.id.imgbt_username).setOnClickListener(this);
		imgbt_username.setFactory(new ViewFactory() {

			@Override
			public View makeView() {
				// TODO Auto-generated method stub
				ImageView img = new ImageView(LogActivity.this);
				return img;
			}
		});// 按钮图片工厂
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0.getId() == R.id.head_left) {
			if (state == 0 || state == 2) {
				ll_log.setVisibility(View.VISIBLE);
				ll_regist.setVisibility(View.GONE);
				ll_forgetpwd.setVisibility(View.GONE);
				head_text.setText("登录");
				state = 1;
			} else if (state == 1) {
				this.finish();
			}
		} else if (arg0.getId() == R.id.bt_log) {
			MyDialog.showDialog(this);
			log();
		} else if (arg0.getId() == R.id.bt_regist) {
			state = 0;
			ll_log.setVisibility(View.GONE);
			ll_regist.setVisibility(View.VISIBLE);
			ll_forgetpwd.setVisibility(View.GONE);
			head_text.setText("注册");
		} else if (arg0.getId() == R.id.bt_regist1) {
			MyDialog.showDialog(this);
			regist();
		} else if (arg0.getId() == R.id.bt_forgetpwd) {
			state = 2;
			ll_log.setVisibility(View.GONE);
			ll_regist.setVisibility(View.GONE);
			ll_forgetpwd.setVisibility(View.VISIBLE);
			head_text.setText("找回密码");
		} else if (arg0.getId() == R.id.bt_okpwd) {
			MyDialog.showDialog(this);
			forgetpwd();
		} else if (arg0.getId() == R.id.imgbt_username) {
			Dropbox();
		}
	}

	int imgindex = 0;
	private PopupWindow pw;

	// 改变切换用户按钮上下
	private void Dropbox() {
		// TODO Auto-generated method stub
		if (imgindex == 0) {
			imgindex = 1;
			imgbt_username.setBackgroundResource(R.drawable.arrow_up);
			popupwindow();
		} else {
			imgindex = 0;
			imgbt_username.setBackgroundResource(R.drawable.arrow_down_b);
			pw.dismiss();
		}

	}

	private void popupwindow() {
		ListView lv = new ListView(this);//
		UseDb ud = new UseDb(this);
		final List<LoginuserBin> select = ud.select(LoginuserBin.class);// 得到值
		CommonAdapter<LoginuserBin> adapter = new CommonAdapter<LoginuserBin>(
				this, select, R.layout.log_item) {
			@Override
			public void setViewData(View currentView, LoginuserBin item) {
				// TODO Auto-generated method stub
				super.setViewData(currentView, item);
				ImageView iv_head = CommonAdapter
						.get(currentView, R.id.iv_head);
				TextView tv_log_user = CommonAdapter.get(currentView,
						R.id.tv_log_user);
				// Picasso.with(LogActivity.this).load(item.getIcon()).into(iv_head);
				ImageLoader.getInstance().displayImage(item.getIcon(), iv_head);
				tv_log_user.setText(item.getUser_name());
			}
		};
		lv.setAdapter(adapter);
		pw = new PopupWindow(lv, et_username.getWidth() - 20, 500);// 长宽高
		pw.setOutsideTouchable(true);// 边缘关闭
		pw.setFocusable(true);// 获得焦点
		pw.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.aaaaaaaaa));
		pw.showAsDropDown(et_username, 5, -5);// 坐标
		// 切换用户listview监听
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				LoginuserBin lb = new LoginuserBin();
				LoginuserBin bin = select.get(position);// 拿到下标
				et_username.setText(bin.getUser_name());
				et_userpwd.setText(bin.getUser_pwd());
				// popupwindow关闭
				imgindex = 0;
				imgbt_username.setBackgroundResource(R.drawable.arrow_down_b);
				pw.dismiss();
			}

		});
		// popupwindow监听
		pw.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				imgindex = 0;
				imgbt_username.setBackgroundResource(R.drawable.arrow_down_b);
				pw.dismiss();
			}
		});

	}

	private void forgetpwd() {
		// TODO Auto-generated method stub
		String email = et_forgetyx.getText().toString();
		// ver=版本号&email=邮箱
		if (email.length() <= 0) {
			Toast.makeText(this, "请输入邮箱", Toast.LENGTH_SHORT).show();
			MyDialog.dismiss();
		}
		RequestParams params = new RequestParams();
		params.put("email", email);
		params.put("ver", 1);
		AsyncHttpClient ac = new AsyncHttpClient();
		ac.get(Config.ip + Config.user_forgetpass, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						String str = new String(arg2);
						try {
							JSONObject obj = new JSONObject(str);
							int i = obj.getInt("status");
							if (i == 0) {
								JSONObject data = obj.getJSONObject("data");
								int result = data.getInt("result");
								explain = data.getString("explain");
								if (result == 0) {
									mhandler.sendEmptyMessage(2);
								} else {
									Toast.makeText(LogActivity.this, explain,
											Toast.LENGTH_SHORT).show();
									MyDialog.dismiss();
								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub

					}
				});
	}

	private void regist() {
		// TODO Auto-generated method stub
		boolean checked = cb_regist.isChecked();
		if (!checked) {
			Toast.makeText(this, "请选中服务条款", Toast.LENGTH_SHORT).show();
			MyDialog.dismiss();
		}
		// ver=版本号&uid=用户名&email=邮箱&pwd=登陆密码
		String email = regist_email.getText().toString();
		username = regist_name.getText().toString();
		userpwd = regist_userpwd.getText().toString();
		if (username.length() <= 0 || userpwd.length() <= 0) {
			Toast.makeText(this, "请输入账号密码", Toast.LENGTH_SHORT).show();
			MyDialog.dismiss();
		} else if (email.length() <= 0) {
			Toast.makeText(this, "请输入邮箱", Toast.LENGTH_SHORT).show();
			MyDialog.dismiss();
		}

		RequestParams params = new RequestParams();
		params.put("email", email);
		params.put("uid", username);
		params.put("pwd", userpwd);
		params.put("ver", 1);
		AsyncHttpClient ac = new AsyncHttpClient();
		ac.get(Config.ip + Config.user_register, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						String str = new String(arg2);
						try {
							JSONObject obj = new JSONObject(str);
							int i = obj.getInt("status");
							if (i == 0) {
								JSONObject jo = obj.getJSONObject("data");
								int result = jo.getInt("result");
								// String token = jo.getString("token");
								explain = jo.getString("explain");

								// User user=new User();
								// user.getUser_name();
								// user.getUser_pwd();
								// user.getToken();
								// MyApp myapp
								// =(MyApp)LogActivity.this.getApplication();
								// myapp.user=user;
								if (result == 0) {
									mhandler.sendEmptyMessage(1);
								} else {
									MyDialog.dismiss();
									Toast.makeText(LogActivity.this, explain,
											Toast.LENGTH_SHORT).show();
								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub

					}
				});
	}

	private void log() {
		username = et_username.getText().toString();
		userpwd = et_userpwd.getText().toString();
		if (username.length() <= 0 || userpwd.length() <= 0) {
			Toast.makeText(this, "请输入账号密码", Toast.LENGTH_SHORT).show();
			MyDialog.dismiss();
		} else {
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
									JSONObject jo = obj.getJSONObject("data");
									int result = jo.getInt("result");
									String token = jo.getString("token");
									System.out.println(token);
									explain = jo.getString("explain");
									User user = new User();
									user.setUser_pwd(userpwd);
									user.setUser_name(username);
									user.setToken(token);
									// Log.d("ceshiyixia",token);
									MyApp myapp = (MyApp) LogActivity.this
											.getApplication();
									myapp.user = user;
									// 保存数据
									MySharedPreferences msp = new MySharedPreferences(
											LogActivity.this);
									msp.setusername(username);
									msp.setuserpwd(userpwd);
									msp.setisexit(true);
									if (result == 0) {

										mhandler.sendEmptyMessage(0);
									} else {
										MyDialog.dismiss();
										Toast.makeText(LogActivity.this,
												explain, Toast.LENGTH_SHORT)
												.show();
									}
								} else {
									MyDialog.dismiss();
									Toast.makeText(LogActivity.this,
											"用户名或密码错误", Toast.LENGTH_SHORT)
											.show();
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
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == 4) {
			if (state == 0 || state == 2) {
				ll_log.setVisibility(View.VISIBLE);
				ll_regist.setVisibility(View.GONE);
				ll_forgetpwd.setVisibility(View.GONE);
				head_text.setText("登录");
				state = 1;
			} else if (state == 1) {
				this.finish();
			}
		}
		return true;
	}
}
