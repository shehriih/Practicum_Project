package com.rimproject.logreadings;

import java.io.Serializable;
import java.util.Date;

import com.rimproject.andsensor.BasicLogger;

public class LocationNetworkReading extends BasicLogReading implements Serializable 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -717519922945736631L;
	private Date locationRecordingTime;
	private double latitude,longitude;


	public LocationNetworkReading(Date timeStamp, Date locationRecordingTime, double latitude, double longitude)
	{
		super(timeStamp);
		this.locationRecordingTime = locationRecordingTime;
		this.latitude=latitude;
		this.longitude=longitude;
		
	}
	
	public LocationNetworkReading() {
		// TODO Auto-generated constructor stub
	}

	public LocationNetworkReading parseObjFromString (String inputFromLogFile)
	{
		String[] lineArr   = inputFromLogFile.split(BasicLogger.timeStampDel,2);
		
		String stringDate  =lineArr[0];
		Date dateTime = getDateFromString(stringDate);
		super.setDateTimeStamp(dateTime);
		
		String[] params =  lineArr[1].split(BasicLogger.genericDel);
	
		LocationNetworkReading glr = new LocationNetworkReading(dateTime, getDateFromString(params[0]), Double.parseDouble(params[1]), Double.parseDouble(params[2]));
		
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
		+getLocationRecordingTime()+BasicLogger.genericDel
		+getLatitude()+BasicLogger.genericDel
		+getLongitude()+"\n";
	}
	
}
