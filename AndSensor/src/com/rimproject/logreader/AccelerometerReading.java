package com.rimproject.logreader;

import java.io.Serializable;
import java.util.Date;

import com.rimproject.logger.BasicLogger;

/*
 * Class to hold any accelerometer reading, it should be used when writing to the file
 * and when parsing the file
 */
public class AccelerometerReading extends BasicLogReading implements Serializable {
	

		/**
	 * 
	 */
	private static final long serialVersionUID = 4378294751034466809L;
		private float x,y,z;

		public AccelerometerReading(Date dateTimeStamp, float x, float y, float z)
		{
			super(dateTimeStamp);
			this.x=x;
			this.y=y;
			this.z=z;
		}
		public AccelerometerReading() {
			super();
			// TODO Auto-generated constructor stub
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
		
		// method to find the Acceleration vector reading
		public double getACCVector()
		{
			return Math.sqrt(getX()*getX() + getY()*getY() + getZ()*getZ() );
		}
		
		
		public AccelerometerReading parseObjFromString (String inputFromLogFile)
		{
			String[] lineArr   = inputFromLogFile.split(BasicLogger.timeStampDel,2);
			
			String stringDate  =lineArr[0];
			Date dateTime = getDateFromString(stringDate);
			
			
			String[] params =  lineArr[1].split(BasicLogger.genericDel); 
			
			AccelerometerReading ar = new AccelerometerReading(dateTime,Float.parseFloat(params[0]),Float.parseFloat(params[1]),Float.parseFloat(params[2]));
			return ar;
		}
		@Override
		public String toString() {
			
			return BasicLogReading.getStringFormattedDateTime(getDateTimeStamp())+BasicLogger.timeStampDel+getX()+BasicLogger.genericDel+getY()+BasicLogger.genericDel+getZ()+"\n";
		}
		
		
	
}
