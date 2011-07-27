package com.rimproject.context;

public class SensorConstants {
	public static final String LOCATION_HOME = "home";
	public static final String LOCATION_WORK = "work";
	public static final double LIGHT_DIM = 10.0;
	public static final int GPS_STATIONARY = 0;
	
	// accelerometer vector reading range, see  getACCVector() @ AccelerometerReading Class 
	public static final double MIN_ACCELEROMETER_STATIONARY_LEVEL=8.7;
	public static final double MAX_ACCELEROMETER_STATIONARY_LEVEL=9.4;

}


/*
LIGHT_CLOUDY 100.0
LIGHT_FULLMOON 0.25
LIGHT_NO_MOON 0.0010
LIGHT_OVERCAST 10000.0
LIGHT_SHADE 20000.0
LIGHT_SUNLIGHT 110000.0
LIGHT_SUNLIGHT_MAX 120000.0
LIGHT_SUNRISE 400.0
*/