package com.ebraille.view;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.ebraille.models.Aplikasi;
import com.example.test.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityRegistrasi extends Activity{
	
	
	private EditText username, password, confirmPassword, name, phoneNumber, email;
	
	private Button btnRegis;
	private Aplikasi apps;
	
	private Activity activity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registrasi);
		
		activity = ActivityRegistrasi.this;
		
		apps = new Aplikasi(activity);
		
		username = (EditText) findViewById(R.id.username);
		
		password = (EditText) findViewById(R.id.password);
		confirmPassword = (EditText) findViewById(R.id.confirmpassword);
		name = (EditText) findViewById(R.id.name);
		phoneNumber = (EditText) findViewById(R.id.phoneNumber);
		email = (EditText) findViewById(R.id.email);
		
		btnRegis = (Button) findViewById(R.id.btnSubmit);
		btnRegis.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!password.getText().toString().equals(confirmPassword.getText().toString()))
				{
					confirmPassword.setError("Confirmation password doesn't match.");
				}
				else
				{
					if (!email.getText().toString().contains("@"))
					{
						email.setError("Email is not valid.");
					}
					else
					{
						String data[] = {username.getText().toString(), password.getText().toString(), name.getText().toString()
								, phoneNumber.getText().toString(), email.getText().toString()};
						
						
					}
				}
			}
		});
		
		
	}
	
	
	
	private class registrasi extends AsyncTask<String, String, JSONObject>
	{
		private ProgressDialog progress = new ProgressDialog(activity);
		
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progress.setMessage(getString(R.string.loading));
		}
		
		@Override
		protected JSONObject doInBackground(String... params) {
			// TODO Auto-generated method stub
			apps.resetJson();
			HashMap<String, String> parameter = new HashMap<String, String>();
			parameter.put("username", params[0]);
			parameter.put("password", params[1]);
			parameter.put("name", params[2]);
			parameter.put("phone_number", params[3]);
			parameter.put("email", params[4]);
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
						
						new AlertDialog.Builder(activity)
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setMessage("Succesfully. Press OK to verify your account !")
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
						
					}else
					{
						Toast.makeText(activity, "Username does exist", Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(activity, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
				}
			}
			else
			{
				Toast.makeText(activity, "ERROR", Toast.LENGTH_SHORT).show();
			}
		}
	}

}
