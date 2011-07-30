package com.rimproject.logreader;

import java.io.Serializable;
import java.util.Date;

import com.rimproject.logger.BasicLogger;

public class LocationGPSReading extends BasicLogReading implements Serializable 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -717519922945736631L;
	private Date locationRecordingTime;
	private double latitude,longitude, altitude;
	private float accuracy;


	public LocationGPSReading(Date timeStamp, Date locationRecordingTime, double latitude, double longitude, double altitude, float accuracy)
	{		
		super(timeStamp);
		this.locationRecordingTime = locationRecordingTime;
		this.latitude=latitude;
		this.longitude=longitude;
		this.altitude=altitude;
		this.accuracy=accuracy;
	}
	
	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public float getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
	}

	public LocationGPSReading() {
		// TODO Auto-generated constructor stub
	}

	public LocationGPSReading parseObjFromString (String inputFromLogFile)
	{
		String[] lineArr   = inputFromLogFile.split(BasicLogger.timeStampDel,2);
		
		String stringDate  =lineArr[0];
		Date dateTime = getDateFromString(stringDate);
		super.setDateTimeStamp(dateTime);
		
		String[] params =  lineArr[1].split(BasicLogger.genericDel);
	
		LocationGPSReading glr = new LocationGPSReading(
				dateTime, 
				getDateFromString(params[0]), 
				Double.parseDouble(params[1]), 
				Double.parseDouble(params[2]), 
				Double.parseDouble(params[3]), 
				Float.parseFloat(params[4])
				);
		return glr;
	}
		
		
	public Date getLocationRecordingTime() {
		return locationRecordingTime;
	}

	public void setLocationRecordingTime(Date locationRecordingTime) {
		this.locationRecordingTime = locationRecordingTime;
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
		+BasicLogReading.getStringFormattedDateTime(getLocationRecordingTime())+BasicLogger.genericDel
		+getLatitude()+BasicLogger.genericDel
		+getLongitude()+BasicLogger.genericDel
		+getAltitude()+BasicLogger.genericDel
		+getAccuracy()+"\n";
	}
}
