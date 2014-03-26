package csc.team10.studentessentials;

//import java.util.ArrayList;
//import java.util.List;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class DealsListFragment extends ListFragment{
	DealsListAdapter adapter;
	
	List<DealSelectedCallback> selectCallbacks = new ArrayList<DealSelectedCallback>();
	List<DealViewFragment> selectCallbackFragments = new ArrayList<DealViewFragment>();
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DealsListAdapter adapter = new DealsListAdapter(inflater.getContext(), R.layout.deals_item_row);
        setListAdapter(adapter);
        
        return super.onCreateView(inflater, container, savedInstanceState);
    }
	
	public void onStart()
	{
		super.onStart();
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		Deal deal = (Deal)getListView().getItemAtPosition(position);
		
		int size = selectCallbacks.size();
		for(int i = 0; i < size; i++)
		{
			//get callback and associated fragment
			DealSelectedCallback dsc = selectCallbacks.get(i);
			DealViewFragment f = selectCallbackFragments.get(i);
			
			//call action on the callback
			dsc.action(f, deal);
		}
		
	}

	public void onDealSelect(DealSelectedCallback dealSelectedCallback, DealViewFragment fragment) {
		selectCallbacks.add(dealSelectedCallback);
		selectCallbackFragments.add(fragment);
	}

}
