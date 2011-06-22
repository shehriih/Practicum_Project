package com.rimproject.andsensor;

import java.util.Calendar;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

public class LightSensorLogger extends BasicLogger
{
	public LightSensorLogger() 
	{
		super();
		this.sensorManager = (SensorManager) AndSensor.getContext().getSystemService(android.content.Context.SENSOR_SERVICE);
		//this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
		this.sensor = sensorManager.getSensorList(Sensor.TYPE_LIGHT).get(0);
		setDelayBetweenLogging(1000);
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
            	//sensorManager.LIGHT_CLOUDY; //100
            	//sensorManager.LIGHT_SUNRISE;//
            	//sensorManager.L
            	//if(values[0] )
            		
            	System.out.println(this+" onSensorChanged: " + values[0] );
            } else {
            	System.out.println(this+" ERROR: Unexpected sensor reading from sensor "+sensor);
            }
        }
	}
}
