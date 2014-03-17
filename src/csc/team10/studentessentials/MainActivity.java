package csc.team10.studentessentials;

import csc.team10.studentessentials.R;

import com.google.analytics.tracking.android.EasyTracker;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.content.res.Configuration;
import android.view.MenuInflater;


public class MainActivity extends Activity {
	private ListView mDrawerList;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private String[] myStringArray = { "News", "Smart Card", "Student Deals",
			"Student Discounts", "Student Nights Out", "FAQ" };

	public Authentication authentication;
	public Common common;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
        


		common.showShortToast(authentication.isLoggedIn() ? "Logged In"
				: "Logged Out");
		common.showShortToast(authentication.getUsername());
		
		
		
		
		// Sets the default fragment to 0 (news)
		Fragment fragment1 = new Deals();
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
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
	        // If the nav drawer is open, hide action items related to the content view
	       

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

		if (position == 1) {
			Fragment fragment1 = new Smartcard();
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment1).commit();

		} else if (position == 5) {
			Fragment fragment = new Faq();
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();

		} else if (position == 2) {
			Fragment fragment = new Deals();
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();

		} else {
			Fragment fragment = new Deals();
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();

		}

		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(myStringArray[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	public static class Smartcard extends Fragment {

		public Smartcard() {
			// Empty constructor required for fragment subclasses
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			return inflater.inflate(R.layout.fragment_smartcard, container,
					false);

		}
	}

	public static class Faq extends Fragment {

		//Context context;
		View faqView;
		
		public Faq() {
			//this.context = context;
			// Empty constructor required for fragment subclasses
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			faqView = inflater.inflate(R.layout.fragment_faq, container, false);
			//textView = (TextView) faq_view.findViewById(R.id.textView1);
			return faqView;
		}
		
		@Override
		public void onStart()
		{
			super.onStart();
			Connection con = new Connection("http://homepages.cs.ncl.ac.uk/2013-14/csc2022_team10/App/clubs/rtvvlF4Q", new Authentication(getActivity().getApplicationContext()));
			new GetClub().execute(con);
		}
		
	    private class GetClub extends AsyncTask<Connection, Void, String> {
	    	final TextView textView = (TextView) faqView.findViewById(R.id.textView1);
	    	
			protected String doInBackground(Connection... cons) {
				
				
				for(Connection con : cons)
				{
					try{
						ConnectionResult result = con.get();
						Log.d("ConnectionResult", result.getResponse());
						return result.getResponse();
						
						//common.showShortToast("Status:" + result.getStatus());
						//Toast.makeText(context, "Hello", Toast.LENGTH_LONG).show();
						
					} catch (ConnectionException e) {
						//common.showShortToast(e.getMessage());
					}
				}
				
				return null;
			}
			
			protected void onPostExecute(String result)
			{
				textView.setText(result);
			}
	    }
	}

	public static class Deals extends Fragment {

		public Deals() {
			// Empty constructor required for fragment subclasses
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			return inflater.inflate(R.layout.fragment_deals, container, false);

		}

	}

}
