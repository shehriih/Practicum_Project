package com.rimproject.logger;

import java.io.File;
import java.util.Calendar;

import android.util.Log;

import com.rimproject.contextanalyzer.ContextDetector;
import com.rimproject.contextanalyzer.DeviceStatus;
import com.rimproject.logreader.ContextReading;

public class ContextLogger extends BasicLogger
{
	public static final String SENSOR_NAME="_ContextLogger";
	public static final String LOCATION_NAME = "_LocationContextLogger";
	public ContextLogger() {
		super();
		
		setDelayBetweenLogging(60*1000);
	}
	
	public void startLogging() {
		super.startLogging();
	}
	
	protected void stopLogging() 
	{
		super.stopLogging();
	}
	
	protected void performLogging() {
		super.performLogging();
		String contextName = "Sleeping";
		int durationToConsider = 300;
		double probability = ContextDetector.probabilityOfSleeping(durationToConsider);
		
		String optionalTags = "None";
		DeviceStatus deviceStatus = new DeviceStatus();
		if (deviceStatus.isWithinHomeWifiRange(durationToConsider)) {
			optionalTags = "Home";
		}
		
		flio.writeToTXTLogFile(SENSOR_NAME,new ContextReading(Calendar.getInstance().getTime(), contextName, probability, optionalTags));
		String[] gpsLocation = deviceStatus.getLocation();
			if(null != gpsLocation){
			Log.d("GPS AVAILABLE", "TRUE");
				Log.d("GPS AVAILABLE", "Recording GPS DATA");
				if(!("NULL".equalsIgnoreCase(gpsLocation[1])));
				flio.writeToTXTLogFile(LOCATION_NAME, deviceStatus.getLocation());
			}
			else{
				Log.d("GPS AVAILABLE", "Not recording GPS DATA");
			}
			
	}

}



//old stuff

//
//public class ContextLogger {
//
//	BasicTimedDurationLogger basicLogger = new BasicTimedDurationLogger();
//	String activityDetails;
//	String activity;
//	public void logSleeping(){
//		int counter = 0;
//		 activity = "Sleeping";
//		
//		while(counter <= 60){
//			activityDetails = Calendar.getInstance().getTime() + ":" + ContextDetector.probabilityOfSleeping(60) + "/n";
//			//basicLogger.writeToLogFile(activity,activityDetails);	
//		}
//		
//	}
//	
//	public void logDriving(){
//		int counter = 0;
//		 activity = "Sleeping";
//		
//		while(counter <= 60){
//			activityDetails = Calendar.getInstance().getTime() + ":" + ContextDetector.probabilityOfSleeping() + "/n";
//			basicLogger.writeToLogFile(activity,activityDetails);	
//		}
//		
//	}
//	
//	public void logEating(){
//		int counter = 0;
//		 activity = "Sleeping";
//		
//		while(counter <= 60){
//			activityDetails = Calendar.getInstance().getTime() + ":" + ContextDetector.probabilityOfSleeping() + "/n";
//			basicLogger.writeToLogFile(activity,activityDetails);	
//		}
//		
//	}
//	
//	public void logWalking(){
//		int counter = 0;
//		 activity = "Sleeping";
//		
//		while(counter <= 60){
//			activityDetails = Calendar.getInstance().getTime() + ":" + ContextDetector.probabilityOfSleeping() + "/n";
//			basicLogger.writeToLogFile(activity,activityDetails);	
//		}
//		
//	}
//}
