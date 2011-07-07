package com.rimproject.andsensor;

public class ContextDetector {
	public static double probabilityOfSleeping() {
		final double timeWeighting = 0.4;
		double timeScore = 1.0; //TODO: get score from sensor analyzer
		double timeFactor = timeWeighting * timeScore;
		
		final double lightWeighting = 0.2;
		double lightScore = 1.0; //TODO: get score from sensor analyzer
		double lightFactor = lightWeighting * lightScore;
		
		final double deviceIsChargingWeighting = 0.1;
		double deviceIsChargingScore = 1.0; //TODO: get score from sensor analyzer
		double deviceIsChargingFactor = deviceIsChargingWeighting * deviceIsChargingScore;
		
		final double stationaryWeighting = 0.25;
		double stationaryScore = 1.0; //TODO: get score from sensor analyzer
		double stationaryFactor = stationaryWeighting * stationaryScore;
		
		final double deviceUseWeighting = 0.05;
		double deviceUseScore = 1.0; //TODO: get score from sensor analyzer
		double deviceUseFactor = deviceUseWeighting * deviceUseScore;
		
		return timeFactor + lightFactor + deviceIsChargingFactor + stationaryFactor + deviceUseFactor;
	}
}
