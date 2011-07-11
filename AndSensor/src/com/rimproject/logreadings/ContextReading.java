package com.rimproject.logreadings;

import java.io.Serializable;
import java.util.Date;

import com.rimproject.andsensor.BasicLogger;

public class ContextReading extends BasicLogReading implements Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1412251076778561174L;
	private String contextName;
	private double probability;
	
	
	
	public ContextReading(Date dateTimeStamp, String contextName, double probability)
	{
		super(dateTimeStamp);
		this.contextName = contextName;
		this.probability=probability;
	}
	public ContextReading() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ContextReading parseObjFromString (String inputFromLogFile)
	{
		String[] lineArr   = inputFromLogFile.split(BasicLogger.timeStampDel,2);
		
		String stringDate  =lineArr[0];
		Date dateTime = getDateFromString(stringDate);
		super.setDateTimeStamp(dateTime);
		
		String[] params =  lineArr[1].split(BasicLogger.genericDel);

		ContextReading cr = new ContextReading(dateTime,params[0],Double.parseDouble(params[1]));
		return cr;
	}
	
	public String toString()
	{
		return BasicLogReading.getStringFormattedDateTime(getDateTimeStamp())+BasicLogger.timeStampDel+contextName+BasicLogger.genericDel+probability+"\n";
	}
	

}
