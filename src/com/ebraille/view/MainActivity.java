package com.ebraille.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.VideoView;

import com.ebraille.controller.speech;
import com.example.test.R;

public class MainActivity extends Activity {
	
	private speech play;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.splash);
		
		play = new speech(getApplicationContext(), "com.googlecode.eyefree.espeak");
		
//		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		VideoView videoPlayer = (VideoView) findViewById(R.id.videoView1);
		
		Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.flash);
		videoPlayer.setVideoURI(uri);
		
//		Toast.makeText(getApplicationContext(), ""+videoPlayer.getDuration(), Toast.LENGTH_SHORT).show();
		videoPlayer.start();
		
		
//		if (!videoPlayer.isPlaying())
//		{
//			startActivity(new Intent(getApplicationContext(), AppsEbraille.class));
//			MainActivity.this.finish();
//		}
		
		Thread th = new Thread(new Runnable() {
			
			int i=0;
			@Override
			public void run() {
				
				while (true)
				{
					
					try
					{
						i++;
						Thread.sleep(1000);
						
						if (i==3)
						{
							break;
						}
					}
					catch (Exception ex)
					{
						ex.printStackTrace();
					}
				}
				
				startActivity(new Intent(getApplicationContext(), ActivityLogin.class));
				MainActivity.this.finish();
				play.speak("Silahkan mulai mengetik");
			}
		});
		
		th.start();
		
	}
	
	
	
}
