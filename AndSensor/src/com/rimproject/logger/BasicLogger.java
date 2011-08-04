package com.rimproject.logger;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.rimproject.fileio.FileLoggingIO;

/* Example of how this class and its timer will work:
 * 
 * initiateRepeatedLogging called
 * 		Timer 1 -> 10 seconds has passed 
 * 			Sensor recording start
 * 			Perform work
 * 			sensor recording stop
 * 
 * 		Timer 1 -> 10 seconds has passed 
 * 			Sensor recording start
 * 			Perform work
 * 			sensor recording stop
 * terminateRepeatedLogging called
 * 	etc.
 */

public abstract class BasicLogger extends TimerTask  implements LoggerInterface, SensorEventListener
{
	private Timer delayBetweenLoggingTimer; //Timer 1
	private long delayBetweenLogging;
	
	public static final String genericDel=" %&# "; //delimiter to be used between different values like x,y,z in Accelerometer reading
    public static final String timeStampDel= " @ "; // delimiter to be used between the timestamp and values
	protected FileLoggingIO flio = new FileLoggingIO();

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
	
	//Note immediate only used in subclass BasicTimedDurationLogger
	public void terminateRepeatedLogging(boolean immidiate) 
	{
		this.cancel();
		this.delayBetweenLoggingTimer.cancel();
		System.out.println(this+" : "+Calendar.getInstance().getTime()+" @ Logging Terminated");
		this.delayBetweenLoggingTimer = null;
	
	}
	
	protected void startLogging() 
	{
		System.out.println(this+" : "+Calendar.getInstance().getTime()+" @ Logging Started");
	}
	
	protected void stopLogging() 
	{
		System.out.println(this+" : "+Calendar.getInstance().getTime()+" @ Logging Stopped");
	}
	
	
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		System.out.println(this+" : "+"onAccuracyChanged: " + sensor + ", accuracy: " + accuracy);
		
	}
	
	
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
	
	/*
	 * Used in when writing data to files, as it simplifies the process of seeing what logger the data
	 * is coming from
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
	
	protected void performLogging() {
		System.out.println(this+" : "+Calendar.getInstance().getTime()+" @ Perform logging");
	}
}
