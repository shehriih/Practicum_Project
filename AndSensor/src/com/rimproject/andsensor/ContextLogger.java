package com.rimproject.andsensor;

import java.util.Calendar;

import com.rimproject.contexts.ContextDetector;
import com.rimproject.logreadings.ContextReading;

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
		double probability = ContextDetector.probabilityOfSleeping(300);
		flio.writeToTXTLogFile(SENSOR_NAME,new ContextReading(Calendar.getInstance().getTime(), contextName, probability));
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
