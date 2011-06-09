package com.rimproject.andsensor;

import AccelerometerLogger;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import AccelerometerLogger.DataLogger;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

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


public class AccelerometerLogger extends TimerTask
{
	private Timer delayBetweenLoggingTimer; // Timer 1
	private long delayBetweenLogging;
	private long loggingDuration;
	private DataLogger dataLogger;
	

	public AccelerometerLogger() 
	{
		super();		
		
		this.delayBetweenLogging = 10 * 1000;
		this.loggingDuration = 3 * 1000;
		this.delayBetweenLoggingTimer = new Timer();
		this.delayBetweenLoggingTimer.scheduleAtFixedRate(this, 0, this.delayBetweenLogging); //0 == triggers immediately
	}
	
	class DataLogger extends TimerTask
	{
		private Timer loggingDurationTimer; // Timer 2
		public DataLogger()
		{
			this.loggingDurationTimer = new Timer();
			this.loggingDurationTimer.scheduleAtFixedRate(this, loggingDuration, loggingDuration);
			startLogging();
		}
		
		public void startLogging() {
			System.out.println(Calendar.getInstance().getTime()+" @ Logging Started");
		}
		
		public void terminateLogging() {
			System.out.println(Calendar.getInstance().getTime()+" @ Logging Stopped");
		}
		
		public void run()
		{
			terminateLogging();
			
			//stop the timer as we don't want timer 2 to repeat
			this.loggingDurationTimer.cancel();
		}
		
	}

	public long getDelayBetweenLogging() {
		return delayBetweenLogging;
	}

	public void setDelayBetweenLogging(long delayBetweenLogging) {
		this.delayBetweenLogging = delayBetweenLogging;
	}

	public long getLoggingDuration() {
		return loggingDuration;
	}


	public void setLoggingDuration(long loggingDuration) {
		this.loggingDuration = loggingDuration;
	}

	
	@Override
	public void run() {
		//timer 1
		System.out.println(Calendar.getInstance().getTime()+" @ Trigger Logging");
		this.dataLogger = new DataLogger();
	}

}
