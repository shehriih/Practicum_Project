package com.rimproject.andsensor;

import java.util.*;

public class LoggerManager 
{
  HashMap<String, BasicLogger> listOfSensors;

	public LoggerManager() 
	{
		listOfSensors = new HashMap<String, BasicLogger>();
		//listOfSensors.put("Accelerometer", new AccelerometerLogger());
		//listOfSensors.put("GeoLocation", new GeoLocationLogger());
		//listOfSensors.put("LightSensor", new LightSensorLogger());
		//listOfSensors.put("NearbyWifi", new NearbyWifiLogger());
		//listOfSensors.put("NearbyBluetooth", new NearbyBluetoothLogger());
	}
	
	public void initiateAllLogging()
	{
		listOfSensors.put("Accelerometer", new AccelerometerLogger());
		listOfSensors.put("GeoLocation", new GeoLocationLogger());
		listOfSensors.put("LightSensor", new LightSensorLogger());
		listOfSensors.put("NearbyWifi", new NearbyWifiLogger());
		listOfSensors.put("NearbyBluetooth", new NearbyBluetoothLogger());
		
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
