package com.rimproject.contexts;

import com.rimproject.activities.DeviceStatus;
import com.rimproject.activities.SensorConstants;;

public class ContextDetector {
	public static double probabilityOfSleeping(int durationToConsider) {
		DeviceStatus deviceStatus = new DeviceStatus();
		
		final double timeWeighting = 0.4;
		double timeScore = 1.0; //TODO: get score from sensor analyzer
		double timeFactor = timeWeighting * timeScore;
		
		final double lightWeighting = 0.2;
		double lightScore = 1.0;
		double lightValue = deviceStatus.checkLightLevel(durationToConsider);
		
		int modifier = 10;
		while(lightScore > 0.0 && lightValue - modifier > 0) {
			lightScore -= 0.1;
			modifier += 30;
		}	
		double lightFactor = lightWeighting * lightScore;
		
		
		final double deviceIsChargingWeighting = 0.1;
		double deviceIsChargingScore = deviceStatus.isDeviceCharging() ? 1.0 : 0.0;
		double deviceIsChargingFactor = deviceIsChargingWeighting * deviceIsChargingScore;
		
		
		final double stationaryWeighting = 0.25;
		double stationaryScore = deviceStatus.isStationary(durationToConsider) ? 1.0 : 0.0;
		double stationaryFactor = stationaryWeighting * stationaryScore;
		
		
		final double deviceUseWeighting = 0.05;
		double deviceUseScore = -1.0;
		if (deviceStatus.isPassive(durationToConsider)) {
			deviceUseScore = 0.7;
		} else if (deviceStatus.isActive(durationToConsider)) {
			deviceUseScore = 0.0;
		} else {
			deviceUseScore = 1.0;
		}
		double deviceUseFactor = deviceUseWeighting * deviceUseScore;
		
		return timeFactor + lightFactor + deviceIsChargingFactor + stationaryFactor + deviceUseFactor;
	}
}
