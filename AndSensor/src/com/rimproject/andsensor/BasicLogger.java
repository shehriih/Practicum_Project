package com.rimproject.andsensor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Environment;
import android.util.Log;

import com.rimproject.fileio.FileLoggingIO;
import com.rimproject.logreadings.BasicLogReading;

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
	
	public static final String genericDel=" %&# "; //delimiter to be used between dfferent values like x,y,z in Acc reading
    public static final String timeStampDel= " @ "; // delimiter to be used between the timestamp and values
	FileLoggingIO flio = new FileLoggingIO();

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
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
	
	protected void performLogging() {
		System.out.println(this+" : "+Calendar.getInstance().getTime()+" @ Perform logging");
	}
	
	
}
