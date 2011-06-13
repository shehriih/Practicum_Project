package com.rimproject.andsensor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainView extends Activity implements OnClickListener {
	Button myButton;
	boolean isLogging;
	SensorLogger logger;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	isLogging = false;
    	myButton = new Button(this);
    	myButton.setText("Start logging");
    	myButton.setOnClickListener(this);
    	setContentView(myButton);
    	
    	this.logger = new SensorLogger();
    }
    
    public void onClick(View v) {
    	isLogging = !isLogging;
    	if(isLogging) {
    		myButton.setText("Stop logging");
    		this.logger.startAllLogging();
    	} else {
    		myButton.setText("Start logging");
    	}
    }
}