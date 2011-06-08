package com.rimproject.andsensor;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class Test extends TimerTask {

	
	private Timer loggingDuerationTimer; // Timer 2
	private Timer delayBetweenLoggingTmer;// Timer 1
	private long delayBetweenLogging;
	private long loggingDuration;
	
	

	public Test()
	{
		super();
		
		
		setDelayBetweenLogging(10*1000);
		setLoggingDuration(3 * 1000);
		delayBetweenLoggingTmer = new Timer();
		delayBetweenLoggingTmer.scheduleAtFixedRate(this,0 , getDelayBetweenLogging());
	}
	
	
	class DataLogger extends TimerTask
	{
		long loggingDuration;
		public DataLogger(long loggingDuration)
		{
			this.loggingDuration = loggingDuration;
		}
		
		public void run()
		{
			// actuatl sensor logging
			System.out.println("Logging @"+Calendar.getInstance().getTime());
		}
		
	}
	


	
	public Timer getLoggingDuerationTimer() {
		return loggingDuerationTimer;
	}


	public void setLoggingDuerationTimer(Timer loggingDuerationTimer) {
		this.loggingDuerationTimer = loggingDuerationTimer;
	}



	public Timer getDelayBetweenLoggingTmer() {
		return delayBetweenLoggingTmer;
	}

	public void setDelayBetweenLoggingTmer(Timer delayBetweenLoggingTmer) {
		this.delayBetweenLoggingTmer = delayBetweenLoggingTmer;
	}

	public long getDelayBetweenLogging() {
		return delayBetweenLogging;
	}

	public void setDelayBetweenLogging(long delayBetweenLogging) {
		this.delayBetweenLogging=delayBetweenLogging;
	}

	public long getLoggingDuration() {
		return loggingDuration;
	}



	public void setLoggingDuration(long loggingDuration) {
		this.loggingDuration = loggingDuration;
	}


	
	
	@Override
	public void run() {
		
		System.out.println(Calendar.getInstance().getTime());
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		new Test();
	}

}
