/*
 * Created on 2005-8-23
 */
package org.commonfarm.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.context.i18n.LocaleContextHolder;

import org.commonfarm.Constant;


/**
 * 
 *
 * @author Junhao Yang
 */
public class DateUtil {
    private static Log log = LogFactory.getLog(DateUtil.class);
    private static String defaultDatePattern = null;
    private static String DATE_PATTERN = "yyyy-MM-dd";
    private static String DATETIME_PATTERN = "yyyy-MM-dd hh:mm:ss";
    
    public static String NORMAL_TIME = "07:30:00";
    
    //private static String timePattern = "HH:mm";
    /**
     * get system date
     * @return yyyy/mm/dd
     */
    public static String getSystemDate() {
        DateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        return sdf.format(new Date());
    }
    
    /**
     * get system datetime
     * @return yyyy/mm/dd hh:mm:ss
     */
    public static String getSystemDateTime() {
        DateFormat sdf = new SimpleDateFormat(DATETIME_PATTERN);
        return sdf.format(new Date());
    }
    
    /**
     * 
     * @param date String format date
     * @return Date format date
     */
    public static Date convert(String date) {
        DateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        Date dateFormat = null;
        try {
            dateFormat = sdf.parse(date);
        } catch(ParseException pe) {
            log.error(pe);
        }
        return dateFormat;
    }
    /**
     * 
     * @param date String format date
     * @return Date format date
     */
    public static Date convertStamp(String date) {
        DateFormat sdf = new SimpleDateFormat(DATETIME_PATTERN);
        Date dateFormat = null;
        try {
            dateFormat = sdf.parse(date);
        } catch(ParseException pe) {
            log.error(pe);
        }
        return dateFormat;
    }
    
    /**
     * 
     * @param date Date format date
     * @return Date format date
     */
    public static String convert(Date date) {
        if(date == null) {
            return "";
        }
        DateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        String dateFormat = sdf.format(date);

        return dateFormat;
    }
    public static String convertFull(Date date) {
    	if(date == null) {
            return "";
        }
        DateFormat sdf = new SimpleDateFormat(DATETIME_PATTERN);
        String dateFormat = sdf.format(date);

        return dateFormat;
	}
    public static String convertMonth(Date date) {
    	if(date == null) {
            return "";
        }
        DateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        String dateFormat = sdf.format(date);
        return dateFormat.substring(5);
    }
    public static String convert(Timestamp date) {
        if(date == null) {
            return "";
        }
        DateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        String dateFormat = sdf.format(date);

        return dateFormat;
    }

    /**
     * @param date
     * @return sql date
     */
    public static java.sql.Date convertSQLDate(String date) {
        DateFormat sdf = new SimpleDateFormat(DATE_PATTERN);  
        Date dateFormat = null;
        try {
            dateFormat = sdf.parse(date);
        } catch(ParseException pe) {
            log.error(pe);
        }
        java.sql.Date sqlDate = new java.sql.Date(dateFormat.getTime());
        return sqlDate;
    }

    /**
     * @param date
     * @return Timestamp
     */
    public static Timestamp convertTimestamp(String date) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date dateFormat = null;
        try {
            dateFormat = sdf.parse(date);
        } catch(ParseException pe) {
            log.error(pe);
        }
        Timestamp time = new Timestamp(dateFormat.getTime());
        return time;
    }
    
    /**
     * getting end date
     * intervalDays <= 40
     * @param startDay 
     * @param interval days
     * @return
     */
    public static String getEndDate(String startDay, int intervalDays) {
        StringTokenizer st = new StringTokenizer(startDay, "/");
        int year = 0;
        int mon = 0;
        int day = 0;
        for (int i = 0; st.hasMoreTokens(); i++) {
            if (i == 0) {
                year = Integer.parseInt(st.nextToken());
            }
            if (i == 1) {
                String sMon = st.nextToken();
                if (sMon.startsWith("0")) {
                    sMon = sMon.substring(1);
                }
          
                mon = Integer.parseInt(sMon);
            }
            if (i == 2) {
                String sDay = st.nextToken();
                if (sDay.startsWith("0")) {
                    sDay = sDay.substring(1);
                }
                day = Integer.parseInt(sDay);
            }
        }
        DateTime start = new DateTime(year, mon, day, 0, 0, 0, 0);
        
        Period p1 = new Period(20*86400000);
        Period p2 = new Period((intervalDays - 20)*86400000);

        DateTime end = start.plus(p1);
        end = end.plus(p2);
        year = end.getYear();
        mon = end.getMonthOfYear();
        day = end.getDayOfMonth();
        String xMon = "";
        String xDay = "";
        if (mon < 10) {
            xMon = "0" + (new Integer(mon)).toString();
        } else {
            xMon = (new Integer(mon)).toString();
        }
        if (day < 10) {
            xDay = "0" + (new Integer(day)).toString();
        } else {
            xDay = (new Integer(day)).toString();
        }
        String endDay = (new Integer(year)).toString() + "/" + xMon + "/" + xDay;
        return endDay;
    }
    public static Date getAfterDate(Date currentDate, int intervalDays) {
    	DateTime dateTime = new DateTime(currentDate.getTime());
		DateTime date = dateTime.plusDays(intervalDays);
		return date.toDate();
	}
    public static Date getBeforeDate(Date currentDate, int intervalDays) {
		DateTime dateTime = new DateTime(currentDate.getTime());
		DateTime date = dateTime.minusDays(intervalDays);
		return date.toDate();
	}
    /**
     * Return default datePattern (MM/dd/yyyy)
     * @return a string representing the date pattern on the UI
     */
    public static synchronized String getDatePattern() {
        Locale locale = LocaleContextHolder.getLocale();
        try {
            defaultDatePattern = ResourceBundle.getBundle(Constant.BUNDLE_KEY, locale)
                .getString("date.format");
        } catch (MissingResourceException mse) {
            defaultDatePattern = "MM/dd/yyyy";
        }
        
        return defaultDatePattern;
    }

    /**
     * date1 > date2?
     * @param date1
     * @param date2
     * @return
     */
    public static boolean greaterThan(Date date1, String date2) {
        Date date = convert(date2);
        return greaterThan(date1, date);
    }
    public static boolean greaterThan(Date date1, Date date2) {
        if (date1.getTime() > date2.getTime()) {
            return true;
        }
        return false;
    }
    public static boolean equals(Date date1, String date2) {
        Date date = convert(date2);
        return equals(date1, date);
    }
    public static boolean equals(Date date1, Date date2) {
        if (date1.getTime() == date2.getTime()) {
            return true;
        }
        return false;
    }
    /**
     * 
     * @param intervalDays
     * @return
     */
    public static String getReportDate(int intervalDays) {
    	Date sysDate = convert(convert(new Date()));
    	DateTime dateTime = new DateTime(sysDate.getTime());
		DateTime date = dateTime.plusDays(intervalDays);
		return convert(date.toDate()) + " " + NORMAL_TIME;
	}
    /**
     * 
     * @param intervalDays
     * @return
     */
    public static String getDate(int intervalDays) {
    	Date sysDate = convert(convert(new Date()));
    	DateTime dateTime = new DateTime(sysDate.getTime());
		DateTime date = dateTime.plusDays(intervalDays);
		return convert(date.toDate()) + " " + "00:00:00";
	}

	public static Date convertReportDate(String evtDate) {
		String date = StringUtil.getStringArray(evtDate)[0];
		return convert(date);
	}
	
    public static List getDateList(Date from, Date to){
        DateTime dtf = new DateTime(from);
        DateTime dtt = new DateTime(to);
        List list = new ArrayList();
        while (dtt.compareTo(dtf) > 0) {
            dtf.toString("yyyy/MM/dd");
            dtf = dtf.plusDays(1);
        }
        return list;
    }
    
    public static List getDateList(String from, String to){ 
        DateTime dtf = new DateTime(DateUtil.convert(from));
        DateTime dtt = new DateTime(DateUtil.convert(to));
        List list = new ArrayList();
        while (dtt.compareTo(dtf) >= 0) {
            list.add(dtf.toString("MM/dd"));
            dtf = dtf.plusDays(1);
        }
        return list;
    }
    
    /**
     * 
     * @param startDate
     * @param endDate
     * @param type
     * @return
     */
    public static long getInterval(String startDate, String endDate, String type){
        Date start = convertStamp(startDate);
        Date end = convertStamp(endDate);
        return getInterval(start, end, type);
    }
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @return
	 */
    public static long getInterval(Date startDate, Date endDate, String type){
    	if (type.equals("S")){
    		return getIntervalTime(startDate, endDate)/1000;
    	}else if ( type.equals("M") ){
    		long times = getIntervalTime(startDate, endDate);
    		return times/1000/60;
    	}else if ( type.equals("H") ){
    		long times = getIntervalTime(startDate, endDate);
    		return times/1000/60/60;
    	}else if ( type.equals("D") ){
    		long times = getIntervalTime(startDate, endDate);
    		return times/1000/60/60/24;
    	}else 
    		return 0l;
    	
    }
    
    /**
	 * millisecond level
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long getIntervalTime(Date startDate, Date endDate) {
		long intervalTime = endDate.getTime() - startDate.getTime();
		return intervalTime;
	}
    
    public static String getIntervalTimeString(String startDate, String endDate) {
        
        long intervalTime = getIntervalTime(convertStamp(startDate), convertStamp(endDate));
        intervalTime = intervalTime/1000;
        long second = intervalTime % 60;
        intervalTime = intervalTime / 60;
        long minute = intervalTime % 60;
        intervalTime = intervalTime / 60;
        long hour = intervalTime % 24;
        long day = intervalTime / 24;
        
        return day+"D "+hour+"H "+minute+"M "+second+"S";
    }
        
    /**
     *  format date by pattern 
     *      (yyyy-MM-dd) or (yyyy-MM-dd hh:mm:ss) or (yyyy/MM/dd hh:mm:ss)
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        DateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
    
    public static String format(String date, String pattern) {
        return format(convert(date), pattern);
    }
    /*
    public static Interval getIntervalDateTime(Date startDate, Date endDate) {
        
        long intervalTime = getIntervalTime(startDate,endDate);
        intervalTime = intervalTime/1000;
        long second = intervalTime % 60;
        intervalTime = intervalTime / 60;
        long minute = intervalTime % 60;
        intervalTime = intervalTime / 60;
        long hour = intervalTime % 24;
        long day = intervalTime / 24;
        
        return new Interval(day, hour, minute, second);
    }*/

    public static void main(String[] args){

        //System.out.println(DateUtil.getInterval(convertStamp("2006-8-1 7:30:35"), convertStamp("2006-8-2 8:40:40"),"H"));
        System.out.println(DateUtil.getInterval("2006-11-2 9:21:34", "2006-11-4 12:29:21", "M"));
        //System.out.println(DateUtil.getIntervalTime(DateUtil.convertStamp("2006-11-2 9:21:34"), DateUtil.convertStamp("2006-11-4 12:29:21")));
    }
}
