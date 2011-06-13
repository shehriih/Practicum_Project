package com.rimproject.andsensor;

import java.util.*;

public class SensorLogger 
{
  HashMap<String, Object> listOfSensors;

	public SensorLogger() 
	{
		listOfSensors = new HashMap<String, Object>();
		listOfSensors.put("Test", new AccelerometerLogger());
	}
	
	public void startAllLogging()
	{
		Iterator<?> it = listOfSensors.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, Object> pairs = (Map.Entry)it.next();
	        System.out.println("Starting logging from "+pairs.getKey());
	        
	        LoggerInterface logger = (LoggerInterface) pairs.getValue();
	        logger.startLogging();
	    }
	}
	  
  
}
