package com.example.dampdesign;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WelcomeFragment extends Fragment {
	//Probably going to be gotten rid of in future versions
	//Or changed completely
	private OnClickListener clicky = new OnClickListener(){
		@Override
		public void onClick(View v) {
			try {
				((MainActivity)getActivity()).switchSelectScreen(new AlbumListFragment());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedINstanceState){
		View view;
		view = inflater.inflate(R.layout.welcome_fragment, container, false);
		
		Button b = (Button)view.findViewById(R.id.button1);
		b.setOnClickListener(clicky);
		
		return view;
	}

}
