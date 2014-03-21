package csc.team10.studentessentials;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class DealsListFragment extends ListFragment{
	
	String[] countries = new String[] {
	        "India",
	        "Pakistan",
	        "Sri Lanka",
	        "China",
	        "Bangladesh",
	        "Nepal",
	        "Afghanistan",
	        "North Korea",
	        "South Korea",
	        "Japan"
	    };
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Creating an array adapter to store the list of countries **/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), R.layout.drawer_list_item, countries);
 
        /** Setting the list adapter for the ListFragment */
        setListAdapter(adapter);
 
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
