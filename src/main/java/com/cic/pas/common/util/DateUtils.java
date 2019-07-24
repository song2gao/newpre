package com.cic.pas.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 
 * 本类中包括日期转换相关的方法
 * 
 * @author 张为锋 Dec 31, 2008 2:56:33 PM
 * 
 */
public class DateUtils {
	public DateUtils() {

	}

	public static String[] getYears(String startYear, String endYear) {
		String[] date = new String[Integer.valueOf(endYear)
				- Integer.valueOf(startYear) + 1];
		int j = 0;
		for (int i = Integer.valueOf(startYear); i <= Integer.valueOf(endYear); i++) {
			date[j] = String.valueOf(i);
			j++;
		}
		return date;
	}

	/**
	 * 求两个日期之间的月数， 起始日期和结束日期的日期格式为：yyyy-mm
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static String[] getMonthsBetweenStartYearsAndEndYears(
			String startDate, String endDate) {
		if (startDate == null || endDate == null || startDate.length() < 7
				|| endDate.length() < 7) {
			return new String[0];
		}

		int startYear = Integer.parseInt(startDate.substring(0, 4));
		int startMonth = Integer.parseInt(startDate.substring(5, 7));
		int endYear = Integer.parseInt(endDate.substring(0, 4));
		int endMonth = Integer.parseInt(endDate.substring(5, 7));
		int months = 0;

		// 假如起始日期比结束日期大就返回一个size=0的数组
		if (startYear > endYear) {
			return new String[0];
		} else if (startYear == endYear) {
			if (startMonth > endMonth) {
				return new String[0];
			}
		}
		if (startYear == endYear) {
			months = endMonth - startMonth;
		} else {
			months = (endYear - startYear - 1) * 12 + (12 - startMonth)
					+ endMonth;
		}

		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.YEAR, 2011);
		ca.set(Calendar.MONTH, 11);

		String[] date = new String[months + 1];
		String datetime = "";
		int monthAvg = startMonth;
		int yearAvg = startYear;
		for (int i = 0; i <= months; i++) {
			if (yearAvg > endYear) {
				break;
			}
			// System.out.println(i<Integer.valueOf(month));
			if (monthAvg < 10) {
				date[i] = yearAvg + "-0" + monthAvg;
			} else {
				date[i] = yearAvg + "-" + monthAvg;
			}

			if (monthAvg >= 12) {
				yearAvg += 1;
				monthAvg = 1;
			} else {
				monthAvg++;
			}

			// System.out.println(date);
		}
		return date;
	}

	/**
	 * 把日期字符串转换为具体日期sq;
	 * 
	 * @param dateString
	 *            要转换的日期字符串，格式：yyyy-MM-dd HH:mm:ss,如果字符串为""，返回null
	 * @return 转换后的日期
	 */
	public static Timestamp stringToSqlDatetime(String dateString) {
		Timestamp tempDate = null;

		if (dateString == null)
			return tempDate;
		if (dateString.equals(""))
			return tempDate;

		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			tempDate = new Timestamp(dateformat.parse(dateString).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return tempDate;
	}

	public static java.sql.Date stringToSqlDate(String dateString) {
		java.sql.Date timeDate = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			dateFormat.setLenient(false);
			timeDate = new java.sql.Date(dateFormat.parse(dateString).getTime());
			return timeDate;

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return timeDate;
	}

	/**
	 * 把日期字符串转换为具体日期
	 * 
	 * @param dateString
	 *            要转换的日期字符串，格式：yyyy-MM-dd,如果字符串为""，返回null
	 * @return 转换后的日期
	 */
	public static Date stringToDate(String dateString) {
		return stringToDate(dateString, "yyyy-MM-dd");
	}

	public static Date stringToDate1(String dateString) {
		return stringToDate(dateString, "yyyy-MM");
	}

	public static Date stringToDate2(String dateString) {
		return stringToDate(dateString, "yyyy");
	}

	/**
	 * 把日期字符串转换为具体日期
	 * 
	 * @param dateString
	 *            要转换的日期字符串
	 * @param fmt
	 *            格式，例如"yyyy-MM-dd"
	 * @return 转换后的日期
	 */
	public static Date stringToDate(String dateString, String fmt) {
		Date tempDate = null;

		if (dateString == null)
			return tempDate;
		if (dateString.equals(""))
			return tempDate;

		SimpleDateFormat dateformat = new SimpleDateFormat(fmt);
		try {
			tempDate = dateformat.parse(dateString);
		} catch (ParseException e) {
			//e.printStackTrace();
		}

		return tempDate;
	}

	/**
	 * 把日期转换为字符串
	 * 
	 * @param date
	 *            要转换的日期
	 * @return 转换后的字符串，格式"yyyy-MM-dd"
	 */
	public static String dateToString(Date date) {
		return dateToString(date, "yyyy-MM-dd");
	}

	/**
	 * 把日期转换为字符串
	 * 
	 * @param date
	 *            要转换的日期
	 * @param fmt
	 *            格式,例如"yyyy-MM-dd"
	 * @return
	 */
	public static String dateToString(Date date, String fmt) {
		String string = "";

		if (date == null)
			return string;

		SimpleDateFormat dateformat = new SimpleDateFormat(fmt);
		string = dateformat.format(date);

		return string;
	}

	/**
	 * 把日期字符串转换为具体日期
	 * 
	 * @param dateString
	 *            要转换的日期字符串，格式：yyyy-MM-dd HH:mm:ss,如果字符串为""，返回null
	 * @return 转换后的日期
	 */
	public static Date stringToDatetime(String dateString) {
		Date tempDate = null;

		if (dateString == null)
			return tempDate;
		if (dateString.equals(""))
			return tempDate;

		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			tempDate = dateformat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return tempDate;
	}

	/**
	 * 把日期转换为字符串
	 * 
	 * @param date
	 *            要转换的日期
	 * @return 转换后的字符串，格式"yyyy-MM-dd HH:mm:ss"
	 */
	public static String datetimeToString(Date date) {
		String string = "";

		if (date == null)
			return string;

		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		string = dateformat.format(date);

		return string;
	}

	/**
	 * 取得当前时间的字符串
	 * 
	 * @return 当前时间的字符串，格式"yyyy-MM-dd"
	 */

	public static String getToday() {
		String tempString = "";
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			tempString = dateformat.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempString;
	}

	public static String getNow() {
		String tempString = "";
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			tempString = dateformat.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempString;
	}

	// 只获取小时
	public static String getNow(String now) {
		Date date = stringToDatetime(now);
		String tempString = "";
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:00:00");
		try {
			tempString = dateformat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempString;
	}

	/**
	 * 取得当前年的字符串
	 * 
	 * @return 当前时间的字符串，格式"yyyy"
	 */

	public static String getYear() {
		String tempString = "";
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy");
		try {
			tempString = dateformat.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempString;
	}

	/**
	 * 取得当前年月的字符串
	 * 
	 * @return 当前时间的字符串，格式"yyyy-MM"
	 */

	public static String getYearMonth() {
		String tempString = "";
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM");
		try {
			tempString = dateformat.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempString;
	}

	/**
	 * 取得当前时间的字符串
	 * 
	 * @return 当前时间的字符串，格式"yyyy-MM-dd HH:mm:ss"
	 */
	public static String getDatetime() {
		String tempString = "";
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			tempString = dateformat.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempString;
	}

	/**
	 * 计算两日期差多少天。第二个日期减第一个日期。可能为负数。
	 * 
	 * @param time1
	 *            yyyy-MM-dd格式
	 * @param time2
	 *            yyyy-MM-dd格式
	 * @return
	 */
	public static Long getDateDiff(String time1, String time2) {
		long diff = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			diff = date2.getTime() - date1.getTime();
			diff = diff / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Long(diff);
	}

	/**
	 * 计算两日期差多少天。第二个日期减第一个日期。可能为负数。
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Long getDateDiff(Date d1, Date d2) {
		long diff = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			d1 = ft.parse(ft.format(d1));
			d2 = ft.parse(ft.format(d2));
			diff = d2.getTime() - d1.getTime();
			diff = diff / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Long(diff);
	}

	public static int getDays(GregorianCalendar g1, GregorianCalendar g2) {
		int elapsed = 0;
		GregorianCalendar gc1, gc2;

		if (g2.after(g1)) {
			gc2 = (GregorianCalendar) g2.clone();
			gc1 = (GregorianCalendar) g1.clone();
		} else {
			gc2 = (GregorianCalendar) g1.clone();
			gc1 = (GregorianCalendar) g2.clone();
		}

		gc1.clear(Calendar.MILLISECOND);
		gc1.clear(Calendar.SECOND);
		gc1.clear(Calendar.MINUTE);
		gc1.clear(Calendar.HOUR_OF_DAY);

		gc2.clear(Calendar.MILLISECOND);
		gc2.clear(Calendar.SECOND);
		gc2.clear(Calendar.MINUTE);
		gc2.clear(Calendar.HOUR_OF_DAY);

		while (gc1.before(gc2)) {
			gc1.add(Calendar.DATE, 1);
			elapsed++;
		}
		return elapsed - 1;
	}

	// 获得本季度
	public static String getThisSeasonTime(int month) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];
		int end_month = array[season - 1][2];

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);

		int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
		int end_days = getLastDayOfMonth(years_value, end_month);
		String seasonDate = years_value + "-" + start_month + "-" + start_days
				+ ";" + years_value + "-" + end_month + "-" + end_days;
		return seasonDate;

	}

	/**
	 * 获取某年某月的最后一天
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 最后一天
	 */
	public static int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}

	/**
	 * 是否闰年
	 * 
	 * @param year
	 *            年
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	/**
	 * 获取年月日日期字符串 例如2009年12月15日 shilei
	 */
	public static String getYearMonDay() {
		Calendar calender = Calendar.getInstance();
		int year = calender.get(Calendar.YEAR);
		int month = calender.get(Calendar.MONTH) + 1;
		int day = calender.get(Calendar.DAY_OF_MONTH);
		return String.valueOf(year) + "年" + String.valueOf(month) + "月"
				+ String.valueOf(day) + "日";
	}

	/**
	 * 获取星期 shilei
	 */
	public static String getDayOfWeek() {
		Calendar calender = Calendar.getInstance();
		calender.setTime(new Date());
		if (1 == calender.get(Calendar.DAY_OF_WEEK)) {
			return "星期日";
		} else if (2 == calender.get(Calendar.DAY_OF_WEEK)) {
			return "星期一";
		} else if (3 == calender.get(Calendar.DAY_OF_WEEK)) {
			return "星期二";
		} else if (4 == calender.get(Calendar.DAY_OF_WEEK)) {
			return "星期三";
		} else if (5 == calender.get(Calendar.DAY_OF_WEEK)) {
			return "星期四";
		} else if (6 == calender.get(Calendar.DAY_OF_WEEK)) {
			return "星期五";
		} else if (7 == calender.get(Calendar.DAY_OF_WEEK)) {
			return "星期六";
		}
		return "";
	}
	/**
	 * 获取星期 shilei
	 */
	public static String getDayOfWeek(Date date) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		if (1 == calender.get(Calendar.DAY_OF_WEEK)) {
			return "7";
		} else if (2 == calender.get(Calendar.DAY_OF_WEEK)) {
			return "1";
		} else if (3 == calender.get(Calendar.DAY_OF_WEEK)) {
			return "2";
		} else if (4 == calender.get(Calendar.DAY_OF_WEEK)) {
			return "3";
		} else if (5 == calender.get(Calendar.DAY_OF_WEEK)) {
			return "4";
		} else if (6 == calender.get(Calendar.DAY_OF_WEEK)) {
			return "5";
		} else if (7 == calender.get(Calendar.DAY_OF_WEEK)) {
			return "6";
		}
		return "";
	}

	/**
	 * @author 李帅 Nov 26, 2009 PM 取得当月第一天时间的字符串
	 * 
	 * @return 当前时间的字符串，格式"yyyy-MM-dd"
	 */
	public static String getFirstDayOfMonth() {
		String tempString = "";
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar firstDate = Calendar.getInstance();
		firstDate.set(Calendar.DATE, 1);
		try {
			tempString = dateformat.format(firstDate.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempString;
	}

	/**
	 * 得到当月第一天的日期字符串
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static String getFirstDayOfMonth(String date) {
		String tempString = "";
		Date d = stringToDate(date);
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar firstDate = Calendar.getInstance();
		firstDate.set(Calendar.DATE, 1);
		firstDate.set(Calendar.MONTH, d.getMonth());
		// firstDate.setTime(stringToDate(date));
		try {
			tempString = dateformat.format(firstDate.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempString;
	}

	/**
	 * 取当前时间的上一个月的该天
	 * 
	 * @return
	 */
	public static String getLastMonthofDay() {
		String tempString = "";
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar firstDate = Calendar.getInstance();
		firstDate.set(Calendar.MONTH, firstDate.get(Calendar.MONTH) - 1);
		try {
			tempString = dateformat.format(firstDate.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempString;
	}

	/**
	 * 取当前时间的下个月的该天
	 * 
	 * @return
	 */
	public static String getAfterMonthofDay(Date start) {
		String tempString = "";
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(start);
		cal.set(cal.MONTH, cal.get(cal.MONTH) + 1);
		try {
			tempString = dateformat.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempString;
	}

	/**
	 * 取当前时间的上一个月的该天
	 * 
	 * @return
	 */
	public static String getYesToDay(Date start) {
		String tempString = "";
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(start);
		cal.set(cal.DAY_OF_MONTH, cal.get(cal.DAY_OF_MONTH) - 1);
		try {
			tempString = dateformat.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempString;
	}

	/**
	 * 取当前时间的上一个月的该天
	 * 
	 * @return
	 */
	public static String getLastMonthofDay(Date start) {
		String tempString = "";
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(start);
		cal.set(cal.MONTH, cal.get(cal.MONTH) - 1);
		try {
			tempString = dateformat.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempString;
	}

	/**
	 * 取当前时间的上一个年的该天
	 * 
	 * @return
	 */
	public static String getLastYearofDay() {
		String tempString = "";
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar firstDate = Calendar.getInstance();
		firstDate.set(Calendar.YEAR, firstDate.get(Calendar.YEAR) - 1);
		try {
			tempString = dateformat.format(firstDate.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempString;
	}

	/**
	 * 取当前时间的前五年的该天
	 * 
	 * @return
	 */
	public static String getLastFiveYearofDay() {
		String tempString = "";
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar firstDate = Calendar.getInstance();
		firstDate.set(Calendar.YEAR, firstDate.get(Calendar.YEAR) - 5);
		try {
			tempString = dateformat.format(firstDate.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempString;
	}

	/**
	 * 取一个日期的 月 日 mm-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String getMonthAndDayOfDate(Date date) {
		String tempString = "";
		try {
			tempString = date.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempString.substring(5, 10);
	}

	/**
	 * 取某个日期月份
	 */
	public static int getMonthOfDate(Date date) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal.get(cal.MONTH);
	}

	/**
	 * 取某天的上一个年的该天
	 * 
	 * @return
	 */
	public static String getLastYearofDay(Date start) {
		String tempString = "";
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(start);
		cal.set(cal.YEAR, cal.get(cal.YEAR) - 1);
		try {
			tempString = dateformat.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempString;
	}

	/***************************************************************************
	 * 取当前时间前一个小时
	 * 
	 * @return
	 */
	public static String getLastHourOfCurrent() {
		String tempString = "";
		Date date = new Date();
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(cal.HOUR, cal.get(cal.HOUR) - 1);
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		try {
			tempString = dateformat.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempString;
	}

	public static String getLastHourOfCurrent(String now) {
		Date date = stringToDatetime(now);
		String tempString = "";
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(cal.HOUR, cal.get(cal.HOUR) - 1);
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:00:00");

		try {
			tempString = dateformat.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempString;
	}

	/***************************************************************************
	 * 取当前时间前76 minite
	 * 
	 * @return
	 */
	public static String getLast76MinOfCurrent() {
		String tempString = "";
		Date date = new Date();
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(cal.MINUTE, cal.get(cal.MINUTE) - 76);
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		try {
			tempString = dateformat.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempString;
	}

	public static String getLast76MinOfCurrent(String now) {
		String tempString = "";
		Date date = stringToDatetime(now);
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(cal.MINUTE, cal.get(cal.MINUTE) - 76);
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:00");

		try {
			tempString = dateformat.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempString;
	}

	/**
	 * 取得当前时间的前一天
	 * 
	 * @return
	 */
	public static String getLastDayOfCurrent() {
		String tempString = "";
		Date date = new Date();
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(cal.DATE, cal.get(cal.DATE) - 1);
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			tempString = dateformat.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempString;
	}

	/**
	 * 获取输入日期的前一天
	 * 
	 * @param now
	 * @return
	 */
	public static String getLastDayOfDate(Date date) {
		// Date date = stringToDatetime(now);
		String tempString = "";
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(cal.DATE, cal.get(cal.DATE) - 1);
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			tempString = dateformat.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempString;
	}

	public static void main(String[] a) {
		DateUtils s = new DateUtils();
		// Date aa=new Date();
		// //String m=s.getLast76MinOfCurrent();
		// String m = s.getNow("2011-11-09 14:50:00");
		// String mm = s.getLastHourOfCurrent(m);
		// String mmm = s.getLast76MinOfCurrent(m);
		// System.out.println(m);
		// System.out.println(mm);
		// System.out.println(mmm);
		// System.out.println(s.getAfterMonthofDay(s.stringToDate("2010-02-10")));

		/*
		 * String []
		 * date=s.getMonthsBetweenStartYearsAndEndYears("2010-01-01","2010-05-01");
		 */
		// String[] str=getMonthOfDays("201310");
		//			
		// for(int i=0;i<str.length;i++){
		// System.out.println(str[i]);
		// }
		String[] data=getMonthOfDays("201602");
		int i=1;
		for(String str : data){
			//System.out.println("		{ title: '"+str+"', field: 'point"+i+"',align:'center', width: 60},");
			//i++;
			System.out.println(str);
		}
	}

	/**
	 * 取当前时间的上一个年的该天
	 * 
	 * @return
	 */
	public static String getLastYearofDay1(Date start) {
		String tempString = "";
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM");
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(start);
		cal.set(cal.YEAR, cal.get(cal.YEAR) - 1);
		try {
			tempString = dateformat.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempString;
	}

	/**
	 * 求得某年 某月的所有日期
	 * 
	 * @param year
	 *            某年
	 * @param month
	 *            某月
	 * @return yyyy-mm-dd
	 */
	public static List<String> getDays(int year, int month) {
		List<String> list = new ArrayList<String>();
		int dayofmonth = 0;
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			dayofmonth = 31;
		} else if (month == 2) {
			if ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0)) {
				dayofmonth = 29;
			} else {
				dayofmonth = 28;
			}
		} else {
			dayofmonth = 30;
		}
		System.out.println(dayofmonth);
		for (int i = 1; i <= dayofmonth; i++) {
			StringBuffer sb = new StringBuffer(String.valueOf(year));
			if (month < 10) {
				sb.append("-0").append(String.valueOf(month));
			} else {
				sb.append("-").append(String.valueOf(month));
			}
			if (i < 10) {
				sb.append("-0").append(String.valueOf(i));
			} else {
				sb.append("-").append(String.valueOf(i));
			}
			list.add(sb.toString());
		}
		return list;
	}

	/**
	 * 取某一年的所有月
	 * 
	 * @param year
	 * @return yyyy-mm
	 */
	public static List<String> getMonths(String year) {
		List<String> list = new ArrayList<String>();
		for (int i = 1; i <= 12; i++) {
			StringBuffer sb = new StringBuffer(year);
			if (i < 10) {
				sb.append("-0").append(String.valueOf(i));
			} else {
				sb.append("-").append(String.valueOf(i));
			}
			list.add(sb.toString());
		}
		return list;
	}

	/**
	 * 转换日期格式
	 * 
	 * @param date
	 * @return
	 */
	public static String getFormatDate(String date) {
		String[] str = date.split("-");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length; i++) {
			sb.append(str[i]);
		}
		return sb.toString();
	}

	public static List<String> getMonths1(String year) {
		List<String> list = new ArrayList<String>();
		for (int i = 1; i <= 12; i++) {
			StringBuffer sb = new StringBuffer(year);
			if (i < 10) {
				sb.append("0").append(String.valueOf(i));
			} else {
				sb.append("").append(String.valueOf(i));
			}
			list.add(sb.toString());
		}
		return list;
	}

	/***
	 * 返回年月  格式 yyyyMM
	 * @return yyyyMM
	 */
	public static String getYearMonth1() {
		String tempString = "";
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMM");
		try {
			tempString = dateformat.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempString;
	}

	/**
	 * 返回指定月份天数，格式为yyyyMM
	 * 
	 * @param date
	 * @return
	 */
	public static String[] getMonthOfDays(String date) {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		// 如果写成年月日的形式的话，要写小d， 如："yyyy/MM/dd"
		// String date="201310";//要计算你想要的月份，改变这里即可
		try {
			// System.out.println(sdf.parse(date));
			cal.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		String str = "";
		for (int i = 0; i < days; i++) {
			if (i == days - 1) {
				str += date+(i + 1);
			} else {
				if (i + 1 < 10) {
					str +=date+ ("0" + (i + 1)) + ",";
				} else {
					str +=date+ (i + 1) + ",";
				}
			}

		}

		String[] day = { str };
		return day;
	}

	/**
	 * 得到指定日期所在月份第一天到该天的日期
	 * 
	 * @param date
	 * @return
	 */
	public static String[] getDaysInMonthOfDate(String date) {
		Calendar startCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		List<String> days = new ArrayList<String>();
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date endDate = stringToDate(date);
			Date startDate = df.parse(getFirstDayOfMonth(date));
			startCalendar.setTime(startDate);
			endCalendar.setTime(endDate);
			days.add(dateToString(startDate).replaceAll("-", ""));
			while (true) {
				startCalendar.add(Calendar.DAY_OF_MONTH, 1);
				if (startCalendar.getTimeInMillis() < endCalendar
						.getTimeInMillis()) {// TODO 转数组或是集合，看着写吧
					// System.out.println(df.format(startCalendar.getTime()));
					days.add(df.format(startCalendar.getTime()).replaceAll("-",
							""));
				} else {
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}

		return days.toArray(new String[days.size()]);
	}

	/**
	 * 得到指定日期所在年份第一个月到该月的月份
	 * 
	 * @param date
	 * @return
	 */
	public static String[] getMonthInYearOfDate(String date) {
		List<String> list = new ArrayList<String>();
		int month = Integer.parseInt(date.substring(5, 7));
		for (int i = 1; i < month; i++) {
			String m = i < 10 ? "0" + i : i + "";
			list.add(date.substring(0, 4) + m);
		}
		return list.toArray(new String[list.size()]);
	}

	/**
	 * 得到当前月份的上个月
	 * 
	 * @return yyyyMM
	 */
	public static String getLastMonth() {
		// 取得系统当前时间
		Calendar cal = Calendar.getInstance();
		// 月分减一,取得上月时间对象
		cal.add(Calendar.MONTH, -1);
		// 输出上月最后一天日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String lastmonth = sdf.format(cal.getTime());
		return lastmonth;
	}
	/**
	 * 得到一天96个时间点
	 * @return 96个时间点集合
	 */
	public static String[] getTime15Min() {

		String[] time15Min = new String[96];
		time15Min[0]="00:00";
		Calendar cal = Calendar.getInstance();
		// cal.setTime(new Date("2013-10-11 00:00:00"));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 00);
		for (int i = 1; i < 96; i++) {
			cal.add(Calendar.MINUTE, 15);
			String hour=(cal.get(Calendar.HOUR_OF_DAY)+"").length()>1?cal.get(Calendar.HOUR_OF_DAY)+"":"0"+cal.get(Calendar.HOUR_OF_DAY)+"";
			String minute=(cal.get(Calendar.MINUTE) + "").length()>1?cal.get(Calendar.MINUTE) + "":"0"+cal.get(Calendar.MINUTE) + "";
			time15Min[i] = hour+ ":"+minute;
			//System.out.println(time15Min[i]);
		}
		
		return time15Min;
	}

}
