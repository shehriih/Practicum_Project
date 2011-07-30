package com.rimproject.logger;

import java.util.Calendar;

import com.rimproject.contextanalyzer.ContextDetector;
import com.rimproject.contextanalyzer.DeviceStatus;
import com.rimproject.logreader.ContextReading;

public class ContextLogger extends BasicLogger
{
	public static final String SENSOR_NAME="_ContextLogger";
	
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
