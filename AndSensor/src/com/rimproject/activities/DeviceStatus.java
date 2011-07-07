package com.rimproject.activities;

import java.util.List;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;

import com.rimproject.andsensor.*;

public class DeviceStatus {
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
		double result = 0;
		
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
	
	
}
