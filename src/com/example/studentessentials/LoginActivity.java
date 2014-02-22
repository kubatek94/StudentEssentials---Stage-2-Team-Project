package com.example.studentessentials;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

import android.widget.EditText;
//import android.widget.ProgressBar;

public class LoginActivity extends Activity {
	public Authentication authentication;
	public Common common;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        
        authentication = new Authentication(this);
        common = new Common(this);
        
        if( authentication.isLoggedIn() )
        	goToMainScreen();
        	
        
        setContentView(R.layout.activity_login);
        
       
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
    
    public void logMeIn(View view){
    	EditText usernameEdit = (EditText) findViewById(R.id.login_username);
    	EditText passwordEdit = (EditText) findViewById(R.id.login_password);
    	
    	boolean gotUserData = false;
    	
    	try{
        	authentication.setUsername(usernameEdit.getText().toString());
        	authentication.setPassword(passwordEdit.getText().toString());
        	
        	gotUserData = true;
    	} catch(AuthenticationException e){
    		common.showLongToast(e.getMessage());
    	}
    	
    	if(gotUserData)
    	{
    		try{
    			authentication.authenticate();
    		} catch(AuthenticationException e){
    			common.showLongToast(e.getMessage());
    		}
    	}
    	
    	if(!authentication.isLoggedIn())
    	{
    		common.showLongToast("Could not log in. Check your Student ID and Password.");
    	} else {
    		goToMainScreen();
    	}

    	/*ProgressBar progressBar = (ProgressBar) findViewById(R.id.login_progress);
    	progressBar.setVisibility(View.VISIBLE);*/
    }
    
    public void goToMainScreen()
    {
    	Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
    	finish();
    }
    
}
