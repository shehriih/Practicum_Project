package com.rimproject.andsensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

public class AccelerometerLogger extends BasicTimedDurationLogger
{
	SensorManager sensorManager;
	Sensor sensor;
	
	public AccelerometerLogger() 
	{
		super();
		this.sensorManager = (SensorManager) AndSensor.getContext().getSystemService(android.content.Context.SENSOR_SERVICE);
		this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
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
            if (sensor == Sensor.TYPE_ACCELEROMETER) {
            	System.out.println(this+" onSensorChanged: " + sensor + ", x: " + 
    					values[0] + ", y: " + values[1] + ", z: " + values[2]);
            } else {
            	System.out.println(this+" ERROR: Unexpected sensor reading from sensor "+sensor);
            }
        }
	}
}
