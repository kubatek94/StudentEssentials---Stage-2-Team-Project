package csc.team10.studentessentials;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DealsFragment extends Fragment
{
	//View thisView;
			
	public DealsFragment()
	{
		
	}
	
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View fragmentView  = inflater.inflate(R.layout.fragment_deals, container, false);
		
		SlidingPaneLayout pane = (SlidingPaneLayout)fragmentView.findViewById(R.id.deals_pane);
		
		pane.openPane();
		
		//return thisView;
		return fragmentView;
	}
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}
	
	public void onStart()
	{
		super.onStart();
	}
}
