package com.example.model;

import java.util.List;

public class Userlog {
	private String uid;
	private String portrait;//Í·Ïñ
	private List<Userlogbin>  loginlog;
	private int comnum;
	private int integration;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public int getIntegration() {
		return integration;
	}
	public void setIntegration(int integration) {
		this.integration = integration;
	}
	public List<Userlogbin> getloginlog() {
		return loginlog;
	}
	public void setUserlog(List<Userlogbin> loginlog) {
		this.loginlog = loginlog;
	}
	public String getPortrait() {
		return portrait;
	}
	public void setVportrait(String portrait) {
		this.portrait = portrait;
	}
	public int getComnum() {
		return comnum;
	}
	public void setComnum(int comnum) {
		this.comnum = comnum;
	}
}
