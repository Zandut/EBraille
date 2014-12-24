package com.ebraille.view;

import java.util.ArrayList;

import com.ebraille.controller.Adapter_list_kotak_masuk;
import com.ebraille.controller.KotakMasuk;
import com.ebraille.controller.SMS;
import com.ebraille.controller.speech;
import com.example.test.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityKotakMasuk extends Activity{
	
	private ListView list;
	private speech play;
	private int i = 0;
	private SMS sms;
	private static int position2 = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.daftar_kotak_masuk);
		

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		sms = new SMS(getApplicationContext());
		
		play = new speech(getApplicationContext(), "com.googlecode.eyefree.espeak");
		
		ArrayList<KotakMasuk> kotak = getKotakMasuk();
		
		list = (ListView) findViewById(R.id.listView1);
		
		list.setAdapter(new Adapter_list_kotak_masuk(getApplicationContext(), kotak));
		
		list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				KotakMasuk kotak = (KotakMasuk) list.getItemAtPosition(arg2);
				play.speak(kotak.getPesan()+" dari "+kotak.getNomer());
				
				return true;
			}
			
		});
		
		
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				int position1 = arg2;
				
				KotakMasuk k = (KotakMasuk)list.getItemAtPosition(arg2);
				i++;
				
				
				if (i == 1)
				{
					Toast.makeText(getApplicationContext(), "Tekan lagi untuk membalas", Toast.LENGTH_LONG).show();
					position2 = arg2;
				}
				
				if (i==2 && position1 == position2)
				{
					Intent intent = new Intent(getApplicationContext(), AppsEbraille.class);
					sms.setNo_hp(k.getNomer());
					intent.putExtra("sms", sms.getNo_hp());
					intent.putExtra("fromInbox", true);
					startActivity(intent);
					finish();
					
					play.speak("Anda akan membalas pesan dari "+sms.getNo_hp()+". Silahkan masukkan isi pesan");
					i=0;
					position2 = -1;
				}
				else
					if (position1 != position2 )
					{
						i = 0;
						
					}
				
				
				
					
			}
		});
	}
	
	
	@SuppressWarnings("deprecation")
	private ArrayList<KotakMasuk> getKotakMasuk()
	{
		ArrayList<KotakMasuk> daftar = new ArrayList<KotakMasuk>();
		Uri uri = Uri.parse("content://sms/inbox");
		
		Cursor c = getContentResolver().query(uri, null, null, null, null);
		startManagingCursor(c);
		
		while (c.moveToNext())
		{
			KotakMasuk m = new KotakMasuk();
			m.setNomer(c.getString(c.getColumnIndex("address")).toString().replace("+62", "0"));
			m.setPesan(c.getString(c.getColumnIndex("body")).toString());
			daftar.add(m);
			
		}
		
		c.close();
		
		return daftar;
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
