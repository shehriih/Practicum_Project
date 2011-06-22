package com.rimproject.andsensor;

import java.util.List;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class NearbyWifiLogger extends BasicLogger
{
	WifiManager wifiManager;
	
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
			super.writeToLogFile(scanResults.toString());
		} else {
			System.out.println(this+" performLogging failed");
		}
	}	
}
