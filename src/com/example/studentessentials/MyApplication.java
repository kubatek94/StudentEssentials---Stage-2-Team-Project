package com.example.studentessentials;
import android.app.Application;  

import com.testflightapp.lib.TestFlight;

public class MyApplication extends Application {
  @Override
  public void onCreate() {
      super.onCreate();
      //Initialize TestFlight with app token.
      TestFlight.takeOff(this, "42a750cf-10d4-407d-946b-15bbac8ade76");
     

      
  }
}