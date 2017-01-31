package com.ebraille.view;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.AvoidXfermode;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ebraille.controller.KotakKeluar;
import com.ebraille.controller.KotakMasuk;
import com.ebraille.controller.SMS;
import com.ebraille.controller.speech;
import com.ebraille.models.Braille;
import com.ebraille.models.Database;
import com.example.test.R;


public class AppsEbraille extends Activity{
	
	
private speech play;
	
	private ImageButton imagebutton1;
	private ImageButton imagebutton2;
	private ImageButton imagebutton3;
	private ImageButton imagebutton4;
	private ImageButton imagebutton5;
	private ImageButton imagebutton6;
	private boolean state_sending = false;
	
	private SMS sms;
	private boolean isHapus = false;
	
	private Braille braille = new Braille();
	
	private String kata = new String("");
	
	
	private boolean fromInbox = false;
	
	private Database db;
	private boolean mode_tulis = true, mode_inbox = false, mode_outbox = false, mode_tulis_standar = true, mode_tulis_expert = false;
	
	private int a = 0; 
	private int b = 0; 
	private int c = 0; 
	private int d = 0; 
	private int e = 0; 
	private int f = 0;
	private boolean double_touch = false;
	private float x1, x2, y1, y2;
	private boolean terkirim = true;
	private int pointer = -1;
	private ArrayList<KotakMasuk> daftar_masuk = new ArrayList<KotakMasuk>();
	private ArrayList<KotakKeluar> daftar_keluar = new ArrayList<KotakKeluar>();
	
	private void sendSMS(String nomer, String pesan)
	{
		String SENT = "SMS_SENT";
		String DELIVERED = "SMS_DELIVERED";
		final KotakKeluar k = new KotakKeluar();
		k.setNomer(nomer);
		k.setPesan(pesan);
		
		
		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
		
		PendingIntent delivPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);
		
		registerReceiver(new BroadcastReceiver()
		{
			
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				// TODO Auto-generated method stub
				
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					play.speak("Pesan Terkirim");
					k.setStatus("terkirim");
					db.insertKotakKeluar(k);
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE :
					play.speak("Pesan tidak terkirim");
//					play.speak("Pesan Terkirim");
					k.setStatus("tidak terkirim");
					db.insertKotakKeluar(k);
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					play.speak("Pesan tidak terkirim");
					k.setStatus("tidak terkirim");
					db.insertKotakKeluar(k);
					break;

				
				}
				
			}
		}, new IntentFilter(SENT));
		
		registerReceiver(new BroadcastReceiver(){
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				// TODO Auto-generated method stub
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					play.speak("Pesan sudah diterima "+k.getNomer());
					break;

				case Activity.RESULT_CANCELED:
					play.speak("Pesan tidak diterima");
					break;
				}
				
			}
		}, new IntentFilter(DELIVERED));
		
		android.telephony.SmsManager sms = android.telephony.SmsManager.getDefault();
		sms.sendTextMessage(nomer, null, pesan, sentPI, delivPI);
		
	}
	
	SimpleTwoFingerDoubleTapDetector doubleTap = new SimpleTwoFingerDoubleTapDetector() {
		
		@Override
		public void onTwoFingerDoubleTap() {
			// TODO Auto-generated method stub
			if (mode_tulis)
			{
				if (double_touch)
				{
					
					if (!state_sending)
					{
						try
						{
							if (!getIntent().getExtras().isEmpty())
								fromInbox = getIntent().getExtras().getBoolean("fromInbox");
						}
						catch (Exception ex)
						{
							fromInbox = false;
						}
						
						if (!fromInbox)
						{
							sms.setKalimat(kata);
							Toast.makeText(getApplicationContext(), kata, Toast.LENGTH_LONG).show();
							play.speak("Silahkan masukkan nomer hp");
							
							state_sending = true;
							kata = new String();
						}
						else
						{
							
							String no_hp = getIntent().getExtras().getString("sms");
							
							sms.setKalimat(kata);
							sms.setNo_hp(no_hp);
							
							Toast.makeText(getApplicationContext(), kata+"; "+sms.getNo_hp(), Toast.LENGTH_LONG).show();
							sendSMS(sms.getNo_hp(), sms.getKalimat());
							
							state_sending = false;
							kata = new String();
							fromInbox = false;
							getIntent().putExtra("fromInbox", fromInbox);
							
							
						}
						
					}
					else
					{
						
						sms.setNo_hp(kata);
						
						Toast.makeText(getApplicationContext(), sms.getNo_hp(), Toast.LENGTH_LONG).show();
						sendSMS(sms.getNo_hp(), sms.getKalimat());
						
						
						state_sending = false;
						
						kata = new String();
					}
					double_touch = false;
				}
			}
			else
				if (mode_inbox)
				{
					if (double_touch)
					{
						if (!state_sending)
						{
							if (play.isSpeaking())
								play.stop();
							
							play.speak("Anda akan membalas pesan dari "+daftar_masuk.get(pointer).getNomer()+". Silahkan masukan isi pesan");
							fromInbox = true;
							getIntent().putExtra("fromInbox", fromInbox);
							getIntent().putExtra("sms", daftar_masuk.get(pointer).getNomer());
							mode_tulis = true;
							mode_inbox = false;
							pointer = -1;
						}
						
						double_touch = false;
					}
				}
			
			
		}
	};
	 
	public abstract class SimpleTwoFingerDoubleTapDetector {
	    private final int TIMEOUT = ViewConfiguration.getDoubleTapTimeout() + 100;
	    private long mFirstDownTime = 0;
	    private boolean mSeparateTouches = false;
	    private byte mTwoFingerTapCount = 0;

	    private void reset(long time) {
	        mFirstDownTime = time;
	        mSeparateTouches = false;
	        mTwoFingerTapCount = 0;
	    }

	    public boolean onTouchEvent(MotionEvent event) {
	    	boolean onTOuch = false;
	    	if (event.getActionMasked() == MotionEvent.ACTION_DOWN)
	    	{
	            if(mFirstDownTime == 0 || event.getEventTime() - mFirstDownTime > TIMEOUT) 
	                reset(event.getDownTime());
	    	}
	    	else
	    		if (event.getActionMasked() == MotionEvent.ACTION_POINTER_UP)
	    		{
		            if(event.getPointerCount() == 2)  
		            {
		            	
		            	//Toast.makeText(getApplicationContext(), ""+event.getPointerCount(), Toast.LENGTH_SHORT).show();
		                mTwoFingerTapCount++;
		            }
		            else 
		                mFirstDownTime = 0;
	    		}
	            
	    		else
	    			
		    			if (event.getActionMasked() == MotionEvent.ACTION_UP)
		    			{
				            if(!mSeparateTouches)
				                mSeparateTouches = true;
				            else if(mTwoFingerTapCount == 2 && event.getEventTime() - mFirstDownTime < TIMEOUT) {
				            	
				                onTwoFingerDoubleTap();
				                mFirstDownTime = 0;
				                onTOuch = true;
				            }
		    			}
	    			
	            
	                    
	    	return onTOuch;
	        
	    }

	    public abstract void onTwoFingerDoubleTap();
	}
	private int count = 0;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		
			if (doubleTap.onTouchEvent(event))
			{
				return true;
				
			}
			else
			{
				
					count = 0;
					if (event.getActionMasked() == MotionEvent.ACTION_DOWN)
					{
						x1 = event.getX();
						y1 = event.getY();
						isHapus = true;
						//x2=0;
						
						
					}
					
					
					if (event.getActionMasked() == MotionEvent.ACTION_POINTER_UP)
					{
						//Toast.makeText(getApplicationContext(), ""+event.getPointerCount(), Toast.LENGTH_SHORT).show();
						
						count = event.getPointerCount();
						
						if (count >= 3)
						{
							y2 = event.getY();
							
							if (y1 < y2)
							{
								
								kata = new String("");
								play.speak("Teks terhapus");
								
							}
							
						}
						else
							if (count == 2)
							{
								double_touch = true;
							}
							else
								double_touch = false;
						
						
						
					}
					
					
					
					if (event.getActionMasked() == MotionEvent.ACTION_UP)
					{
						
						if (!double_touch)
						{
							x2 = event.getX();
							if (isHapus)
							{
								if (x1 < x2)
								{
									
									//kata.addHuruf(editText2.getText().toString());
									if (!kata.equals(""))
									{
										play.speak(kata);
										kata += " ";
			
									}
									
									isHapus = false;
									
									//kalimat = new Kalimat();
								}
								
								else
									if (x1 > x2)
									{
										
										if (!kata.equals(""))
										{
											String huruf = new String("");
											for (int i=0;i<kata.length()-1;i++)
											{
												huruf += kata.charAt(i);
											}
											
											kata = huruf;
											
											
											play.speak("Hapus");
										}
										
										isHapus = false;
									}
					//			
							}
						
						}
					}
	
				}
		
		
		return super.onTouchEvent(event);
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
	
	private ArrayList<KotakKeluar> getKotakKeluar()
	{
		return db.getAllKotakKeluar();
	}
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		//getWindow().addFlags(WindowManager.LayoutParam)
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		 
		
//		getIntent().putExtra("fromInbox", false);
		
		play = new speech(getApplicationContext(), "com.googlecode.eyefree.espeak");
		
		sms = new SMS(getApplicationContext());
		
		db = new Database(getApplicationContext());
		
		try {
			db.createDatabase();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		db.openDatabase();
		
		
		daftar_keluar = getKotakKeluar();
		mode_tulis = true;
		
		//layout = (RelativeLayout) findViewById(R.id.relative1);
		
		imagebutton1 = (ImageButton) findViewById(R.id.imageButton1);
		imagebutton1.setImageResource(R.drawable.kotakitemfix1);
		
		imagebutton2 = (ImageButton) findViewById(R.id.imageButton2);
		imagebutton2.setImageResource(R.drawable.kotakitemfix2);
		
		imagebutton3 = (ImageButton) findViewById(R.id.imageButton3);
		imagebutton3.setImageResource(R.drawable.kotakitemfix3);
		
		imagebutton4 = (ImageButton) findViewById(R.id.imageButton4);
		imagebutton4.setImageResource(R.drawable.kotakitemfix4);
		
		imagebutton5 = (ImageButton) findViewById(R.id.imageButton5);
		imagebutton5.setImageResource(R.drawable.kotakitemfix5);
		
		imagebutton6 = (ImageButton) findViewById(R.id.imageButton6);
		imagebutton6.setImageResource(R.drawable.kotakitemfix6);
		
		imagebutton1.setOnTouchListener(new OnTouchListener() {
			
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				
					if (arg1.getAction() == MotionEvent.ACTION_DOWN)
					{
						if (mode_tulis)
						{
							a=1;
							//tempHuruf2="";
							//editText2.setText(a+","+b+","+c+","+d+","+e+","+f);
							braille.changeState(a, b, c, d, e, f);
							double_touch = false;
						}
							
					}
					else if(arg1.getAction() == MotionEvent.ACTION_UP && arg1.getAction() != MotionEvent.ACTION_MOVE)
					{
						if (mode_tulis)
						{
							String h = new String("");
							if (!state_sending)
								h = (db.getHuruf(braille.getState()[0], braille.getState()[1], braille.getState()[2], braille.getState()[3], braille.getState()[4], braille.getState()[5]));
							else
								h = (db.getAngka(braille.getState()[0], braille.getState()[1], braille.getState()[2], braille.getState()[3], braille.getState()[4], braille.getState()[5]));
							
							kata += h;
							
							a=0;
		
							braille.changeState(0, 0, 0, 0, 0, 0);
						}
						
						
					}
				
				
				
				return false;
			}
			
			
		});
		
		imagebutton4.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				
					if (arg1.getAction() == MotionEvent.ACTION_DOWN)
					{
						if (mode_tulis)
						{
							d=1;
							//editText2.setText(a+","+b+","+c+","+d+","+e+","+f);
							braille.changeState(a, b, c, d, e, f);
							double_touch = false;
						}
						
					}
					else if(arg1.getAction() == MotionEvent.ACTION_UP && arg1.getAction() != MotionEvent.ACTION_MOVE)
					{
						if (mode_tulis)
						{
							String h = new String("");
							if (!state_sending)
								h = (db.getHuruf(braille.getState()[0], braille.getState()[1], braille.getState()[2], braille.getState()[3], braille.getState()[4], braille.getState()[5]));
							else
								h = (db.getAngka(braille.getState()[0], braille.getState()[1], braille.getState()[2], braille.getState()[3], braille.getState()[4], braille.getState()[5]));
							
							if (h.equals("gantimode"))
							{
								if (mode_tulis_standar)
								{
									setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
									play.speak("Mode Expert");
									imagebutton1.setImageResource(R.drawable.kotakitemfix1_1);
									imagebutton2.setImageResource(R.drawable.kotakitemfix2_2);
									imagebutton3.setImageResource(R.drawable.kotakitemfix3_3);
									imagebutton4.setImageResource(R.drawable.kotakitemfix4_4);
									imagebutton5.setImageResource(R.drawable.kotakitemfix5_5);
									imagebutton6.setImageResource(R.drawable.kotakitemfix6_6);
									mode_tulis_expert = true;
									mode_tulis_standar = false;
								}
								else
									if (mode_tulis_expert)
									{
										setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
										play.speak("Mode Standar");
										imagebutton1.setImageResource(R.drawable.kotakitemfix1);
										imagebutton2.setImageResource(R.drawable.kotakitemfix2);
										imagebutton3.setImageResource(R.drawable.kotakitemfix3);
										imagebutton4.setImageResource(R.drawable.kotakitemfix4);
										imagebutton5.setImageResource(R.drawable.kotakitemfix5);
										imagebutton6.setImageResource(R.drawable.kotakitemfix6);
										mode_tulis_expert = false;
										mode_tulis_standar = true;
									}
							}
							else
							{
								kata += h;
							}
							
							d=0;
		
							
							braille.changeState(0, 0, 0, 0, 0, 0);
						}
						else
							if (mode_inbox || mode_outbox)
							{
								mode_outbox = false;
								mode_inbox = false;
								mode_tulis = true;
								if (play.isSpeaking())
									play.stop();
								
								a=0;
								b=0;
								c=0;
								d=0;
								e=0;
								f=0;
								pointer = -1;
								braille.changeState(0, 0, 0, 0, 0, 0);
								play.speak("Anda memasuki mode tulis");
							}
							
						
					}
				
				
					
				return false;
				
			}
		});
		
		imagebutton6.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				
					if (arg1.getAction() == MotionEvent.ACTION_DOWN)
					{
						if (mode_tulis)
						{
							f=1;
							//editText2.setText(a+","+b+","+c+","+d+","+e+","+f);
							braille.changeState(a, b, c, d, e, f);
							double_touch = false;
						}
						
						
					}
					else if(arg1.getAction() == MotionEvent.ACTION_UP && arg1.getAction() != MotionEvent.ACTION_MOVE)
					{
						if (mode_tulis)
						{
		//					braille.changeState(a, b, c, d, e, f);
							String h = new String("");
							if (!state_sending )
								h = (db.getHuruf(braille.getState()[0], braille.getState()[1], braille.getState()[2], braille.getState()[3], braille.getState()[4], braille.getState()[5]));
							else
								h = (db.getAngka(braille.getState()[0], braille.getState()[1], braille.getState()[2], braille.getState()[3], braille.getState()[4], braille.getState()[5]));
							
							
							kata += h;
							f=0;
							
							braille.changeState(0, 0, 0, 0, 0, 0);
						}
						else
							if (mode_inbox)
							{
								if (play.isSpeaking())
									play.stop();
								
								
								if (pointer > 0)
								{
									pointer--;
									KotakMasuk kotak_masuk = daftar_masuk.get(pointer);
									play.speak(kotak_masuk.getPesan()+". Dari "+kotak_masuk.getNomer());
								}
								else
								{
									pointer = -1;
									play.speak("Ini pesan terakhir.");
								}
								
							}
							else
								if (mode_outbox)
								{
									if (play.isSpeaking())
										play.stop();
									
									
									if (pointer > 0)
									{
										pointer--;
										KotakKeluar kotak_keluar = daftar_keluar.get(pointer);
										play.speak(kotak_keluar.getPesan()+". Kepada "+kotak_keluar.getNomer());
									}
									else
									{
										pointer = -1;
										play.speak("Ini pesan terakhir");
									}
									
								}
							
						
					}
				
				
				//braille.changeState(a, b, c, d, e, f);
				//editText2.setText(a+","+b+","+c+","+d+","+e+","+f);
				return false;
			}
		});

		imagebutton2.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				
					if (arg1.getAction() == MotionEvent.ACTION_DOWN)
					{
						if (mode_tulis)
						{
							b=1;
							//editText2.setText(a+","+b+","+c+","+d+","+e+","+f);
							braille.changeState(a, b, c, d, e, f);
							double_touch = false;
						}
						
					}
					else if(arg1.getAction() == MotionEvent.ACTION_UP && arg1.getAction() != MotionEvent.ACTION_MOVE)
					{
						if (mode_tulis)
						{
		//					braille.changeState(a, b, c, d, e, f);
							String h = new String("");
							if (!state_sending)
								h = (db.getHuruf(braille.getState()[0], braille.getState()[1], braille.getState()[2], braille.getState()[3], braille.getState()[4], braille.getState()[5]));
							else
								h = (db.getAngka(braille.getState()[0], braille.getState()[1], braille.getState()[2], braille.getState()[3], braille.getState()[4], braille.getState()[5]));
							
							
								if (h.equals("modeinbox"))
								{
									mode_inbox = true;
									mode_tulis = false;
									if (play.isSpeaking())
										play.stop();
									
									daftar_masuk = getKotakMasuk();
									kata = new String();
									play.speak("Anda memasuki mode kotak masuk");
									
								}
								else
									kata += h;
							
							
							b=0;
							braille.changeState(0, 0, 0, 0, 0, 0);
						}
						
					}
				
				
				//braille.changeState(a, b, c, d, e, f);
				//editText2.setText(a+","+b+","+c+","+d+","+e+","+f);
				return false;
			}
		});

		imagebutton5.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				
					if (arg1.getAction() == MotionEvent.ACTION_DOWN)
					{
						if (mode_tulis)
						{
							e=1;
							//editText2.setText(a+","+b+","+c+","+d+","+e+","+f);
							braille.changeState(a, b, c, d, e, f);
							double_touch = false;
						}
						
					}
					else if(arg1.getAction() == MotionEvent.ACTION_UP && arg1.getAction() != MotionEvent.ACTION_MOVE)
					{
						if (mode_tulis)
						{
		//					braille.changeState(a, b, c, d, e, f);
							String h = new String("");
							if (!state_sending)
								h = (db.getHuruf(braille.getState()[0], braille.getState()[1], braille.getState()[2], braille.getState()[3], braille.getState()[4], braille.getState()[5]));
							else
								h = (db.getAngka(braille.getState()[0], braille.getState()[1], braille.getState()[2], braille.getState()[3], braille.getState()[4], braille.getState()[5]));
							
							
								if (h.equals("modeoutbox"))
								{
									mode_outbox = true;
									mode_tulis = false;
									if (play.isSpeaking())
										play.stop();
									
									kata = new String();
									daftar_keluar = getKotakKeluar();
									play.speak("Anda memasuki mode kotak keluar");
									
								}
								else
									kata += h;
							
							e=0;
							braille.changeState(0, 0, 0, 0, 0, 0);
						}
						
					}
				
				
				
				//braille.changeState(a, b, c, d, e, f);
				//editText2.setText(a+","+b+","+c+","+d+","+e+","+f);
				return false;
			}
		});
		
		imagebutton3.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				
					if (arg1.getAction() == MotionEvent.ACTION_DOWN)
					{
						if (mode_tulis)
						{
							c=1;
							//editText2.setText(a+","+b+","+c+","+d+","+e+","+f);
							braille.changeState(a, b, c, d, e, f);
							double_touch = false;
						}
						
					}
					else if(arg1.getAction() == MotionEvent.ACTION_UP && arg1.getAction() != MotionEvent.ACTION_MOVE)
					{
						if (mode_tulis)
						{
		//					braille.changeState(a, b, c, d, e, f);
							String h = new String("");
							if (!state_sending)
								h = (db.getHuruf(braille.getState()[0], braille.getState()[1], braille.getState()[2], braille.getState()[3], braille.getState()[4], braille.getState()[5]));
							else
								h = (db.getAngka(braille.getState()[0], braille.getState()[1], braille.getState()[2], braille.getState()[3], braille.getState()[4], braille.getState()[5]));
							
							kata += h;
							
							c=0;
							
							braille.changeState(0, 0, 0, 0, 0, 0);
						}
						else
							if (mode_inbox)
							{
								if (play.isSpeaking())
									play.stop();
								
								
								if (pointer < daftar_masuk.size() - 1)
								{
									pointer++;
									KotakMasuk kotak_masuk = daftar_masuk.get(pointer);
									play.speak(kotak_masuk.getPesan()+". Dari "+kotak_masuk.getNomer());
								}
								else
								{
									play.speak("Ini pesan terakhir.");
									pointer = daftar_masuk.size();
								}
								
							}
							else
								if (mode_outbox)
								{
									if (play.isSpeaking())
										play.stop();
									
									
									if (pointer < daftar_keluar.size() - 1)
									{
										pointer++;
										KotakKeluar kotak_keluar = daftar_keluar.get(pointer);
										play.speak(kotak_keluar.getPesan()+". Kepada "+kotak_keluar.getNomer());
									}
									else
									{
										play.speak("Ini pesan terakhir");
										pointer = daftar_keluar.size();
									}
									
								}
						
						
					}
				
				
				
				//braille.changeState(a, b, c, d, e, f);
				//editText2.setText(a+","+b+","+c+","+d+","+e+","+f);
				return false;
			}
		});
		
		
		
		

		
	}
	
	@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// TODO Auto-generated method stub
			
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}
	
	@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// TODO Auto-generated method stub
			switch (item.getItemId()) {
			case R.id.item1:
				Intent inten1 = new Intent(getApplicationContext(), ActivityDaftarKotakKeluar.class);
				startActivity(inten1);
				this.finish();
				break;

			case R.id.item2:
				Intent inten2 = new Intent(getApplicationContext(), ActivityKamus.class);
				startActivity(inten2);
				this.finish();
				break;
			case R.id.item3:
				Intent inten3 = new Intent(getApplicationContext(), ActivityPetunjuk.class);
				startActivity(inten3);
				this.finish();
				break;
				
//			case R.id.item4:
//				Intent inten4 = new Intent(getApplicationContext(), ActivityAboutUs.class);
//				startActivity(inten4);
//				this.finish();
//				break;
				
			case R.id.item5:
				Intent inten5 = new Intent(getApplicationContext(), ActivityKotakMasuk.class);
				startActivity(inten5);
				this.finish();
				break;
			}
			return true;
		}
	
	
	@Override
		public void onBackPressed() {
			// TODO Auto-generated method stub
			if (play.isSpeaking())
				play.stop();
			
			this.finish();
		}
	
	

	
	

}
