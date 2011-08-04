package com.rimproject.logreader;


import java.util.Date;


import com.rimproject.logger.BasicLogger;

public class WifiReading extends BasicLogReading
{
	
	private String SSID,BSSID;


	public WifiReading(Date dateTimeStamp,String SSID, String BSSID)
	{
		super(dateTimeStamp);
		this.SSID=SSID;
		this.BSSID=BSSID;
		
	}
	
	public WifiReading() {
		// TODO Auto-generated constructor stub
	}

	public WifiReading parseObjFromString (String inputFromLogFile)
	{
		String[] lineArr   = inputFromLogFile.split(BasicLogger.timeStampDel,2);
		
		String stringDate  =lineArr[0];
		Date dateTime = getDateFromString(stringDate);
		super.setDateTimeStamp(dateTime);
		
		String[] params =  lineArr[1].split(BasicLogger.genericDel);
		
		WifiReading wr = new WifiReading(dateTime, params[0], params[1]);
		return wr;

	}


		
	public String getSSID() {
		return SSID;
	}




	public void setSSID(String sSID) {
		SSID = sSID;
	}




	public String getBSSID() {
		return BSSID;
	}




	public void setBSSID(String bSSID) {
		BSSID = bSSID;
	}




	@Override
	public String toString() {
		String output="";
			
		output+=BasicLogReading.getStringFormattedDateTime(getDateTimeStamp())+BasicLogger.timeStampDel+getSSID()+BasicLogger.genericDel+getBSSID()+"\n";
	
		return output;

		
	}

	
}

