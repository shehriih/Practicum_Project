package com.rimproject.logger;

import java.util.Calendar;
import java.util.Date;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

import com.rimproject.logreader.LightReading;
import com.rimproject.main.AndSensor;

public class LightSensorLogger extends BasicTimedDurationLogger
{
	SensorManager sensorManager;
	Sensor sensor;
	public static final String SENSOR_NAME="Light";
	
	public LightSensorLogger() 
	{
		super();
		this.sensorManager = (SensorManager) AndSensor.getContext().getSystemService(android.content.Context.SENSOR_SERVICE);
		this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
		
		this.setDelayBetweenLogging(60*1000);
		this.setLoggingDuration(5*1000);
	}
	
	public void startLogging() {
		super.startLogging();
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
            if (sensor == Sensor.TYPE_LIGHT) {
            	flio.writeToTXTLogFile(SENSOR_NAME,new LightReading(Calendar.getInstance().getTime(),values[0]));
            } else {
            	System.out.println(this+" ERROR: Unexpected sensor reading from sensor "+sensor);
            }
        }
	}

}
