package com.example.dampdesign;

import java.util.ArrayList;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import com.example.dampdesign.Fragments.MenuFragment;
import com.example.dampdesign.Fragments.PlayerFragment;
import com.example.dampdesign.Fragments.WelcomeFragment;

public class MainActivity extends FragmentActivity {
	
	public static String HAS_WHERE = "haswhere";
	public static String WHERE = "where";
	
	//to tell the adapter when the fragment has changed
	boolean fragmentChanged;
	private MenuFragment menu;
	private PlayerFragment player;
	private Fragment selectScreen;
	
	private ViewPager pager;
	private PagerAdapter pagerAdapter;
	
	//personal backstack implementation
	//specifically for fragments
	private ArrayList<Fragment> backStack;
	
	//test code that's probably going to be gotten rid of
	//or evolved into something better
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		fragmentChanged = false;
		backStack = new ArrayList<Fragment>();
		
		menu = new MenuFragment();
		player = new PlayerFragment();
		selectScreen = new WelcomeFragment();
		
		pager = (ViewPager) findViewById(R.id.pager);
		pagerAdapter = new DampPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(pagerAdapter);
		pager.setCurrentItem(1);
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
			return;
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
		player.setSong(index, c);
		pager.setCurrentItem(2);
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
