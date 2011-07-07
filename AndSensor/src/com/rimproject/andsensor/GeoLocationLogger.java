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
			super.writeToLogFile(new GeoLocationReading(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude())+"\n",SENSOR_NAME);
		} else {
			System.out.println(this+" performLogging failed");
		}
	}	
	
	/*
	 * Class to hold any GeoLocation reading, it should be used when writing to the file
	 * and when parsing the file
	 */
	class GeoLocationReading
	{
		private double latitude,longitude;

		public GeoLocationReading(double latitude, double longitude)
		{
			this.latitude=latitude;
			this.longitude=longitude;
		}
		

		public double getLatitude() {
			return latitude;
		}


		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}


		public double getLongitude() {
			return longitude;
		}


		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}


		@Override
		public String toString() {
			
			return getLatitude()+genericDel+getLongitude();
		}
		
		
	}
	
}