package com.butao.ulifebiz.util;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * <p>
 * Description:时间工具类，提供时间和字符串之间的互相转换
 * </p>
 * <p>
 * Copyright:Copyright (c) 2012
 * </p>
 *
 * @author gujc
 */
public class TimeUtil {
    /**
     * 获取当前时间为yyyy-MM-dd HH:mm:ss格式的字符串
     *
     * @return
     */
    public static String calendarToString() {
        return calendarToString(Calendar.getInstance());
    }

    /**
     * 将Calendar类型转换为yyyy-MM-dd HH:mm:ss格式的字符串
     *
     * @param calendar
     * @return
     */
    public static String calendarToString(Calendar calendar) {
        if (calendar == null)
            return "";
        String result = simpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
        return result;
    }

    /* 惠抢购 用到的  特殊时间格式 */
    public static String getTime() {
        Calendar calendar = Calendar.getInstance();
        if (calendar == null)
            return "";
        String result = simpleDateFormat("yyyy-MM-dd%20HH").format(calendar.getTime());
        return result;
    }

    public static String getTime2() {
        Calendar calendar = Calendar.getInstance();
        if (calendar == null)
            return "";
        String result = simpleDateFormat("HH").format(calendar.getTime());
        return result;
    }

    public static String getTime1() {
        Calendar calendar = Calendar.getInstance();
        if (calendar == null)
            return "";
        String result = simpleDateFormat("yyyy-MM-dd%20").format(calendar.getTime());
        String resultString = simpleDateFormat("HH").format(calendar.getTime());
        int x = Integer.parseInt(resultString) + 1;
        String resu = result + String.valueOf(x);
        return resu;
    }

    public static String getDate() {
        Calendar calendar = Calendar.getInstance();
        if (calendar == null)
            return "";
        String result = simpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        return result;
    }
    public static String getDayDate() {
        Calendar calendar = Calendar.getInstance();
        if (calendar == null)
            return "";
        String result = simpleDateFormat("MM-dd").format(calendar.getTime());
        return result;
    }

    public static String getTimeDate() {
        Calendar calendar = Calendar.getInstance();
        if (calendar == null)
            return "";
        String result = simpleDateFormat("HH:mm").format(calendar.getTime());
        return result;
    }
    /**
     * 将Calendar类型转换为yyyyMMdd格式的字符串
     *
     * @param calendar
     * @return
     */
    public static String dayToString0(Calendar calendar) {
        if (calendar == null)
            return "";
        String result = simpleDateFormat("yyyyMMdd").format(calendar.getTime());
        return result;
    }

    /**
     * 获取当前月的上一个月
     *
     * @return
     */
    public static String lastMonth() {
        SimpleDateFormat format = simpleDateFormat("yyyyMM");
        String thisMonth = monthToString();
        Calendar calendar = stringToCalendar(thisMonth, "yyyyMM");
        calendar.add(Calendar.MONTH, -1);
        return format.format(calendar.getTime());
    }

    /**
     * 当前月份基础上提前3月
     *
     * @return
     */
    public static String next3Month() {
        SimpleDateFormat format = simpleDateFormat("yyyyMM");
        String thisMonth = monthToString();
        Calendar calendar = stringToCalendar(thisMonth, "yyyyMM");
        calendar.add(Calendar.MONTH, +3);
        return format.format(calendar.getTime());
    }

    /**
     * 将Calendar类型转换为yyyy-MM-dd格式的字符串
     *
     * @return
     */
    public static String dayToString1() {
        return simpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    }

    /**
     * 将Calendar类型转换为yyyy年MM月dd日格式的字符串
     *
     * @return
     */
    public static String dayToString2() {
        return simpleDateFormat("yyyy年MM月dd日").format(Calendar.getInstance().getTime());
    }

    /**
     * 将Calendar类型转换为yyyyMMdd格式的字符串
     *
     * @return
     */
    public static String dayToString3() {
        return dayToString0(Calendar.getInstance());
    }

    /**
     * 将Calendar类型转换为yyyyMM格式的字符串
     *
     * @return yyyyMM
     */
    public static String monthToString() {
        Calendar cal = Calendar.getInstance();
        return simpleDateFormat("yyyyMM").format(cal.getTime());
    }

    /**
     * 获取当前时间为HHmmss格式的字符串
     *
     * @return
     */
    public static String timeToString1() {
        return timeToString0(Calendar.getInstance());
    }

    /**
     * 将Calendar类型转换为HHmmss格式的字符串
     *
     * @param calendar
     * @return
     */
    public static String timeToString0(Calendar calendar) {
        if (calendar == null)
            return "";
        String result = simpleDateFormat("HHmmss").format(calendar.getTime());
        return result;
    }

    /**
     * 将Calendar类型转换为HH:mm:ss格式的字符串
     *
     * @param calendar
     * @return
     */
    public static String timeToString1(Calendar calendar) {
        if (calendar == null)
            return "";
        String result = simpleDateFormat("HH:mm:ss").format(calendar.getTime());
        return result;
    }

    /**
     * 将Calendar类型转换为pattern格式的字符串，若格式错误，转换为yyyy-MM-dd HH:mm:ss格式
     *
     * @param calendar
     * @param pattern
     * @return
     */
    public static String calendarToString(Calendar calendar, String pattern) {
        if (calendar == null)
            return "";
        try {
            return simpleDateFormat(pattern).format(calendar.getTime());
        } catch (Exception e) {
            return calendarToString(calendar);
        }
    }

    /**
     * 将yyyy-MM-dd格式的字符串转换为Calendar类型
     *
     * @param calendarString
     * @return
     */
    public static Calendar stringToCalendar(String calendarString) {
        if (calendarString == null || calendarString.trim().length() == 0) {
            return null;
        }
        String dateStr = calendarString;
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat formatter = simpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(formatter.parse(dateStr));
        } catch (ParseException e) {
            return null;
        }
        return calendar;
    }

    /**
     * 将字符串转换为Calendar类型
     *
     * @param calendarString
     * @param pattern
     * @return
     */
    public static Calendar stringToCalendar(String calendarString, String pattern) {
        SimpleDateFormat formatter = simpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(formatter.parse(calendarString));
        } catch (ParseException e) {
            return null;
        }
        return calendar;
    }

    /**
     * 检查日期格式是否正确
     *
     * @param day
     * @return boolean
     */
    public static boolean checkDayFormat(String day) {
        if (day == null) {
            return false;
        } else {
            try {
                simpleDateFormat("yyyy-MM-dd").parse(day);
                return true;
            } catch (ParseException e) {
                return false;
            }
        }
    }

    /**
     * 检查时间格式是否正确
     *
     * @param time
     * @return boolean
     */
    public static boolean checkTimeFormat(String time) {
        if (time == null) {
            return false;
        } else {
            try {
                simpleDateFormat("HH:mm:ss").parse(time);
                return true;
            } catch (ParseException e) {
                return false;
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat simpleDateFormat(String pattern) {
        return (new SimpleDateFormat(pattern));
    }

    /**
     * 将时间戳转换为yyyy-MM-dd 格式的字符串
     *
     * @param time
     * @return
     */
    public static String timeToString(String time) {
        if (time == null) {
            return "";
        }
        long ltime = Long.parseLong(time) * 1000;
        Date date = new Date(ltime);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    public static String timeToString3(String time) {
        if (time == null) {
            return "";
        }
        long ltime = Long.parseLong(time) * 1000;
        Date date = new Date(ltime);
        SimpleDateFormat df = new SimpleDateFormat("MM-dd");
        return df.format(date);
    }

    /**
     * 将时间戳转换为yyyy-MM-dd HH:mm:ss格式的字符串
     *
     * @param time
     * @return
     */
    public static String TimeToString(String time) {
        if (time == null) {
            return "";
        }
        long ltime = Long.parseLong(time) * 1000;
        Date date = new Date(ltime);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return df.format(date);
    }

    /**
     * 将时间戳转换为yyyy-MM-dd HH:mm:ss格式的字符串
     *
     * @param time
     * @return
     */
    public static String TimeToString1(String time) {
        if (time == null) {
            return "";
        }
        long ltime = Long.parseLong(time);
        Date date = new Date(ltime);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }
    public static String TimeString(String time) {
        if (time == null) {
            return "";
        }
        long ltime = Long.parseLong(time);
        Date date = new Date(ltime);
        SimpleDateFormat df = new SimpleDateFormat("MM-dd");
        return df.format(date);
    }
    public static String TimeYearToString(String time) {
        if (time == null) {
            return "";
        }
        long ltime = Long.parseLong(time);
        Date date = new Date(ltime);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }
    /**
     * 将时间戳转换为yyyy-MM-dd HH:mm:ss格式的字符串
     *
     * @param time
     * @return
     */
    public static String TimeToString2(String time) {
        if (time == null) {
            return "";
        }
        long ltime = Long.parseLong(time) * 1000;
        Date date = new Date(ltime);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);

    }

    /**
     * 掉此方法输入所要转换的时间输入例如（"2014-06-14"）返回时间戳
     *
     * @param time
     * @return
     */
    public static String dataOne(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd",
                Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime() / 1000;
            times = String.valueOf(l);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return times;
    }

    public static String DataOne(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime() / 1000;
            times = String.valueOf(l);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return times;
    }

    public static String dataOne2(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime() / 1000;
            times = String.valueOf(l);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return times;
    }

    public static String TimeDifference(String time, String nowtime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String min = "";
        try {
            Date d1 = df.parse(nowtime);
            Date d2 = df.parse(time);
            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
            if (diff > 0) {

            } else {
                long diff1 = d2.getTime() - d1.getTime();//这样得到的差值是微秒级别
                long days = diff1 / (1000 * 60 * 60 * 24);
                long hours = (diff1 - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                long minutes = (diff1 - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
                long second = (diff1 - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;
                min = String.valueOf(minutes + ":" + second);
            }

        } catch (Exception e) {
        }
        return min;
    }

    public static String[] WEEK = {
            "周日",
            "周一",
            "周二",
            "周三",
            "周四",
            "周五",
            "周六"
//	  "SUNDAY",
//	  "MONDAY",
//	  "TUESDAY",
//	  "WEDNESDAY",
//	  "THRUSDAY",
//	  "FRIDAY",
//	  "SATURDAY"
    };
    public static final int WEEKDAYS = 7;

    /**
     * 日期变量转成对应的星期字符串
     *
     * @param date
     * @return
     */
    public static String DateToWeek(String date) throws Exception {
        Date date2 = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            date2 = df.parse(date);
        } catch (Exception e) {
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date2);
        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayIndex < 1 || dayIndex > WEEKDAYS) {
            return null;
        }

        return WEEK[dayIndex - 1];

    }

    //把字符串转为日期
    public static Date ConverToDate(String strDate) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.parse(strDate);
    }

    //时间转换成字符串 给图片命名使用
    public static String getCharacterAndNumber() {
        String rel = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date curDate = new Date(System.currentTimeMillis());
        rel = formatter.format(curDate);
        Random random = new Random();
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        return rannum + rel;
    }
}
