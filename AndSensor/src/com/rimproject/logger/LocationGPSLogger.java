package com.rimproject.logger;

import java.util.Calendar;
import java.util.Date;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.rimproject.logreader.LocationGPSReading;
import com.rimproject.main.AndSensor;

public class LocationGPSLogger extends BasicLogger implements LocationListener
{
	private LocationManager locationManager;
	public static final String SENSOR_NAME="LocationGPS";
	Location latestLocation;
	Location latestLoggedLocation;
	
	public LocationGPSLogger() 
	{
		super();
		this.locationManager = (LocationManager) AndSensor.getContext().getSystemService(android.content.Context.LOCATION_SERVICE);
		//update location every 5 seconds, but only written to file when performLogging is called.
		//this means a lot of location updates won't currently be stored. This should probably
		//be improved
		this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10f, this);
	}
	
	protected void performLogging() {
			super.performLogging();
			
			//commented out code:
			//would be used if you only want log lines written when location _has_ changed
			if (latestLocation != null) {// && ! latestLocation.equals(latestLoggedLocation)) {
				double longitude = latestLocation.getLongitude();
				double latitude = latestLocation.getLatitude();
				double altitude = latestLocation.getAltitude();
				float accuracy = latestLocation.getAccuracy();
				long time = latestLocation.getTime();
				
				flio.writeToTXTLogFile(SENSOR_NAME,
				new LocationGPSReading(
						Calendar.getInstance().getTime(),
						new Date(time),
						latitude,
						longitude,
						altitude,
						accuracy
						));
				latestLoggedLocation = latestLocation;
			}
	}	
	
	
	public void onLocationChanged(Location location)
	{
		latestLocation = location;
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}
}