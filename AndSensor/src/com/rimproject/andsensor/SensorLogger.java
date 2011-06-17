package com.rimproject.andsensor;

import java.util.*;

public class SensorLogger 
{
  HashMap<String, BasicLogger> listOfSensors;

	public SensorLogger() 
	{
		listOfSensors = new HashMap<String, BasicLogger>();
		//listOfSensors.put("Accelerometer", new AccelerometerLogger());
		listOfSensors.put("Light", new LightSensorLogger());
	}
	
	public void initiateAllLogging()
	{
		Iterator<?> it = listOfSensors.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, BasicLogger> pairs = (Map.Entry)it.next();
	        System.out.println("Initiating logging from "+pairs.getKey());
	        
	        LoggerInterface logger = (LoggerInterface) pairs.getValue();
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
