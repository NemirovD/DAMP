package com.example.dampdesign.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dampdesign.R;
import com.example.dampdesign.SortListDialog;

public class WelcomeFragment extends MainScreenFragment{
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedINstanceState){
		View view;
		view = inflater.inflate(R.layout.welcome_fragment, container, false);
		
		return view;
	}

	@Override
	public SortListDialog getSortDialog() {
		return null;
	}

	@Override
	public void sort(int sm) {
		// TODO Auto-generated method stub
	}
}
