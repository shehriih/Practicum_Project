package com.rimproject.logreader;

import java.util.Date;
import com.rimproject.logger.BasicLogger;

public class ContextReading extends BasicLogReading 
{

	
	private String contextName;
	private double probability;
	private String optionalTags;
	
	
	
	public String getOptionalTags() {
		return optionalTags;
	}
	public void setOptionalTags(String optionalTags) {
		this.optionalTags = optionalTags;
	}
	public String getContextName() {
		return contextName;
	}
	public void setContextName(String contextName) {
		this.contextName = contextName;
	}
	public double getProbability() {
		return probability;
	}
	public void setProbability(double probability) {
		this.probability = probability;
	}
	public ContextReading(Date dateTimeStamp, String contextName, double probability, String optionalTags)
	{
		super(dateTimeStamp);
		this.contextName = contextName;
		this.probability=probability;
		this.optionalTags = optionalTags;
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

		ContextReading cr = new ContextReading(dateTime,params[0],Double.parseDouble(params[1]), params[2]);
		return cr;
	}
	
	public String toString()
	{
		return BasicLogReading.getStringFormattedDateTime(
				getDateTimeStamp())+BasicLogger.timeStampDel
				+contextName+BasicLogger.genericDel
				+probability+BasicLogger.genericDel
				+optionalTags+"\n";
	}
	

}
