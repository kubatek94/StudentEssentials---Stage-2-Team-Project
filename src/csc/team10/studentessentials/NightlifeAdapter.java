package csc.team10.studentessentials;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NightlifeAdapter extends ArrayAdapter<Deal>
{	
	public NightlifeAdapter(Context context, int textViewResourceId) {
	    super(context, textViewResourceId);
	}

	public NightlifeAdapter(Context context, int resource, List<Deal> deals)
	{
		super(context, resource, deals);
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{

		View v = convertView;

		if (v == null)
		{

			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.deals_item_row, null);
		}

		Deal deal = getItem(position);

		if (deal != null)
		{
			
			TextView title = (TextView) v.findViewById(R.id.deal_item_title);
			TextView description = (TextView) v.findViewById(R.id.deal_item_description);
			TextView rank = (TextView) v.findViewById(R.id.deal_item_score);
			ImageView category = (ImageView) v.findViewById(R.id.deal_item_category);
		
			if(title != null)
				title.setText(deal.getTitle());
			
			
			if(description != null)
				description.setText(deal.getDescription());
			
			if(rank != null)
			{
				rank.setText(String.valueOf(deal.getRank()));
				
				if(deal.getRank() >= 0)
					rank.setTextColor(Color.parseColor("#269617")); //green
				else
					rank.setTextColor(Color.parseColor("#C71A1A")); //red
			}
			
			if(category != null)
			{
				if(Deal.CATEGORIES[deal.getCategory()] != 0)
					category.setImageResource(Deal.CATEGORIES[deal.getCategory()]);
			}
		}

		return v;

	}

}
