package com.ebraille.controller;

import java.util.ArrayList;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.test.R;

public class AdapterList extends ArrayAdapter<KamusBraille>{
	private Context myContext;
	private ArrayList<KamusBraille> daftar;
	
	public AdapterList(Context contex, ArrayList<KamusBraille> kamus) {
		// TODO Auto-generated constructor stub
		super(contex, R.layout.layout_list_item, kamus);
		this.myContext = contex;
		this.daftar = kamus;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.layout_list_item, parent, false);
		
		ImageView image = (ImageView) rowView.findViewById(R.id.itemGambar);
		image.setImageURI(Uri.parse("android.resource://"+myContext.getPackageName()+"/"+daftar.get(position).getGambar()));
//		image.setImageBitmap(BitmapFactory.decodeByteArray(daftar.get(position).getGambar(), 0, daftar.get(position).getGambar().length));
		
		return rowView;
	}

}
