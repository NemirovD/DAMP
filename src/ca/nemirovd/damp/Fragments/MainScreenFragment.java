package ca.nemirovd.damp.Fragments;

import ca.nemirovd.damp.SortListDialog;

import android.support.v4.app.Fragment;


public abstract class MainScreenFragment extends Fragment {
	public abstract SortListDialog getSortDialog();
	
	public abstract void sort(int sm);
}
