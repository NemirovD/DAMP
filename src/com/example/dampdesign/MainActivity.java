package com.example.dampdesign;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;

import com.example.dampdesign.Fragments.MenuFragment;
import com.example.dampdesign.Fragments.PlayerFragment;
import com.example.dampdesign.Fragments.WelcomeFragment;

public class MainActivity extends FragmentActivity {
	//to tell the adapter when the fragment has changed
	boolean fragmentChanged;
	private MenuFragment menu;
	private PlayerFragment player;
	private Fragment selectScreen;
	
	private ViewPager pager;
	private PagerAdapter pagerAdapter;
	private DepthPageTransformer dPT;
	
	//test code that's probably going to be gotten rid of
	//or evolved into something better
	public void switchSelectScreen(Fragment frag, Bundle extras) throws Exception{
		getSupportFragmentManager().beginTransaction().remove(selectScreen).commit();
		selectScreen = frag;
		fragmentChanged = true;
		pagerAdapter.notifyDataSetChanged();
		pager.setCurrentItem(1,true);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fragmentChanged = false;
		
		menu = new MenuFragment();
		player = new PlayerFragment();
		selectScreen = new WelcomeFragment();
		
		pager = (ViewPager) findViewById(R.id.pager);
		pagerAdapter = new DampPagerAdapter(getSupportFragmentManager());
		dPT =  new DepthPageTransformer();
		pager.setAdapter(pagerAdapter);
		pager.setCurrentItem(1);
		pager.setPageTransformer(false,dPT);
		pager.setPageTransformer(false, null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class DampPagerAdapter extends FragmentStatePagerAdapter{
		//class designed to allow switching to and from player fragment
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
