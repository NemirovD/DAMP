package com.example.dampdesign.Fragments;

import android.support.v4.app.Fragment;

import com.example.dampdesign.SortListDialog;

public abstract class MainScreenFragment extends Fragment {
	public abstract SortListDialog getSortDialog();
	
	public abstract void sort(int sm);
}
