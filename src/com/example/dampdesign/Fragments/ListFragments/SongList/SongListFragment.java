package com.example.dampdesign.Fragments.ListFragments.SongList;

import com.example.dampdesign.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SongListFragment extends Fragment {
	private SongListAdapter ad;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedINstanceState){
		View view;
		view = inflater.inflate(R.layout.damp_generic_list_layout, container, false);
		ad = new SongListAdapter(getActivity());
		ListView lv = (ListView)view.findViewById(R.id.generic_list_view);
		lv.setAdapter(ad);
		
		return view;
	}
}