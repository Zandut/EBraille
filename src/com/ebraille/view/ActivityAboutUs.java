package com.ebraille.view;

import java.util.ArrayList;

import com.ebraille.controller.AdapterListAboutUs;
import com.example.test.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ActivityAboutUs extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutus);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		ArrayAdapter<Integer> daftar = new AdapterListAboutUs(getApplicationContext(), getGambar());
		ListView list = (ListView) findViewById(R.id.listView1);
		list.setAdapter(daftar);
		
		
	}
	
	public ArrayList<Integer> getGambar()
	{
		ArrayList<Integer> daftar = new ArrayList<Integer>();
		daftar.add(R.drawable.tentangkami3);
		daftar.add(R.drawable.developer);
		daftar.add(R.drawable.programmer);
		daftar.add(R.drawable.designer);
		daftar.add(R.drawable.systemanalist);
		daftar.add(R.drawable.supervisor1);
		daftar.add(R.drawable.supervisor2);
		daftar.add(R.drawable.advisor);
		daftar.add(R.drawable.d3if35pr);
		daftar.add(R.drawable.d3if35cd);
		daftar.add(R.drawable.d3if);
		daftar.add(R.drawable.tass);
		daftar.add(R.drawable.telu);
		daftar.add(R.drawable.logo);
		daftar.add(R.drawable.copyright);
		
		return daftar;
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		this.finish();
		Intent intent = new Intent(getApplicationContext(), AppsEbraille.class);
		startActivity(intent);
	}

	
}
