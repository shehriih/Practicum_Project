package com.rimproject.main;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.rimproject.andsensor.R;
import com.rimproject.fileio.FileLoggingIO;
import com.rimproject.logger.ContextLogger;
import com.rimproject.logger.LoggerManager;
import com.rimproject.logreader.BasicLogReading;
import com.rimproject.logreader.ContextReading;


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
		
		if (v.getId() == refresh.getId()) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, -5);
			Date d1 = cal.getTime();
		    Date d2 = Calendar.getInstance().getTime();

    	    FileLoggingIO<ContextReading> fio = new FileLoggingIO<ContextReading>();
            HashMap<Date,List<ContextReading>> map = fio.readFromTXTLogFile(ContextLogger.SENSOR_NAME, new ContextReading(), null,d1,d2);
    		String outputString = "";
    		
    		
    		Set<Date> es = map.keySet();
    		TreeSet<Date> ts = new TreeSet<Date>(es);
    		String lastReading="";
    		for (Date key : ts) {
    			
				for (ContextReading cr: map.get(key)) {

					lastReading = BasicLogReading.getStringFormattedDateTime(key)+"--"+cr.getProbability()+"--"+ cr.getContextName();
				}
				
			}
			
    		outputView.setText(lastReading);

			
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