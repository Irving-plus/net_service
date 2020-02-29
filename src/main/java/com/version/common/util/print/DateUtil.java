package com.version.common.util.print;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static final SimpleDateFormat df_ymd_log =new SimpleDateFormat("yyyy-MM-dd");  
	public static final SimpleDateFormat df_ymd =new SimpleDateFormat("yyyyMMdd");  
	public static final SimpleDateFormat df_ymd_hms=new SimpleDateFormat("yyyyMMddHHmmss");  
	public static long nowTime() {
		return System.currentTimeMillis();
	}
	public static Date format(long time){
		return new Date(time);
	}
	public static Date todayZero(){
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	public static Date tomorrowZero(){
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.add(Calendar.DAY_OF_MONTH, +1);
		return cal.getTime();
	}
}
