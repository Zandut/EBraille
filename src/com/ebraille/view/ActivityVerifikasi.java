package com.ebraille.view;

import java.util.HashMap;

import org.json.JSONObject;

import com.ebraille.models.Aplikasi;
import com.example.test.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityVerifikasi extends Activity{

	
	private EditText kodeVerifikasi;
	private Button btnSubmit;
	
	private Aplikasi apps;
	private Activity activity;
	private Bundle extras;
	
	private class sendingSMS extends AsyncTask<String, String, JSONObject>
	{
		@Override
		protected JSONObject doInBackground(String... params) {
			// TODO Auto-generated method stub
			apps.resetJson();
			apps.getJson().setUrl("https://reguler.zenziva.net/apps/smsapi.php?userkey=2njxiw&passkey=6e616e79&nohp="+params[0]+"&pesan="+params[1]);
			
			return apps.getJson().getXMLToJsonObject();
		}
		
		@Override
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			if (result != null)
			{
				try {
					String response = result.getString("response");
					JSONObject jsonResponse = new JSONObject(
							response);

					String message = jsonResponse
							.getString("message");
					JSONObject jsonMessage = new JSONObject(
							message);

					int status = Integer
							.parseInt(jsonMessage
									.getString("status"));
					if (status == 0)
					{
						Toast.makeText(activity, "Verification Code has been sent. If not, contact the administrator.", Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(activity, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
				}
			}
			
		}
	}
	
	private class Verifikasi extends AsyncTask<String, String, JSONObject>
	{
		private ProgressDialog progress=  new ProgressDialog(activity);
		
		
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
			HashMap<String, String> parameter = new HashMap<String, String>();
			parameter.put("username", params[0]);
			
			
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
						
						JSONObject data = result.getJSONObject("data");
						Intent intent = new Intent(activity, AppsEbraille.class);
						intent.putExtra("id", data.getInt("id"));
						startActivity(intent);
						activity.finish();
					}
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(activity, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.verifikasi);
		
		extras = getIntent().getExtras();
		
		activity = ActivityVerifikasi.this;
		apps = new Aplikasi(activity);
		
		String data[] = {extras.getString("phone_number"), "Kode Anda adalah "+extras.getString("kode_verifikasi")};
		
		new sendingSMS().execute(data);
		
		kodeVerifikasi = (EditText) findViewById(R.id.kode);
		btnSubmit = (Button) findViewById(R.id.button1);
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (kodeVerifikasi.getText().toString().equals(extras.getString("kode_verifikasi")))
				{
					
				}
			}
		});
		
		
	}
}
