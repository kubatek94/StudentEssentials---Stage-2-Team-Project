package csc.team10.studentessentials;

import android.os.AsyncTask;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;



import csc.team10.studentessentials.R;
import com.google.analytics.tracking.android.EasyTracker;

public class LoginActivity extends Activity {
	public Authentication authentication;
	public Common common;

	  @Override
	  public void onStop() {
	    super.onStop();
	    EasyTracker.getInstance(this).activityStop(this);
	  }
	  
	  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        
        //get authentication object, with shared saved preferences
        authentication = new Authentication(this);
        common = new Common(this);
        
        Log.d("Login Activity", "Setting login activity!");
        setContentView(R.layout.activity_login);
    }
    
	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
		
        EditText username = (EditText) findViewById(R.id.login_username);
    	EditText password = (EditText) findViewById(R.id.login_password);
    	
    	Log.d("Login", authentication.getUsername());
    	Log.d("Login", authentication.getPassword());
    	Log.d("Login", authentication.getBase64Hash());		

        //if we have previously generated the hash (in last session), then try logging in
        if(!authentication.getBase64Hash().isEmpty())
        {
        	//set edit values to the saved ones
        	username.setText(authentication.getUsername());
	    	password.setText(authentication.getPassword());

	    	new AuthTask(this).execute(authentication);
        }
	}


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }*/
    
    public void logMeIn(View view){
    	EditText username = (EditText) findViewById(R.id.login_username);
    	EditText password = (EditText) findViewById(R.id.login_password);
    	
    	try{
    		//check user input
        	authentication.setUsername(username.getText().toString());
        	authentication.setPassword(password.getText().toString());
        	
    	} catch(AuthenticationException e){
    		//show toast if invalid and return
    		common.showLongToast(e.getMessage());
    		return;
    	}
    	
		//try logging in
    	new AuthTask(this).execute(authentication);
    }
    
    private class AuthTask extends AsyncTask<Authentication, Void, Boolean> {
    	public AuthTask(Context context)
    	{
    		//this.context = context.getApplicationContext();
    	}
    	
    	protected void onPreExecute()
    	{
           	ProgressBar progressBar = (ProgressBar) findViewById(R.id.login_progress);
    		EditText username = (EditText) findViewById(R.id.login_username);
        	EditText password = (EditText) findViewById(R.id.login_password);
        	Button submit = (Button) findViewById(R.id.login_button);
        	
			username.setEnabled(false);
			password.setEnabled(false);
			submit.setEnabled(false);
			
			progressBar.setVisibility(View.VISIBLE);
    	}
    	
		protected Boolean doInBackground(Authentication... auths) {

			for(Authentication auth : auths)
			{
				try{
					boolean r = auth.authenticate();
					return Boolean.valueOf(r);
				} catch (AuthenticationException e) {
					return false;
				}
			}

			return Boolean.valueOf(false);
		}
		
		protected void onPostExecute(Boolean result)
		{
			boolean r = result.booleanValue();
			
			if(r)
			{
				goToMainScreen();
			} else {
	           	ProgressBar progressBar = (ProgressBar) findViewById(R.id.login_progress);
	    		EditText username = (EditText) findViewById(R.id.login_username);
	        	EditText password = (EditText) findViewById(R.id.login_password);
	        	Button submit = (Button) findViewById(R.id.login_button);
	        	
				username.setEnabled(true);
				password.setEnabled(true);
				submit.setEnabled(true);
				
				progressBar.setVisibility(View.GONE);
				
				Toast.makeText(getApplicationContext(), "Could not log in. Check your Student ID and Password.", Toast.LENGTH_SHORT).show();
			}
		}
    }
    
    public void goToMainScreen()
    {
    	Log.d("LoginActivity", "Go to main screen!");
    	
    	Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
    	finish();
    }
    
}
