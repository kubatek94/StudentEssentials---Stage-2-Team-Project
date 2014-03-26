package csc.team10.studentessentials;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class DealsFragment extends Fragment
{
	//View thisView;
	List<Deal> deals = new ArrayList<Deal>();
	AsyncConnection connection;
	DealsListAdapter adapter = null;
			
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
	
	public void onStart()
	{
		super.onStart();
		
		// listview itemonclick
		ListView dealList = (ListView) getActivity().findViewById(
				R.id.deal_list);

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
		
		if (adapter == null)
		{
			adapter = new DealsListAdapter(getActivity(), R.layout.deals_item_row);

			// adapter = (DealsListAdapter) dealList.getAdapter();
			connection = new AsyncConnection(
					"http://homepages.cs.ncl.ac.uk/2013-14/csc2022_team10/App",
					new Authentication(getActivity().getApplicationContext()));

			// specific onClick action
			/*
			 * dealsList.onDealSelect(new DealSelectedCallback(){ public void
			 * action(DealViewFragment fragment, Deal deal) {
			 * fragment.setDealView(deal); pane.closePane(); } },
			 * (DealViewFragment
			 * )fragmentManager.findFragmentById(R.id.deal_view_fragment));
			 */

			// finally download deals data from server and populate the list
			connection.get("/deals", new AsyncConnectionCallback() {
				public void onSuccess(Object callbackContext,
						ConnectionResult result) {
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

													common.showShortToast("Cannot download deals at this time..");
												}
											}, activity);
								}
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

					common.showShortToast("Cannot download deals at this time..");
				}

			}, this.getActivity());
		}
		
		dealList.setAdapter(adapter);
		
		//show category selection dialog
		ImageButton categoriesButton = (ImageButton) getActivity().findViewById(R.id.action_categories);
		categoriesButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
			    Fragment prev = getFragmentManager().findFragmentByTag("dialog");
			    if (prev != null) {
			        ft.remove(prev);
			    }
			    ft.addToBackStack(null);

			    // Create and show the dialog.
			    DialogFragment newFragment = DealsCategoryDialog.newInstance(3);
			    newFragment.show(ft, "dialog");
			}
			
		});
	}
}
