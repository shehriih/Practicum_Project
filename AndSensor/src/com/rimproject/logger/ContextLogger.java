package com.rimproject.logger;

import java.util.Calendar;

import com.rimproject.contextanalyzer.ContextDetector;
import com.rimproject.contextanalyzer.DeviceStatus;
import com.rimproject.logreader.ContextReading;

public class ContextLogger extends BasicLogger
{
	public static final String SENSOR_NAME="_ContextLogger";
	
	public ContextLogger() {
		super();
		setDelayBetweenLogging(20*1000);
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
		int durationToConsider = 120;
		double probability = ContextDetector.probabilityOfSleeping(durationToConsider);
		
		String optionalTags = "None";
		DeviceStatus deviceStatus = new DeviceStatus();
		if (deviceStatus.isWithinHomeWifiRange(durationToConsider)) {
			optionalTags = "Home";
		}
		
		flio.writeToTXTLogFile(SENSOR_NAME,new ContextReading(Calendar.getInstance().getTime(), contextName, probability, optionalTags));
	}

}
