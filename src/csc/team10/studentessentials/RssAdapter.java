package csc.team10.studentessentials;

import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RssAdapter extends ArrayAdapter<RssItem> 
{
	public RssAdapter(Context context, int textViewResourceId) 
	{
		super(context, textViewResourceId);
	}

	public RssAdapter(Context context, int resource, List<RssItem> item) 
	{
		super(context, resource, item);
	}

	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View v = convertView;

		if (v == null) 
		{

			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.rss_item_row, null);
		}

		RssItem item = getItem(position);

		if (item != null)
		{
			TextView title = (TextView) v.findViewById(R.id.rss_item_title);
			TextView description = (TextView) v.findViewById(R.id.rss_item_description);
			TextView date = (TextView) v.findViewById(R.id.rss_item_date);
			ImageView image = (ImageView) v.findViewById(R.id.rss_item_image);

			if (title != null)
				title.setText(item.getTitle());

			if (description != null)
				description.setText(item.getDescription());

			if (date != null)
				date.setText(item.getDate());

//			if (image != null) 
//			{
////				Toast.makeText(getContext(), item.getImage() , Toast.LENGTH_LONG).show();
//				
//				Uri uri = Uri.parse("https://i.imgur.com/fxahLhK.png");
//				image.setImageURI(uri);
//				
//			}
		}
		return v;

	}

}
