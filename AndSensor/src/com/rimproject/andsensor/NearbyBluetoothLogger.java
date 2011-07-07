package com.rimproject.andsensor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class NearbyBluetoothLogger extends BasicTimedDurationLogger
{	
	BluetoothAdapter bluetoothAdapter;
	BroadcastReceiver bluetoothReceiver;
	List<BluetoothDevice> bluetoothDevices;
	public static final String SENSOR_NAME="Bluetooth";

	
	public NearbyBluetoothLogger() 
	{
		super();
		setLoggingDuration(20 * 1000);
		
		this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		this.bluetoothReceiver = new BluetoothReceiver(this);
	}
	
	class BluetoothReceiver extends BroadcastReceiver {
		
		NearbyBluetoothLogger logger;
		
		public BluetoothReceiver(NearbyBluetoothLogger aLogger) {
			this.logger = aLogger;
		}
		
		public void onReceive(Context context, Intent intent) {
	        String action = intent.getAction();
	        // When discovery finds a device
	        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
	            // Get the BluetoothDevice object from the Intent
	            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	            // Add the name and address to an array adapter to show in a ListView
	            this.logger.bluetoothDevices.add(device);
	            
	            // debugging
	            Log.v("Bluetooth :",device.toString());
	        }
	    }
	}
	
	public void startLogging() {
		super.startLogging();
		
		this.bluetoothDevices = new ArrayList<BluetoothDevice>();
		if(this.bluetoothAdapter.isEnabled()) { 
			IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
			AndSensor.getContext().registerReceiver(this.bluetoothReceiver, filter);
			this.bluetoothAdapter.startDiscovery();
		}
	}
	
	protected void stopLogging() 
	{
		super.stopLogging();
		
		try {
			AndSensor.getContext().unregisterReceiver(this.bluetoothReceiver);
		} catch (IllegalArgumentException e) {
			//receiver was not registered, no action required
		} finally {
			this.bluetoothAdapter.cancelDiscovery();
			if (this.bluetoothDevices != null) {
				super.writeToLogFile(getDevicesListAsString(bluetoothDevices),SENSOR_NAME);
			}
		}
	}
	
	public String getDevicesListAsString(List<BluetoothDevice> list)
	{
		String output="";
		for (BluetoothDevice item:list)
		{
			output+=new BluetoohReading(item.getName(),item.getAddress(),getDeviceType(item.getBluetoothClass().getDeviceClass()))+"\n";
		}
		return output;
	}
	
	
	
	public static String getDeviceType(int deviceIntType)
	
	{
		/* obtained from 
		 * http://developer.android.com/reference/android/bluetooth/BluetoothClass.Device.html
		 * 
		 * public static final int COMPUTER_LAPTOP = 268 
		 * public static final int PHONE_SMART = 524
		 */
		
		switch(deviceIntType)
		{
			case 268: return "COMPUTER_LAPTOP";
			case 524: return "PHONE_SMART";
		}
		
		return "OTHER";
	}
	
	/*
	 * Class to hold any Bluetooth reading, it should be used when writing to the file
	 * and when parsing the file
	 */
	class BluetoohReading
	{
		private String deviceName,deviceAddress,deviceType; 

		public BluetoohReading(String deviceName, String deviceAddress, String deviceType)
		{
			this.deviceName=deviceName;
			this.deviceAddress=deviceAddress;
			this.deviceType=deviceType;
		}
		
		
		
		
		public String getDeviceName() {
			return deviceName;
		}




		public void setDeviceName(String deviceName) {
			this.deviceName = deviceName;
		}




		public String getDeviceAddress() {
			return deviceAddress;
		}




		public void setDeviceAddress(String deviceAddress) {
			this.deviceAddress = deviceAddress;
		}




		public String getDeviceType() {
			return deviceType;
		}




		public void setDeviceType(String deviceType) {
			this.deviceType = deviceType;
		}




		@Override
		public String toString() {
			
			return getDeviceName()+genericDel+getDeviceAddress()+genericDel+getDeviceType();
		}

		
	}

}
