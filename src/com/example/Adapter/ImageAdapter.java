package com.example.Adapter;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.model.News;
import com.example.news_day.MainActivity;
import com.example.news_day.MyApp;
import com.example.news_day.R;
import com.example.utils.Config;
import com.example.utils.MyDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Selection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 评论适配器
 * */
public class ImageAdapter extends BaseAdapter {
	List<News> list;
	LayoutInflater layout;
	Context context;
	private ViewHadler vh;
	ImageAdapter adapter;
	
	public ImageAdapter(List<News> list ,Context context) {
		super();
		this.list = list;
		layout = LayoutInflater.from(context);
		this.context=context;
		adapter=this;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		vh = null;
		if (v==null) {
			vh=new ViewHadler();
			v=layout.inflate(R.layout.img_list_item, null);
			vh.img_news=(ImageView)v.findViewById(R.id.img_news);
			vh.tv_img_news=(TextView)v.findViewById(R.id.tv_img_news);
			vh.ib_img_pinglun=(ImageView)v.findViewById(R.id.ib_img_pinglun);
			vh.ll_pinglun=(LinearLayout) v.findViewById(R.id.ll_pinglun);
			vh.tv_pinglun=(EditText) v.findViewById(R.id.tv_pinglun);
			vh.bt_pinglun=(Button) v.findViewById(R.id.bt_pinglun);
			
			vh.bt_pinglun.setOnClickListener(new SendOncick(vh.tv_pinglun,position));
			v.setTag(vh);
		}else {
			vh=(ViewHadler) v.getTag();
		}
		News news = list.get(position);
		
		//图片设值
		Picasso.with(context).load(news.getIcon()).into(vh.img_news);
		//标题设值
		vh.tv_img_news.setText(news.getTitle());
		
		vh.ib_img_pinglun.setOnClickListener(new MyonClick(position));
		
		if (news.isIsshow()) {
			vh.ll_pinglun.setVisibility(View.VISIBLE);//显示
			  //设置可获得焦点  
			vh.tv_pinglun.setFocusable(true);  
			vh.tv_pinglun.setFocusableInTouchMode(true);  
            //请求获得焦点  
			vh.tv_pinglun.requestFocus(); 
			//调用系统输入法  
            InputMethodManager inputManager = (InputMethodManager) vh.tv_pinglun  
                    .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);  
//            inputManager.showSoftInput(vh.tv_pinglun, 0);  
            Editable etext=(Editable) vh.tv_pinglun.getText();
            Selection.setSelection(etext, etext.length());//
		}else {
			vh.tv_pinglun.setText("");
			vh.ll_pinglun.setVisibility(View.GONE);//隐藏
		}
		
		return v;
	}
	
	static class ViewHadler{
		ImageView img_news;
		TextView tv_img_news;
		ImageView ib_img_pinglun;
		LinearLayout ll_pinglun;
		EditText tv_pinglun;
		Button bt_pinglun;
	}
	class MyonClick implements OnClickListener{

		int index;
		public MyonClick(int position) {
			// TODO Auto-generated constructor stub
			this.index=position;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).isIsshow()) {
					list.get(i).setIsshow(false);
				}else if (i==index) {
					list.get(i).setIsshow(true);
				}
			}
			adapter.notifyDataSetChanged();
		}
	}
	
	
	class SendOncick implements OnClickListener{
		EditText tv_pinglun;
		int index;

		public SendOncick(EditText tv_pinglun, int index) {
			super();
			this.tv_pinglun = tv_pinglun;
			this.index = index;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			MyDialog.showDialog(context);
			News news = list.get(index);
			MainActivity m=(MainActivity) context;
			MyApp app=(MyApp) m.getApplication();
			String text = tv_pinglun.getText().toString();
			AsyncHttpClient ac=new AsyncHttpClient();
			RequestParams params=new RequestParams();
//ver=0nid=token=用户令牌&imei=手机标识符&ctx=评论内容
			params.put("ver", "0");
			params.put("nid", news.getNid()+"");
			params.put("token", app.user.getToken());
			params.put("imei", "123456");
			params.put("ctx", "text");
			ac.get(Config.ip+Config.cmt_commit, params, new AsyncHttpResponseHandler() {
				
				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
					// TODO Auto-generated method stub
					try {
						JSONObject json=new JSONObject(new String(arg2));
						int status=json.getInt("status");
						if(status==0){
							MyDialog.dismiss();
							Toast.makeText(context, "评论成功", Toast.LENGTH_SHORT).show();
							tv_pinglun.setText("");
							News news = list.get(index);
							news.setIsshow(false);
							adapter.notifyDataSetChanged();
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
			
			
		}
		
	}
}
