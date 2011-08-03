package com.rimproject.contextanalyzer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;

import com.rimproject.main.AndSensor;

public class CalendarIO
{
	
    public  List<EventDetail> getEventList(Date d1, Date d2)
    {
    	
    	List<EventDetail> list = new ArrayList<EventDetail>();
    	
        ContentResolver contentResolver = AndSensor.getContext().getContentResolver();
        String contentProvider = "com.android.calendar";
    	 Uri.Builder builder = Uri.parse(String.format("content://%s/instances/when",contentProvider)).buildUpon();
         
          
          ContentUris.appendId(builder, d1.getTime());
          ContentUris.appendId(builder, d2.getTime());
         
          Cursor eventCursor = contentResolver.query(builder.build(),
           new String[] { "title", "begin", "end", "eventLocation"}, "Calendars._id=" + "1",
           null, "startDay ASC, startMinute ASC");
         
          while (eventCursor.moveToNext()) {
           String title = eventCursor.getString(0);
            Date begin = new Date(eventCursor.getLong(1));
            Date end = new Date(eventCursor.getLong(2));
            String eventLocation = eventCursor.getString(3);
            
            list.add(new EventDetail(title, eventLocation, begin, end));
         
           System.out.println("Title: " + title + " Begin: " + begin + " End: " + end +
          "  Location : "+eventLocation );
          }
          return list;
    }
    
    class EventDetail
    {
    	private String title,eventLocation;
    	private Date   begin,end;
    	
    	public EventDetail(String title,String eventLocation, Date begin, Date end)
    	{
    		this.title=title;
    		this.eventLocation = eventLocation;
    		this.begin=begin;
    		this.end=end;
    	}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getEventLocation() {
			return eventLocation;
		}
		public void setEventLocation(String eventLocation) {
			this.eventLocation = eventLocation;
		}
		public Date getBegin() {
			return begin;
		}
		public void setBegin(Date begin) {
			this.begin = begin;
		}
		public Date getEnd() {
			return end;
		}
		public void setEnd(Date end) {
			this.end = end;
		}
    }


}
