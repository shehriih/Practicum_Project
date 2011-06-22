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
    	isLogging = false;
    	myButton = new Button(this);
    	myButton.setText("Initiate logging");
    	myButton.setOnClickListener(this);
    	setContentView(myButton);
    	
    	this.logger = new LoggerManager();
    }
    
    public void onClick(View v) {
    	isLogging = !isLogging;
    	if(isLogging) {
    		myButton.setText("Terminate logging");
    		this.logger.initiateAllLogging();
    	} else {
    		myButton.setText("Initiate logging");
    		this.logger.terminateAllLogging();
    	}
    }
}