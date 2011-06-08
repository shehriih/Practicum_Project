package com.rimproject.andsensor;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

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


public class AccelerometerLogger extends TimerTask implements SensorEventListener
{
	private Timer delayBetweenLoggingTimer; // Timer 1
	private long delayBetweenLogging;
	private long loggingDuration;
	private DataLogger dataLogger;
	

	public AccelerometerLogger() 
	{
		super();		
		
		setDelayBetweenLogging(10*1000);
		setLoggingDuration(3 * 1000);
		
		dataLogger = new DataLogger();
		
		delayBetweenLoggingTimer = new Timer();
		delayBetweenLoggingTimer.scheduleAtFixedRate(this,0 , getDelayBetweenLogging());
	}
	
	class DataLogger extends TimerTask
	{
		private Timer loggingDurationTimer; // Timer 2
		public DataLogger()
		{
			this.loggingDurationTimer = new Timer();
		}
		
		public void triggerLogging(long loggingDuration)
		{
			this.loggingDurationTimer.scheduleAtFixedRate(this, loggingDuration, loggingDuration);
		}
		
		public void run()
		{
			// actual sensor logging
			System.out.println("Logging @"+Calendar.getInstance().getTime());
			
			//stop the timer as we don't want timer 2 to repeat
			this.loggingDurationTimer.cancel();
		}
		
	}

	public Timer getDelayBetweenLoggingTimer() {
		return delayBetweenLoggingTimer;
	}

	public void setDelayBetweenLoggingTimer(Timer delayBetweenLoggingTmer) {
		this.delayBetweenLoggingTimer = delayBetweenLoggingTmer;
	}

	public long getDelayBetweenLogging() {
		return delayBetweenLogging;
	}

	public void setDelayBetweenLogging(long delayBetweenLogging) {
		this.delayBetweenLogging=delayBetweenLogging;
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
		System.out.println(Calendar.getInstance().getTime());
		this.dataLogger.triggerLogging(getLoggingDuration());
	}


	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		
	}

}
