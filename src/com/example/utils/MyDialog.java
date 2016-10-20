package com.example.utils;

import com.example.View.photoview.PhotoView;
import com.example.View.photoview.PhotoViewAttacher.OnViewTapListener;
import com.example.news_day.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
/**
 * 动画效果
 * */
public class MyDialog {
	private static Dialog dialog;
	private static Dialog dialog_img_show;
	public static void showDialog(Context context){
		LayoutInflater layout=LayoutInflater.from(context);
		View view = layout.inflate(R.layout.load_dialog, null);
		Animation animation = AnimationUtils.loadAnimation(context, R.anim.start_rotate);
		dialog = new Dialog(context, R.style.MyDialog);
		ImageView img_dialog=(ImageView) view.findViewById(R.id.img_dialog);
		img_dialog.startAnimation(animation);
		
		dialog.setContentView(view);
		dialog.show();
	}
	public static void dismiss(){
		if (dialog!=null) {
			dialog.dismiss();
			dialog=null;
		}
	}
	/**
	 * 头像图片展示
	 * */
	public static void showimageDialog(Context context , String path){
		
		LayoutInflater layout=LayoutInflater.from(context);
		View view = layout.inflate(R.layout.show_img_item, null);
		dialog_img_show=new Dialog(context, R.style.ImageDialog);
		PhotoView img_show=(PhotoView) view.findViewById(R.id.img_show);
		img_show.setOnViewTapListener(new OnViewTapListener() {
			
			@Override
			public void onViewTap(View view, float x, float y) {
				// TODO Auto-generated method stub
				imagedismiss();
			}
		});//photoview监听   
		ImageLoader.getInstance().displayImage(path, img_show);
//		Picasso.with(context).load(path).into(img_show);
		
		Window window=dialog_img_show.getWindow();//窗口设置
		WindowManager.LayoutParams lp=new WindowManager.LayoutParams();
		lp.width=WindowManager.LayoutParams.MATCH_PARENT;
		lp.height=WindowManager.LayoutParams.MATCH_PARENT;
		lp.gravity=Gravity.CENTER;
		 
		
		dialog_img_show.setContentView(view,lp);
		dialog_img_show.show();
	}
	public static void imagedismiss(){
		if (dialog_img_show!=null) {
			dialog_img_show.dismiss();
		}
	}
}
