package com.rimproject.contextanalyzer;

import java.util.Calendar;



public class ContextDetector {
	
	/*Computes the probability of sleep based on various sensor readings
	 * @durationToConsider : The time in seconds for which each sensor reading will be observed
	 * 
	 */
	public static double probabilityOfSleeping(int durationToConsider) {
		DeviceStatus deviceStatus = new DeviceStatus();

		final double timeWeighting = 0.4;
		double timeScore = 0.0;

		int hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		
		//zero score if hour is not between 1pm and 7pm. 
		//increasing score as time approaches middle of night (Defined as 4am)
		switch (hourOfDay) {
		case 19:
		case 13:
			timeScore = 0.1;
			break;
		case 20:
		case 12:
			timeScore = 0.2;
			break;
		case 21:
		case 11:
			timeScore = 0.3;
			break;
		case 22:
		case 10:
			timeScore = 0.4;
			break;
		case 23:
		case 9:
			timeScore = 0.5;
			break;
		case 24:
		case 8:
			timeScore = 0.6;
			break;
		case 1:
		case 7:
			timeScore = 0.7;
			break;
		case 2:
		case 6:
			timeScore = 0.8;
			break;
		case 3:
		case 5:
			timeScore = 0.9;
			break;
		case 4:
			timeScore = 1.0;
			break;
		default:
			break;
		}


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
		
		double deviceIsChargingScore = deviceStatus.isDeviceCharging() != 0 ? 1.0 : 0.0;
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
		
		System.out.println("!!!!!!!" 
				+ "time: [" + timeFactor + "], "  
				+ "light: [" + lightFactor + "], "
				+ "charging: [" + deviceIsChargingFactor + "], "
				+ "stationary: [" + stationaryFactor + "], "
				+ "in-use: [" + deviceUseFactor + "], "
				);
		return timeFactor + lightFactor + deviceIsChargingFactor + stationaryFactor + deviceUseFactor;
	}
	
	
}
