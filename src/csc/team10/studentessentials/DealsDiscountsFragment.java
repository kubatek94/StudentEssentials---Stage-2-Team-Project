package csc.team10.studentessentials;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DealsDiscountsFragment extends Fragment {
	
	private ViewPager viewPager;
    private DealsDiscountsPagerAdapter mAdapter;
    private ActionBar actionBar;
    
    public DealsDiscountsFragment()
    {

    }
    
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_deals_discounts, container, false);
	}
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

}
