package com.rimproject.andsensor;

import java.util.Calendar;
import java.util.Date;

import android.location.Location;
import android.location.LocationManager;

import com.rimproject.logreadings.GeoLocationReading;

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
			flio.writeToTXTLogFile(SENSOR_NAME,new GeoLocationReading(Calendar.getInstance().getTime(),lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude()));
			
		} else {
			System.out.println(this+" performLogging failed");
		}
	}	
	
	/*
	 * Class to hold any GeoLocation reading, it should be used when writing to the file
	 * and when parsing the file
	 */
	   
	
}