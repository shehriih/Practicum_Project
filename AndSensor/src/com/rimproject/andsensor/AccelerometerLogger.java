package com.rimproject.andsensor;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/* Example of how this class and its timer's will work:
 * 
 * Timer 1 -> 10 seconds has passed 
 * 		Timer 2 created -> let me know when 3 seconds has passed
 * 		Sensor recording start
 * 			10:55:10 v:11
 * 			10:55:10 v:12
 * 			10:55:11 v:11
 * 			10:55:11 v:10
 * 			10:55:12 v:15
 * 			10:55:12 v:16
 * 			10:55:13 v:11
 * 		Timer 2 -> 3 seconds has passed
 * 		sensor recording stop
 * 
 * Timer 1 -> 10 seconds has passed 
 * 		Timer 2 created -> let me know when 3 seconds has passed
 * 		Sensor recording start
 * 			10:55:20 v:11
 * 			10:55:20 v:12
 * 			10:55:21 v:11
 * 			10:55:21 v:10
 * 			10:55:22 v:15
 * 			10:55:22 v:16
 * 			10:55:23 v:11
 * 		Timer 2 -> 3 seconds has passed
 * 		sensor recording stop
 * 	etc.
 */


public class AccelerometerLogger extends BasicLogger
{
	public AccelerometerLogger() 
	{
		super();
	}
	
//	public void startLogging() {
//		System.out.println(Calendar.getInstance().getTime()+" @ Logging Started");
//	}
//	
//	public void terminateLogging() {
//		System.out.println(Calendar.getInstance().getTime()+" @ Logging Stopped");
//	}

}
