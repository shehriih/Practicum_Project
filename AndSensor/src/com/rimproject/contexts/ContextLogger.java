package com.rimproject.contexts;

import java.util.*;


import com.rimproject.andsensor.BasicTimedDurationLogger;

public class ContextLogger {

	BasicTimedDurationLogger basicLogger = new BasicTimedDurationLogger();
	String activityDetails;
	String activity;
	public void logSleeping(){
		int counter = 0;
		 activity = "Sleeping";
		
		while(counter <= 60){
			activityDetails = Calendar.getInstance().getTime() + ":" + ContextDetector.probabilityOfSleeping(60) + "/n";
			//basicLogger.writeToLogFile(activity,activityDetails);	
		}
		
	}
	
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
}
