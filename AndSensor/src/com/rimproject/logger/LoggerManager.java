package com.rimproject.logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*
 * Handles the batch turning on and off of the sensor loggers
 */
public class LoggerManager 
{
  HashMap<String, BasicLogger> listOfSensors;

	public LoggerManager() 
	{
		this.listOfSensors = new HashMap<String, BasicLogger>();
	}
	
	public void initiateAllLogging()
	{
		this.listOfSensors.put("Accelerometer", new AccelerometerLogger());
		this.listOfSensors.put("LocationGPS", new LocationGPSLogger());
		this.listOfSensors.put("LocationNetwork", new LocationNetworkLogger());
		this.listOfSensors.put("LightSensor", new LightSensorLogger());
		this.listOfSensors.put("NearbyWifi", new NearbyWifiLogger());
		this.listOfSensors.put("NearbyBluetooth", new NearbyBluetoothLogger());
		this.listOfSensors.put("ContextLogger", new ContextLogger());
		
		Iterator<?> it = this.listOfSensors.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, BasicLogger> pairs = (Map.Entry)it.next();
	        System.out.println("Initiating logging from "+pairs.getKey());
	        
	        LoggerInterface logger = (LoggerInterface)pairs.getValue();
	        logger.initiateRepeatedLogging();
	    }
	}
	
	public void terminateAllLogging()
	{
		Iterator<?> it = this.listOfSensors.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, BasicLogger> pairs = (Map.Entry)it.next();
	        System.out.println("Terminating logging from "+pairs.getKey());
	        
	        LoggerInterface logger = (LoggerInterface) pairs.getValue();
	        logger.terminateRepeatedLogging(true);
	    }
	    this.listOfSensors.clear();
	}
	  
  
}
