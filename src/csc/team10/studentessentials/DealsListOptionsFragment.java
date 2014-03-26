package csc.team10.studentessentials;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class DealsListOptionsFragment extends Fragment
{
	public DealsListOptionsFragment()
	{
		
	}
	
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_deals_list_options, container, false);
	}
}
