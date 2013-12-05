package com.example.dampdesign;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MusicAdapter extends BaseAdapter {
	private Context context;
	private Cursor cursor;
	
	public MusicAdapter(Context c){
		context = c;
	}
	
	public MusicAdapter(Context c, Cursor cu){
		context = c;
		cursor = cu;
	}

	@Override
	public int getCount() {
		if(cursor!=null)
			return cursor.getCount();
		else
			return 0;
	}

	@Override
	public Object getItem(int position) {
		//TODO
		return null;
	}

	@Override
	public long getItemId(int position) {
		//TODO
		return 0;
	}
	
	private static class ItemHolder{
		public ImageView albumArt;
		public TextView songItem1;
		public TextView songItem2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		System.gc();
		ItemHolder holder = new ItemHolder();
		
		if(convertView == null){
			
		}else{
			
		}
		
		return null;
	}

}
