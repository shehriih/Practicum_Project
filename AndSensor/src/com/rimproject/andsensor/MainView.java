package com.rimproject.andsensor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainView extends Activity implements OnClickListener {
	Button myButton;
	boolean isLogging;
	LoggerManager logger;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.main);
    	
    	isLogging = false;
    	
    	myButton = (Button) findViewById(R.id.button1);
    	myButton.setText(R.string.start);
    	myButton.setOnClickListener(this);
    	
    	
    	this.logger = new LoggerManager();
    }
    
    public void onClick(View v) {
    
    		isLogging = !isLogging;
        	if(isLogging) {
        		myButton.setText(R.string.stop);
        		this.logger.initiateAllLogging();
        	} else {
        		myButton.setText(R.string.start);
        		this.logger.terminateAllLogging();
        	}
    }
    
}