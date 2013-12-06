package com.example.dampdesign.Fragments;

import com.example.dampdesign.R;
import com.example.dampdesign.R.id;
import com.example.dampdesign.R.layout;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class PlayerFragment extends Fragment {
	Button playerBackButton;
	Button playerPlayPauseButton;
	Button playerNextButton;
	
	
	private OnClickListener playPauseButtonListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			//TODO
			Log.d("xxx","Play");
		}
	};
	
	private OnClickListener backButtonListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO
			Log.d("xxx","Back");
		}
	};
	
	private OnClickListener nextButtonListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO
			Log.d("xxx","Next");
		}
	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedINstanceState){
		View view;
		view = inflater.inflate(R.layout.player_fragment,container, false);
		
		playerBackButton = (Button) view.findViewById(R.id.player_fragment_back_button);
		playerPlayPauseButton = (Button) view.findViewById(R.id.player_fragment_playpause_button);
		playerNextButton = (Button) view.findViewById(R.id.player_fragment_next_button);
		
		playerBackButton.setOnClickListener(backButtonListener);
		playerPlayPauseButton.setOnClickListener(playPauseButtonListener);
		playerNextButton.setOnClickListener(nextButtonListener);
		
		return view;
	}
	
	
}
