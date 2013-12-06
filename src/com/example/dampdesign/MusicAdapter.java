package com.example.dampdesign;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


//class will soon be destroyed
//and every view fragment will have it's own class
public class MusicAdapter extends BaseAdapter {
	private Context context;
	private Cursor cursor;
	private String SongItem1Desc;
	private String SongItem2Desc;

	String proj[] = { MediaStore.Audio.Media.ALBUM,
			MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.ARTIST,
			MediaStore.Audio.Media._ID, MediaStore.Audio.Media.ALBUM_ID };

	public MusicAdapter(Context c) {
		context = c;
	}

	public MusicAdapter(Context c, String s1, String s2) {
		context = c;
		cursor = c.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proj, null, null,
				null);
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
		public int position;
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
		holder.position = position;
		cursor.moveToPosition(position);
		holder.songItem1.setText(cursor.getString(cursor
				.getColumnIndex(SongItem1Desc)));
		holder.songItem2.setText(cursor.getString(cursor
				.getColumnIndex(SongItem2Desc)));
		String albumId = cursor.getString(cursor
				.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
		
		holder.albumArt.setImageResource(R.drawable.grayscale_logo);
		(new ImageLoader(holder)).execute(albumId,holder.albumArt);
		
		return v;
	}

	private class ImageLoader extends AsyncTask<Object, String, Bitmap> {
		private ImageView imageView = null;
		private ItemHolder holder;
		private int id;
		
		public ImageLoader(ItemHolder h){
			holder = h;
			id = h.position;
		}

		@Override
		protected Bitmap doInBackground(Object... parameters) {
			String albumId = (String) parameters[0];
			imageView = (ImageView)parameters[1];
			
			
			Bitmap bitmap;
			
			try {
				
				String where = MediaStore.Audio.Albums._ID + "=" + albumId;
				String projection[] = { MediaStore.Audio.Albums.ALBUM_ART };
				Cursor c = context.getContentResolver().query(
						MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, projection,
						where, null, null);
				c.moveToFirst();
				String path = c.getString(c
						.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
				if(id != holder.position){
					this.cancel(true);
				}
				bitmap = BitmapFactory.decodeFile(path);
				c.close();
			} catch (Exception e) {
				bitmap = null;
			}

			return bitmap;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			if(id != holder.position){
				return;
			}
			if(result == null){
				imageView.setImageResource(R.drawable.grayscale_logo);
			}else{
		      imageView.setImageBitmap(result);
			}
		}

	}
}
