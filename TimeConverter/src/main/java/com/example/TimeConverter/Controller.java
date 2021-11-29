package com.example.TimeConverter;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.util.UrlPathHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@RestController
@SpringBootApplication
public class Controller {

	private static final String DATE_FORMAT = "dd/mm/yyyy hh:mm:ss a";
	
	public static void main(String[] args)
	{
		System.setProperty("org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH", "true");
		SpringApplication.run(Controller.class,args);
	}
	//For enabling the controller to point to the specified html page/file
	public void configurePathMatch(PathMatchConfigurer configurer) {
	    UrlPathHelper urlPathHelper = new UrlPathHelper();
	    urlPathHelper.setUrlDecode(false);
	    configurer.setUrlPathHelper(urlPathHelper);
	  }
	//Convert the timeZone
	@RequestMapping(path = "/{timeZone}/{date}/{toTimeZone}", method = RequestMethod.GET)
	public String conversion(@PathVariable String timeZone, @PathVariable String date, @PathVariable String toTimeZone) throws ParseException
	{
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
		Date d=formatter.parse(date);
		Time obj=new Time();
		return obj.convertTimezone(d,toTimeZone);
	}
	//conversion to Epoch
	@RequestMapping(path = "/1/{date}/{timeZone}")
	public long convertToEpoch(@PathVariable String date, @PathVariable String timeZone) throws ParseException
	{
		Time obj=new Time();
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
		Date d=formatter.parse(date);
		return obj.convertToEpoch(d);
	}
	//checking if DST is observed by that timezone
	@RequestMapping(path = "/2/{timeZone}")
	public String checkDst(@PathVariable String timeZone) throws ParseException
	{
		Time obj=new Time();
		for (String tzId : TimeZone.getAvailableIDs()) {
            if (tzId.equals(timeZone))
            {
            	if(obj.checkDST(timeZone))
        		{
        			return "True";
        		}
        		else
        		{
        			return "false";
        		}
            	}
            }
		return "Invalid TimeZone";
 
		
	}
	//get all the applicable timezones
	@RequestMapping(path = "/3")
	public String[] getTimeZone()
	{
		Time obj=new Time();
		return obj.getZones();
	}
    @RequestMapping("/") 
    public ModelAndView welcome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        return modelAndView;
    }  
    @RequestMapping("/epoch") 
    public ModelAndView epoch() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("epoch.html");
        return modelAndView;
    }  
    @RequestMapping("/dst") 
    public ModelAndView dst() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("dst.html");
        return modelAndView;
    }  
}

