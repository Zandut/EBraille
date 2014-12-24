package com.ebraille.models;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.ebraille.controller.Huruf;
import com.ebraille.controller.KotakKeluar;
import com.ebraille.controller.speech;


public class Database extends SQLiteOpenHelper{
	private static String path;
	private static String db_name = "eBraille.sqlite";
	private Context myContext;
	private static String table_kata = "kata";
	private static String table_angka = "angka";
	private String table_kotak_keluar = "kotak_keluar";
	private String pesan = "pesan";
	private String nomer = "nomer";
	private String status = "status";
	private String waktu = "waktu";
	private static String Id = "_id";
	private static String Huruf = "Huruf";
	private static String Satuan = "satuan";
	private static String satu = "satu";
	private static String dua = "dua";
	private static String tiga = "tiga";
	private static String empat = "empat";
	private static String lima = "lima";
	private static String enam = "enam";
	private speech play;
	private ArrayList<Huruf> daftar_huruf = new ArrayList<Huruf>();
	private ArrayList<Huruf> daftar_angka = new ArrayList<Huruf>();
	
	private SQLiteDatabase myDatabase;
	
	public Database(Context context)
	{
		super(context, db_name, null, 1);
		this.myContext = context;
		path = "/data/data/"+myContext.getPackageName()+"/databases/";
		
		play = new speech(myContext, "com.googlecode.eyefree.espeak");
		daftar_huruf.add(new Huruf("a", 1, 0, 0, 0, 0, 0));
		daftar_huruf.add(new Huruf("b", 1, 1, 0, 0, 0, 0));
		daftar_huruf.add(new Huruf("c", 1, 0, 0, 1, 0, 0));
		daftar_huruf.add(new Huruf("d", 1, 0, 0, 1, 1, 0));
		daftar_huruf.add(new Huruf("e", 1, 0, 0, 0, 1, 0));
		daftar_huruf.add(new Huruf("f", 1, 1, 0, 1, 0, 0));
		daftar_huruf.add(new Huruf("g", 1, 1, 0, 1, 1, 0));
		daftar_huruf.add(new Huruf("h", 1, 1, 0, 0, 1, 0));
		daftar_huruf.add(new Huruf("i", 0, 1, 0, 1, 0, 0));
		daftar_huruf.add(new Huruf("j", 0, 1, 0, 1, 1, 0));
		daftar_huruf.add(new Huruf("k", 1, 0, 1, 0, 0, 0));
		daftar_huruf.add(new Huruf("l", 1, 1, 1, 0, 0, 0));
		daftar_huruf.add(new Huruf("m", 1, 0, 1, 1, 0, 0));
		daftar_huruf.add(new Huruf("n", 1, 0, 1, 1, 1, 0));
		daftar_huruf.add(new Huruf("o", 1, 0, 1, 0, 1, 0));
		daftar_huruf.add(new Huruf("p", 1, 1, 1, 1, 0, 0));
		daftar_huruf.add(new Huruf("q", 1, 1, 1, 1, 1, 0));
		daftar_huruf.add(new Huruf("r", 1, 1, 1, 0, 1, 0));
		daftar_huruf.add(new Huruf("s", 0, 1, 1, 1, 0, 0));
		daftar_huruf.add(new Huruf("t", 0, 1, 1, 1, 1, 0));
		daftar_huruf.add(new Huruf("u", 1, 0, 1, 0, 0, 1));
		daftar_huruf.add(new Huruf("v", 1, 1, 1, 0, 0, 1));
		daftar_huruf.add(new Huruf("w", 0, 1, 0, 1, 1, 1));
		daftar_huruf.add(new Huruf("x", 1, 0, 1, 1, 0, 1));
		daftar_huruf.add(new Huruf("y", 1, 0, 1, 1, 1, 1));
		daftar_huruf.add(new Huruf("z", 1, 0, 1, 0, 1, 1));
		daftar_huruf.add(new Huruf("modeinbox", 0, 1, 0, 0, 0, 0));
		daftar_huruf.add(new Huruf("modeoutbox", 0, 0, 0, 0, 1, 0));
		daftar_huruf.add(new Huruf("gantimode", 0, 0, 0, 1, 0, 0));
		
		daftar_angka.add(new Huruf("1", 1, 0, 0, 0, 0, 0));
		daftar_angka.add(new Huruf("2", 1, 1, 0, 0, 0, 0));
		daftar_angka.add(new Huruf("3", 1, 0, 0, 1, 0, 0));
		daftar_angka.add(new Huruf("4", 1, 0, 0, 1, 1, 0));
		daftar_angka.add(new Huruf("5", 1, 0, 0, 0, 1, 0));
		daftar_angka.add(new Huruf("6", 1, 1, 0, 1, 0, 0));
		daftar_angka.add(new Huruf("7", 1, 1, 0, 1, 1, 0));
		daftar_angka.add(new Huruf("8", 1, 1, 0, 0, 1, 0));
		daftar_angka.add(new Huruf("9", 0, 1, 0, 1, 0, 0));
		daftar_angka.add(new Huruf("0", 0, 1, 0, 1, 1, 0));
		daftar_angka.add(new Huruf("modeinbox", 0, 1, 0, 0, 0, 0));
		daftar_angka.add(new Huruf("modeoutbox", 0, 0, 0, 0, 1, 0));
		daftar_angka.add(new Huruf("gantimode", 0, 0, 0, 1, 0, 0));
	}
	
	public void createDatabase() throws IOException
	{
		boolean is_ada = checkDatabase();
		if (is_ada)
		{
			//Toast.makeText(myContext, "Database is exist", Toast.LENGTH_SHORT).show();
		}
		else
		{
			myDatabase = this.getReadableDatabase();
			try
			{
				copyDatabase();
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
				Toast.makeText(myContext, ex.toString(), Toast.LENGTH_LONG).show();
			}
			
		}
	}
	
	private boolean checkDatabase()
	{
		SQLiteDatabase db = null;
		try
		{
			 String myPath = path + db_name;
			 db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		}
		catch (SQLiteException ex)
		{
			//Toast.makeText(myContext, "Error check : "+ex.getMessage(), Toast.LENGTH_SHORT).show();
			
		}
		
		if (db != null)
		{
			db.close();
		}
		
		return db != null ? true : false;
	}
	
	private void copyDatabase() throws IOException {
		InputStream myInput = myContext.getAssets().open(db_name);
		String outFile = path + db_name;
		OutputStream myOutput = new FileOutputStream(outFile);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0)
		{
			myOutput.write(buffer, 0, length);
		}
		
		myOutput.flush();
		myOutput.close();
		myInput.close();
		
		
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
			
	}
	
	public void openDatabase() throws SQLiteException{
		String mypath = path + db_name;
		myDatabase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READONLY);
	}
	
	@Override
	public synchronized void close() {
		// TODO Auto-generated method stub
		if (myDatabase != null)
		{
			myDatabase.close();
		}
		
		super.close();
	}
	
	public void insertKotakKeluar(KotakKeluar k) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		try
		{
			db.execSQL("insert into "+table_kotak_keluar+"(nomer, pesan, status, waktu) values('"+k.getNomer()+"','"+k.getPesan()+"','"+k.getStatus()+"',CURRENT_TIMESTAMP)");
			//Toast.makeText(myContext, "Pesan Terkirim", Toast.LENGTH_SHORT).show();
		}
		catch (SQLiteException ex)
		{
			Toast.makeText(myContext, ex.getMessage(), Toast.LENGTH_SHORT).show();
		}
		
		db.close();
		
	}
	
	//6,5,4,3 
	//1,2,3,4
	public ArrayList<KotakKeluar> getAllKotakKeluar()
	{
		SQLiteDatabase db = this.getReadableDatabase();
		//int i=1;
		ArrayList<KotakKeluar> array = new ArrayList<KotakKeluar>();
		
		Cursor cs = db.rawQuery("select * from kotak_keluar order by waktu desc", null);
		
		while (cs.moveToNext())
		{
			KotakKeluar k = new KotakKeluar();
			k.setId(cs.getInt(cs.getColumnIndex(Id)));
			k.setNomer(cs.getString(cs.getColumnIndex(nomer)));
			k.setPesan(cs.getString(cs.getColumnIndex(pesan)));
			k.setStatus(cs.getString(cs.getColumnIndex(status)));
			k.setWaktu(cs.getString(cs.getColumnIndex(waktu)));
			
			array.add(k);
		}
		
		db.close();
		
		return array;
	}
	
	public KotakKeluar getKotakKeluar(int id)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		KotakKeluar k = new KotakKeluar();
		Cursor cs = db.rawQuery("select * from kotak_keluar where _id='"+id+"'", null);
		if (cs.moveToFirst())
		{
			k.setNomer(cs.getString(cs.getColumnIndex(nomer)));
			k.setPesan(cs.getString(cs.getColumnIndex(pesan)));
			k.setStatus(cs.getString(cs.getColumnIndex(status)));
			k.setWaktu(cs.getString(cs.getColumnIndex(waktu)));
		}
		
		db.close();
		
		return k;
	}
	
	public int getIDKotakKeluar()
	{
		int id = -99;
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cs = db.rawQuery("select * from kotak_keluar order by waktu desc", null);
		
		if (cs.moveToFirst())
		{
			id = cs.getInt(cs.getColumnIndex(Id));
		}
		
		db.close();
		return id;
	}
	
//	
	public void deleteAllKotakKeluar()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from kotak_keluar");
		Toast.makeText(myContext, "Kotak Keluar telah terhapus", Toast.LENGTH_SHORT).show();
		db.close();
	}
	
	public String getHuruf(int state1, int state2, int state3, int state4, int state5, int state6) {
		
		Huruf cari = new Huruf();
		boolean ketemu = false;
		for (Huruf huruf : daftar_huruf)
		{
			if (huruf.getState()[0] == state1 && huruf.getState()[1] == state2 && huruf.getState()[2] == state3 && huruf.getState()[3] == state4 && huruf.getState()[4] == state5 && huruf.getState()[5] == state6)
			{
				//Toast.makeText(myContext, "Ketemu", Toast.LENGTH_SHORT).show();
				cari = huruf;
				ketemu = true;
				break;
			}
			else
			{
				ketemu = false;
			}
			
		}
		
		if (ketemu)
		{
			if (!cari.getHuruf().equals("modeinbox") && !cari.getHuruf().equals("modeoutbox") && !cari.getHuruf().equals("gantimode"))
				play.speak(cari.getHuruf());
			
		}
		else
		{
			cari.setHuruf("");
			
		}
		
		return cari.getHuruf();
		
		
		
	}
	
	public String getAngka(int state1, int state2, int state3, int state4, int state5, int state6) {

		Huruf cari = new Huruf();
		boolean ketemu = false;
		for (Huruf huruf : daftar_angka)
		{
			if (huruf.getState()[0] == state1 && huruf.getState()[1] == state2 && huruf.getState()[2] == state3 && huruf.getState()[3] == state4 && huruf.getState()[4] == state5 && huruf.getState()[5] == state6)
			{
				//Toast.makeText(myContext, "Ketemu", Toast.LENGTH_SHORT).show();
				cari = huruf;
				ketemu = true;
				break;
			}
			else
			{
				ketemu = false;
			}
			
		}
		
		if (ketemu)
		{
			if (!cari.getHuruf().equals("modeinbox") && !cari.getHuruf().equals("modeoutbox") && !cari.getHuruf().equals("gantimode"))
				play.speak(cari.getHuruf());
			
			
		}
		else
		{
			cari.setHuruf("");
			
		}
		
		return cari.getHuruf();
	}
	
	
	
	
	

}
