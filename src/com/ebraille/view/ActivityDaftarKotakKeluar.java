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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.ebraille.controller.Adapter_list_kotak_keluar;
import com.ebraille.controller.KotakKeluar;
import com.ebraille.controller.speech;
import com.ebraille.models.Database;
import com.example.test.R;

public class ActivityDaftarKotakKeluar extends Activity{
	
	private ListView list;
	private Database db;
	private speech play;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.daftar_kotak_keluar);
		

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		list = (ListView) findViewById(R.id.listView1);
		play = new speech(getApplicationContext(), "com.googlecode.eyefree.espeak");
		
		
		db = new Database(getApplicationContext());
		
		try
		{
			db.createDatabase();
		}
		catch (Exception Ex)
		{
			Toast.makeText(getApplicationContext(), Ex.getMessage(), Toast.LENGTH_SHORT).show();
		}
		
		db.openDatabase();
		
		refresh();
		
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				KotakKeluar k = (KotakKeluar) list.getItemAtPosition(arg2);
				
				Toast.makeText(getApplicationContext(), "Nomor : "+k.getNomer()+"\nPesan : "+k.getPesan()+"\nWaktu : "+k.getWaktu()+"\nStatus : "+k.getStatus() , Toast.LENGTH_LONG).show();
				
				
			}
			
		});
		
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				KotakKeluar k = (KotakKeluar)list.getItemAtPosition(arg2);
				
				play.speak("Pesan kepada "+k.getNomer()+" yang berisi "+k.getPesan());
				
				return true;
			}
		});
	}
	
//	private ArrayList<KotakKeluar> getKotakKeluar()
//	{
//		ArrayList<KotakKeluar> daftar = new ArrayList<KotakKeluar>();
//		Uri uri = Uri.parse("content://sms/sent");
//		
//		Cursor cs = getContentResolver().query(uri, null, null, null, null);
//		
//		while (cs.moveToNext())
//		{
//			KotakKeluar k = new KotakKeluar();
//			k.setNomer(cs.getString(cs.getColumnIndex("address")));
//			k.setPesan(cs.getString(cs.getColumnIndex("body")));
//			k.setWaktu(cs.getString(cs.getColumnIndex("date")));
//			daftar.add(k);
//		}
//		
//		cs.close();
//		return daftar;
//		
//	}
	
	private void refresh()
	{
		ArrayList<KotakKeluar> daftar = db.getAllKotakKeluar();
		list.setAdapter(new Adapter_list_kotak_keluar(getApplicationContext(), daftar));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.menu_kotak_keluar, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId())
		{
			case R.id.item_hapus :
			{
				
				db.deleteAllKotakKeluar();
				refresh();
				
				break;
			}
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
