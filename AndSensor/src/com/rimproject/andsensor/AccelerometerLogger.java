package com.rimproject.andsensor;

import java.util.Calendar;


public class AccelerometerLogger extends BasicLogger
{
	public AccelerometerLogger() 
	{
		super();
	}
	
	public void startLogging() {
		System.out.println(Calendar.getInstance().getTime()+" @ Accelerometer Logging Started");
	}
	
	protected void stopLogging() 
	{
		System.out.println(Calendar.getInstance().getTime()+" @ Accelerometer Logging Stopped");
	}

}
