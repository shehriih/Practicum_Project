package com.rimproject.andsensor;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/* Example of how this class and its timer's will work:
 * 
 * Timer 1 -> 10 seconds has passed 
 * 		Sensor recording start
 * 		Perform work
 * 		sensor recording stop
 * 
 * Timer 1 -> 10 seconds has passed 
 * 		Sensor recording start
 * 		Perform work
 * 		sensor recording stop
 * 	etc.
 */

public abstract class BasicLogger extends TimerTask  implements LoggerInterface, SensorEventListener
{
	private Timer delayBetweenLoggingTimer; // Timer 1
	private long delayBetweenLogging;

	public BasicLogger() 
	{
		super();		
		
		this.delayBetweenLogging = 60 * 1000;
	}
	
	public void initiateRepeatedLogging() 
	{
		System.out.println(this+" : "+Calendar.getInstance().getTime()+" @ Logging Initiated");
		this.delayBetweenLoggingTimer = new Timer();
		this.delayBetweenLoggingTimer.scheduleAtFixedRate(this, 0, this.delayBetweenLogging); //0 == triggers immediately
	}
	
	public void terminateRepeatedLogging(boolean immidiate) 
	{
		this.delayBetweenLoggingTimer.cancel();
		System.out.println(this+" : "+Calendar.getInstance().getTime()+" @ Logging Terminated");
	}
	
	protected void startLogging() 
	{
		System.out.println(this+" : "+Calendar.getInstance().getTime()+" @ Logging Started");
	}
	
	protected void stopLogging() 
	{
		System.out.println(this+" : "+Calendar.getInstance().getTime()+" @ Logging Stopped");
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		System.out.println(this+" : "+"onAccuracyChanged: " + sensor + ", accuracy: " + accuracy);
		
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		System.out.println(this+" : "+"onSensorChanged: " + event);
	}
	
	

	public long getDelayBetweenLogging() {
		return delayBetweenLogging;
	}

	public void setDelayBetweenLogging(long delayBetweenLogging) {
		this.delayBetweenLogging = delayBetweenLogging;
	}

	
	@Override
	public void run() {
		//timer 1
		System.out.println(this+" : "+Calendar.getInstance().getTime()+" @ Trigger Logging");
		performLogging();
	}
	
	public String toString() {
		return this.getClass().getSimpleName();
	}
	
	protected void performLogging() {
		System.out.println(this+" : "+Calendar.getInstance().getTime()+" @ Perform logging");
	}
	
	protected void writeToLogFile(String logStatement) {
		System.out.println(this+" writeToLogFile: " + logStatement);
	}
}
