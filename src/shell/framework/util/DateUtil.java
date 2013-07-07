package shell.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Calendar;


/**
 * 日期时间工具类
 */
public class DateUtil {

    /**
     * 日期格式化字符串定义
     */
    public static final String timeFormatStr = "yyyy-MM-dd HH:mm:ss";
	private static final String dateFormatStr = "yyyy-MM-dd";
	private static final String mimuteFormatStr = "yyyy-MM-dd HH:mm";
    /**
     * 一天的开始时间格式
     */
    private static final String todayBeginFormatStr = "yyyy-MM-dd 00:00:00";
	/**
	  * 一天的结束时间格式
	 */
    private static final String todayEndFormatStr = "yyyy-MM-dd 23:59:59";
	/**
	 * 纯数字、带毫秒的日期格式串
	 */
	private static final String numMillFormatStr = "yyyyMMddHHmmssSSS";
	/**
	 * yyyy-mm-dd HH:mm:ss格式的日期格式化对象
	 */
	public static final SimpleDateFormat timeFormat = new SimpleDateFormat(timeFormatStr);
	/**
	 * yyyy-MM-dd格式的日期格式化对象
	 */
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
	/**
	 * yyyy-MM-dd HH:mm格式的日期格式化对象
	 */
	public static final SimpleDateFormat minuteFormat = new SimpleDateFormat(mimuteFormatStr);
	/**
	 * yyyy-MM-dd 00:00:00格式的日期格式化对象
	 */
	public static final SimpleDateFormat todayBegin = new SimpleDateFormat(todayBeginFormatStr);
	/**
	 * yyyy-MM-dd 23:59:59格式的日期格式化对象
	 */
	public static final SimpleDateFormat todayEnd = new SimpleDateFormat(todayEndFormatStr);
	/**
	 * yyyyMMddHHmmssSSS格式的日期格式化对象
	 */
	public static final SimpleDateFormat numMillFormat = new SimpleDateFormat(numMillFormatStr);


    /**
     * 初始化时，给各个日期格式化对象设置中国时区
     */
    static{
		TimeZone zone = TimeZone.getTimeZone("GMT+08:00");
		timeFormat.setTimeZone(zone);
		dateFormat.setTimeZone(zone);
		minuteFormat.setTimeZone(zone);
		todayBegin.setTimeZone(zone);
		todayEnd.setTimeZone(zone);
		numMillFormat.setTimeZone(zone);
	}


	/**
	 * 获取当前日期字符串，格式为yyyy-MM-dd
	 * @return yyyy-MM-dd
	 */
	public static String currentDate() {
		return dateFormat.format(new Date());
	}

	/**
	 * 获取昨天日期字符串，格式为yyyy-MM-dd
	 * @return  yyyy-MM-dd
	 */
	public static String yesterDayDate() {
		return dateFormat.format(addDay(new Date(),-1));
	}

	/**
	 * 获取当期时间字符串，格式为yyyy-MM-dd HH:mm:ss
	 * @return  yyyy-MM-dd HH:mm:ss
	 */
	public static String currentTime() {
		return timeFormat.format(new Date());
	}
    
	/**
	 * 获得当前时间字符串，精确到分钟；格式为yyyy-mm-dd HH:mm
	 * @return  yyyy-mm-dd HH:mm
	 */
	public static String currentMinute() {
		return minuteFormat.format(new Date());
	}

	/**
	 * 获取某天开始时19位长度的时间字符串，格式为：yyyy-MM-dd 00:00:00
	 * @return   yyyy-MM-dd 00:00:00
	 */
	public static String todayBegin() {
		return todayBegin.format(new Date());
	}

	/**
	 * 获取某天结束时19位长度的时间字符串，格式为：yyyy-MM-dd 23:59:59
	 * @return yyyy-MM-dd 23:59:59
	 */
	public static String todayEnd() {
		return todayEnd.format(new Date());
	}

	/**
	 * 获得精确到毫秒的当前时间字符串。格式为：yyyyMMddHHmmssSSS
	 * @return   yyyyMMddHHmmssSSS
	 */
	public static String currentNumMill() {
		return numMillFormat.format(new Date());
	}

	/**
	 * 获取当前时间字符串， 格式为yyyy-MM-dd HH:mm:ss
	 * @return   yyyy-MM-dd HH:mm:ss
	 */
	public static String getNow(){
		return currentTime();
	}

    /**
     * 将时间加到指定的月份后，返回增加后的日期字符串
     * @param dateTime 时间字符串
     * @param months   指定的月份
     * @param formatStr 时间格式字符串
     * @return  指定月份增加了时间后的日期字符串
     */
	public static String addMonth(String dateTime,int months,String formatStr){
		if(formatStr==null)
			formatStr=dateFormatStr;
		SimpleDateFormat sdf=new SimpleDateFormat(formatStr);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
		try {
			Date d=addMonth(sdf.parse(dateTime),months);
			return sdf.format(d);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

    /**
     * 把传入的时间增加指定的小时后返回
     * @param dateTime 时间字符串
     * @param hours     指定的小时
     * @param formatStr 时间格式字符串
     * @return  增加小时后的时间字符串
     */
	public static String addHour(String dateTime,int hours,String formatStr){
		if(formatStr==null)
			formatStr=dateFormatStr;
		SimpleDateFormat sdf=new SimpleDateFormat(formatStr);
		try {
			Date d=addHour(sdf.parse(dateTime),hours);
			return sdf.format(d);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

    /**
     * 把传入的时间增加指定的分钟数后返回
     * @param dateTime  时间字符串
     * @param min        指定的分钟数
     * @param formatStr   时间格式字符串
     * @return   指定formatStr格式的时间字符串
     */
	public static String addMinute(String dateTime,int min,String formatStr){
		if(formatStr==null)
			formatStr=dateFormatStr;
		SimpleDateFormat sdf=new SimpleDateFormat(formatStr);
		try {
			Date d=addMinute(sdf.parse(dateTime),min);
			return sdf.format(d);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将指定时间数增加到小时数，返回
	 * @param d  时间
	 * @param hours 小时
	 * @return  日期值
	 */
	public static Date addHour(Date d,int hours){
		Calendar cal=Calendar.getInstance(new Locale("zh-CN"));
		cal.setTime(d);
		cal.add(Calendar.HOUR,hours);
		return cal.getTime();
	}

    /**
     * 将指定日期增加分钟后返回
     * @param d 日期
     * @param min 分钟数
     * @return    日期
     */
	public static Date addMinute(Date d,int min){
		Calendar cal=Calendar.getInstance(new Locale("zh-CN"));
		cal.setTime(d);
		cal.add(Calendar.MINUTE,min);
		return cal.getTime();
	}

	/**
	 * 指定日期后加上月份后返回
	 * @param d   日期
	 * @param months   指定月份
	 * @return     日期
	 */
	public static Date addMonth(Date d,int months){
		Calendar cal=Calendar.getInstance(new Locale("zh-CN"));
		cal.setTime(d);
		cal.add(Calendar.MONTH,months);
		return cal.getTime();
	}

	/**
	 * 指定日期后加上天数返回
	 * @param d  日期
	 * @param days   指定天数
	 * @return     日期
	 */
	public static Date addDay(Date d,int days){
		Calendar cal=Calendar.getInstance(new Locale("zh-CN"));
		cal.setTime(d);
		cal.add(Calendar.DATE,days);
		return cal.getTime();
	}

	/**
	 * 获取当期日期
	 * @return  Date类型
	 */
	public static Date getCurrentDate(){
		Calendar cal=Calendar.getInstance(new Locale("zh-CN"));
		return cal.getTime();
	}

	/**
	 * 在指定日期后增加天数后，返回
	 * @param dateTime   指定日期
	 * @param days       指定天数
	 * @param formatStr  时间格式字符串
	 * @return  日期字符串
	 */
	public static String addDay(String dateTime,int days,String formatStr){
		if(formatStr==null)
			formatStr=dateFormatStr;
		SimpleDateFormat sdf=new SimpleDateFormat(formatStr);
		try {
			return sdf.format(addDay(sdf.parse(dateTime),days));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取本周星期几的具体日期
	 * @param i 本周星期几，星期日用0表示
	 * @return 日期
     */
	public static String getThisWeekDay(int i){
		Calendar cal = Calendar.getInstance(new Locale("zh-CN"));
		cal.setTime(new Date());
		cal.add(Calendar.DATE,i+1-cal.get(Calendar.DAY_OF_WEEK));
		return dateFormat.format(cal.getTime());
	}

	/**
	 * 获取上周的星期几的具体日期
	 * @param i  星期几, 星期日用0表示
     * @return   日期
	 */
	public static String getLastWeekDay(int i){
		Calendar cal = Calendar.getInstance(new Locale("zh-CN"));
		cal.setTime(new Date());
		cal.add(Calendar.DATE,i-6-cal.get(Calendar.DAY_OF_WEEK));
		return dateFormat.format(cal.getTime());
	}

	/**
	 * 将字符串型的时间转化成Date类型
     * @param timeStr 时间字符串，格式 yyyy-MM-dd HH:mm:ss
	 * @return  日期
	 */
    public static Date stringToDate(String timeStr){
		   TimeZone zone=TimeZone.getTimeZone("GMT+08:00");
		   Calendar cal=Calendar.getInstance(zone);
		   try{
		     int year=Integer.parseInt(timeStr.substring(0,4));
		     int month=Integer.parseInt(timeStr.substring(5,7))-1;
		     int date=Integer.parseInt(timeStr.substring(8,10));
		     int hour=Integer.parseInt(timeStr.substring(11,13));
	         int min=Integer.parseInt(timeStr.substring(14,16));
	         cal.set(year,month,date,hour,min,0);
	         Date d=cal.getTime();
	  	     d.setHours(cal.get(Calendar.HOUR_OF_DAY));
	         return d;
		   }catch(Exception e){
			   e.printStackTrace();
		       return cal.getTime();
		   }
	   }

	   /**
	    * 计算系统当前时间-输入时间 的时间间隔 单位：小时
	    * @param timeStr 指定时间字符串，格式 yyyy-MM-dd HH:mm:ss
	    * @return  相隔小时数
	    */
    public static double disTime(String timeStr){
    	   double d=(stringToDate(getNow()).getTime())-(stringToDate(timeStr).getTime());
		   double dd=d/3600000;
		   return dd;
    }





    /**
     * 测试方法
     * @param args
     */
	public static void main(String[] args){
		double d=(stringToDate(getNow()).getTime())-(stringToDate("2009-08-27 16:00:00").getTime());
		double dd=d/3600000;
		System.out.println(d+"  "+dd+" "+disTime("2009-08-27 16:00:00"));
		System.out.println(getLastWeekDay(3));
	}
}