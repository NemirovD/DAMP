package com.example.dampdesign;

import java.util.ArrayList;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dampdesign.Fragments.MenuFragment;
import com.example.dampdesign.Fragments.PlayerFragment;
import com.example.dampdesign.Fragments.WelcomeFragment;

public class MainActivity extends FragmentActivity {
	public static String HAS_WHERE = "haswhere";
	public static String WHERE = "where";
	
	//to tell the adapter when the fragment has changed
	String tester;
	boolean fragmentChanged;
	private MenuFragment menu;
	private PlayerFragment player;
	private Fragment selectScreen;
	
	private TextView leftTitle;
	private TextView rightTitle;
	public LinearLayout title;
	
	public String song;
	public String artist;
	public Drawable titleArt;
	
	private ViewPager pager;
	private PagerAdapter pagerAdapter;
	
	//personal backstack implementation
	//specifically for fragments
	private ArrayList<Fragment> backStack;
	
	OnClickListener leftClick = new OnClickListener(){
		@Override
		public void onClick(View arg0) {
			if(pager.getCurrentItem() > 0)
			pager.setCurrentItem(pager.getCurrentItem()-1);
		}
	};
	
	OnClickListener rightClick = new OnClickListener(){
		@Override
		public void onClick(View arg0) {
			if(pager.getCurrentItem() < pagerAdapter.getCount())
				pager.setCurrentItem(pager.getCurrentItem()+1);
		}
	};
	
	OnPageChangeListener pageListener = new OnPageChangeListener(){
		private String[] viewNames = {"Menu","Songs","Player"};
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {	
		}
		@Override
		public void onPageSelected(int position) {
			/*
			if(position == 0){
				leftTitle.setText("");
				leftTitle.setClickable(false);
			}else{
				leftTitle.setText(viewNames[position-1]);
				leftTitle.setClickable(true);
			}
			
			if(position == (viewNames.length-1)){
				rightTitle.setText("");
				rightTitle.setClickable(false);
			}else{
				rightTitle.setText(viewNames[position+1]);
				rightTitle.setClickable(true);
			}*/
			
			switch(position){
			case 0:
				title.setVisibility(View.GONE);
				return;
			case 1:
				title.setVisibility(View.VISIBLE);
				title.removeAllViews();
				title = (LinearLayout) getLayoutInflater().inflate(R.layout.title_mainscreen, title);
				leftTitle = (TextView) title.findViewById(R.id.main_title_left);
				leftTitle.setOnClickListener(leftClick);
				rightTitle = (TextView) title.findViewById(R.id.main_title_right);
				rightTitle.setOnClickListener(rightClick);
				tester = "not works";
				return;
			case 2:
				title.removeAllViews();
				title = (LinearLayout) getLayoutInflater().inflate(R.layout.title_songscreen, title);
				if(song!=null&&artist!=null){
					TextView songtv = (TextView) findViewById(R.id.title_songscreen_text_1);
					TextView artisttv = (TextView) findViewById(R.id.title_songscreen_text_2);
					songtv.setText(song);
					artisttv.setText(artist);
				}
				if(titleArt!=null){
					ImageView titleArtiv = (ImageView) findViewById(R.id.title_songscreen_album_art);
					titleArtiv.setImageDrawable(titleArt);
				}
				return;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Remove title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		fragmentChanged = false;
		backStack = new ArrayList<Fragment>();
		resetBackStack();
		
		menu = new MenuFragment();
		player = new PlayerFragment();
		selectScreen = new WelcomeFragment();
		
		leftTitle = (TextView) findViewById(R.id.main_title_left);
		leftTitle.setOnClickListener(leftClick);
		rightTitle = (TextView) findViewById(R.id.main_title_right);
		rightTitle.setOnClickListener(rightClick);
		
		title = (LinearLayout)findViewById(R.id.main_title_layout);
		
		pager = (ViewPager) findViewById(R.id.pager);
		pagerAdapter = new DampPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(pagerAdapter);
		pager.setCurrentItem(1);
		pager.setOnPageChangeListener(pageListener);
		pager.setPageTransformer(false, new ZoomOutPageTransformer());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onBackPressed(){
		int page = pager.getCurrentItem();
		if(page == 0 || page == 2){
			pager.setCurrentItem(1);
			return;
		}
		
		if(backStack.size()==1){
			Intent setIntent = new Intent(Intent.ACTION_MAIN);
			setIntent.addCategory(Intent.CATEGORY_HOME);
			setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(setIntent);
		}else{
			backStack.remove(backStack.size()-1);
			setSelectScreen(backStack.get(backStack.size()-1));
		}
	}
	
	public void switchSelectScreen(Fragment frag) throws Exception{
		getSupportFragmentManager().beginTransaction().remove(selectScreen).commit();
		
		if(frag.getArguments() == null){
			resetBackStack();
		}
		backStack.add(frag);
		
		setSelectScreen(frag);
		pager.setCurrentItem(1);
	}
	
	public void playSong(int index, Cursor c){
		pager.setCurrentItem(2);
		player.setSong(index, c);
	}
	
	private void setSelectScreen(Fragment frag){
		selectScreen = frag;
		fragmentChanged = true;
		pagerAdapter.notifyDataSetChanged();
	}
	
	private void resetBackStack(){
		backStack.clear();
		backStack.add(new WelcomeFragment());
	}
	
	private class DampPagerAdapter extends FragmentStatePagerAdapter{
		//class designed to allow switching to and from player fragment and menu fragment
		public DampPagerAdapter(FragmentManager fm){
			super(fm);
		}
		
		@Override
		public Fragment getItem(int position) {
			switch(position){
			case 0:
				return menu;
			default:
			case 1:
				return selectScreen;
			case 2:
				return player;
			}
		}
		
		@Override
		public float getPageWidth(int position){
			if(position == 0){
				return (float) 0.45;
			}else{
				return (float) 1.0;
			}
		}
		
		//if this were not here the app would crash
		//like seriously, after a fragment switch  IT WOULD CRASH HARD
	    @Override
	    public int getItemPosition(Object object)
	    {
	        if (fragmentChanged && !(object instanceof PlayerFragment) && !(object instanceof MenuFragment)){
	        	fragmentChanged = false;
	            return POSITION_NONE;
	        }
	        return POSITION_UNCHANGED;
	    }

		@Override
		public int getCount(){
			return 3;
		}
	}
}
