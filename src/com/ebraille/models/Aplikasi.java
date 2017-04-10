package com.ebraille.models;

import java.util.HashMap;

import android.app.Activity;
import android.content.SharedPreferences;

public class Aplikasi {
	private Activity activity;
	private SharedPreferences sharedPreferences;
	private HashMap<String, String> urls = new HashMap<String, String>();
	private String host = "http://apiv1-braille.azurewebsites.net";
	private JSON json = new JSON();
	
	public JSON getJson() {
		return json;
	}
	
	public void resetJson() {
		json = new JSON();

	}
	
	public SharedPreferences getSharedPreferences() {
		return sharedPreferences;
	}
	
	public Aplikasi(Activity act) {
		// TODO Auto-generated constructor stub
		this.activity = act;
		
		sharedPreferences = activity.getSharedPreferences("session", activity.MODE_PRIVATE);		
		urls.put("cekUsername", host+"/username");
		urls.put("registrasi", host+"/registrasi");
		urls.put("verifikasi", host+"/verifikasi");
		urls.put("login", host+"/login");
		urls.put("logout", host+"/logout");
	}
	
	public HashMap<String, String> getUrls() {
		return urls;
	}
	
	
}
