package com.rimproject.andsensor;

import java.util.Calendar;
import java.util.Date;

import android.location.Location;
import android.location.LocationManager;

import com.rimproject.logreadings.GeoLocationReading;

public class GeoLocationLogger extends BasicLogger
{
	private LocationManager locationManager;
	private String locationSensorGPS;
	private String locationSensorNetwork;
	public static final String SENSOR_NAME="GeoLocation";
	
	public GeoLocationLogger() 
	{
		super();
		this.locationManager = (LocationManager) AndSensor.getContext().getSystemService(android.content.Context.LOCATION_SERVICE);
		this.locationSensorNetwork = LocationManager.NETWORK_PROVIDER;
		this.locationSensorGPS = LocationManager.GPS_PROVIDER;
		
		setDelayBetweenLogging(5000);
	}
	
	protected void performLogging() {
		super.performLogging();
		
		Location lastKnownLocationGPS = this.locationManager.getLastKnownLocation(this.locationSensorGPS);
		Location lastKnownLocationNetwork = this.locationManager.getLastKnownLocation(this.locationSensorNetwork);
		
		if (lastKnownLocationNetwork != null) {
			flio.writeToTXTLogFile(SENSOR_NAME,
					new GeoLocationReading(Calendar.getInstance().getTime(),
					new Date(lastKnownLocationNetwork.getTime()),
					"Network", 
					lastKnownLocationNetwork.getLatitude(),
					lastKnownLocationNetwork.getLongitude()
					));
		}
		
		if (lastKnownLocationGPS != null) {
			flio.writeToTXTLogFile(SENSOR_NAME,
					new GeoLocationReading(Calendar.getInstance().getTime(),
					new Date(lastKnownLocationGPS.getTime()),
					"GPS", 
					lastKnownLocationGPS.getLatitude(),
					lastKnownLocationGPS.getLongitude()
					));
		}
	}	
	
	/*
	 * Class to hold any GeoLocation reading, it should be used when writing to the file
	 * and when parsing the file
	 */
	   
	
}