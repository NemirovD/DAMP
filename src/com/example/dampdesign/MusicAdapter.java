package com.example.dampdesign;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MusicAdapter extends BaseAdapter {
	private Context context;
	private Cursor cursor;
	private String SongItem1Desc;
	private String SongItem2Desc;
	
	String proj[] = {
			MediaStore.Audio.Media.ALBUM,
			MediaStore.Audio.Media.DISPLAY_NAME,
			MediaStore.Audio.Media.ARTIST
	};

	public MusicAdapter(Context c) {
		context = c;
	}

	public MusicAdapter(Context c, String s1, String s2) {
		context = c;
		Log.d("xxx",c.getContentResolver().toString());
		cursor = c.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, 
				proj, null, null, null);
		Log.d("xxx",cursor.toString());
		Log.d("xxx",String.valueOf(cursor.getCount()));
		Log.d("xxx","test3");
		SongItem1Desc = s1;
		SongItem2Desc = s2;
	}

	@Override
	public int getCount() {
		if (cursor != null)
			return cursor.getCount();
		else
			return 0;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private static class ItemHolder {
		public ImageView albumArt;
		public TextView songItem1;
		public TextView songItem2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ItemHolder holder = new ItemHolder();

		if (convertView == null) {
			LayoutInflater in = (LayoutInflater) context
					.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			v = in.inflate(R.layout.song_list_item, null);

			ImageView img = (ImageView) v
					.findViewById(R.id.song_list_item_album_art);
			TextView tv1 = (TextView) v
					.findViewById(R.id.song_list_item_text_1);
			TextView tv2 = (TextView) v
					.findViewById(R.id.song_list_item_text_2);

			cursor.moveToPosition(position);
			img.setImageBitmap(null);
			tv1.setText(cursor.getString(cursor.getColumnIndex(SongItem1Desc)));
			tv2.setText(cursor.getString(cursor.getColumnIndex(SongItem2Desc)));
			Log.d("xxx",cursor.getString(cursor.getColumnIndex(SongItem1Desc)));

			holder.albumArt = img;
			holder.songItem1 = tv1;
			holder.songItem2 = tv2;
			v.setTag(holder);

			// String album =
			// cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));

			// String proj[] = {MediaStore.Audio.Albums.ALBUM_ART};
			// String where = MediaStore.Audio.Albums.ALBUM + " = " + album;
			// Cursor albumCursor =
			// context.getContentResolver().query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
			// proj, where, null, null);
		} else {
			holder = (ItemHolder) v.getTag();
		}

		return v;
	}

	/*
	 * private class ImageLoader extends AsyncTask<Object, String, Bitmap>{
	 * private View view; private Bitmap bitmap = null;
	 * 
	 * @Override protected Bitmap doInBackground(Object... parameters) { view =
	 * (View)parameters[0]; String uri = (String)parameters[1];
	 * 
	 * 
	 * 
	 * return bitmap; }
	 * 
	 * }
	 */

}
