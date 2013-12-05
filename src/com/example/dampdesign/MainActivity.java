package com.example.dampdesign;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends FragmentActivity {
	//to tell the adapter when the fragment has changed
	boolean fragmentChanged;
	private PlayerFragment player;
	private Fragment selectScreen;
	
	private ViewPager pager;
	private PagerAdapter pagerAdapter;
	
	
	//test code that's probably going to be gotten rid of
	//or evolved into something better
	public void switchSelectScreen(Fragment frag, Bundle extras) throws Exception{
		getSupportFragmentManager().beginTransaction().remove(selectScreen).commit();
		selectScreen = frag.getClass().newInstance();
		pagerAdapter.notifyDataSetChanged();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fragmentChanged = true;
		
		player = new PlayerFragment();
		selectScreen = new WelcomeFragment();
		
		pager = (ViewPager) findViewById(R.id.pager);
		pagerAdapter = new DampPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(pagerAdapter);
		//pager.setPageTransformer(false, new DepthPageTransformer());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
	
	private class DampPagerAdapter extends FragmentStatePagerAdapter{
		//class designed to allow switching to the player
		public DampPagerAdapter(FragmentManager fm){
			super(fm);
		}
		
		@Override
		public Fragment getItem(int position) {
			switch(position){
			default:
			case 0:
				return selectScreen;
			case 1:
				return player;
			}
		}
		
		//if this were not here the app would crash
	    @Override
	    public int getItemPosition(Object object)
	    {
	        if (fragmentChanged && !(object instanceof PlayerFragment)){
	        	fragmentChanged = false;
	            return POSITION_NONE;
	        }
	        return POSITION_UNCHANGED;
	    }

		@Override
		public int getCount() {
			return 2;
		}
	}
}
