package com.rimproject.andsensor;

import java.util.Calendar;
import java.util.Date;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;

import com.rimproject.logreadings.LocationGPSReading;

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
		this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10f, this);
	}
	
	protected void performLogging() {
			super.performLogging();
			
			if (latestLocation != null && ! latestLocation.equals(latestLoggedLocation)) {
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




//package com.rimproject.andsensor;
//
//import java.util.Calendar;
//import java.util.Date;
//
//import android.location.Location;
//import android.location.LocationManager;
//
//import com.rimproject.logreadings.LocationGPSReading;
//
//public class LocationGPSLogger extends BasicLogger
//{
//	private LocationManager locationManager;
//	private String locationSensorNetwork;
//	public static final String SENSOR_NAME="LocationGPS";
//	
//	public LocationGPSLogger() 
//	{
//		super();
//		this.locationManager = (LocationManager) AndSensor.getContext().getSystemService(android.content.Context.LOCATION_SERVICE);
//		this.locationSensorNetwork = LocationManager.GPS_PROVIDER;
//	}
//	
//	protected void performLogging() {
//		super.performLogging();
//		
//		Location lastKnownLocationNetwork = this.locationManager.getLastKnownLocation(this.locationSensorNetwork);
//		
//		if (lastKnownLocationNetwork != null) {
//			flio.writeToTXTLogFile(SENSOR_NAME,
//					new LocationGPSReading(Calendar.getInstance().getTime(),
//					new Date(lastKnownLocationNetwork.getTime()),
//					lastKnownLocationNetwork.getLatitude(),
//					lastKnownLocationNetwork.getLongitude()
//					));
//		}
//	}	
//}