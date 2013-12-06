package com.example.dampdesign.Fragments.ListFragments.SongList;

import com.example.dampdesign.R;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SongListAdapter extends BaseAdapter {
	Context context;
	Cursor cursor;

	String proj[] = { MediaStore.Audio.Media.DISPLAY_NAME,
			MediaStore.Audio.Media.ARTIST };

	public SongListAdapter(Context context) {
		this.context = context;
		this.cursor = context.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proj, null, null,
				null);
	}

	@Override
	public int getCount() {
		return cursor.getCount();
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

			holder.albumArt = img;
			holder.songItem1 = tv1;
			holder.songItem2 = tv2;
			v.setTag(holder);
		} else {
			holder = (ItemHolder) v.getTag();
		}
		cursor.moveToPosition(position);
		holder.albumArt.setImageBitmap(null);
		holder.songItem1.setText(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)));
		holder.songItem2.setText(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));

		return v;
	}

}
