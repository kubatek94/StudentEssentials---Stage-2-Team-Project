package csc.team10.RssHandling;

import java.util.List;

import csc.team10.studentessentials.R;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/* 									   *
 * Author: Connor Shields			   *
 * This class runs the rss activity	   *
 * inside the application.			   *
 *                                     */
public class RssActivity extends Activity 
{
	// Save an instance of the activity.
	private RssActivity local;
	
	/*					   			     *
	 * A method to start up the rss feed *
	 * when the activity is created. 	 *
	 * 	 		 					     */	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// Initialise the activity and layout
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.rss_layout);		
		local = this;
		
		// Start the RSSTask
		RSSTask task = new RSSTask();		
		// Set the input stream for the rss feed and execute.
		task.execute("http://www.ncl.ac.uk/news/rss.xml?a=pressreleases");
	}
	
	/* 										   *
	 * This class controls all of the rss feed *
	 * parsing and display. 			       *
	 * 										   */	
	private class RSSTask extends AsyncTask<String, Void, List<RssItem> >
	{
		/* 											 	  *
		 * This method runs in the background (Async) and *
		 * grabs the RssItems					          *
		 * 											 	  */	
		@Override
		protected List<RssItem> doInBackground(String... url)
		{
			try
			{
				// Initialise a new Reader with the input url.
				Reader reader = new Reader(url[0]);
				// Return a list of RssItems.
				return reader.getItems();
				
			}catch(Exception e)
			{
			
			}			
			return null;
		}
		
		/* 										   *
		 * Method that controls the display of the *
		 * List of RssItems.				       *
		 * 										   */	
		@Override
		protected void onPostExecute(List<RssItem> result)
		{
			// Find the ListView element in the layout.xml file.
			ListView items = (ListView) findViewById(R.id.rssListMainView);
						
			// Create a list adapter, and feed in the List of RssItems
			ArrayAdapter<RssItem> adapter = new ArrayAdapter<RssItem>(local ,android.R.layout.simple_list_item_1, result);
			
			// Set the adapter for the selected view.
			items.setAdapter(adapter);
			
			// Initialise the ClickListener that sends the user to the link of the RssItem they clicked.
			items.setOnItemClickListener(new ListListener(result, local));
		}	
	}
}