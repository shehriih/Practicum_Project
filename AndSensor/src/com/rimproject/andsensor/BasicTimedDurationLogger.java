package com.rimproject.andsensor;

import java.util.Timer;
import java.util.TimerTask;

/* Example of how this class and its timer's will work:
 * 
 * Timer 1 -> 10 seconds has passed 
 * 		Timer 2 created -> let me know when 3 seconds has passed
 * 		Sensor recording start
 * 			10:55:10 v:11
 * 			10:55:10 v:12
 * 			10:55:11 v:11
 * 			10:55:11 v:10
 * 			10:55:12 v:15
 * 			10:55:12 v:16
 * 			10:55:13 v:11
 * 		Timer 2 -> 3 seconds has passed
 * 		sensor recording stop
 * 
 * Timer 1 -> 10 seconds has passed 
 * 		Timer 2 created -> let me know when 3 seconds has passed
 * 		Sensor recording start
 * 			10:55:20 v:11
 * 			10:55:20 v:12
 * 			10:55:21 v:11
 * 			10:55:21 v:10
 * 			10:55:22 v:15
 * 			10:55:22 v:16
 * 			10:55:23 v:11
 * 		Timer 2 -> 3 seconds has passed
 * 		sensor recording stop
 * 	etc.
 */

public class BasicTimedDurationLogger extends BasicLogger {
	private long loggingDuration;
	private DataLogger dataLogger;
	
	public BasicTimedDurationLogger() {
		super();
		
		this.loggingDuration = 10 * 1000;
	}
	
	public void terminateRepeatedLogging(boolean immidiate) {
		super.terminateRepeatedLogging(immidiate);
		if (immidiate) {
			if(this.dataLogger != null) {
				this.dataLogger.run();
			}
		}
	}
	
	class DataLogger extends TimerTask
	{
		private BasicLogger logger;
		private Timer loggingDurationTimer; // Timer 2
		public DataLogger(BasicLogger logger)
		{
			this.logger = logger;
			this.loggingDurationTimer = new Timer();
			this.loggingDurationTimer.scheduleAtFixedRate(this, loggingDuration, loggingDuration);
			this.logger.startLogging();
		}
		
		public void run()
		{
			this.logger.stopLogging();
			//stop the timer as we don't want timer 2 to repeat
			this.loggingDurationTimer.cancel();
		}
		
	}
	
	public void run() {
		//timer 1
		super.run();
		
		this.dataLogger = new DataLogger(this);
	}
	
	public long getLoggingDuration() {
		return loggingDuration;
	}

	public void setLoggingDuration(long loggingDuration) {
		this.loggingDuration = loggingDuration;
	}
}
