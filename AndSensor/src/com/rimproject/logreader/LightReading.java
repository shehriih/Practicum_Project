package com.rimproject.logreader;


import java.util.Date;

import com.rimproject.logger.BasicLogger;

public class LightReading extends BasicLogReading implements Comparable<LightReading>
{

	private float lightValue;
	
	
	public LightReading(Date dateTimeStamp, float lightValue)
	{
		super(dateTimeStamp);
		this.lightValue=lightValue;
	}
	
	public LightReading() {
		// TODO Auto-generated constructor stub
	}

	public LightReading parseObjFromString (String inputFromLogFile)
	{
		String[] lineArr   = inputFromLogFile.split(BasicLogger.timeStampDel,2);
		
		String stringDate  =lineArr[0];
		Date dateTime = getDateFromString(stringDate);
		super.setDateTimeStamp(dateTime);
		
		String[] params =  lineArr[1].split(BasicLogger.genericDel);

		LightReading lr = new LightReading(dateTime,Float.parseFloat(params[0]));
		return lr;
	}
		

	
	public float getLightValue() 
	{
		return lightValue;
	}
	
	public void setLightValue(float lightValue) 
	{
		this.lightValue = lightValue;
	}
	
	public String toString()
	{
		return BasicLogReading.getStringFormattedDateTime(getDateTimeStamp())+BasicLogger.timeStampDel+lightValue+"\n";
	}

	public int compareTo(LightReading another) {
		if (this.lightValue < another.lightValue) {
			return -1;
		} else {
			return 1;
		}
	}
	

}
