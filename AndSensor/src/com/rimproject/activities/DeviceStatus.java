package com.rimproject.activities;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.rimproject.andsensor.*;
import com.rimproject.fileio.FileLoggingIO;
import com.rimproject.logreadings.ContextReading;
import com.rimproject.logreadings.LightReading;

public class DeviceStatus {
	
	private String location;
	
	public boolean isIdle(int duration){
		boolean result = false;
		if(isDeviceInUse(duration) && isStationary(duration)){
			result = true;
		}
		return result;
	}
	
	public boolean isActive(int duration){
		boolean result = false;
		return result;
	}
	
	public boolean isPassive(int duration){
		boolean result = false;
		return result;
	}
	
	public boolean isDeviceInUse(int duration){
		boolean result = false;
		if(isActive(duration) || isPassive(duration)) {
			result = true;
		}
		return result;
	}
	
	public boolean isStationary(int duration){
		boolean result = false;
		if(checkAccelerometerActivityLevel(duration) <= SensorConstants.STATIONARY && isLocationChanged(duration)){
			result = true;
		}
		return result;
	}
	
	public boolean isLocationChanged(int duration){
		boolean result = false;
		if(isGPSAvailable()){
			if(checkIfGPSChanging(duration) >= SensorConstants.STATIONARY){
				result = true;
			}
		}
		else{
			if(isWIFIAvailable()){
					
			}
			else if(isBluetoothAvailable()){
				
			}
			else if(isWIFIAvailable()){
				
			}
			else if(isNetworkAvailable()){
				
			}
		}
		return result;
	}
	
	public boolean isDeviceCharging(){
		boolean result = false;
		return result;
	}
	
	public double checkLightLevel(int duration){
		double result = -1.0;
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -duration);
		Date d1 = cal.getTime();
	    Date d2 = Calendar.getInstance().getTime();

	    FileLoggingIO<LightReading> fio = new FileLoggingIO<LightReading>();
        HashMap<Date,List<LightReading>> map = fio.readFromTXTLogFile(LightSensorLogger.SENSOR_NAME, new LightReading(), null,d1,d2);
		
		Set<Date> es = map.keySet();
		TreeSet<Date> ts = new TreeSet<Date>(es);
		
		int numberOfReadings = 0;
		double accumulator = 0.0;
		for (Date key : ts) {
			for (LightReading reading: map.get(key)) {
				numberOfReadings++;
				 accumulator += reading.getLightValue();
			}
		}
		result = accumulator / numberOfReadings; //average of all readings
		
		return result;
	}
	
	public double checkAccelerometerActivityLevel(int duration){
		double result = 0;
		
		return result;
	}
	
	public boolean isGPSAvailable(){
		boolean result = false;
		LocationManager loc_manager = (LocationManager) AndSensor.getContext().getSystemService(Context.LOCATION_SERVICE); 
		List<String> str = loc_manager.getProviders(true); 
		if(str.size() > 0) {
			result = true;
		}
		return result;
	}
	
	public boolean isNetworkAvailable(){
		boolean result = false;
		ConnectivityManager connectivity = (ConnectivityManager) AndSensor.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
		if(networkInfo.isAvailable()){
			result = true;
		}
		return result;				
	}
	
	public boolean isWIFIAvailable(){
		boolean result = false;
		ConnectivityManager connectivity = (ConnectivityManager) AndSensor.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiInfo = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(wifiInfo.isAvailable()){
			result = true;
		}
		return result;
	}
	
	public boolean isBluetoothAvailable(){
		boolean result = false;
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if(bluetoothAdapter == null){
			result = false;
		}
		else{
			if(bluetoothAdapter.isEnabled()){
				result = true;
			}
		}
		return result;
	}
	
	public double checkIfGPSChanging(int duration){
		double result = 0.0;
		
		return result;
	}
	
	public double checkIfWIFIChanging(int duration){
		double result = 0.0;
		
		return result;
	}
	
	public double checkIfNetworkChanging(int duration){
		double result = 0.0;
		
		return result;
	}
	
	public void setLocation(String location){
		this.location = location;
	}
	
	public String getLocation(){
		return location;
	}
}
