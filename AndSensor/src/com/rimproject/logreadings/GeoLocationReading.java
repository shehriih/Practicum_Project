package com.rimproject.logreadings;

import java.io.Serializable;
import java.util.Date;

import android.location.Location;

import com.rimproject.andsensor.BasicLogger;

public class GeoLocationReading extends BasicLogReading implements Serializable 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -717519922945736631L;
	private Date locationRecordingTime;
	private String locationSensorType;
	private double latitude,longitude;


	public GeoLocationReading(Date timeStamp, Date locationRecordingTime, String locationSensorType, double latitude, double longitude)
	{
		super(timeStamp);
		this.locationRecordingTime = locationRecordingTime;
		this.locationSensorType = locationSensorType;
		this.latitude=latitude;
		this.longitude=longitude;
		
	}
	
	public GeoLocationReading() {
		// TODO Auto-generated constructor stub
	}

	public GeoLocationReading parseObjFromString (String inputFromLogFile)
	{
		String[] lineArr   = inputFromLogFile.split(BasicLogger.timeStampDel,2);
		
		String stringDate  =lineArr[0];
		Date dateTime = getDateFromString(stringDate);
		super.setDateTimeStamp(dateTime);
		
		String[] params =  lineArr[1].split(BasicLogger.genericDel);
	
		GeoLocationReading glr = new GeoLocationReading(dateTime, getDateFromString(params[0]), params[1], Double.parseDouble(params[2]), Double.parseDouble(params[3]));
		
		return glr;
	}
		
		
	public Date getLocationRecordingTime() {
		return locationRecordingTime;
	}

	public void setLocationRecordingTime(Date locationRecordingTime) {
		this.locationRecordingTime = locationRecordingTime;
	}

	public String getLocationSensorType() {
		return locationSensorType;
	}

	public void setLocationSensorType(String locationSensorType) {
		this.locationSensorType = locationSensorType;
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
		
		return BasicLogReading.getStringFormattedDateTime(getDateTimeStamp())+BasicLogger.timeStampDel
		+getLocationRecordingTime()+BasicLogger.genericDel
		+getLocationSensorType()+BasicLogger.genericDel+
		+getLatitude()+BasicLogger.genericDel
		+getLongitude()+"\n";
	}
	
}
