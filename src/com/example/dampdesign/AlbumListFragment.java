package com.example.dampdesign;

import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class AlbumListFragment extends Fragment {
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedINstanceState){
		View view;
		view = inflater.inflate(R.layout.album_list, container, false);
		ListView lv = (ListView)view.findViewById(R.id.album_list_view);
		lv.setAdapter(new MusicAdapter(getActivity(),MediaStore.Audio.Media.ALBUM,MediaStore.Audio.Media.ARTIST));
		
		return view;
	}

}
