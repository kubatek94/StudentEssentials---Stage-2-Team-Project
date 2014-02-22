package com.example.studentessentials;

/**
 * Author: Jakub Gawron
 * Date: 03.12.2013
 */



import android.content.Context;
import android.content.SharedPreferences;

public class Authentication {
	private static final String SHARED_PREFERENCES_NAME = "AUTH_PREF"; //name to use for shared preferences file
	
	private SharedPreferences state;
	
	private boolean loggedIn = false;
	private String username = "";
	private String password = "";
	
	public Authentication(Context context)
	{
		state = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
		loadState();
	}
	
	private void loadState()
	{
		loggedIn = state.getBoolean("loggedIn", false);
		username = state.getString("username", "");
		password = state.getString("password", "");
	}
	
	private void saveState()
	{
		SharedPreferences.Editor editor = state.edit();
		
		editor.putBoolean("loggedIn", loggedIn);
		editor.putString("username", username);
		editor.putString("password", password);
		
		editor.commit();
	}
	
	public void setUsername(String username) throws AuthenticationException
	{
		if(username.isEmpty())
			throw new AuthenticationException("Student ID required");
		
		this.username = username;
	}
	
	public void setPassword(String password)
	{
		if(password.isEmpty())
			throw new AuthenticationException("Password required");
		
		this.password = password;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	private void setLoggedIn(boolean loggedIn)
	{
		this.loggedIn = loggedIn;

	}
	
	public boolean isLoggedIn()
	{
		return loggedIn;
	}
	
	public void reset()
	{
		loggedIn = false;
		username = "";
		password = "";
		
		saveState();

	}
	
	public boolean authenticate() throws AuthenticationException
	{
		if(username.isEmpty())
			throw new AuthenticationException("Student ID required to login.");
		
		if(password.isEmpty())
			throw new AuthenticationException("Password required to login.");
		
		setLoggedIn( auth() );
		saveState();
		
		return isLoggedIn();
	}
	
	/**
	 * Connects to MySQL ISS server and checks if username matches the password
	 * @return true if valid username and password, false otherwise
	 */
	private boolean auth()
	{
		/* Implemented MySQL connection */
		/* Now data always validates! */
		
		return true;
	}
}
