package com.rimproject.andsensor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

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