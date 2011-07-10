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
    		
    	   /* Date d1 = BasicLogReading.getDateFromString("07-09-2011 20:42:23");
		    Date d2 = BasicLogReading.getDateFromString("07-09-2011 20:42:28");

    	    FileLoggingIO<AccelerometerReading> fio = new FileLoggingIO<AccelerometerReading>();
            HashMap<Date,List<AccelerometerReading>> map = fio.readFromTXTLogFile(AccelerometerLogger.SENSOR_NAME, new AccelerometerReading(), null,d1,d2);
    		String hm = map.toString();
    		fio.write2test("test", hm);*/
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