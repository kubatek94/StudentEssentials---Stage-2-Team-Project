package csc.team10.studentessentials;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;
import android.util.Log;

public class Connection
{	
	HttpURLConnection connection;
	URL url;
	
	String hash = "";
	Authentication auth = null;
	
	public Connection(String uri)
	{
		try {
			url = new URL(uri);
		} catch (MalformedURLException e) {
			throw new ConnectionException(e.getMessage());
		}
	}
	
	public Connection(String uri, Authentication auth)
	{
		try {
			url = new URL(uri);
		} catch (MalformedURLException e) {
			throw new ConnectionException(e.getMessage());
		}
		
		this.auth = auth;
		
		if(!auth.getBase64Hash().isEmpty())
		{
			this.setHash(auth.getBase64Hash());
		}
	}
	
	public void setHash(String hash)
	{
		if(hash.isEmpty())
		{
			throw new ConnectionException("Auth hash cannot be empty!");
		}
		
		this.hash = hash;
	}
	
	private ConnectionResult request(JSONObject input)
	{
		OutputStream out = null;
		InputStream in = null;
		byte[] response = null;
		int status = 0;
		
		if(hash.isEmpty())
		{
			throw new ConnectionException("Authentication credentials required!");
		}
		
		//set http auth
		connection.setRequestProperty("Authorization", "Basic " + hash);
		
		if(input != null)
		{
			String inputString = input.toString();
			if(inputString.length() > 0)
			{
				connection.setDoOutput(true);
				try {
					connection.setFixedLengthStreamingMode(inputString.getBytes("UTF-8").length);
				} catch (UnsupportedEncodingException e) {
					throw new ConnectionException(e.getMessage());
				}
				
				try {
					out = connection.getOutputStream();
					out.write(inputString.getBytes("UTF-8"));
				} catch (IOException e) {
					throw new ConnectionException(e.getMessage());
				}  finally {
		            if (out != null)
		            {
		                try {
		                    out.close();
		                } catch (IOException e) {
		                    e.printStackTrace();
		                }
		            }
				}
			}
		}
		
		try {
			connection.setDoInput(true);
			connection.setConnectTimeout(5000);
			connection.connect();
			
			status = connection.getResponseCode();
			int length = connection.getContentLength();
			
			Log.d("Connection", "Status:" + status);
			Log.d("Connection", "Length:" + length);
			
			if(length != -1)
			{
				response = new byte[length];
			} else {
				response = new byte[1024*5]; //5 MB of data
			}
			
			if(status != -1)
			{
				in = connection.getInputStream();
				BufferedInputStream bin = new BufferedInputStream(in);
				bin.read(response);
				
				return new ConnectionResult(status, response);
			}

			connection.disconnect();
		} catch (IOException e) {
			throw new ConnectionException(e.getLocalizedMessage());
			//Log.d("Connection", "IOException:" + e.getLocalizedMessage());
		}
		
		return null;
	}
	
	public ConnectionResult post(JSONObject input)
	{
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			
			ConnectionResult cr = request(input);
			
			if(cr == null) throw new ConnectionException("Unsuccessful POST request!");
			
			return cr;
		} catch (IOException e) {
			throw new ConnectionException(e.getMessage());
		}
	}
	
	public ConnectionResult get()
	{	
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			ConnectionResult cr = request(null);
			
			if(cr == null) throw new ConnectionException("Unsuccessful GET request!");
			
			return cr;
		} catch (IOException e) {
			throw new ConnectionException(e.getMessage());
		}
	}
	
	public ConnectionResult put(JSONObject input)
	{
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Content-Type", "application/json");
			
			ConnectionResult cr = request(input);
			
			if(cr == null) throw new ConnectionException("Unsuccessful PUT request!");
			
			return cr;
		} catch (IOException e) {
			throw new ConnectionException(e.getMessage());
		}
	}
	
	public ConnectionResult delete()
	{
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("DELETE");
			
			ConnectionResult cr = request(null);
			
			if(cr == null) throw new ConnectionException("Unsuccessful DELETE request!");
			
			return cr;
		} catch (IOException e) {
			throw new ConnectionException(e.getMessage());
		}
	}
}