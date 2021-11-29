package com.example.TimeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Time {
	public String convertTimezone(Date d,String timezone) throws ParseException
	{
		 SimpleDateFormat formatter = new SimpleDateFormat("MMM dd hh:mm:ss z yyyy");
		 TimeZone t= TimeZone.getTimeZone(timezone); //adding timeZone to the formatter
		 formatter.setTimeZone(t);
		 String convertedDate= formatter.format(d); //would change the timeZone to the one specified earlier
		 System.out.println(t+" ......... "+convertedDate);
		 return convertedDate;
		
	}
	public long convertToEpoch(Date d)
	{
		return d.getTime(); //converts the given date to epoch
	}
	public boolean checkDST(String timezone) throws ParseException
	{

            	SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
            	Date d= formatter.parse("Fri Jul 16 07:02:05 IST 2021"); //using a date which would fall in DST enabled time
            	return TimeZone.getTimeZone(timezone).inDaylightTime(d); //would check if dst is enabled for this date for the specified timezone
            
		
	}
	public String[] getZones()
	{
		return TimeZone.getAvailableIDs(); //get all the applicable timeZones for the dropdown
	}
}
