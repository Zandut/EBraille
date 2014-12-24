package com.ebraille.controller;

import java.util.ArrayList;

import com.example.test.R;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class AdapterListAboutUs extends ArrayAdapter<Integer>{
	private Context myContext;
	private ArrayList<Integer> daftar = new ArrayList<Integer>();
	
	public AdapterListAboutUs(Context context, ArrayList<Integer> isi) {
		// TODO Auto-generated constructor stub
		super(context, R.layout.layout_list_item, isi);
		this.myContext = context;
		this.daftar = isi;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View root = inflater.inflate(R.layout.layout_list_item, parent, false);
		
		ImageView image = (ImageView) root.findViewById(R.id.itemGambar);
		image.setImageURI(Uri.parse("android.resource://"+myContext.getPackageName()+"/"+daftar.get(position)));
		
		return root;
	}
}
