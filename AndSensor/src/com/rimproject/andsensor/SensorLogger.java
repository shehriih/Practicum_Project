package com.rimproject.andsensor;

import java.util.*;

public class SensorLogger 
{
  HashMap<String, BasicLogger> listOfSensors;

	public SensorLogger() 
	{
		listOfSensors = new HashMap<String, BasicLogger>();
		listOfSensors.put("Test", new AccelerometerLogger());
	}
	
	public void startAllLogging()
	{
		Iterator<?> it = listOfSensors.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, BasicLogger> pairs = (Map.Entry)it.next();
	        System.out.println("Starting logging from "+pairs.getKey());
	        
	        LoggerInterface logger = (LoggerInterface) pairs.getValue();
	        logger.initiateRepeatedLogging();
	    }
	}
	  
  
}
