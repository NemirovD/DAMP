package ca.nemirovd.damp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;


public class SortListDialog extends DialogFragment {
	int sortMethods;
	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			((MainActivity)getActivity()).selectScreenSort(which);
			return;
		}
	};
	
	public Dialog onCreateDialog(Bundle savedInstanceState){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Sort By:");
		builder.setItems(sortMethods, listener);
		
		return builder.create();
	}
	
	public void setSortMethod(int sm){
		sortMethods = sm;
	}
}
