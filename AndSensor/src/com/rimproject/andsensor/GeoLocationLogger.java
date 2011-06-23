package com.rimproject.andsensor;

import java.util.Calendar;

import android.location.Location;
import android.location.LocationManager;

public class GeoLocationLogger extends BasicLogger
{
	private LocationManager locationManager;
	private String locationSensor;
	public static final String SENSOR_NAME="GeoLocation";
	
	public GeoLocationLogger() 
	{
		super();
		this.locationManager = (LocationManager) AndSensor.getContext().getSystemService(android.content.Context.LOCATION_SERVICE);
		this.locationSensor = /*LocationManager.GPS_PROVIDER;*/LocationManager.NETWORK_PROVIDER;
		
		setDelayBetweenLogging(5000);
	}
	
	protected void performLogging() {
		super.performLogging();
		
		Location lastKnownLocation = this.locationManager.getLastKnownLocation(this.locationSensor);
		if (lastKnownLocation != null) {
			super.writeToLogFile(lastKnownLocation.toString(),SENSOR_NAME);
		} else {
			System.out.println(this+" performLogging failed");
		}
	}	
}