package com.rimproject.logreadings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.bluetooth.BluetoothDevice;

import com.rimproject.andsensor.BasicLogger;

public class BluetoothReading extends BasicLogReading implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5958605740599140570L;
	String name,address;
	int type;




	public BluetoothReading(Date dateTimeStamp, String name, String address, int type)
	{
		super(dateTimeStamp);
		this.name=name;
		this.address=address;
		this.type=type;
	}
	
	public BluetoothReading parseObjFromString (String inputFromLogFile)
	{
		String[] lineArr   = inputFromLogFile.split(BasicLogger.timeStampDel,2);
		
		String stringDate  =lineArr[0];
		Date dateTime = getDateFromString(stringDate);
		super.setDateTimeStamp(dateTime);
		
		String[] params =  lineArr[1].split(BasicLogger.genericDel); 
	    BluetoothReading br = new BluetoothReading(dateTime, params[0], params[1], Integer.parseInt(params[2]));
		
		return br;
	}
	
	




	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	
	public  String getDeviceType(int deviceIntType)
	
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

	@Override
	public String toString() 
	{
		String output="";

		output+=BasicLogReading.getStringFormattedDateTime(getDateTimeStamp())+BasicLogger.timeStampDel+getName()+BasicLogger.genericDel+getAddress()
		       +BasicLogger.genericDel+getType()+"\n";
		
		
		
		return output;
	}


}


