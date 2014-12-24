package com.ebraille.view;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.ebraille.controller.AdapterList;
import com.ebraille.controller.KamusBraille;
import com.ebraille.controller.speech;
import com.ebraille.models.Database;
import com.example.test.R;

public class ActivityKamus extends Activity{
	
	private ListView list;
	private Database db;
	private speech play;
	private boolean nyala = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.daftar_kamus);
		

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		play = new speech(getApplicationContext(), "com.googlecode.eyefree.espeak");
		
		list = (ListView) findViewById(R.id.listView1);
		db = new Database(getApplicationContext());
		try
		{
			db.createDatabase();
		}
		catch (Exception e)
		{
			Toast.makeText(getApplicationContext(), "Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		
		db.openDatabase();
		
		list.setAdapter(new AdapterList(getApplicationContext(), getKamus()));
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				KamusBraille k = (KamusBraille) list.getItemAtPosition(arg2);
				if (nyala)
					play.speak(k.getHuruf());
				
			}
			
		});
		
	}
	
	public ArrayList<KamusBraille> getKamus() {
		ArrayList<KamusBraille> daftar = new ArrayList<KamusBraille>();
		KamusBraille kamus = new KamusBraille();
		kamus.setHuruf("a");
		kamus.setGambar(R.drawable.a);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("b");
		kamus.setGambar(R.drawable.b);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("c");
		kamus.setGambar(R.drawable.c);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("d");
		kamus.setGambar(R.drawable.d);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("e");
		kamus.setGambar(R.drawable.e);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("f");
		kamus.setGambar(R.drawable.f);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("g");
		kamus.setGambar(R.drawable.g);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("h");
		kamus.setGambar(R.drawable.h);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("i");
		kamus.setGambar(R.drawable.i);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("j");
		kamus.setGambar(R.drawable.j);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("k");
		kamus.setGambar(R.drawable.k);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("l");
		kamus.setGambar(R.drawable.l);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("m");
		kamus.setGambar(R.drawable.m);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("n");
		kamus.setGambar(R.drawable.n);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("o");
		kamus.setGambar(R.drawable.o);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("p");
		kamus.setGambar(R.drawable.p);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("q");
		kamus.setGambar(R.drawable.q);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("r");
		kamus.setGambar(R.drawable.r);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("s");
		kamus.setGambar(R.drawable.s);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("t");
		kamus.setGambar(R.drawable.t);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("u");
		kamus.setGambar(R.drawable.u);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("v");
		kamus.setGambar(R.drawable.v);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("w");
		kamus.setGambar(R.drawable.w);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("x");
		kamus.setGambar(R.drawable.x);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("y");
		kamus.setGambar(R.drawable.y);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("z");
		kamus.setGambar(R.drawable.z);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("0");
		kamus.setGambar(R.drawable.angka0);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("1");
		kamus.setGambar(R.drawable.angka1);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("2");
		kamus.setGambar(R.drawable.angka2);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("3");
		kamus.setGambar(R.drawable.angka3);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("4");
		kamus.setGambar(R.drawable.angka4);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("5");
		kamus.setGambar(R.drawable.angka5);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("6");
		kamus.setGambar(R.drawable.angka6);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("7");
		kamus.setGambar(R.drawable.angka7);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("8");
		kamus.setGambar(R.drawable.angka8);
		daftar.add(kamus);
		kamus = new KamusBraille();
		kamus.setHuruf("9");
		kamus.setGambar(R.drawable.angka9);
		daftar.add(kamus);
		
		return daftar;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.menu_kamus, menu);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
			case R.id.item_sound:
				if (!nyala)
				{
					item.setTitle("Suara Padam");
					nyala = true;
				}
				else
				{
					item.setTitle("Suara Nyala");
					nyala = false;
				}
				break;

		
		}
		
		return true;
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		this.finish();
		
		Intent intent = new Intent(getApplicationContext(), AppsEbraille.class);
		startActivity(intent);
		if (play.isSpeaking())
		{
			play.stop();
		}
	}

}
