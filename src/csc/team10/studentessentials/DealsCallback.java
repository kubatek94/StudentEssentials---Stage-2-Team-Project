package csc.team10.studentessentials;

import org.json.JSONObject;

public interface DealsCallback {
	public void onSubmit(Object callbackContext, JSONObject deal);
}
