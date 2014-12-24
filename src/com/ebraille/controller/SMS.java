package com.ebraille.controller;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SMS {
	private String kalimat;
	private String no_hp;
	private SmsManager sms;
	
	public static final int SENT = 0;
	public static final int DELIVERED = 1;
	
	private Context context;
	
	public SMS(Context context) {
		
		this.context = context;
		sms = SmsManager.getDefault();
		
	}
	
	public void setKalimat(String kalimat) {
		this.kalimat = kalimat;
	}
	
	public void setNo_hp(String no_hp) {
		this.no_hp = no_hp;
	}
	
	public String getKalimat() {
		return kalimat;
	}
	
	public String getNo_hp() {
		return no_hp;
	}
		
}
