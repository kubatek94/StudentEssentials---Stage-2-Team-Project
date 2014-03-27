package csc.team10.studentessentials;

import org.json.JSONObject;
import android.os.AsyncTask;

public class AsyncConnection extends AsyncTask<String, Void, ConnectionResult> {
	
	private Connection connection;
	private AsyncConnectionCallback callback;
	private Object callbackContext;
	private ConnectionMethod method;
	private JSONObject input = null;
	private ConnectionException lastException = null;
	
	public AsyncConnection(String base)
	{
		this.connection = new Connection(base);
	}
	
	public AsyncConnection(String base, Authentication auth)
	{
		this.connection = new Connection(base, auth);
	}
	
	public AsyncConnection(AsyncConnection old)
	{
		this.connection = old.getConnection();
	}
	
	public Connection getConnection()
	{
		return this.connection;
	}
	
	public void setInput(JSONObject input)
	{
		this.input = input;
	}
	
	public void setCallback(AsyncConnectionCallback callback, Object callbackContext)
	{
		this.callback = callback;
		this.callbackContext = callbackContext;
	}
	
	public void setMethod(ConnectionMethod method)
	{
		this.method = method;
	}
	
	public void get(String url, AsyncConnectionCallback callback, Object callbackContext)
	{
		AsyncConnection c = new AsyncConnection(this);
		c.setCallback(callback, callbackContext);
		c.setMethod(ConnectionMethod.GET);
		c.execute(url);
	}

	public void options(String url, JSONObject input, AsyncConnectionCallback callback, Object callbackContext)
	{
		AsyncConnection c = new AsyncConnection(this);
		c.setInput(input);
		c.setCallback(callback, callbackContext);
		c.setMethod(ConnectionMethod.OPTIONS);
		c.execute(url);
	}
	
	public void post(String url, JSONObject input, AsyncConnectionCallback callback, Object callbackContext)
	{
		AsyncConnection c = new AsyncConnection(this);
		c.setInput(input);
		c.setCallback(callback, callbackContext);
		c.setMethod(ConnectionMethod.POST);
		c.execute(url);
	}
	
	public void put(String url, JSONObject input, AsyncConnectionCallback callback, Object callbackContext)
	{
		AsyncConnection c = new AsyncConnection(this);
		c.setInput(input);
		c.setCallback(callback, callbackContext);
		c.setMethod(ConnectionMethod.PUT);
		c.execute(url);
	}
	
	public void delete(String url, AsyncConnectionCallback callback, Object callbackContext)
	{
		AsyncConnection c = new AsyncConnection(this);
		c.setCallback(callback, callbackContext);
		c.setMethod(ConnectionMethod.DELETE);
		c.execute(url);
	}
	
	@Override
	protected ConnectionResult doInBackground(String... params) {
		String url = params[0];
		try{
			return this.connection.execute(this.method, url, this.input);
		} catch (ConnectionException e) {
			this.lastException = e;
			return null;
		}
	}
	
	@Override
	protected void onPostExecute(ConnectionResult result) {
		if(result == null)
		{
			this.callback.onError(this.callbackContext, this.lastException);
		} else {
			this.callback.onSuccess(this.callbackContext, result);
		}
    }
}
