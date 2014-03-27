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
	URL baseUri;
	
	String hash = "";
	Authentication auth = null;
	
	public Connection(String baseUri)
	{
		try {
			this.baseUri = new URL(baseUri);
		} catch (MalformedURLException e) {
			throw new ConnectionException(e.getMessage());
		}
	}
	
	public Connection(String baseUri, Authentication auth)
	{
		try {
			this.baseUri = new URL(baseUri);
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
		
		if(!hash.isEmpty())
		{
			//set http auth
			connection.setRequestProperty("Authorization", "Basic " + hash);
		}
		
		connection.setDoInput(true);
		
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
		                	throw new ConnectionException(e.getMessage());
		                }
		            }
				}
			}
		}
		
		try {
			//connection.setConnectTimeout(5000);
			//connection.connect();
			
			status = connection.getResponseCode();
			int length = connection.getContentLength();
			
			Log.d("Connection", "Status:" + status);
			Log.d("Connection", "Length:" + length);
			
			if(length >= 0)
			{
				response = new byte[length];
			} else {
				response = new byte[1024*5]; //5 MB of data
			}
			
			if(status == 401)
			{
				return new ConnectionResult(status, "Authentication Required!".getBytes());
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
		}
		
		return null;
	}
	
	public ConnectionResult execute(ConnectionMethod method, String url, JSONObject input)
	{
		switch(method)
		{
		case GET:
			return this.get(url);
			
		case POST:
			return this.post(url, input);
			
		case PUT:
			return this.put(url, input);
			
		case DELETE:
			return this.delete(url);
			
		case OPTIONS:
			return this.options(url, input);
			
		default:
			throw new ConnectionException("Invalid connection method!");
		}
	}
	
	public ConnectionResult post(String url, JSONObject input)
	{
		try {
			URL uri = new URL(baseUri.toString() + url);
			
			connection = (HttpURLConnection) uri.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			
			ConnectionResult cr = request(input);
			
			if(cr == null) throw new ConnectionException("Unsuccessful POST request!");
			
			return cr;
		} catch (IOException e) {
			throw new ConnectionException(e.getMessage());
		}
	}
	
	public ConnectionResult options(String url, JSONObject input)
	{
		try {
			URL uri = new URL(baseUri.toString() + url);
			
			connection = (HttpURLConnection) uri.openConnection();
			
			connection.setRequestMethod("POST");
			connection.setRequestProperty("X-HTTP-Method-Override", "OPTIONS");
			connection.setRequestProperty("Content-Type", "application/json");
			
			ConnectionResult cr = request(input);
			
			if(cr == null) throw new ConnectionException("Unsuccessful OPTIONS request!");
			
			return cr;
		} catch (IOException e) {
			throw new ConnectionException(e.getMessage());
		}
	}
	
	public ConnectionResult get(String url)
	{	
		try {
			URL uri = new URL(baseUri.toString() + url);
			
			connection = (HttpURLConnection) uri.openConnection();
			connection.setRequestMethod("GET");
			
			ConnectionResult cr = request(null);
			
			if(cr == null) throw new ConnectionException("Unsuccessful GET request!");
			
			return cr;
		} catch (IOException e) {
			throw new ConnectionException(e.getMessage());
		}
	}
	
	public ConnectionResult put(String url, JSONObject input)
	{
		try {
			URL uri = new URL(baseUri.toString() + url);
			
			connection = (HttpURLConnection) uri.openConnection();
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Content-Type", "application/json");
			
			ConnectionResult cr = request(input);
			
			if(cr == null) throw new ConnectionException("Unsuccessful PUT request!");
			
			return cr;
		} catch (IOException e) {
			throw new ConnectionException(e.getMessage());
		}
	}
	
	public ConnectionResult delete(String url)
	{
		try {
			URL uri = new URL(baseUri.toString() + url);
			
			connection = (HttpURLConnection) uri.openConnection();
			connection.setRequestMethod("DELETE");
			
			ConnectionResult cr = request(null);
			
			if(cr == null) throw new ConnectionException("Unsuccessful DELETE request!");
			
			return cr;
		} catch (IOException e) {
			throw new ConnectionException(e.getMessage());
		}
	}
}