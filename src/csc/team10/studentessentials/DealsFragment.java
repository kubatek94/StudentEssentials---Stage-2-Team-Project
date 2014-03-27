package csc.team10.studentessentials;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

public class DealsFragment extends Fragment
{
	AsyncConnection connection = null;
	DealsListAdapter adapter = null;
	ListView dealList = null;
	LinearLayout dealListLoading = null;
	
	int chosenCategory = 0; //show all deals
			
	public DealsFragment()
	{
		
	}
	
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_deals, container, false);
	}
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}
	
	public void getDeals(int category)
	{
		//enable progress bar
		dealListLoading.setVisibility(View.VISIBLE);
		
		//reset adapter
		adapter = new DealsListAdapter(getActivity(), R.layout.deals_item_row);
		connection = new AsyncConnection("http://homepages.cs.ncl.ac.uk/2013-14/csc2022_team10/App", new Authentication(getActivity().getApplicationContext()));
		
		AsyncConnectionCallback allDealsCallback = new AsyncConnectionCallback() {
			public void onSuccess(Object callbackContext, ConnectionResult result) {
				Activity activity = (Activity) callbackContext;
				Common common = new Common(activity);

				if (result.getStatus() == 200) {
					try {
						JSONObject response = new JSONObject(result
								.getResponse());
						JSONArray dealsUrl = response.getJSONArray("data");

						int length = 0;

						if ((length = dealsUrl.length()) > 0) {
							for (int i = 0; i < length; i++) {
								String url = dealsUrl.getString(i);

								connection.get(url,
										new AsyncConnectionCallback() {

											@Override
											public void onSuccess(
													Object callbackContext,
													ConnectionResult result) {
												Activity activity = (Activity) callbackContext;
												Common common = new Common(
														activity);

												if (result.getStatus() == 200) {
													try {
														JSONObject response = new JSONObject(
																result.getResponse());
														JSONObject data = response
																.getJSONObject("data");

														Deal deal = new Deal(
																data);

														adapter.add(deal);
														
														//hide progress bar if we have at least one deal
														if(dealListLoading.getVisibility() == View.VISIBLE)
															dealListLoading.setVisibility(View.GONE);
														
													} catch (JSONException e) {
														common.showShortToast(e
																.getMessage());
													}
												}
											}

											@Override
											public void onError(
													Object callbackContext,
													ConnectionException exception) {
												Activity activity = (Activity) callbackContext;
												Common common = new Common(
														activity);
												
												common.showShortToast(exception.getMessage());
												common.showShortToast("Cannot download deals at this time.");
											}
										}, activity);
							}
						} else {
							//no deals found
							//hide progress bar
							dealListLoading.setVisibility(View.GONE);
							
							//show toast
							common.showShortToast("There are no deals to show.");
						}
					} catch (JSONException e) {
						common.showShortToast(e.getMessage());
					}
				}

			}

			public void onError(Object callbackContext,
					ConnectionException exception) {
				Activity activity = (Activity) callbackContext;
				Common common = new Common(activity);

				common.showShortToast(exception.getMessage());
				common.showShortToast("Cannot download deals at this time.");
			}

		};
		
		//9 valid categories
		if(category > 0 && category < 10)
		{
			//update current category
			chosenCategory = category;
			
			//download deals from chosen category
			JSONObject cat = new JSONObject();
			try {
				cat.put("category", String.valueOf(category));
				
				//download deals
				connection.options("/deals", cat, allDealsCallback, this.getActivity());
			} catch (JSONException e) {
				Common common = new Common(getActivity());
				common.showShortToast(e.getMessage());
			}
		} else {
			//download all deals
			connection.get("/deals", allDealsCallback, this.getActivity());
		}
	}
	
	public void onStart()
	{
		super.onStart();
		
		dealList = (ListView) getActivity().findViewById(R.id.deal_list);
		dealListLoading = (LinearLayout) getActivity().findViewById(R.id.deal_list_loading);

		dealList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Deal deal = adapter.getItem(position);
				
				DealViewFragment dealView = new DealViewFragment(deal, adapter);
				
				getFragmentManager().beginTransaction()
						.replace(R.id.content_frame, dealView)
						.addToBackStack(null).commit();
			}
		});
		
		//if we started the fragment
		if (adapter == null)
		{
			// finally download deals data from server and populate the list
			getDeals(chosenCategory);
		}
		
		//else we resumed it
		dealList.setAdapter(adapter);
		
		//show category selection dialog
		ImageButton categoriesButton = (ImageButton) getActivity().findViewById(R.id.action_categories);
		categoriesButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
			    Fragment prev = getFragmentManager().findFragmentByTag("categoriesDialog");
			    if (prev != null) {
			        ft.remove(prev);
			    }
			    ft.addToBackStack(null);

			    // Create and show the dialog.
			    DealsCategoryDialog categoryDialog = new DealsCategoryDialog();
			    
			    categoryDialog.addCategoryCallback(new CategoryCallback(){
					public void onSelect(Object callbackContext, int category)
					{
						DealsCategoryDialog dialog = (DealsCategoryDialog)callbackContext;
						
						getDeals(category);
						dealList.setAdapter(adapter);
						
						dialog.dismiss();
					}
			    }, categoryDialog);
			 
			    
			    categoryDialog.show(ft, "categoriesDialog");
			}
			
		});
		
		
		//show deal submission dialog
		ImageButton addDealButton = (ImageButton) getActivity().findViewById(R.id.action_submit);
		addDealButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
			    Fragment prev = getFragmentManager().findFragmentByTag("addDealDialog");
			    if (prev != null) {
			        ft.remove(prev);
			    }
			    ft.addToBackStack(null);

			    // Create and show the dialog.
			    AddDealsDialog addDealDialog = new AddDealsDialog();

			    addDealDialog.addDealsCallback(new DealsCallback(){
					public void onSubmit(Object callbackContext, JSONObject deal) {
						AddDealsDialog dialog = (AddDealsDialog)callbackContext;
						Authentication auth = new Authentication(getActivity());
						
						
						connection.post("/students/" + auth.getStudentNumber() + "/deals", deal, new AsyncConnectionCallback(){
							public void onSuccess(Object callbackContext, ConnectionResult result) {
								if(result.getStatus() == 200 && result.getResponse().isEmpty())
								{									
									AddDealsDialog dialog = (AddDealsDialog)callbackContext;
									dialog.dismiss();
									
									//update listview
									getDeals(chosenCategory);
									dealList.setAdapter(adapter);
								}
							}

							public void onError(Object callbackContext, ConnectionException exception) {
								Common c = new Common(getActivity());
								c.showShortToast(exception.getMessage());
							}
						}, dialog);
					}
			    }, addDealDialog);
			    
			    addDealDialog.show(ft, "addDealDialog");
			}
			
		});
		
		//show deal submission dialog
		ImageButton settingsButton = (ImageButton) getActivity().findViewById(R.id.action_settings);
		settingsButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Common c = new Common(getActivity());
				c.showShortToast("Settings not implemented!");
			}
		});
	}
}
