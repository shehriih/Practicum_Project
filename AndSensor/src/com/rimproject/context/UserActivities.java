package com.rimproject.context;

import java.util.*;
import com.rimproject.andsensor.BasicTimedDurationLogger;

public class UserActivities {

	BasicTimedDurationLogger basicLogger = new BasicTimedDurationLogger();
	DeviceStatus deviceStatus = new DeviceStatus();
	
	private String activityDetails;
	private String activity;
	
	Calendar calendar = Calendar.getInstance();
	 
	public void sleeping(int duration){
		if(deviceStatus.isIdle(duration) && !(deviceStatus.isLocationChanged(duration)) && (deviceStatus.checkLightLevel(duration) == SensorConstants.LIGHT_DIM)){
			activity = "Sleeping";
			if(SensorConstants.LOCATION_HOME.equalsIgnoreCase(deviceStatus.getLocation())){
				activityDetails = deviceStatus.getLocation() + ":" + calendar.getTime(); 
			}
			else if(SensorConstants.LOCATION_WORK.equalsIgnoreCase(deviceStatus.getLocation())){
				activityDetails = deviceStatus.getLocation() + ":"+ calendar.getTime() +"/n";
			}
			
		}
		//basicLogger.writeToLogFile(activity, activityDetails);
	}
}
