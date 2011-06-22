package com.rimproject.andsensor;

import java.util.*;

public class LoggerManager 
{
  HashMap<String, BasicLogger> listOfSensors;

	public LoggerManager() 
	{
		listOfSensors = new HashMap<String, BasicLogger>();
		listOfSensors.put("Accelerometer", new AccelerometerLogger());
		listOfSensors.put("Location", new LocationLogger());
		listOfSensors.put("LightSensor", new LightSensorLogger());
	}
	
	public void initiateAllLogging()
	{
		Iterator<?> it = listOfSensors.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, BasicLogger> pairs = (Map.Entry)it.next();
	        System.out.println("Initiating logging from "+pairs.getKey());
	        
	        LoggerInterface logger = (LoggerInterface)pairs.getValue();
	        logger.initiateRepeatedLogging();
	    }
	}
	
	public void terminateAllLogging()
	{
		Iterator<?> it = listOfSensors.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, BasicLogger> pairs = (Map.Entry)it.next();
	        System.out.println("Terminating logging from "+pairs.getKey());
	        
	        LoggerInterface logger = (LoggerInterface) pairs.getValue();
	        logger.terminateRepeatedLogging(true);
	    }
	}
	  
  
}
