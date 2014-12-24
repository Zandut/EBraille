package com.ebraille.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ebraille.view.fragment.*;
import com.example.test.R;

public class ActivityPetunjuk extends FragmentActivity {

	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page);
		
		
		
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		
		

	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		this.finish();
		startActivity(new Intent(getApplicationContext(), AppsEbraille.class));
	}

	

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return new petunjuk1();
			case 1:
				return new petunjuk2();
			case 2:
				return new petunjuk3();
			case 3:
				return new petunjuk4();
			case 4:
				return new petunjuk5();
			case 5:
				return new petunjuk6();
			case 6:
				return new petunjuk7();
			case 7:
				return new petunjuk8();
			case 8:
				return new petunjuk9();
			case 9:
				return new petunjuk10();
			case 10:
				return new petunjuk11();
			case 11:
				return new petunjuk12();
			case 12:
				return new petunjuk13();
			case 13:
				return new petunjuk14();
			case 14:
				return new petunjuk15();
			case 15:
				return new petunjuk16();
			case 16:
				return new petunjuk17();
			case 17:
				return new petunjuk18();
			case 18:
				return new petunjuk19();
			case 19:
				return new petunjuk20();
			
			
			}
			return null;
		}

		@Override
		public int getCount() {
			return 20;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.title_section1);
				
			case 1:
				return getString(R.string.title_section2);
			case 2:
				return getString(R.string.title_section3);
			case 3:
				return getString(R.string.title_section4);
			case 4:
				return getString(R.string.title_section5);
			case 5:
				return getString(R.string.title_section6);
			case 6:
				return getString(R.string.title_section7);
			case 7:
				return getString(R.string.title_section8);
			case 8:
				return getString(R.string.title_section9);
			case 9:
				return getString(R.string.title_section10);
			case 10:
				return getString(R.string.title_section11);
			case 11:
				return getString(R.string.title_section12);
			case 12:
				return getString(R.string.title_section13);
			case 13:
				return getString(R.string.title_section14);
			case 14:
				return getString(R.string.title_section15);
			case 15:
				return getString(R.string.title_section16);
			case 16:
				return getString(R.string.title_section17);
			case 17:
				return getString(R.string.title_section18);
			case 18:
				return getString(R.string.title_section19);
			case 19:
				return getString(R.string.title_section20);
			}
			
			return new String();
		}
	}

	public static class DummySectionFragment extends Fragment {
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		private ImageView imgnumber;
		private View rootView;
		private int skrinno;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			 rootView = inflater.inflate(R.layout.layout_petunjuk,
					container, false);
			 skrinno= getArguments().getInt(ARG_SECTION_NUMBER);
			
			imgnumber=(ImageView)rootView.findViewById(R.id.imgnumber);
			
			if(skrinno==0){
				imgnumber.setImageResource(R.drawable.petunjuk1);
			}
			else if(skrinno==1){
				imgnumber.setImageResource(R.drawable.petunjuk2);
			}
			else if(skrinno==2){
				imgnumber.setImageResource(R.drawable.petunjuk3);
			}
			else if(skrinno==3){
				imgnumber.setImageResource(R.drawable.petunjuk4);
			}
			else if(skrinno==4){
				imgnumber.setImageResource(R.drawable.petunjuk5);
			}
			else if(skrinno==5){
				imgnumber.setImageResource(R.drawable.petunjuk6);
			}
			else if(skrinno==6){
				imgnumber.setImageResource(R.drawable.petunjuk7);
			}
			else if(skrinno==7){
				imgnumber.setImageResource(R.drawable.petunjuk8);
			}
			else if(skrinno==8){
				imgnumber.setImageResource(R.drawable.petunjuk9);
			}
			else if(skrinno==9){
				imgnumber.setImageResource(R.drawable.petunjuk10);
			}
			else if(skrinno==10){
				imgnumber.setImageResource(R.drawable.petunjuk11);
			}
			else if(skrinno==11){
				imgnumber.setImageResource(R.drawable.petunjuk12);
			}
			else if(skrinno==12){
				imgnumber.setImageResource(R.drawable.petunjuk13);
			}
			else if(skrinno==13){
				imgnumber.setImageResource(R.drawable.petunjuk14);
			}
			else if(skrinno==14){
				imgnumber.setImageResource(R.drawable.petunjuk15);
			}
			else if(skrinno==15){
				imgnumber.setImageResource(R.drawable.petunjuk16);
			}
			else if(skrinno==16){
				imgnumber.setImageResource(R.drawable.petunjuk17);
			}
			else if(skrinno==17){
				imgnumber.setImageResource(R.drawable.petunjuk18);
			}
			else if(skrinno==18){
				imgnumber.setImageResource(R.drawable.petunjuk19);
			}
			else if(skrinno==19){
				imgnumber.setImageResource(R.drawable.petunjuk20);
			}
			
			return rootView;
		}
		
	}

}
