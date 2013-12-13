package ca.nemirovd.damp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import ca.nemirovd.damp.MainActivity;
import ca.nemirovd.damp.Fragments.ListFragments.AlbumList.AlbumListFragment;
import ca.nemirovd.damp.Fragments.ListFragments.ArtistList.ArtistListFragment;
import ca.nemirovd.damp.Fragments.ListFragments.SongList.SongListFragment;

import ca.nemirovd.damp.R;


public class MenuFragment extends Fragment {
	
	TextView songTextView;
	TextView artistTextView;
	TextView albumTextView;
	
	private OnClickListener songListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			try {
				((MainActivity)getActivity()).switchSelectScreen(new SongListFragment());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	private OnClickListener albumListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			try {
				((MainActivity)getActivity()).switchSelectScreen(new AlbumListFragment());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	private OnClickListener artistListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			try {
				((MainActivity)getActivity()).switchSelectScreen(new ArtistListFragment());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedINstanceState){
		View view;
		view = inflater.inflate(R.layout.menu_fragment,container, false);
		
		songTextView = (TextView) view.findViewById(R.id.viewSongs);
		songTextView.setClickable(true);
		songTextView.setOnClickListener(songListener);
		
		albumTextView = (TextView) view.findViewById(R.id.viewAlbums);
		albumTextView.setClickable(true);
		albumTextView.setOnClickListener(albumListener);
		
		artistTextView = (TextView) view.findViewById(R.id.viewArtists);
		artistTextView.setClickable(true);
		artistTextView.setOnClickListener(artistListener);
		
		return view;
	}
}
