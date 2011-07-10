package com.rimproject.logreadings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BasicLogReading
{
	private Date dateTimeStamp;
	public static SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
	
	public BasicLogReading(Date dateTimeStamp)
	{
		this.dateTimeStamp=dateTimeStamp;
	}

	public BasicLogReading() {
		// TODO Auto-generated constructor stub
	}

	public Date getDateTimeStamp() {
		return dateTimeStamp;
	}

	public void setDateTimeStamp(Date dateTimeStamp) {
		this.dateTimeStamp = dateTimeStamp;
	}
	
	public static String getStringFormattedDateTime(Date date)
	{
		
		String formattedTime=sdf.format(date);
		
		return formattedTime;
	}
	
	public abstract BasicLogReading parseObjFromString(String inputFromLogFile);
	
	public static Date getDateFromString(String sDate)
	{
		Date date=null;
		try {
			 date = sdf.parse(sDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
}
