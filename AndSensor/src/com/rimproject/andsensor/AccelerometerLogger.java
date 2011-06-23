package com.rimproject.andsensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

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
            	super.writeToLogFile(new AccelerometerReading(values[0], values[1], values[2]).toString(),SENSOR_NAME);
            } else {
            	System.out.println(this+" ERROR: Unexpected sensor reading from sensor "+sensor);
            }
        }
	}
	
	/*
	 * Class to hold any accelerometer reading, it should be used when writing to the file
	 * and when parsing the file
	 */
	class AccelerometerReading
	{
		private float x,y,z;

		public AccelerometerReading(float x,float y, float z)
		{
			this.x=x;
			this.y=y;
			this.z=z;
		}
		public float getX() {
			return x;
		}

		public void setX(float x) {
			this.x = x;
		}

		public float getY() {
			return y;
		}

		public void setY(float y) {
			this.y = y;
		}

		public float getZ() {
			return z;
		}

		public void setZ(float z) {
			this.z = z;
		}

		

		@Override
		public String toString() {
			
			return getX()+":"+getY()+":"+getZ();
		}
		
		
	}
}
