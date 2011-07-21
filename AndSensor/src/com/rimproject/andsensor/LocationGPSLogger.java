package com.rimproject.andsensor;

import java.util.Calendar;
import java.util.Date;

import android.location.Location;
import android.location.LocationManager;

import com.rimproject.logreadings.LocationGPSReading;

public class LocationGPSLogger extends BasicLogger
{
	private LocationManager locationManager;
	private String locationSensorNetwork;
	public static final String SENSOR_NAME="LocationGPS";
	
	public LocationGPSLogger() 
	{
		super();
		this.locationManager = (LocationManager) AndSensor.getContext().getSystemService(android.content.Context.LOCATION_SERVICE);
		this.locationSensorNetwork = LocationManager.GPS_PROVIDER;
	}
	
	protected void performLogging() {
		super.performLogging();
		
		Location lastKnownLocationNetwork = this.locationManager.getLastKnownLocation(this.locationSensorNetwork);
		
		if (lastKnownLocationNetwork != null) {
			flio.writeToTXTLogFile(SENSOR_NAME,
					new LocationGPSReading(Calendar.getInstance().getTime(),
					new Date(lastKnownLocationNetwork.getTime()),
					lastKnownLocationNetwork.getLatitude(),
					lastKnownLocationNetwork.getLongitude()
					));
		}
	}	
}