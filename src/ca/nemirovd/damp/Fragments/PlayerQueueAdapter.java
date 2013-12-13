package ca.nemirovd.damp.Fragments;

import java.io.File;

import ca.nemirovd.damp.R;
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

public class PlayerQueueAdapter extends BaseAdapter {
	Context context;
	Cursor cursor;

	PlayerQueueAdapter(Context context, Cursor cursor){
		this.context = context;
		this.cursor = cursor;
	}
	
	protected Cursor getCursor(){
		return cursor;
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
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

		holder.songItem1.setText(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
		holder.songItem2.setText(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
		
		String albumId = cursor.getString(cursor
				.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
		
		holder.albumArt.setImageResource(R.drawable.grayscale_logo);
		(new ImageLoader(holder)).execute(albumId,holder.albumArt);

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
				if(id != holder.position || path == null || !(new File(path).exists())){
					this.cancel(true);
					return null;
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
			}else{
		      imageView.setImageBitmap(result);
			}
		}
	}
	
}
