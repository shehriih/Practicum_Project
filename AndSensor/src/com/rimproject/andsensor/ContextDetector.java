package com.rimproject.andsensor;

import com.rimproject.activities.DeviceStatus;
import android.hardware.SensorManager;

public class ContextDetector {
	public static double probabilityOfSleeping() {
		DeviceStatus ds = new DeviceStatus();
		int durationToConsider = 60;
		
		final double timeWeighting = 0.4;
		double timeScore = 1.0; //TODO: get score from sensor analyzer
		double timeFactor = timeWeighting * timeScore;
		
		final double lightWeighting = 0.2;
		double lightScore = 0.0;
		
		double lightValue = ds.checkLightLevel(durationToConsider);
		
		/*LIGHT_CLOUDY
		LIGHT_FULLMOON
		LIGHT_NO_MOON
		LIGHT_OVERCAST
		LIGHT_SHADE
		LIGHT_SUNLIGHT
		LIGHT_SUNLIGHT_MAX
		LIGHT_SUNRISE*/
		
		if (lightValue == LIGHT_CLOUDY) {
			lightScore = 1.0;
		}
		
		
		
		double lightFactor = lightWeighting * lightScore;
		
		final double deviceIsChargingWeighting = 0.1;
		double deviceIsChargingScore = ds.isDeviceCharging() ? 1.0 : 0.0;
		double deviceIsChargingFactor = deviceIsChargingWeighting * deviceIsChargingScore;
		
		final double stationaryWeighting = 0.25;
		double stationaryScore = ds.isStationary(durationToConsider) ? 1.0 : 0.0;
		double stationaryFactor = stationaryWeighting * stationaryScore;
		
		final double deviceUseWeighting = 0.05;
		double deviceUseScore = -1.0;
		if (ds.isPassive(durationToConsider)) {
			deviceUseScore = 0.7;
		} else if (ds.isActive(durationToConsider)) {
			deviceUseScore = 0.0;
		} else {
			deviceUseScore = 1.0;
		}
		
		double deviceUseFactor = deviceUseWeighting * deviceUseScore;
		
		return timeFactor + lightFactor + deviceIsChargingFactor + stationaryFactor + deviceUseFactor;
	}
}
