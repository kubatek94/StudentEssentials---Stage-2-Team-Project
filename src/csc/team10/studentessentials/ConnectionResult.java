package csc.team10.studentessentials;

import java.io.UnsupportedEncodingException;

public class ConnectionResult {
	int status = -1;
	String response = "";
	
	public ConnectionResult(int status, byte[] response)
	{
		this.status = status;
		try {
			this.response = new String(response, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getStatus()
	{
		return status;
	}
	
	public String getResponse()
	{
		return response;
	}
}
