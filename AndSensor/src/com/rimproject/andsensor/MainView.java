package com.rimproject.andsensor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.rimproject.fileio.FileLoggingIO;
import com.rimproject.logreadings.AccelerometerReading;
import com.rimproject.logreadings.BasicLogReading;
import com.rimproject.logreadings.BluetoothReading;
import com.rimproject.logreadings.GeoLocationReading;
import com.rimproject.logreadings.LightReading;
import com.rimproject.logreadings.WifiReading;

public class MainView extends Activity implements OnClickListener {
	Button toggleLogging;
	boolean isLogging;
	TextView sleepLabel;
	TextView sleepProbability;
	LoggerManager logger;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.main);
    	
    	isLogging = false;
    	
    	toggleLogging = (Button) findViewById(R.id.toggleLogging);
    	toggleLogging.setText(R.string.start);
    	toggleLogging.setOnClickListener(this);
    	
    	sleepLabel = (TextView) findViewById(R.id.sleepLabel);
    	sleepLabel.setText(R.string.sleepLabel);
    	
    	sleepProbability = (TextView) findViewById(R.id.sleepProbability);
    	sleepProbability.setText("needs to be dynamically updated");

    	this.logger = new LoggerManager();
    }
    
    public void onClick(View v) {
             
    	// Debugging code to test that what is loaded in the HashMap is same as what is in the file
    	// it is also useful to demo how to use the read method
    	//-------------------------
    		
    	    // Test Acc
    	   /* Date d1 = BasicLogReading.getDateFromString("07-11-2011 17:02:35");
		    Date d2 = BasicLogReading.getDateFromString("07-11-2011 17:03:00");
		    FileLoggingIO<AccelerometerReading> fioA = new FileLoggingIO<AccelerometerReading>();
            HashMap<Date,List<AccelerometerReading>> map = fioA.readFromTXTLogFile(AccelerometerLogger.SENSOR_NAME, new AccelerometerReading(), null,d1,d2);
    		String hm = map.toString();
    		fioA.write2test("testAcc", hm);
    		
    		//Test Bluetooth
    	    d1 = BasicLogReading.getDateFromString("07-11-2011 17:03:18");
		    d2 = BasicLogReading.getDateFromString("07-11-2011 17:05:03");
		    FileLoggingIO<BluetoothReading> fioB = new FileLoggingIO<BluetoothReading>();
		    HashMap<Date,List<BluetoothReading>> mapB = fioB.readFromTXTLogFile(NearbyBluetoothLogger.SENSOR_NAME, new BluetoothReading(), null,d1,d2);
    		hm = mapB.toString();
    		fioB.write2test("testBlue", hm);
    	   
    		// Test Light 
    		d1 = BasicLogReading.getDateFromString("07-11-2011 17:02:58");
		    d2 = BasicLogReading.getDateFromString("07-11-2011 17:05:59");
		    FileLoggingIO<LightReading> fioL = new FileLoggingIO<LightReading>();
		    HashMap<Date,List<LightReading>> mapL = fioL.readFromTXTLogFile(LightSensorLogger.SENSOR_NAME, new LightReading(), null,d1,d2);
    	    hm = mapL.toString();
    	    fioL.write2test("testLight", hm);
    	   
    	    // Test Wifi
    	    d1 = BasicLogReading.getDateFromString("07-11-2011 17:02:32");
		    d2 = BasicLogReading.getDateFromString("07-11-2011 17:03:58");
		    FileLoggingIO<WifiReading> fioW = new FileLoggingIO<WifiReading>();
		    HashMap<Date,List<WifiReading>> mapW = fioW.readFromTXTLogFile(NearbyWifiLogger.SENSOR_NAME, new WifiReading(), null,d1,d2);
    		hm = mapW.toString();
    		fioW.write2test("testWifi", hm);
    		
    		
    	    // Test GeoLoc
    	    d1 = BasicLogReading.getDateFromString("07-11-2011 17:32:16");
		    d2 = BasicLogReading.getDateFromString("07-11-2011 17:32:16");
		    FileLoggingIO<GeoLocationReading> fioG = new FileLoggingIO<GeoLocationReading>();
		    HashMap<Date,List<GeoLocationReading>> mapG = fioG.readFromTXTLogFile(GeoLocationLogger.SENSOR_NAME, new GeoLocationReading(), null,d1,d2);
    		hm = mapG.toString();
    		fioG.write2test("testGeo", hm);*/
    	//-------------------------
    		
          isLogging = !isLogging;
        	if(isLogging) {
        		toggleLogging.setText(R.string.stop);
        		this.logger.initiateAllLogging();
        	} else {
        		toggleLogging.setText(R.string.start);
        		this.logger.terminateAllLogging();
        		
        	}
    }
    
    
    
}