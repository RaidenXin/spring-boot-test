package com.raiden.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * 除了静态解析方法，其余方法都有可能返回空
 */
@Slf4j
public final class LocalDateUtils {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static final String[] PARSE_PATTERNS = {
            "yyyy-MM-dd'T'HH:mm:ss",
            YYYY_MM_DD,
            "yyyyMMdd",
            YYYY_MM_DD_HH_MM_SS,
            "yyyy/MM/dd HH:mm:ss",
            "yyyy/MM/dd",
            "yyyy-MM-dd'T'HH:mm:ss.SSS",
            "HH:mm",
            "yyyy-MM-dd'T'HH:mm:ss+08:00",
            "yyyy-MM-dd HH:mm",
            "yyyy-MM-dd'T'HH:mm",
            "yyyy-MM-dd HH:mm:ss.SSS",
            "yyyy.MM.dd",
            "yyyy年MM月dd日"
    };

    private LocalDateUtils() {
    }

    public static Date parseDate(String date) {
        if (StringUtils.isNotBlank(date)) {
            try {
                return DateUtils.parseDate(date, PARSE_PATTERNS);
            } catch (ParseException e) {
                log.error("This string is not a known time type! Date: " + date);
            }
        }
        return null;
    }

    public static LocalTime parseLocalTime(String date) {
        LocalDateTime localDateTime = parseLocalDateTime(date);
        if (null == localDateTime) {
            return null;
        }
        LocalTime localTime = localDateTime.toLocalTime();
        return localTime;
    }


    public static LocalDateTime parseLocalDateTime(String date) {
        Date time = parseDate(date);
        return parseLocalDateTime(time);
    }

    public static LocalDateTime parseLocalDateTime(Date date) {
        if (null == date) {
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return localDateTime;
    }

    /**
     * 判断当前时间是否在2个日期之间，如果某个日期为空，则表示不做限制
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static boolean checkNowBetweenTowDays(String beginDate, String endDate) {
        LocalDateTime now = LocalDateTime.now();
        if (StringUtils.isNotBlank(beginDate)) {
            LocalDateTime begin = parseLocalDateTime(beginDate);
            if (begin != null && now.isBefore(begin)) {
                return false;
            }
        }
        if (StringUtils.isNotBlank(endDate)) {
            LocalDateTime end = parseLocalDateTime(endDate);
            if (end != null && now.isAfter(end)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断当前时间是否在2个日期之间，不包含时间
     * @param beginDate
     * @param endDate
     * @return
     */
    public static boolean checkDate(String currDate,String beginDate, String endDate) {
        LocalDate now = parseLocalDate(currDate);
        if(now == null){
            return false;
        }
        LocalDate begin = parseLocalDate(beginDate);
        if (begin != null && begin.isAfter(now)) {
            return false;
        }
        LocalDate end = parseLocalDate(endDate);
        if (end != null && end.isBefore(now)) {
            return false;
        }
        return true;
    }

    public static boolean isValid(String endDate) {
        LocalDateTime localEndDate = parseLocalDateTime(endDate);
        return localEndDate == null || localEndDate.compareTo(LocalDateTime.now()) >= 0;
    }

    public static LocalDate parseLocalDate(String date) {
        if(StringUtils.isNotBlank(date)){
            date = date.trim();
        }
        LocalDateTime localDateTime = parseLocalDateTime(date);
        return null == localDateTime ? null : localDateTime.toLocalDate();
    }

    public static LocalDate parseLocalDate(Date date) {
        LocalDateTime localDateTime = parseLocalDateTime(date);
        return null == localDateTime ? null : localDateTime.toLocalDate();
    }


    public static String format(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return df.format(localDateTime);
    }

    public static String format(LocalDate localDate, String pattern) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return df.format(localDate);
    }

    public static String format(LocalDateTime localDateTime, String pattern, String defaultValue) {
        if (null == localDateTime) {
            return defaultValue;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return df.format(localDateTime);
    }


    public static String format(LocalDate localDate, String pattern, String defaultValue) {
        if (null == localDate) {
            return defaultValue;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return df.format(localDate);
    }

    public static String format(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static String format(Date date, String pattern, String defaultValue) {
        if (null == date) {
            return defaultValue;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static String format(LocalTime time, String pattern) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return time.format(df);
    }

    /**
     * 是否是今天
     *
     * @param date
     * @df
     */
    public static boolean isToday(String date) {
        LocalDate time = LocalDateUtils.parseLocalDate(date);
        if (time == null) {
            return false;
        }
        return time.compareTo(LocalDate.now()) == 0;
    }

    /**
     * 日期字符串格式化成指定格式
     * 各种奇奇怪怪的日期格式，model统一用string接收了，就来个字符串日期操作吧
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static String formatDate(String dateStr, String pattern) {
        if (StringUtils.isNoneBlank(dateStr, pattern)) {
            LocalDateTime date = parseLocalDateTime(dateStr);
            if (date != null) {
                return date.format(DateTimeFormatter.ofPattern(pattern));
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取今天的日期+某个时间
     *
     * @param type Calender
     * @return
     */
    public static Date getNowPlusSomeTime(int plus, int type) {
        Calendar c = Calendar.getInstance();
        c.add(type, plus);
        return c.getTime();
    }

    /**
     * 计算日期差
     * 各种奇奇怪怪的日期格式，model统一用string接收了，就来个字符串日期操作吧
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int diffDay(String startDate, String endDate) {
        LocalDate localDateStart = parseLocalDate(startDate);
        if (localDateStart == null) {
            log.error("startDate parse error :" + startDate);
            return -1;
        }
        LocalDate localDateEnd = parseLocalDate(endDate);
        if (localDateEnd == null) {
            log.error("endDate parse error :" + startDate);
            return -1;
        }
        return (int) localDateStart.until(localDateEnd, ChronoUnit.DAYS);
    }

    public static long getSeconds(String dateTime) {
        LocalDateTime localDateTime = parseLocalDateTime(dateTime);
        return localDateTime == null ? 0 : localDateTime.toEpochSecond(ZoneOffset.of("+8"));
    }

    /**
     * 今天是否在实际范围内
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    public static final boolean isTodayWithinTheTimeFrame(String startDate,String endDate){
        if (StringUtils.isAnyBlank(startDate, endDate)){
            return false;
        }
        LocalDateTime startDateTime = parseLocalDateTime(startDate);
        LocalDateTime endDateTime = parseLocalDateTime(endDate);
        if (null == startDateTime || null == endDateTime){
            return false;
        }
        return isTodayWithinTheTimeFrame(startDateTime, endDateTime);
    }

    /**
     * 今天是否在实际范围内
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    public static final boolean isTodayWithinTheTimeFrame(LocalDateTime startDate,LocalDateTime endDate){
        LocalDateTime row = LocalDateTime.now();
        return row.compareTo(startDate) > -1 && row.compareTo(endDate) < 1;
    }

    /**
     * 判断某个时间是否在实际范围内
     * @param targetDate 目标时间
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    public static final boolean isTodayWithinTheTimeFrame(LocalDateTime targetDate,LocalDateTime startDate,LocalDateTime endDate){
        return targetDate != null && targetDate.compareTo(startDate) > -1 && targetDate.compareTo(endDate) < 1;
    }

    public static final int compare(String time1,String time2){
        LocalDateTime localDateTime1 = parseLocalDateTime(time1);
        LocalDateTime localDateTime2 = parseLocalDateTime(time2);
        if (localDateTime1 == null || localDateTime2 == null){
            throw new DateTimeParseException("比较时间格式错误: time1 = " + time1, time1, -1);
        }else if ( localDateTime2 == null){
            throw new DateTimeParseException("比较时间格式错误: time2 = " + time2, time2, -1);
        }
        return localDateTime1.compareTo(localDateTime2);
    }
}
