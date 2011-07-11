package com.rimproject.andsensor;

import java.util.Calendar;
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
import com.rimproject.logreadings.ContextReading;

public class MainView extends Activity implements OnClickListener {
	Button toggleLogging;
	Button refresh;
	boolean isLogging;
	TextView outputView;
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

		refresh = (Button) findViewById(R.id.refresh);
		refresh.setText(R.string.refresh);
		refresh.setOnClickListener(this);

		outputView = (TextView) findViewById(R.id.output);

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
		if (v.getId() == refresh.getId()) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, -5);
			Date d1 = cal.getTime();
		    Date d2 = Calendar.getInstance().getTime();

    	    FileLoggingIO<ContextReading> fio = new FileLoggingIO<ContextReading>();
            HashMap<Date,List<ContextReading>> map = fio.readFromTXTLogFile(ContextLogger.SENSOR_NAME, new ContextReading(), null,d1,d2);
    		String hm = map.toString();
    		outputView.setText(hm);
			
		} else {
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



}