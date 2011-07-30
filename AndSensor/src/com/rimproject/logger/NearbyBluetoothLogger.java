package com.rimproject.logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.rimproject.logreader.BluetoothReading;
import com.rimproject.main.AndSensor;

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
			if (this.bluetoothDevices != null) 
			{
				for( BluetoothDevice item:bluetoothDevices)
					flio.writeToTXTLogFile(SENSOR_NAME,new BluetoothReading(Calendar.getInstance().getTime(),item.getName(),item.getAddress(),item.getBluetoothClass().getDeviceClass()));
			}
		}
	}
	
}
