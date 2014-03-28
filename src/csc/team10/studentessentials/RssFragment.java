package csc.team10.studentessentials;

import java.util.List;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import csc.team10.RssHandling.ListListener;
import csc.team10.RssHandling.Reader;
import csc.team10.RssHandling.RssItem;

public class RssFragment extends Fragment
{
	public RssFragment()
	{
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View inflate = inflater.inflate(R.layout.rss_layout, container, false);	
		
		// Start the RSSTask
		RSSTask task = new RSSTask();		
		// Set the input stream for the rss feed and execute.
		task.execute("http://www.ncl.ac.uk/news/rss.xml?a=pressreleases");
		return inflate;
	}
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
			ListView items = (ListView) getActivity().findViewById(R.id.rssListMainView);

			// Create a list adapter, and feed in the List of RssItems
			ArrayAdapter<RssItem> adapter = new ArrayAdapter<RssItem>(getActivity() ,android.R.layout.simple_list_item_1, result);

			// Set the adapter for the selected view.
			items.setAdapter(adapter);

			// Initialise the ClickListener that sends the user to the link of the RssItem they clicked.
			items.setOnItemClickListener(new ListListener(result, getActivity()));
		}	
	}
}
