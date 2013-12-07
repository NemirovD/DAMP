package com.example.dampdesign.Fragments;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.dampdesign.R;

public class PlayerFragment extends Fragment implements OnSeekBarChangeListener{
	SeekBar seekBar;
	Button playerBackButton;
	Button playerPlayPauseButton;
	Button playerNextButton;
	MediaPlayer player;
	Cursor queue;
	TextView playerTotalTime;
	TextView playerCurrentTime;
	boolean isPlayerSet;
	
	
	private OnClickListener playPauseButtonListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			if(player.isPlaying()){
				player.pause();
				v.setBackgroundResource(R.drawable.player_play_button);
			}else{
				if(isPlayerSet){
					player.start();
					v.setBackgroundResource(R.drawable.player_pause_button);
				}}}};
					
	private OnClickListener backButtonListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			if(isPlayerSet){
				setSong(queue.getPosition()+1,queue);
			}
		}
	};
	private OnClickListener nextButtonListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			if(isPlayerSet){
				setSong(queue.getPosition()+1,queue);
			}
		}
	};
	private Handler seekBarHandler = new Handler();
	private Runnable updateSeekBarTask = new Runnable(){
		@Override
		public void run() {
			if(player!=null){
			seekBar.setProgress(player.getCurrentPosition());
			playerCurrentTime.setText(convertTime(player.getCurrentPosition()));
			seekBarHandler.postDelayed(this, 100);
			}
		}
	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedINstanceState){
		View view;
		view = inflater.inflate(R.layout.player_fragment,container, false);
		isPlayerSet = false;
		if(player==null){
			player = new MediaPlayer();
		}
		playerCurrentTime = (TextView) view.findViewById(R.id.player_fragment_current_time);
		playerTotalTime = (TextView) view.findViewById(R.id.player_fragment_total_time);
		
		playerBackButton = (Button) view.findViewById(R.id.player_fragment_back_button);
		playerBackButton.setOnClickListener(backButtonListener);
		
		playerPlayPauseButton = (Button) view.findViewById(R.id.player_fragment_playpause_button);
		playerPlayPauseButton.setOnClickListener(playPauseButtonListener);
		
		playerNextButton = (Button) view.findViewById(R.id.player_fragment_next_button);
		playerNextButton.setOnClickListener(nextButtonListener);
		
		seekBar = (SeekBar) view.findViewById(R.id.player_seekBar);
		seekBar.setOnSeekBarChangeListener(this);
		return view;
	}
	
	public void setSong(int index, Cursor c){
		queue = c;
		try{
			c.moveToPosition(index);
			String path = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA));
			if(isPlayerSet){
				player.stop();
			}
			player.reset();
			player.setDataSource(path);
			player.prepare();
			player.start();
			isPlayerSet = true;
			playerPlayPauseButton.setBackgroundResource(R.drawable.player_pause_button);
			
			seekBar.setMax(player.getDuration());
			playerTotalTime.setText(convertTime(player.getDuration()));
			seekBar.setProgress(0);
			seekBarHandler.postDelayed(updateSeekBarTask, 100);
		}catch(Exception e){
			return;
		}
	}
	
	private String convertTime(long millis){
		String formattedTime="";
		
		long hours = millis/(1000*60*60);
		long minutes = (millis%(1000*60*60))/(1000*60);
		long seconds = ((millis%(1000*60*60))%(1000*60))/(1000);
		
		if(hours > 0){
			formattedTime += (hours + ":");
		}
		
		formattedTime += "" + minutes+":";
		
		if(seconds < 10){
			formattedTime += ("0"+seconds);
		}else{
			formattedTime += seconds;
		}
		
		return formattedTime;
	}

	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		seekBarHandler.removeCallbacks(updateSeekBarTask);
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		seekBarHandler.removeCallbacks(updateSeekBarTask);
		if(isPlayerSet){
			player.seekTo(seekBar.getProgress());
			seekBarHandler.postDelayed(updateSeekBarTask, 100);
		}else{
			seekBar.setProgress(0);
		}
	}
}
