package com.example.dampdesign.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.dampdesign.MainActivity;
import com.example.dampdesign.R;

public class PlayerFragment extends Fragment implements OnSeekBarChangeListener, OnCompletionListener {
	//MainActivity
	MainActivity root;
	
	// Underlying function members
	MediaPlayer player;
	AudioManager audioManager;
	Cursor queue;
	boolean isPlayerSet;

	// UI members
	ImageView artView;
	SeekBar seekBar;
	Button playerBackButton;
	Button playerPlayPauseButton;
	Button playerNextButton;
	TextView playerTotalTime;
	TextView playerCurrentTime;

	private OnClickListener playPauseButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (player.isPlaying()) {
				player.pause();
				v.setBackgroundResource(R.drawable.player_play_button);
			} else {
				if (isPlayerSet) {
					player.start();
					v.setBackgroundResource(R.drawable.player_pause_button);
				}
			}
		}
	};

	private OnClickListener backButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (isPlayerSet) {
				setSong(queue.getPosition() - 1, queue);
			}
		}
	};
	private OnClickListener nextButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (isPlayerSet) {
				setSong(queue.getPosition() + 1, queue);
			}
		}
	};
	private Handler seekBarHandler = new Handler();
	private Runnable updateSeekBarTask = new Runnable() {
		@Override
		public void run() {
			if (player != null) {
				seekBar.setProgress(player.getCurrentPosition());
				playerCurrentTime.setText(convertTime(player
						.getCurrentPosition()));
				seekBarHandler.postDelayed(this, 100);
			}
		}
	};
	
	private OnAudioFocusChangeListener afListener = new OnAudioFocusChangeListener(){
		private float volume;
		@Override
		public void onAudioFocusChange(int focusChange) {
	        if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
	        		int maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	        		int curVol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
	        		volume = ((float)curVol)/maxVol;
	        		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int)(maxVol/0.2),0);
	                player.setVolume((float)0.2, (float)0.2);
	            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
	            	audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int)volume,0);
	            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
	            	player.pause();
	                audioManager.abandonAudioFocus(afListener);
	            }
		}
	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedINstanceState) {
		View view;
		view = inflater.inflate(R.layout.player_fragment, container, false);
		audioManager = (AudioManager) getActivity().getSystemService(
				Context.AUDIO_SERVICE);

		isPlayerSet = false;
		if (player == null) {
			player = new MediaPlayer();
			player.setOnCompletionListener(this);
		}
		root = (MainActivity)getActivity();
		playerCurrentTime = (TextView) view
				.findViewById(R.id.player_fragment_current_time);
		playerTotalTime = (TextView) view
				.findViewById(R.id.player_fragment_total_time);
		playerBackButton = (Button) view
				.findViewById(R.id.player_fragment_back_button);
		playerBackButton.setOnClickListener(backButtonListener);
		playerPlayPauseButton = (Button) view
				.findViewById(R.id.player_fragment_playpause_button);
		playerPlayPauseButton.setOnClickListener(playPauseButtonListener);
		playerNextButton = (Button) view
				.findViewById(R.id.player_fragment_next_button);
		playerNextButton.setOnClickListener(nextButtonListener);

		seekBar = (SeekBar) view.findViewById(R.id.player_seekBar);
		seekBar.setOnSeekBarChangeListener(this);
		
		artView = (ImageView) view.findViewById(R.id.player_image);
		return view;
	}

	public void setSong(int index, Cursor c) {
		queue = c;
		int result = audioManager.requestAudioFocus(afListener,
				AudioManager.STREAM_MUSIC,
				AudioManager.AUDIOFOCUS_GAIN);

		if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
			try {
				c.moveToPosition(index);
				String path = c.getString(c
						.getColumnIndex(MediaStore.Audio.Media.DATA));
				if (isPlayerSet) {
					player.stop();
				}
				player.reset();
				player.setDataSource(path);
				player.prepare();
				player.start();
				isPlayerSet = true;
				playerPlayPauseButton
						.setBackgroundResource(R.drawable.player_pause_button);
	
				seekBar.setMax(player.getDuration());
				playerTotalTime.setText(convertTime(player.getDuration()));
				seekBar.setProgress(0);
				seekBarHandler.postDelayed(updateSeekBarTask, 100);
				String albumId = c.getString(c
						.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
				(new ImageLoader()).execute(albumId,artView);
				
				ImageView titleArt = (ImageView) root.title.findViewById(R.id.title_songscreen_album_art);
				TextView song =  (TextView) root.title.findViewById(R.id.title_songscreen_text_1);
				TextView artist = (TextView) root.title.findViewById(R.id.title_songscreen_text_2);
				
				song.setText(c.getString(c.getColumnIndex(MediaStore.Audio.Media.TITLE)));
				artist.setText(c.getString(c.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
				(new ImageLoader()).execute(albumId,titleArt);
				
				root.song = song.getText().toString();
				root.artist = artist.getText().toString();
			} catch (Exception e) {
				return;
			}
		}
	}

	private String convertTime(long millis) {
		String formattedTime = "";

		long hours = millis / (1000 * 60 * 60);
		long minutes = (millis % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = ((millis % (1000 * 60 * 60)) % (1000 * 60)) / (1000);

		if (hours > 0) {
			formattedTime += (hours + ":");
		}

		formattedTime += "" + minutes + ":";

		if (seconds < 10) {
			formattedTime += ("0" + seconds);
		} else {
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
		if (isPlayerSet) {
			player.seekTo(seekBar.getProgress());
			seekBarHandler.postDelayed(updateSeekBarTask, 100);
		} else {
			seekBar.setProgress(0);
		}
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		if((queue.getPosition()+1)==queue.getCount()){
			queue.moveToFirst();
			setSong(queue.getPosition(), queue);
		}else{
			setSong(queue.getPosition() + 1, queue);
		}
	}
	
	
	private class ImageLoader extends AsyncTask<Object, String, Bitmap> {
		private ImageView imageView = null;

		@Override
		protected Bitmap doInBackground(Object... parameters) {
			String albumId = (String) parameters[0];
			imageView = (ImageView)parameters[1];
			Bitmap bitmap;
			
			try {
				
				String where = MediaStore.Audio.Albums._ID + "=" + albumId;
				String projection[] = { MediaStore.Audio.Albums.ALBUM_ART };
				Cursor c = getActivity().getContentResolver().query(
						MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, projection,
						where, null, null);
				c.moveToFirst();
				String path = c.getString(c
						.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
				bitmap = BitmapFactory.decodeFile(path);
				c.close();
			} catch (Exception e) {
				bitmap = null;
			}
			return bitmap;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			if(result == null){
				imageView.setImageResource(R.drawable.grayscale_logo);
			}else{
		      imageView.setImageBitmap(result);
			}
			root.titleArt = imageView.getDrawable();
		}
	}
}
