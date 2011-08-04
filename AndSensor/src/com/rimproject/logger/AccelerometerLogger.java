package com.rimproject.logger;

import java.util.Calendar;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

import com.rimproject.logreader.AccelerometerReading;
import com.rimproject.main.AndSensor;

public class AccelerometerLogger extends BasicTimedDurationLogger
{
	SensorManager sensorManager;
	Sensor sensor;
	public static final String SENSOR_NAME="Accelerometer";

	
	public AccelerometerLogger() 
	{
		super();
		this.sensorManager = (SensorManager) AndSensor.getContext().getSystemService(android.content.Context.SENSOR_SERVICE);
		this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	}
	
	public void startLogging() {
		super.startLogging();
		//application does not need "game level" accuracy
		this.sensorManager.registerListener(this, this.sensor, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	protected void stopLogging() 
	{
		super.stopLogging();
		this.sensorManager.unregisterListener(this);
	}
	
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
		System.out.println("onAccuracyChanged: " + sensor + ", accuracy: " + accuracy);
	}
	
	public void onSensorChanged(SensorEvent event)
	{
		int sensor = event.sensor.getType(); 
		float[] values = event.values;
		
		synchronized (this) {
            if (sensor == Sensor.TYPE_ACCELEROMETER) {
            	flio.writeToTXTLogFile(SENSOR_NAME,new AccelerometerReading(Calendar.getInstance().getTime(),values[0], values[1], values[2]));
            } else {
            	System.out.println(this+" ERROR: Unexpected sensor reading from sensor "+sensor);
            }
        }
	}
	

}
