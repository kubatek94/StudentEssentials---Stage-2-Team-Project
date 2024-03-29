package csc.team10.studentessentials;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;

public class MainActivity extends Activity {
	private ListView mDrawerList;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private String[] myStringArray = { "News", "Smart Card",
			"Deals & Discounts", "Student Nights Out", "FAQ" };

	public Authentication authentication;
	public Common common;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		common = new Common(this);
		authentication = new Authentication(this);

		// drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, myStringArray);
		mDrawerList.setAdapter(adapter);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, myStringArray));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);

				// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);

			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// Sets the default fragment to 0 (news)
		
		  Fragment fragment1 = new RssFragment(); FragmentManager fragmentManager =
		  getFragmentManager(); fragmentManager.beginTransaction()
		  .replace(R.id.content_frame, fragment1).commit();
		 
		mDrawerList.setItemChecked(0, true);
		setTitle(myStringArray[0]);

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public void logMeOut(View view) {
		authentication.reset();
		common.showShortToast(authentication.isLoggedIn() ? "Logged In"
				: "Logged Out");
		goToLoginScreen();
	}

	public void goToLoginScreen() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items

		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		switch (item.getItemId()) {
		case R.id.button_logout:
			logMeOut(this.getCurrentFocus());
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}

	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;

		switch (position) {
		case 0: // news
			// load news fragment
			fragment = new RssFragment();
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

			break;

		case 1: // smart card
			fragment = new Smartcard();
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

			break;

		case 2: // deals & discounts
			fragment = new DealsDiscountsFragment();
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

			break;

		case 3: // student nights out
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			break;

		case 4: // faq
			fragment = new Faq();
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

			break;

		default:
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			break;
		}

		if (fragment != null) {
			// load selected fragment
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).addToBackStack(null)
					.commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			setTitle(myStringArray[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		}
	}

	public static class Smartcard extends Fragment {

		View smartcardView;

		public Smartcard() {
			// Empty constructor required for fragment subclasses

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			smartcardView = inflater.inflate(R.layout.fragment_smartcard,
					container, false);

			return smartcardView;
		}

		public void onStart() {
			super.onStart();

			Authentication auth = new Authentication(getActivity()
					.getApplicationContext());
			AsyncConnection con = new AsyncConnection(
					"http://homepages.cs.ncl.ac.uk/2013-14/csc2022_team10/App",
					auth);

			con.get("/students/" + auth.getStudentNumber() + "/information",
					new AsyncConnectionCallback() {
						public void onSuccess(Object callbackContext,
								ConnectionResult result) {
							// Common c = new Common(getActivity());

							// Fragment smartcard = (Fragment)callbackContext;

							View v = (View) callbackContext;

							Log.d("info", "" + result.getStatus());
							Log.d("info", "" + result.getResponse());

							if (result.getStatus() == 200) {
								try {
									JSONObject res = new JSONObject(result
											.getResponse());

									// c.showLongToast(result.getResponse());

									if (res.optString("status")
											.equalsIgnoreCase("success")) {
										JSONObject information = res
												.optJSONObject("data");

										String firstName = information
												.optString("firstName");
										String lastName = information
												.optString("lastName");
										String barcode = information
												.optString("barcode");
										String expiry = information
												.optString("expiry");
										String number = information
												.optString("number");

										Log.d("name", firstName + " "
												+ lastName);

										TextView name = (TextView) v
												.findViewById(R.id.textView1);
										name.setText(firstName + " " + lastName);

										TextView bc = (TextView) v
												.findViewById(R.id.barcode);
										bc.setText(barcode);

										TextView exp = (TextView) v
												.findViewById(R.id.textView3);
										exp.setText(expiry);

										TextView num = (TextView) v
												.findViewById(R.id.deal_title);
										num.setText(number);

									}

								} catch (JSONException e) {
									// TODO Auto-generated catch block
									// e.printStackTrace();
								}
							}

						}

						public void onError(Object callbackContext,
								ConnectionException exception) {

						}

					}, this.smartcardView);

			TextView tv = (TextView) getActivity().findViewById(R.id.barcode);
			Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
					"fonts/barcode.ttf");

			// if(tv == null)
			// Toast.makeText(getActivity(), "TF is null",
			// Toast.LENGTH_SHORT).show();

			tv.setTypeface(tf);

		}
	}

	public static class Faq extends Fragment {

		// Context context;
		View faqView;

		public Faq() {
			// this.context = context;
			// Empty constructor required for fragment subclasses
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			faqView = inflater.inflate(R.layout.fragment_faq, container, false);
			// textView = (TextView) faq_view.findViewById(R.id.textView1);
			return faqView;

		}

		@Override
		public void onStart() {
			super.onStart();

			Authentication auth = new Authentication(getActivity()
					.getApplicationContext());
			AsyncConnection con = new AsyncConnection(
					"http://homepages.cs.ncl.ac.uk/2013-14/csc2022_team10/App",
					auth);

			con.get("/students/" + auth.getStudentNumber() + "/information",
					new AsyncConnectionCallback() {
						public void onSuccess(Object callbackContext,
								ConnectionResult result) {

						}

						public void onError(Object callbackContext,
								ConnectionException exception) {

						}

					}, this.faqView);
		}
	}
}