package com.example.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class MySharedPreferences {
	private android.content.SharedPreferences sp;

	public  MySharedPreferences(Context context) {
		sp = context.getSharedPreferences("box", Context.MODE_PRIVATE);
	}; 
	public void setusername(String username){
		Editor edit = sp.edit();
		edit.putString("username", username);
		edit.commit();
	}
	public String getusername(){
		return sp.getString("username", null);
	}
	public void setuserpwd(String userpwd){
		Editor edit = sp.edit();
		edit.putString("userpwd", userpwd);
		edit.commit();
	}
	public String getuserpwd(){
		return sp.getString("userpwd", null);
	}
	public void setisexit(boolean isexit){
		Editor edit = sp.edit();
		edit.putBoolean("isexit", isexit);
		edit.commit();
	}
	public boolean getisexit(){
		return sp.getBoolean("isexit", false);
	}
}
