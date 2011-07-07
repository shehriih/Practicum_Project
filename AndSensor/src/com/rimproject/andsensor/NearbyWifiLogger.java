package com.rimproject.andsensor;

import java.util.List;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

public class NearbyWifiLogger extends BasicLogger
{
	WifiManager wifiManager;
	public static final String SENSOR_NAME="Wifi";

	
	public NearbyWifiLogger() 
	{
		super();
		setDelayBetweenLogging(60 * 1000);
		
		this.wifiManager = (WifiManager) AndSensor.getContext().getSystemService(android.content.Context.WIFI_SERVICE);
	}
	
	protected void performLogging() {
		super.performLogging();
		
		List<ScanResult> scanResults = this.wifiManager.getScanResults();
		
		if (scanResults != null) {
			super.writeToLogFile(getWifiAsString(scanResults),SENSOR_NAME);
		} else {
			System.out.println(this+" performLogging failed");
		}
	}	
	
	public  String getWifiAsString(List<ScanResult> list)
	{
		String output="";
		
		for(ScanResult sr:list)
		{
			
			output+=new WifiReading(sr.SSID, sr.BSSID)+"\n";
		}
		return output;
	}
	
	/*
	 * Class to hold any Wifi reading, it should be used when writing to the file
	 * and when parsing the file
	 */
	class WifiReading
	{
		private String wifiName,wifiAddress; // SSID,BSSID

		public WifiReading(String wifiName, String wifiAddress)
		{
			this.wifiName=wifiName;
			this.wifiAddress=wifiAddress;
		}
		

		public String getWifiName() {
			return wifiName;
		}



		public void setWifiName(String wifiName) {
			this.wifiName = wifiName;
		}



		public String getWifiAddress() {
			return wifiAddress;
		}



		public void setWifiAddress(String wifiAddress) {
			this.wifiAddress = wifiAddress;
		}
		
		@Override
		public String toString() {
			
			return getWifiName()+genericDel+getWifiAddress();
		}

		
	}

}
