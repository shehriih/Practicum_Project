package com.rimproject.fileio;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.os.Environment;
import android.util.Log;

import com.rimproject.logreader.BasicLogReading;

public class FileLoggingIO<T extends BasicLogReading>
{
	public static final String  DIR="LifeLogger"; // constant for the application main folder that will be created
	 											 // it would be in the sdcard folder
	
	private static final String TXT=".txt";       // readable file end with .txt
	/*
	 * it will form the file name based on contentType_date
	 * example light_23-06-2011
	 */
    public String getFileName(String contentType, Date date)
	 {
	    	SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
	    	String dateString;
			if(date ==null)
				 dateString = sdf.format(Calendar.getInstance().getTime());
			else
				 dateString = sdf.format(date.getTime());
			
			String fileName = contentType+"_"+dateString;
			return fileName;
	 }
	    
	/*
	 * Write to a readable txt file for debugging purpose.
     * @contentType : the name of the sensor or activity that will be used to form the file name
	 * @reading : the object to be serialized
	 */
	 public void writeToTXTLogFile(String contentType , BasicLogReading reading )
	 {
	     BufferedWriter bw = null;
	         
	         try
	         {
	        	Log.v("Logger",Environment.getExternalStorageState()); 
	         	File sdCardDir = Environment.getExternalStorageDirectory();
	         	File dir = new File (sdCardDir.getAbsolutePath() + "/"+DIR);
	         	dir.mkdirs();
	            File file = new File(dir,getFileName(contentType,null)+TXT);
	            
	         	bw = new BufferedWriter(new FileWriter(file,true));
	            bw.append(reading.toString());
	            
	         }
	         catch(IOException e)
	         {
	         	e.printStackTrace();
	         }
	        finally
	         {
	         	try
	         	{
	         		if (bw !=null)
	         			bw.close();
	         	}
	         	  catch(IOException e)
	               {
	               	e.printStackTrace();
	               }
	         }
	    }

	 
	 /*
	  * test method that dump string data to a text named "contentType"
	  */
	 public void write2test(String contentType , String reading )
	 {
	     BufferedWriter bw = null;
	         
	         try
	         {
	        	Log.v("Logger",Environment.getExternalStorageState()); 
	         	File sdCardDir = Environment.getExternalStorageDirectory();
	         	File dir = new File (sdCardDir.getAbsolutePath() + "/"+DIR);
	         	dir.mkdirs();
	            File file = new File(dir,getFileName(contentType,null)+TXT);
	            
	         	bw = new BufferedWriter(new FileWriter(file,true));
	            bw.append(reading.toString());
	            
	         }
	         catch(IOException e)
	         {
	         	e.printStackTrace();
	         }
	        finally
	         {
	         	try
	         	{
	         		if (bw !=null)
	         			bw.close();
	         	}
	         	  catch(IOException e)
	               {
	               	e.printStackTrace();
	               }
	         }
	    }

		

		
		 
		 
		 /*
		  * Reading from a file
		  * @contentType :  is sensor name or activity, will be used with date to find the file
		  * @reading  : empty object for the that type of sensor , e.g new AccelerometerReading()
		  * @date  : the date for the file u want to get, if null , it will get today's date
		  * @dateTimeFrom  : the start datetime stamp for data collection period (must supply the correct time)
		  * @dateTimeTo    : the end datetime stamp for data collection period   (must supply the correct time)
		  */
		 public HashMap<Date,List<T>> readFromTXTLogFile(String contentType , BasicLogReading readingObj, Date date,Date dateTimeFrom, Date dateTimeTo)
		 {
			 HashMap<Date,List<T>> map = new HashMap<Date,List<T>>();
			 List<T> list = new ArrayList<T>();
			 
			
			 try{
				 	
				   File sdCardDir = Environment.getExternalStorageDirectory();
		           File dir = new File (sdCardDir.getAbsolutePath() + "/"+DIR);
		           dir.mkdirs();
		           File file = new File(dir,getFileName(contentType,date)+TXT);
			      
			       FileInputStream fileIS = new FileInputStream(file);
				 
				   BufferedReader buf = new BufferedReader(new InputStreamReader(fileIS));
				 
				   String readString = new String();
				 
				  
				  
				   while((readString = buf.readLine())!= null){
					   
					 
					  @SuppressWarnings("unchecked")
					T newObj =(T) readingObj.parseObjFromString(readString); 
					  Date timeStamp= newObj.getDateTimeStamp();
					 
					  if(timeStamp.equals(dateTimeFrom) || timeStamp.after(dateTimeFrom))
					  {
						  if( !dateTimeFrom.equals(dateTimeTo) && timeStamp.after(dateTimeTo))
							  break;
						  
						  if(map.get(timeStamp)==null)
						  {
							  list = new ArrayList<T>();
							  map.put(timeStamp, list);
						  }
						  list = map.get(timeStamp);
						  list.add(newObj);
					      map.put(timeStamp,list );
						  
						  
					      Log.d("line: ", newObj.toString());

					  }
				 
				   }
				   
				} catch (FileNotFoundException e) {
				 
				   e.printStackTrace();
				 
				} catch (IOException e){
				 
				   e.printStackTrace();
				 
				}
				
				return map;
		 }
		 
		 public HashMap<Date,List<T>> readFromTXTLogFile(String contentType , BasicLogReading readingObj, Date date)
		 {
			 HashMap<Date,List<T>> map = new HashMap<Date,List<T>>();
			 List<T> list = new ArrayList<T>();
			 
			
			 try{
				 	
				   File sdCardDir = Environment.getExternalStorageDirectory();
		           File dir = new File (sdCardDir.getAbsolutePath() + "/"+DIR);
		           dir.mkdirs();
		           File file = new File(dir,getFileName(contentType,date)+TXT);
			     
			       FileInputStream fileIS = new FileInputStream(file);
				 
				   BufferedReader buf = new BufferedReader(new InputStreamReader(fileIS));
				 
				   String readString = null;
				   String tempString;
				  
				  
				   while((tempString = buf.readLine())!= null){
					   
					  readString = tempString;
				   }
				   
				   String lastLine = readString;
					  @SuppressWarnings("unchecked")
					T newObj =(T) readingObj.parseObjFromString(lastLine); 
					  Date timeStamp= newObj.getDateTimeStamp();
					  
					  if(map.get(timeStamp)==null){
							  list = new ArrayList<T>();
							  map.put(timeStamp, list);
					  }
					  list = map.get(timeStamp);
					  list.add(newObj);
				      map.put(timeStamp,list );
						  
				      Log.d("line: ", newObj.toString());
				 
				   
				} catch (FileNotFoundException e) {
				 
				   e.printStackTrace();
				 
				} catch (IOException e){
				 
				   e.printStackTrace();
				 
				}
				
				return map;
		 }

}
