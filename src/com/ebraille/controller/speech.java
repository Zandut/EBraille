package com.ebraille.controller;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech;


@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)

public class speech implements OnInitListener {
	private TextToSpeech tts;
	
	public speech(Context context, String engine) {
		tts = new TextToSpeech(context, this, engine);
	}
	
	public void speak(String text) {
		tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
		
	}

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		//tts.setSpeechRate((float)1.5);
		
		
	}
	
	public void stop()
	{
		tts.stop();
	}
	
	public int getMax() {
		
		return TextToSpeech.getMaxSpeechInputLength();
	}
	
	public boolean isSpeaking() {
		return tts.isSpeaking();
	}
	
}
