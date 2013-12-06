package com.example.dampdesign.Fragments.ListFragments.ArtistList;

import java.io.File;

import com.example.dampdesign.R;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ArtistListAdapter extends BaseAdapter{
	Context context;
	Cursor cursor;
	
	String orderByArtist = MediaStore.Audio.Artists.ARTIST +" ASC";
	
	String proj[] = { MediaStore.Audio.Artists.ARTIST,
			MediaStore.Audio.Artists.Albums.ALBUM_ART};
	
	public ArtistListAdapter(Context context) {
		this.context = context;
		this.cursor = context.getContentResolver().query(
				MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, proj, null, null,
				orderByArtist);
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

		holder.songItem1.setText(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST)));
		holder.songItem2.setText("");
		
		String albumart = cursor.getString(cursor
				.getColumnIndex(MediaStore.Audio.Artists.Albums.ALBUM_ART));
		
		holder.albumArt.setImageResource(R.drawable.grayscale_logo);
		(new ImageLoader(holder)).execute(albumart,holder.albumArt);

		return v;
	}
	
	private static class ItemHolder {
		public ImageView albumArt;
		public TextView songItem1;
		public TextView songItem2;
		public int position;
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
			String path = (String) parameters[0];
			imageView = (ImageView)parameters[1];
			if(path == null || !(new File(path).exists())){
				cancel(true);
				return null;
			}
			Bitmap bitmap;
			
			try {
				if(id != holder.position){
					this.cancel(true);
					return null;
				}
				bitmap = BitmapFactory.decodeFile(path);
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
