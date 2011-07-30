package com.rimproject.logger;

import java.util.Calendar;
import java.util.List;

import com.rimproject.logreader.WifiReading;
import com.rimproject.main.AndSensor;

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
			for(ScanResult sr:scanResults)
			  flio.writeToTXTLogFile(SENSOR_NAME,new WifiReading(Calendar.getInstance().getTime(), sr.SSID,sr.BSSID));
		} else {
			System.out.println(this+" performLogging failed");
		}
	}	
	
	
	
	
	

}
