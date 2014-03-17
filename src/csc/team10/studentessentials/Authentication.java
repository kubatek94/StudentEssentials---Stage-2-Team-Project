package csc.team10.studentessentials;

/**
 * Author: Jakub Gawron
 * Date: 03.12.2013
 */


import java.io.UnsupportedEncodingException;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

public class Authentication {
	private static final String SHARED_PREFERENCES_NAME = "AUTH_PREF"; //name to use for shared preferences file
	private static final String AUTH_URL = "http://homepages.cs.ncl.ac.uk/2013-14/csc2022_team10/App/login";
	
	private SharedPreferences state;
	
	private boolean loggedIn = false;
	
	private String username = "";
	private String password = "";
	private String base64Hash = "";
	
	private Connection connection;
	
	public Authentication(Context context)
	{
		connection = new Connection(AUTH_URL);
		
		state = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
		loadState();
	}
	
	private void loadState()
	{
		//loggedIn = state.getBoolean("loggedIn", false);
		
		username = state.getString("username", "");
		password = state.getString("password", "");
		base64Hash = state.getString("base64Hash", "");
	}
	
	private void saveState()
	{
		SharedPreferences.Editor editor = state.edit();
		
		//editor.putBoolean("loggedIn", loggedIn);
		
		editor.putString("username", username);
		editor.putString("password", password);
		
		editor.putString("base64Hash", base64Hash);
		
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
	
	public String getBase64Hash()
	{
		return base64Hash;
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
		base64Hash = "";
		
		saveState();

	}
	
	public boolean authenticate() throws AuthenticationException
	{
		if(username.isEmpty())
			throw new AuthenticationException("Student ID required to login.");
		
		if(password.isEmpty())
			throw new AuthenticationException("Password required to login.");
		
		try {
			base64Hash = new String(Base64.encode((username + ":" + password).getBytes(), Base64.DEFAULT), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			Log.d("Authentication", e.getLocalizedMessage());
		}
		
		boolean a = auth();
		setLoggedIn(a);
		
		//save state only if authentication successful
		if(a)
			saveState();
		
		return isLoggedIn();
	}
	
	/**
	 * Connects to MySQL ISS server and checks if username matches the password
	 * @return true if valid username and password, false otherwise
	 */
	private boolean auth()
	{
		connection.setHash(base64Hash);
		
		try{
			ConnectionResult result = connection.get();
			
			switch(result.getStatus())
			{
			case -1:
				Log.e("Authentication", "Status not set!");
				return false;
			case 200:
				String response = result.getResponse();
				
				if(response.equals("LOGIN_OK"))
				{
					return true;
				} else {
					Log.d("Authentication response", response);
					return false;
				}
			case 401:
				throw new AuthenticationException("401:" + "Could not log in. Check your Student ID and Password.");
			default:
				Log.d("Authentication status", String.valueOf(result.getStatus()));
				Log.d("Authentication response", result.getResponse());
				return false;
			}
		} catch (ConnectionException e) {
			Log.e("Authentication", e.getMessage());
			return false;
		}
	}
}
