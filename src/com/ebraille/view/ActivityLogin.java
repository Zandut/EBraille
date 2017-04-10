package com.ebraille.view;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.ebraille.models.Aplikasi;
import com.ebraille.models.JSON;
import com.example.test.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ActivityLogin extends Activity{
	
	private EditText username, password;
	private Button btnSubmit, btnRegistrasi;
	private Activity activity;
	private Aplikasi apps;
	
	private class login extends AsyncTask<String, String, JSONObject>
	{
		
		private ProgressDialog progress = new ProgressDialog(ActivityLogin.this);
		
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progress.setMessage(getString(R.string.loading));
			progress.setCancelable(false);
			progress.show();
		}
		
		
		@Override
		protected JSONObject doInBackground(String... params) {
			// TODO Auto-generated method stub
			apps.resetJson();
			HashMap<String, String> parameter  = new HashMap<String, String>();
			parameter.put("username", params[0]);
			parameter.put("password", params[1]);
			
			
			return apps.getJson().getJsonObject(parameter);
		}
		
		@Override
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			progress.dismiss();
			if (result != null)
			{
				try {
					if (result.getInt("status") == 1)
					{
						
						final JSONObject data = result.getJSONObject("data");
						
						if (data.getInt("status") == 1)
						{
							Intent intent = new Intent(activity, AppsEbraille.class);
							intent.putExtra("id", data.getInt("id"));
							startActivity(intent);
							activity.finish();
						}
						else
						{
							new AlertDialog.Builder(ActivityLogin.this)
							.setIcon(android.R.drawable.ic_dialog_alert)
							.setMessage("Please verify your account first !")
							.setTitle("Confirmation")
							.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									Intent intent = new Intent(activity, ActivityVerifikasi.class);
									try {
										intent.putExtra("username", data.getString("username"));
										intent.putExtra("phone_number", data.getString("phone_number"));
										intent.putExtra("kode_verifikasi", data.getString("kode_verifikasi"));
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									startActivity(intent);
									activity.finish();
									
								}
							});
						}
					}
					else
					{
						new AlertDialog.Builder(ActivityLogin.this)
							.setIcon(android.R.drawable.ic_dialog_alert)
							.setMessage("Username doesn't exist. Please registration first !")
							.setTitle("Confirmation")
							.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									Intent intent = new Intent(ActivityLogin.this, ActivityRegistrasi.class);
									startActivity(intent);
									activity.finish();
								}
							});
					}
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(ActivityLogin.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		
		
		activity = ActivityLogin.this;
		
		apps = new Aplikasi(activity);
		
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		
		btnRegistrasi = (Button) findViewById(R.id.btnRegistrasi);
		btnRegistrasi.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ActivityLogin.this, ActivityRegistrasi.class);
				startActivity(intent);
				activity.finish();
			}
		});
		btnSubmit = (Button) findViewById(R.id.btnLogin);
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String[] data = {username.getText().toString(), password.getText().toString()};
				new login().execute(data);
			}
		});
		
		
		
	}
}
