package com.rimproject.andsensor;

import java.util.Timer;
import java.util.TimerTask;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class AccelormetorLogger extends TimerTask implements SensorEventListener
{
	private Timer timer;
	private long frequencyOfLog;
	private long loggingDuration;
	

	public AccelormetorLogger() {
		super();
		
		timer = new Timer();
		setFrequencyOfLog(10*1000);
		setLoggingDuration(3 * 1000);
	}
	
	public Timer getTimer() {
		return timer;
	}



	public void setTimer(Timer timer) {
		this.timer = timer;
	}



	public long getFrequencyOfLog() {
		return frequencyOfLog;
	}



	public void setFrequencyOfLog(long frequencyOfLog) {
		this.frequencyOfLog = frequencyOfLog;
	}



	public long getLoggingDuration() {
		return loggingDuration;
	}



	public void setLoggingDuration(long loggingDuration) {
		this.loggingDuration = loggingDuration;
	}


	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) 
	{
		// TODO Auto-generated method stub
		
	}
	
	public void logData()
	{
		// log the raw data
	}

}
