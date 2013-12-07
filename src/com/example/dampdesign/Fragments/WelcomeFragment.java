package com.example.dampdesign.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dampdesign.MainActivity;
import com.example.dampdesign.R;
import com.example.dampdesign.Fragments.ListFragments.AlbumList.AlbumListFragment;

public class WelcomeFragment extends Fragment {
	//Probably going to be gotten rid of in future versions
	//Or changed completely
	private Bundle extras;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedINstanceState){
		View view;
		view = inflater.inflate(R.layout.welcome_fragment, container, false);
		
		return view;
	}

}
