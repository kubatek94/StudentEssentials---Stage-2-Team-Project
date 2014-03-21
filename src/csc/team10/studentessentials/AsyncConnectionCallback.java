package csc.team10.studentessentials;

public interface AsyncConnectionCallback {
	
	public void onSuccess(Object callbackContext, ConnectionResult result);
	public void onError(Object callbackContext, ConnectionException exception);
}
