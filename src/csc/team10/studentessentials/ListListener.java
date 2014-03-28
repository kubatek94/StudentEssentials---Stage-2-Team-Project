package csc.team10.studentessentials;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/*													*
 * Author: Connor Shields							*
 * This class implements a listener, so when an rss *
 * item is clicked a browser is opened and sent to  *
 * the link in the RssItem link field.				*
 * 													*/
public class ListListener implements OnItemClickListener 
{

	// The list that all the RssItems are stored.
	List<RssItem> listItems;	
	// A local reference to the Activity calling this class.
	Activity activity;
	
	/*									  *
	 * Constructor to initialise the list *
	 * and activity field.				  *
	 * 									  */
	public ListListener(List<RssItem> listItems, Activity activity) 
	{
		this.listItems = listItems;
		this.activity  = activity;
	}
	
	/*									   	  *
	 * This method actually opens the browser *
	 * 									  	  */
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) 
	{
		// Create an intent that will display a url in a browser when it is fed to it.
		Intent i = new Intent(Intent.ACTION_VIEW);
		// Feed the link from an RssItem into the intent.
		i.setData(Uri.parse(listItems.get(pos).getLink()));
		//Start the browser and send it to the url.
		activity.startActivity(i);		
	}
	
}

