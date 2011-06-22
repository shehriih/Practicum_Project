package com.rimproject.andsensor;

import android.location.Location;
import android.location.LocationManager;

public class LocationLogger extends BasicLogger
{
	
	private LocationManager locationManager;
	private String locationSensor;
	
	public LocationLogger() 
	{
		//this sensor is slightly different from the others. We only want a single recording
		//as the OS keeps a rough track of the location for us.
		super();
		this.locationManager = (LocationManager) AndSensor.getContext().getSystemService(android.content.Context.LOCATION_SERVICE);
		this.locationSensor = LocationManager.GPS_PROVIDER;//LocationManager.NETWORK_PROVIDER;
		// Or use LocationManager.GPS_PROVIDER
		setDelayBetweenLogging(60 * 1000);
	}
	
	/*
	 * we override the run method
	 */
	public void run() 
	{
		logLocation();
		stopLogging();
	}
	
	protected void logLocation() {
		Location lastKnownLocation = this.locationManager.getLastKnownLocation(this.locationSensor);
		System.out.println(this+" logLocation: " + lastKnownLocation);
	}
}
