package csc.team10.studentessentials;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DealViewFragment extends Fragment
{
	Deal deal = null;
	DealsListAdapter adapter = null;

	public DealViewFragment()
	{
		
	}
	
	public DealViewFragment(Deal deal, DealsListAdapter adapter)
	{
		this.deal = deal;
		this.adapter = adapter;
	}
	
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_deals_view, container, false);
	}
	
	public void onStart()
	{
		super.onStart();
		
		setDealView();
	}
	
	@SuppressLint("SimpleDateFormat")
	public void setDealView()
	{
		//Toast.makeText(getActivity(), deal == null ? "Null" : "Not null", Toast.LENGTH_SHORT).show();
		
		View v = this.getView();
		
		if (v == null)
		{
	        LayoutInflater vi;
	        vi = LayoutInflater.from(this.getActivity());
	        v = vi.inflate(R.layout.fragment_deals_view, null);
	    }
		
		//Toast.makeText(getActivity(), deal == null ? "Null" : "Not null", Toast.LENGTH_SHORT).show();
		
		TextView title = (TextView) v.findViewById(R.id.deal_title);
		TextView description = (TextView) v.findViewById(R.id.deal_description);
		TextView score = (TextView) v.findViewById(R.id.vote_score);
		TextView creator = (TextView) v.findViewById(R.id.deal_creator_info);
		ImageView category = (ImageView) v.findViewById(R.id.deal_image);
		
		
		ImageButton voteUp = (ImageButton) v.findViewById(R.id.vote_up);
		ImageButton voteDown = (ImageButton) v.findViewById(R.id.vote_down);
		
		Button goToUrl = (Button) v.findViewById(R.id.deal_go_to_url);
		Button report = (Button) v.findViewById(R.id.deal_report);
		
		voteUp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AsyncConnection connection = new AsyncConnection(
						"http://homepages.cs.ncl.ac.uk/2013-14/csc2022_team10/App",
						new Authentication(getActivity().getApplicationContext()));
				
				connection.put("/deals/" + deal.getId() + "/hot", null, new AsyncConnectionCallback(){

					public void onSuccess(Object callbackContext, ConnectionResult result) {
						View view = (View)callbackContext;
						
						JSONObject response;
						
						try {
							response = new JSONObject(result.getResponse());
							JSONObject data = response.getJSONObject("data");
							
							//update view in adapter
							int oldPosition = adapter.getPosition(deal);
							adapter.remove(deal);
							deal = new Deal(data);
							adapter.insert(deal, oldPosition);
							
							//update deal fragment view with new data
							setDealView();
						} catch (JSONException e) {
							Common common = new Common(view.getContext());
							common.showShortToast(e.getMessage());
						}
					}

					public void onError(Object callbackContext, ConnectionException exception) {
						View view = (View)callbackContext;
						
						Common common = new Common(view.getContext());
						common.showShortToast(exception.getMessage());
						
					}
				}, view);
			}
		});
		
		
		voteDown.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AsyncConnection connection = new AsyncConnection(
						"http://homepages.cs.ncl.ac.uk/2013-14/csc2022_team10/App",
						new Authentication(getActivity().getApplicationContext()));
				
				connection.put("/deals/" + deal.getId() + "/cold", null, new AsyncConnectionCallback(){

					public void onSuccess(Object callbackContext, ConnectionResult result) {
						View view = (View)callbackContext;
						
						JSONObject response;
						
						try {
							response = new JSONObject(result.getResponse());
							JSONObject data = response.getJSONObject("data");
							
							//update view in adapter
							int oldPosition = adapter.getPosition(deal);
							adapter.remove(deal);
							deal = new Deal(data);
							adapter.insert(deal, oldPosition);
							
							//update deal fragment view with new data
							setDealView();
						} catch (JSONException e) {
							Common common = new Common(view.getContext());
							common.showShortToast(e.getMessage());
						}
					}

					public void onError(Object callbackContext, ConnectionException exception) {
						View view = (View)callbackContext;
						
						Common common = new Common(view.getContext());
						common.showShortToast(exception.getMessage());
						
					}
				}, view);
			}
		});
		
		goToUrl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(deal.getLocation()));
				startActivity(browserIntent);
			}
		});
		
		
		
		//assign all text and image information
		if(title != null)
		{
			title.setText(deal.getTitle());
		}
		
		if(description != null)
		{
			description.setText(deal.getDescription());
		}
		
		if(score != null)
		{
			score.setText(String.valueOf(deal.getRank()));
			
			if(deal.getRank() >= 0)
				score.setTextColor(Color.parseColor("#269617")); //green
			else
				score.setTextColor(Color.parseColor("#C71A1A")); //red
		}
		
		if(category != null)
		{
			if(Deal.CATEGORIES[deal.getCategory()] != 0)
				category.setImageResource(Deal.CATEGORIES[deal.getCategory()]);
		}
		
		if(creator != null)
		{
			SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss"); //2014-03-11 14:04:47
			
			Date addedDate = null;
			
			try {
				addedDate = sdf.parse(deal.getAdded());
				
			} catch (ParseException e) {
				Log.d("DealViewFragment", "Cannot parse added date: " + e.getMessage());
			}
			
			Date nowDate = new Date();
			
			Calendar calendar1 = Calendar.getInstance();
		    Calendar calendar2 = Calendar.getInstance();
		    calendar1.setTime(addedDate);
		    calendar2.setTime(nowDate);
		    
		    long milliseconds1 = calendar1.getTimeInMillis();
		    long milliseconds2 = calendar2.getTimeInMillis();
		    
		    long diff = milliseconds2 - milliseconds1;
		    
		    long diffSeconds = diff / 1000;
		    long diffMinutes = diff / (60 * 1000);
		    long diffHours = diff / (60 * 60 * 1000);
		    long diffDays = diff / (24 * 60 * 60 * 1000);
		    
		    //if deal submitted more than a day ago..
		    if(diffDays > 0)
		    {
		    	if(diffDays == 1)
		    		creator.setText("Submitted " + diffDays + " day ago by " + deal.getCreatorNumber());
		    	else
		    		creator.setText("Submitted " + diffDays + " days ago by " + deal.getCreatorNumber());
		    	
		    	return;
		    }
		    
		    //if deal submitted more than an hour ago..
		    if(diffHours > 0)
		    {
		    	if(diffHours == 1)
		    		creator.setText("Submitted " + diffHours + " hour ago by " + deal.getCreatorNumber());
		    	else
		    		creator.setText("Submitted " + diffHours + " hours ago by " + deal.getCreatorNumber());
		    	
		    	return;
		    }
		    
		    //if deal submitted more than a minute ago..
		    if(diffMinutes > 0)
		    {
		    	if(diffMinutes == 1)
		    		creator.setText("Submitted " + diffMinutes + " minute ago by " + deal.getCreatorNumber());
		    	else
		    		creator.setText("Submitted " + diffMinutes + " minutes ago by " + deal.getCreatorNumber());
		    	
		    	return;
		    }
		    
		    //if deal submitted more than a second ago..
		    if(diffSeconds > 0)
		    {
		    	if(diffSeconds == 1)
		    		creator.setText("Submitted " + diffSeconds + " second ago by " + deal.getCreatorNumber());
		    	else
		    		creator.setText("Submitted " + diffSeconds + " seconds ago by " + deal.getCreatorNumber());
		    	
		    	return;
		    }
		}

	}
}