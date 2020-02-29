package com.version.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatetimeUtil {

	public static String parseDateToString(String parse, Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(parse);
		String dateString = formatter.format(date);
		return dateString;
	}

	public static Date parseStringToDate(String parse, String dateString) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(parse);
		Date date = new Date();
		try {
			date = formatter.parse(dateString);
		} catch (ParseException e) {
			LoggerUtil.error(e);
			throw e;
		}
		return date;
	}

	public static Date addDate(Date wolftagdispeartime, long i) {
		long date = wolftagdispeartime.getTime();
		long result = date + i;
		return new Date(result);
	}

	public static boolean isSameDay(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);

		boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
		boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
		boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);

		return isSameDate;
	}

	public static int getWeekNum(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	public static int getMonthNum(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	public static long getYearNum(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	public static int between(Date date1, Date date2) {
		Calendar calst = Calendar.getInstance();
		Calendar caled = Calendar.getInstance();

		calst.setTime(date1);
		caled.setTime(date2);

		// 设置时间为0时
		calst.set(Calendar.HOUR_OF_DAY, 0);
		calst.set(Calendar.MINUTE, 0);
		calst.set(Calendar.SECOND, 0);
		caled.set(Calendar.HOUR_OF_DAY, 0);
		caled.set(Calendar.MINUTE, 0);
		caled.set(Calendar.SECOND, 0);
		// 得到两个日期相差的天数
		int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst.getTime().getTime() / 1000)) / 3600 / 24;

		return days;
	}

	public static Date getZeroTimeCurDay() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 001);
		return cal.getTime();
	}

	/**
	 * 时间戳转换日期格式
	 * 
	 * @param String
	 * @return date
	 * 
	 */
	public static String stampToDate(String s) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lt = new Long(s);
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;
	}

	/**
	 * 将时间转换为时间戳
	 * 
	 * @param Date
	 * @return String
	 * 
	 */
	public static String dateToStamp(Date date) throws ParseException {
		String res;
		long ts = date.getTime();
		res = String.valueOf(ts);
		return res;
	}

	/**
	 * 日期大小比较
	 * 
	 * @param String
	 *            date1
	 * @throws ParseException
	 * 
	 * 
	 */
	public static int compare_date(Date date1, Date date2) throws ParseException {

		if (date1.getTime() > date2.getTime()) {
			// date1 在date2前
			return 1;
		} else if (date1.getTime() < date2.getTime()) {
			// date1 在date2后
			return -1;
		} else {
			return 0;
		}
	}

	public static boolean between(long now, Date start, Date end) {
		if(now>=start.getTime()&&now<=end.getTime()){
			return true;
		}
		return false;
	}

}
