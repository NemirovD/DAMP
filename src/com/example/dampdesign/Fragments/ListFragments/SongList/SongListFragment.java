package com.example.dampdesign.Fragments.ListFragments.SongList;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.dampdesign.MainActivity;
import com.example.dampdesign.R;

public class SongListFragment extends Fragment {
	private SongListAdapter ad;
	private OnItemClickListener songSelected = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Cursor c = ad.getCursor();
			((MainActivity)getActivity()).playSong(position, c);
		}
	};
	
	public static SongListFragment newInstance(Bundle extras){
		SongListFragment f = new SongListFragment();
		f.setArguments(extras);
		return f;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view;
		view = inflater.inflate(R.layout.damp_generic_list_layout, container, false);
		
		Bundle extras = getArguments();
		if(extras!=null){
			boolean hasWhere = extras.getBoolean(MainActivity.HAS_WHERE, false);
			if(hasWhere){
				String where = extras.getString(MainActivity.WHERE);
				Log.d("xxx", where);
				ad = new SongListAdapter(getActivity(),where);
			}else{
				ad = new SongListAdapter(getActivity());
			}
		}else{
			ad = new SongListAdapter(getActivity());
		}
		
		ListView lv = (ListView)view.findViewById(R.id.generic_list_view);
		lv.setAdapter(ad);
		lv.setOnItemClickListener(songSelected);
		
		return view;
	}
}
