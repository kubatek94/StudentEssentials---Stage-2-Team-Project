package csc.team10.studentessentials;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DealsPaneFragmentLeft extends Fragment
{
	public DealsPaneFragmentLeft()
	{
		
	}
	
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		//create two fragments
		//one for the deal list
		return inflater.inflate(R.layout.fragment_deals_list_options, container, false);
	}

}
