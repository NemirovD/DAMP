package com.example.dampdesign.Fragments.ListFragments.SongList;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.dampdesign.MainActivity;
import com.example.dampdesign.R;
import com.example.dampdesign.SortListDialog;
import com.example.dampdesign.Fragments.MainScreenFragment;
import com.example.dampdesign.Fragments.ListFragments.AlbumList.AlbumListAdapter;

public class SongListFragment extends MainScreenFragment {
	private SongListAdapter ad;
	private ListView lv;
	private String where;
	
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
				where = extras.getString(MainActivity.WHERE);
				ad = new SongListAdapter(getActivity(),where);
			}else{
				ad = new SongListAdapter(getActivity());
			}
		}else{
			ad = new SongListAdapter(getActivity());
		}
		
		lv = (ListView)view.findViewById(R.id.generic_list_view);
		lv.setAdapter(ad);
		lv.setOnItemClickListener(songSelected);
		
		return view;
	}

	@Override
	public SortListDialog getSortDialog() {
		SortListDialog ret = new SortListDialog();
		ret.setSortMethod(R.array.songs_sort);
		return ret;
	}

	@Override
	public void sort(int sm) {
		ad = new SongListAdapter(getActivity(),where,sm);
		lv.setAdapter(ad);
	}
}
