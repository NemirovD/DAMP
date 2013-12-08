package com.example.dampdesign.Fragments.ListFragments.AlbumList;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.example.dampdesign.Fragments.ListFragments.SongList.SongListFragment;

public class AlbumListFragment extends Fragment {
	private AlbumListAdapter ad;
	
	private OnItemClickListener albumSelected = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			
			Bundle extras = new Bundle();
			Cursor c = ad.getCursor();
			c.moveToPosition(position);
			String alId = c.getString(c.getColumnIndex(MediaStore.Audio.Albums._ID));
			String where = MediaStore.Audio.Media.ALBUM_ID + "=" + alId;
			
			extras.putBoolean(MainActivity.HAS_WHERE,true);
			extras.putString(MainActivity.WHERE,where);
			try {
				((MainActivity)getActivity()).switchSelectScreen(SongListFragment.newInstance(extras));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	public static AlbumListFragment newInstance(Bundle extras){
		AlbumListFragment f = new AlbumListFragment();
		f.setArguments(extras);
		return f;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedINstanceState){
		View view;
		view = inflater.inflate(R.layout.damp_generic_list_layout, container, false);
		
		Bundle extras = getArguments();
		if(extras!=null){
			boolean hasWhere = extras.getBoolean(MainActivity.HAS_WHERE, false);
			if(hasWhere){
				String where = extras.getString(MainActivity.WHERE);
				Log.d("xxx", where);
				ad = new AlbumListAdapter(getActivity(),where);
			}else{
				ad = new AlbumListAdapter(getActivity());
			}
		}else{
			ad = new AlbumListAdapter(getActivity());
		}
		
		ListView lv = (ListView)view.findViewById(R.id.generic_list_view);
		lv.setAdapter(ad);
		lv.setOnItemClickListener(albumSelected);
		
		return view;
	}

}
