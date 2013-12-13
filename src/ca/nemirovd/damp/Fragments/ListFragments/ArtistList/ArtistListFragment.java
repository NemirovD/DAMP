package ca.nemirovd.damp.Fragments.ListFragments.ArtistList;


import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import ca.nemirovd.damp.MainActivity;
import ca.nemirovd.damp.SortListDialog;
import ca.nemirovd.damp.Fragments.MainScreenFragment;
import ca.nemirovd.damp.Fragments.ListFragments.AlbumList.AlbumListFragment;

import ca.nemirovd.damp.R;


public class ArtistListFragment extends MainScreenFragment {
	
	public static ArtistListFragment newInstance(Bundle extras){
		ArtistListFragment f = new ArtistListFragment();
		f.setArguments(extras);
		return f;
	}
	
	private ArtistListAdapter ad;
	
	private OnItemClickListener artistSelected = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Bundle extras = new Bundle();
			Cursor c = ad.getCursor();
			c.moveToPosition(position);
			
			String artId = c.getString(c.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
			
			String where = MediaStore.Audio.Artists.Albums.ARTIST + "=\"" + artId+"\"";
			
			extras.putBoolean(MainActivity.HAS_WHERE,true);
			extras.putString(MainActivity.WHERE,where);
			try {
				((MainActivity)getActivity()).switchSelectScreen(AlbumListFragment.newInstance(extras));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view;
		view = inflater.inflate(R.layout.damp_generic_list_layout, container, false);
		ad = new ArtistListAdapter(getActivity());
		ListView lv = (ListView)view.findViewById(R.id.generic_list_view);
		lv.setAdapter(ad);
		lv.setOnItemClickListener(artistSelected);
		
		return view;
	}

	@Override
	public SortListDialog getSortDialog() {
		return null;
	}

	@Override
	public void sort(int sm) {
		//TODO
	}

}
