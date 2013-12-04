package com.example.studentessentials;

import android.content.Context;
import android.widget.Toast;

public class Common {
	Context context;
	
	public Common(Context context)
	{
		this.context = context;
	}
	
	public void showLongToast(String message)
	{
		showToast(message, Toast.LENGTH_LONG);
	}
	
	public void showShortToast(String message)
	{
		showToast(message, Toast.LENGTH_SHORT);
	}
	
	private void showToast(String message, int duration)
	{
		Toast toast = Toast.makeText(context, message, duration);
		toast.show();
	}

}
