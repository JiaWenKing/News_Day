package com.example.utils;

import java.lang.reflect.Type;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HttpRequestUtils<T> {
	static HttpRequestUtils request = null;
	private AsyncHttpClient ac;
	private Gson gson;

	private HttpRequestUtils() {
		ac = new AsyncHttpClient();
		gson = new Gson();
	}

	// 单例
	public static HttpRequestUtils gethttprequest() {
		if (request == null) {
			return request = new HttpRequestUtils();
		} else {
			return request;
		}
	}

	/**
	 * get 请求数据 单个
	 * */
	public void getrequestdata(String url, RequestParams params,
			final Handler mhandler, final int requestCode, final Class c) {
		ac.get(Config.ip + url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				String s=new String(arg2);
				try {
					JSONObject json = new JSONObject(s);
					int status = json.getInt("status");
					if (status==0) {
						JSONObject data = json.getJSONObject("data");
						Object object = gson.fromJson(data.toString(), c);
						Message message = mhandler.obtainMessage();
						message.obj=object;
						message.what=requestCode;
						mhandler.sendMessage(message);
						
					}else {
						mhandler.sendEmptyMessage(status);
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

	/**
	 * get 请求数据 数组 多个
	 * @param <T>
	 * */
	public void getrequestarray(String url, RequestParams params,
			final Handler mhandler, final int requestCode, final TypeToken<T> c) {
		ac.get(Config.ip + url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				String s=new String(arg2);
				try {
					JSONObject json = new JSONObject(s);
					int status = json.getInt("status");
					if (status==0) {
						JSONArray data = json.getJSONArray("data");
						Object object = gson.fromJson(data.toString(),c.getType());
						Message message = mhandler.obtainMessage();
						message.obj=object;
						message.what=requestCode;
						mhandler.sendMessage(message);
						
					}else {
						mhandler.sendEmptyMessage(status);
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
	/**
	 * post 请求数据 单个
	 * */
	public void postrequestdata(String url, RequestParams params,
			final Handler mhandler, final int requestCode, final Class c) {
		ac.post(Config.ip + url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				String s=new String(arg2);
				try {
					JSONObject json = new JSONObject(s);
					int status = json.getInt("status");
					if (status==0) {
						JSONObject data = json.getJSONObject("data");
						Object object = gson.fromJson(data.toString(), c);
						Message message = mhandler.obtainMessage();
						message.obj=object;
						message.what=requestCode;
						mhandler.sendMessage(message);
						
					}else {
						mhandler.sendEmptyMessage(status);
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
	/**
	 * post 请求数据 duoge
	 * */
	public void postrequestarray(String url, RequestParams params,
			final Handler mhandler, final int requestCode, final TypeToken<T> c) {
		ac.post(Config.ip + url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				String s=new String(arg2);
				try {
					JSONObject json = new JSONObject(s);
					int status = json.getInt("status");
					if (status==0) {
						JSONArray data = json.getJSONArray("data");
						Object object = gson.fromJson(data.toString(), c.getType());
						Message message = mhandler.obtainMessage();
						message.obj=object;
						message.what=requestCode;
						mhandler.sendMessage(message);
						
					}else {
						mhandler.sendEmptyMessage(status);
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
}
