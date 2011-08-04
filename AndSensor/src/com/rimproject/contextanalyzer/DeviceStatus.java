package com.rimproject.contextanalyzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.rimproject.fileio.FileLoggingIO;
import com.rimproject.logger.*;
import com.rimproject.logreader.AccelerometerReading;
import com.rimproject.logreader.LightReading;
import com.rimproject.logreader.LocationGPSReading;
import com.rimproject.logreader.WifiReading;
import com.rimproject.main.AndSensor;

public class DeviceStatus {
	
	static int batteryCharging = 0;
	
	static HashMap<String,ArrayList<String>> definedWifiLocations = new HashMap<String,ArrayList<String>>();
	
	static // static Initialization block to populate the predefined set of mac addresses for Home location
	{
		ArrayList<String> wifiMACAddresses = new ArrayList<String>();
		//wifiMACAddresses.add("c4:3d:c7:aa:22:3a"); // Ibrahim Home Wifi
		wifiMACAddresses.add("f8:1e:df:fc:4e:0d"); // Chris Home WiFi
		//wifiMACAddresses.add("00:0f:7d:0a:7a:51"); // CMU bld 19
		//wifiMACAddresses.add("00:0f:7d:37:99:51"); // CMU bld 19
		//wifiMACAddresses.add("00:0f:7d:37:99:51"); // CMU bld 19
		definedWifiLocations.put("HOME", wifiMACAddresses);
	}
	
	/* Checks whether the device is within the home wifi range
	 * @duration: Time in seconds for which the sensor reading will be observed
	 */
	public boolean isWithinHomeWifiRange(int duration)
	{
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, -duration);
		Date d1 = cal.getTime();
	    Date d2 = Calendar.getInstance().getTime();

	    FileLoggingIO<WifiReading> fio = new FileLoggingIO<WifiReading>();
        HashMap<Date,List<WifiReading>> map = fio.readFromTXTLogFile(NearbyWifiLogger.SENSOR_NAME, new WifiReading(), null,d1,d2);
        
        Set<Date> es = map.keySet();
        TreeSet<Date> ts = new TreeSet<Date>(es);
        
	
		for (Date key : ts) {
			for (WifiReading reading: map.get(key)) {
				 String macAddress = reading.getBSSID();
				 if (definedWifiLocations.get("HOME").contains(macAddress))
					 return true;
			}
		}

		return false;
	}
	
	/* Checks if the device is idle
	 * @duration: The time in seconds for which each sensor reading will be observed
	 * 
	 */
	public boolean isIdle(int duration){
		boolean result = false;
		if(!isDeviceInUse(duration) && isStationary(duration)){
			result = true;
		}
		return result;
	}
	
	/*Checks if there's any active phone call.
	 * @duration : The time in seconds for which TelephonyManager reading will be observed
	 * 
	 * 
	 */
	public boolean isActive(int duration){
		boolean result = false;
		TelephonyManager telephonyManager = (TelephonyManager) AndSensor.getContext().getSystemService(Context.TELEPHONY_SERVICE);
		if(telephonyManager.getCallState() == TelephonyManager.CALL_STATE_IDLE){
			result = false;
		}
		else if(telephonyManager.getCallState() == TelephonyManager.CALL_STATE_OFFHOOK){
			result = true;
		}
		return result;
	}
	
	/*Checks if any music is being played on the device.
	 * @duration : Time in seconds for which the AudioManager readings will be observed
	 * 
	 */
	
	public boolean isPassive(int duration){
		boolean result = false;
		AudioManager audioManager = (AudioManager) AndSensor.getContext().getSystemService(Context.AUDIO_SERVICE);
		if(audioManager.isMusicActive()){
			result = true;
		}
		return result;
	}
	
	/*Checks if the device is in use
	 * This function calls isActive and isPassive and returns a boolean
	 * based on the readings of each function.
	 * @duration : Time in seconds to be passed for isActive and isPassive
	 * 
	 */
	public boolean isDeviceInUse(int duration){
		boolean result = false;
		if(isActive(duration) || isPassive(duration)) {
			result = true;
		}
		return result;
	}
	
	/* Checks if the device is stationary by observe accelerometer readings
	 * @duration : Time in seconds for which accelerometer readings will be observed
	 */
	public boolean isStationary(int duration){
		boolean result = false;
		double accelerometerActivityLevel = checkAccelerometerActivityLevel(duration);
		if((accelerometerActivityLevel > SensorConstants.MIN_ACCELEROMETER_STATIONARY_LEVEL 
		  && accelerometerActivityLevel < SensorConstants.MAX_ACCELEROMETER_STATIONARY_LEVEL)  
		  && !isLocationChanged(duration)
		  ){
			result = true;
		}
		return result;
	}
	
	
	/* Checks if the user location is changed.  
	 * Observes GPS, WIFI, Bluetooth and Network triangulation information
	 * @duration : Time in seconds for which the required sensor reading will be observed
	 * 
	 */
	public boolean isLocationChanged(int duration){
		boolean result = false;
		if(isGPSAvailable()){
			if(checkIfGPSChanging(duration) > SensorConstants.GPS_STATIONARY){
				result = true;
			}
		}
		if(isWIFIAvailable()){
			if(checkIfWIFIChanging(duration) > SensorConstants.ACCEPTABLE_WIFI_CHANGE_VALUE) {
				result = true;
			}
		}
		else if(isBluetoothAvailable()){
			//TODO : Add logic to check if Bluetooth location changed
		}
		else if(isNetworkAvailable()){
				//TODO: Add logic to check if Network Triangulation changed
		}
		return result;
	}
	
		
	/* Checks if the device is charging
	 * 
	 */
	public int isDeviceCharging(){

		AndSensor.getContext().registerReceiver(broadCastReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
			
			if(batteryCharging == 0){
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return batteryCharging;
			}
			else{
				return batteryCharging;
			}
			
		}
	
		BroadcastReceiver broadCastReceiver = new BroadcastReceiver(){
			//int batteryCharging = 0;
			@Override
			public void onReceive(Context context, Intent intent) {
				int batteryStatus = intent.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN);
				if(intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)){
					batteryCharging = batteryStatus;
					Log.v("InIF TestBat", batteryStatus+"");
				}
				
			}

		};
		
		
	/*Checks the light level in the surrounding environment
	 * @duration : Time in seconds for which the light sensor readings will be observed
	 * 	
	 */
	public double checkLightLevel(int duration){
		double result = -1.0;
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, -duration);
		Date d1 = cal.getTime();
	    Date d2 = Calendar.getInstance().getTime();

	    FileLoggingIO<LightReading> fio = new FileLoggingIO<LightReading>();
        HashMap<Date,List<LightReading>> map = fio.readFromTXTLogFile(LightSensorLogger.SENSOR_NAME, new LightReading(), null,d1,d2);
        
        //=========== MEDIAN ===========
//        Collection<List<LightReading>> c = map.values();
//        if (c.size() == 0) {
//        	//no readings
//        	return 999999.9;
//        }
//        
//        //obtain an Iterator for Collection
//        Iterator<List<LightReading>> itr = c.iterator();
//        
//        ArrayList<LightReading> lightReadings = new ArrayList<LightReading>();
//        //iterate through HashMap values iterator
//        while(itr.hasNext()) {
//        	List<LightReading> readings = (List<LightReading>)itr.next();
//        	for (LightReading lightReading : readings) {
//				lightReadings.add(lightReading);
//			}
//      	}
//        
//        Collections.sort(lightReadings);
//        LightReading middleReading = lightReadings.get(lightReadings.size()/2);
//        result = middleReading.getLightValue();
      //=========== END MEDIAN ===========
        
      //=========== AVERAGE ===========
        Set<Date> es = map.keySet();
        TreeSet<Date> ts = new TreeSet<Date>(es);
        
		int numberOfReadings = 0;
		double accumulator = 0.0;
		for (Date key : ts) {
			for (LightReading reading: map.get(key)) {
				numberOfReadings++;
				 accumulator += reading.getLightValue();
			}
		}
		result = accumulator / numberOfReadings; //average of all readings
        //=========== END AVERAGE ===========
		System.out.println("!!!!!" + numberOfReadings);
		return result;
	}
	
	/*Checks the accelerometer activity level
	 * @duration : Time in seconds to consider for the accelerometer readings will be observed
	 * 
	 */
	public double checkAccelerometerActivityLevel(int duration){
		double result = 0;
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, -duration);
		Date d1 = cal.getTime();
	    Date d2 = Calendar.getInstance().getTime();

	    FileLoggingIO<AccelerometerReading> fio = new FileLoggingIO<AccelerometerReading>();
        HashMap<Date,List<AccelerometerReading>> map = fio.readFromTXTLogFile(AccelerometerLogger.SENSOR_NAME, new AccelerometerReading(), null,d1,d2);
        
        Set<Date> es = map.keySet();
        TreeSet<Date> ts = new TreeSet<Date>(es);
        
		int numberOfReadings = 0;
		double accumulator = 0.0;
		for (Date key : ts) {
			for (AccelerometerReading reading: map.get(key)) {
				numberOfReadings++;
				 accumulator += reading.getACCVector();
			}
		}
		result = accumulator / numberOfReadings; //average of all readings

		return result;
	}
	
	/* Checks if GPS is available
	 * 
	 */
	public boolean isGPSAvailable(){
		boolean result = false;
		LocationManager locationManager = (LocationManager) AndSensor.getContext().getSystemService(Context.LOCATION_SERVICE); 
		if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			result = true;
		}
		return result;
	}
	
	/*Checks if network triangulation is available
	 * 
	 */
	public boolean isNetworkAvailable(){
		boolean result = false;
		ConnectivityManager connectivity = (ConnectivityManager) AndSensor.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
		if(networkInfo.isAvailable()){
			result = true;
		}
		return result;				
	}
	
	/*Checks if WiFi on the device is turned on and available
	 * 
	 */
	public boolean isWIFIAvailable(){
		boolean result = false;
		ConnectivityManager connectivity = (ConnectivityManager) AndSensor.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiInfo = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(wifiInfo.isAvailable()){
			result = true;
		}
		return result;
	}
	
	/*Checks if Bluetooth on the device is turned on and available
	 * 
	 */
	public boolean isBluetoothAvailable(){
		boolean result = false;
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if(bluetoothAdapter == null){
			result = false;
		}
		else{
			if(bluetoothAdapter.isEnabled()){
				result = true;
			}
		}
		return result;
	}
	
	/* Checks if GPS information is changing
	 * @duration : Time in seconds for which GPS readings will be observed
	 */
	public double checkIfGPSChanging(int duration){
		double maxDistance = 0.0;
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, -duration);
		Date d1 = cal.getTime();
	    Date d2 = Calendar.getInstance().getTime();

	    FileLoggingIO<LocationGPSReading> fio = new FileLoggingIO<LocationGPSReading>();
        HashMap<Date,List<LocationGPSReading>> map = fio.readFromTXTLogFile(LocationGPSLogger.SENSOR_NAME, new LocationGPSReading(), null,d1,d2);
        
        //String temp = this.getLocation(map);
        
        Collection<List<LocationGPSReading>> locationLists = map.values();
        
        
		ArrayList<Location> locations = new ArrayList<Location>();
		for (List<LocationGPSReading> list : locationLists) {
			for (LocationGPSReading locationGPSReading : list) {
				Location location = new Location("GPS");
				location.setTime(locationGPSReading.getLocationRecordingTime().getTime());
				location.setLongitude(locationGPSReading.getLongitude());
				location.setLatitude(locationGPSReading.getLatitude());
				location.setAltitude(locationGPSReading.getAltitude());
				location.setAccuracy(locationGPSReading.getAccuracy());
				locations.add(location);
			}
		}
		
		for (Location location: locations) {
			for (Location location2 : locations) {
				double newDistance = location.distanceTo(location2);
				if (newDistance > maxDistance) {
					maxDistance = newDistance;
				}
			}
		}
		
		return maxDistance;
	}
	
	/* Checks if WiFi information is changing
	 * @duration : Time in seconds for which WiFi readings will be observed
	 * 
	 */
	public double checkIfWIFIChanging(int duration){
		//increases the result by 0.1 per change 
		//(number of wifi networks that used to be here that are now gone, or new ones that have appeared)
		
		int result = 0;
		
		
		// Getting the set of non duplicat wifi mac addresses within the given duration
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, -duration);
		Date d1 = cal.getTime();
	    Date d2 = Calendar.getInstance().getTime();

	    //FIXME: for some reason this doesn't get any wifi networks (ie map is empty)
	    FileLoggingIO<WifiReading> fio = new FileLoggingIO<WifiReading>();
        HashMap<Date,List<WifiReading>> map = fio.readFromTXTLogFile(NearbyWifiLogger.SENSOR_NAME, new WifiReading(), null,d1,d2);
        Collection<List<WifiReading>> wifiLists = map.values();
        
        Set<String> nonDublicatMacAddressesWithinDurationSet = new TreeSet<String>();
        
        for(List<WifiReading> wifiList : wifiLists)
        {
        	for(WifiReading wifiReading:wifiList)
        	{
        		nonDublicatMacAddressesWithinDurationSet.add(wifiReading.getBSSID());
        	}
        }
        

		// Getting the set of non duplicat wifi mac addresses within a previous  duration of the given one
        // e.g. if we are getting the data of last 60 sec , then we will compare it with the data of the 
        //60 sec before it.
		Calendar cal2 = Calendar.getInstance();
		
		cal2.setTime(d1);
		cal2.add(Calendar.SECOND, -duration);
		Date d1PreDuration = cal2.getTime();
		Date d2PreDuration = d1;
	   

	    //FIXME: for some reason this doesn't get any wifi networks (ie map is empty)
	    FileLoggingIO<WifiReading> fioPreDuration = new FileLoggingIO<WifiReading>();
        HashMap<Date,List<WifiReading>> mapPreDuration = fioPreDuration.readFromTXTLogFile(NearbyWifiLogger.SENSOR_NAME, new WifiReading(), null,d1PreDuration,d2PreDuration);
        Collection<List<WifiReading>> wifiListsPreDuration = mapPreDuration.values();
        
        Set<String> nonDublicatMacAddressesWithinPreDurationSet = new TreeSet<String>();
        
        for(List<WifiReading> wifiListPreDuration : wifiListsPreDuration)
        {
        	for(WifiReading wifiReadingPreDuration:wifiListPreDuration)
        	{
        		nonDublicatMacAddressesWithinPreDurationSet.add(wifiReadingPreDuration.getBSSID());
        	}
        }
        
       
        
		for(String mac:nonDublicatMacAddressesWithinDurationSet)
		{
			
				if (!nonDublicatMacAddressesWithinPreDurationSet.contains(mac))
					result+=0.1;
			
		}
		
		for(String mac2:nonDublicatMacAddressesWithinPreDurationSet)
		{
			
				if (!nonDublicatMacAddressesWithinDurationSet.contains(mac2))
					result+=0.1;
			
		}
        
        
		
		
		return result/10.0;
	}
	
	
	/* Translates geo-coordinates to physical address 
	 * and then uses the Whitepages API to reverse code the 
	 * translated address to a business name
	 * 
	 * @mapG : reads the LocationGPSReading file and returns GPS data as hashmap
	 * 
	 */
	public String getLocation(HashMap<Date,List<LocationGPSReading>> mapG){
		String[] location = new String[3];
		String street, city, state;
		String businessName = "";
		
        Collection<List<LocationGPSReading>> locationLists = mapG.values();

		
        Iterator<List<LocationGPSReading>> locationListsIterator = locationLists.iterator();
        List<LocationGPSReading> locationGPSReadingList = locationListsIterator.next();
        Iterator<LocationGPSReading> locationGPSReadingListIterator = locationGPSReadingList.iterator();
        LocationGPSReading locationGPSReading = locationGPSReadingListIterator.next();

        double latitude = (null != locationGPSReading)?locationGPSReading.getLatitude():0.0;
        double longitude = (null != locationGPSReading)?locationGPSReading.getLongitude():0.0;
        Log.d("GPS LATITUDE", "Latitude :"+latitude);
        Log.d("GPS LONGITUDE", "longitude : "+ longitude);
		if(longitude != 0.0 && latitude != 0.0){
			location = translateCoordinatesToAddress(latitude,longitude);
			street = location[0];
			city = location[1];
			state = location[2];
			businessName = fetchBusinessName(street,city,state);
		}
		else{
			businessName = "Location Unknown";
		}
		
		return businessName;
		
	}
	
	/* Uses geocoder to translate geo-coordinates to a physical addresss
	 * @latitude : latitude of the location
	 * @longitude : longitude of the location
	 * 
	 */
	public String[] translateCoordinatesToAddress(double latitude, double longitude){
		String[] location = new String[3];
		try{
			Geocoder geocoder = new Geocoder(AndSensor.getContext());
			List<Address> address = new ArrayList<Address>();
			address = geocoder.getFromLocation(latitude, longitude, 1);
			
			Address geocoderAddress = address.get(0);
			location[0] = geocoderAddress.getAddressLine(0);
			location[1] = geocoderAddress.getLocality();
			location[2] = geocoderAddress.getAdminArea();
			
		}catch(IOException e){
			e.printStackTrace();
		}
		return location;
	}
	
	/* Converts physical address to business name
	 * @street : street address obtained by a call translateCoordinatesToAddress function
	 * @city : city obtained by a call translateCoordinatesToAddress function
	 * @state : state obtained by a call translateCoordinatesToAddress function
	 * 
	 */
	public String fetchBusinessName(String street, String city, String state){
		String businessName = null;
		street = street.replace(" ", "%20");
		city = city.replace(" ", "%20");
		String constructUrl = "http://api.whitepages.com/reverse_address/1.0/?street="+street+";city="+city+";state="+state+";api_key=4d2234c06b2c4c279a67accd0324d977";
		try {
			URL url = new URL(constructUrl);
			HttpURLConnection httpUrlConnection = (HttpURLConnection)url.openConnection();
			httpUrlConnection.connect();
			InputStreamReader inputStreamReader = new InputStreamReader((InputStream) httpUrlConnection.getContent());
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String content;
			do{
				//<wp:businessname>Starlight High School</wp:businessname>
				content = bufferedReader.readLine();
				if(content.toString().contains("<wp:businessname>")){
					businessName = content.toString();
					break;
				}
				//System.out.println(content);
			}while(null != content);
			businessName = businessName.replace("<wp:businessname>", "");
			businessName = businessName.replace("</wp:businessname>", "");
			businessName = businessName.trim();
			Log.d("BUSINESS NAME", "Business Name:"+businessName);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return businessName;
	}
	
}
