package com.example.studentessentials;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
    private ListView drawerList;
    
    private String[] myStringArray = {
    		"Search",
    		"Digital Smart Card",
    		"Student Deals",
    		"Student Nights"
    };
    
	public Authentication authentication;
	public Common common;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		common = new Common(this);
		authentication = new Authentication(this);
		setContentView(R.layout.activity_main);
		
		//drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		
        drawerList = (ListView)findViewById(R.id.left_drawer);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myStringArray);
        drawerList.setAdapter(adapter);
     
		
		common.showShortToast(authentication.isLoggedIn() ? "Logged In" : "Logged Out");
		common.showShortToast(authentication.getUsername());
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void logMeOut(View view){
		authentication.reset();
		goToLoginScreen();
	}
	
	public void goToLoginScreen()
	{
		Intent intent = new Intent(this, LoginActivity.class);
    	startActivity(intent);
    	finish();
	}

}
