package com.skava.frameworkutils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import com.framework.reporting.BaseClass;

public class loggerUtils extends BaseClass{
		
	static Logger log = Logger.getLogger("OurLogger");
	
		public static void setLogFile() 
		{
			log.getRootLogger().setLevel(Level.TRACE);
			String timeStamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm").format(Calendar.getInstance().getTime());
			FileAppender fileAppender = new FileAppender();
			fileAppender.setFile(currentRunReportPath + "\\Log.log");
			PatternLayout lay = new PatternLayout("%d{dd-MM-yyyy HH:mm} %-5p - %m%n");
			fileAppender.setLayout(lay);
			log.addAppender(fileAppender);
			fileAppender.activateOptions();
		}
		
		public static void failLog(String elementText) 
		{
			log.error( elementText+" - failed");
		}
		
		public static void passLog(String elementText) 
		{
			log.info(elementText+" - Pass");
		}
		
		public static void startTestCase(String testCaseName)
		{ 
			log.info("****************************************************************************************");	 
			log.info("				                 "+testCaseName+ "       								  ");	 
			log.info("****************************************************************************************"); 
		}
		
		public static void stackTracePrint(Exception e)
		{
			log.error(e.getClass().getSimpleName(),e);
		}
		
		public static void write(String longDescription) 
		{
			log.info(longDescription);
		}
		
		public static void writeTitle(String string) 
		{
			log.info("---------"+string+"----------");
		}
		
	}