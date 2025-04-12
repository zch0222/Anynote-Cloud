package com.anynote.core.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * 向下取整到最近的小时。
     * @param date 需要处理的日期对象
     * @return 取整后的日期对象
     */
    public static Date roundDownToHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 向上取整到下一个小时。
     * @param date 需要处理的日期对象
     * @return 取整后的日期对象
     */
    public static Date roundUpToHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.HOUR_OF_DAY, 1); // 如果已经是整点，这里会多加一个小时
        return calendar.getTime();
    }

    public static Calendar buildCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static String getCurrentDateString() {
        // 获取当前日期
        LocalDate today = LocalDate.now();
        // 创建格式化器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        // 格式化日期
        return today.format(formatter);
    }

    public static Date getYesterdayUsingCalendar() {
        Calendar calendar = Calendar.getInstance();  // 获取当前日期和时间的Calendar实例
        calendar.add(Calendar.DATE, -1);             // 将日期减一天来获取昨天的日期
        return calendar.getTime();                  // 将Calendar转换为Date
    }

    public static String getDateString(Date date) {
        // 将Date转换为LocalDateTime
        LocalDateTime localDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        // 创建格式化器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        // 格式化日期
        return formatter.format(localDateTime);
    }

    /**
     * 获取今天开始
     * @return 今天的00:00:00
     */
    public static Date getStartOfDay() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay().withNano(0);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取今天结束
     * @return 今天的23:59:59
     */
    public static Date getEndOfDay() {
        LocalDate today = LocalDate.now();
        LocalDateTime endOfDay = today.atTime(23, 59, 59, 0);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取今天开始
     * @return 今天的00:00:00
     */
    public static Date getStartOfDay(Date time) {
        LocalDate today = time.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDateTime startOfDay = today.atStartOfDay().withNano(0);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取今天结束
     * @return 今天的23:59:59
     */
    public static Date getEndOfDay(Date time) {
        LocalDate today = time.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDateTime endOfDay = today.atTime(23, 59, 59, 0);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getStartOfMinute(Date time) {
        LocalDateTime now = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(time.getTime()),
                ZoneId.systemDefault()
        );
        LocalDateTime startOfMinute = now.withSecond(0).withNano(0);
        return Date.from(startOfMinute.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getEndOfMinute(Date time) {
        LocalDateTime now = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(time.getTime()),
                ZoneId.systemDefault()
        );;
        LocalDateTime endOfMinute = now.withSecond(59).withNano(0);
        return Date.from(endOfMinute.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getStartOfHour(Date time) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(time.getTime()),
                ZoneId.systemDefault()
        );
        LocalDateTime startOfHour = localDateTime.withMinute(0).withSecond(0).withNano(0);
        return Date.from(startOfHour.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getEndOfHour(Date time) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(time.getTime()),
                ZoneId.systemDefault()
        );
        LocalDateTime endOfHour = localDateTime.withMinute(59).withSecond(59).withNano(0);
        return Date.from(endOfHour.atZone(ZoneId.systemDefault()).toInstant());
    }

    // 获取下一天的开始时间（00:00:00）
    public static Date getStartOfNextDay() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDateTime startOfNextDay = tomorrow.atStartOfDay().withNano(0);
        return Date.from(startOfNextDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    // 获取下一天的结束时间（23:59:59）
    public static Date getEndOfNextDay() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDateTime endOfNextDay = tomorrow.atTime(23, 59, 59, 0);
        return Date.from(endOfNextDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    // 获取下一天的开始时间（00:00:00）
    public static Date getStartOfNextDay(Date time) {
        LocalDate tomorrow = time.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate().plusDays(1);
        LocalDateTime startOfNextDay = tomorrow.atStartOfDay().withNano(0);
        return Date.from(startOfNextDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    // 获取下一天的结束时间（23:59:59）
    public static Date getEndOfNextDay(Date time) {
        LocalDate tomorrow = time.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate().plusDays(1);
        LocalDateTime endOfNextDay = tomorrow.atTime(23, 59, 59, 0);
        return Date.from(endOfNextDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    // 获取下一分钟的起始时间（秒和纳秒为0）
    public static Date getStartOfNextMinute() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextMinute = now.plusMinutes(1);
        LocalDateTime startOfNextMinute = nextMinute.withSecond(0).withNano(0);
        return Date.from(startOfNextMinute.atZone(ZoneId.systemDefault()).toInstant());
    }

    // 获取下一分钟的结束时间（59秒，0纳秒）
    public static Date getEndOfNextMinute() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextMinute = now.plusMinutes(1);
        LocalDateTime endOfNextMinute = nextMinute.withSecond(59).withNano(0);
        return Date.from(endOfNextMinute.atZone(ZoneId.systemDefault()).toInstant());
    }

    // 获取下一分钟的起始时间（秒和纳秒为0）
    public static Date getStartOfNextMinute(Date time) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(time.getTime()),
                ZoneId.systemDefault()
        );

        LocalDateTime nextMinute = localDateTime.plusMinutes(1);
        LocalDateTime startOfNextMinute = nextMinute.withSecond(0).withNano(0);
        return Date.from(startOfNextMinute.atZone(ZoneId.systemDefault()).toInstant());
    }

    // 获取下一分钟的结束时间（59秒，0纳秒）
    public static Date getEndOfNextMinute(Date time) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(time.getTime()),
                ZoneId.systemDefault()
        );
        LocalDateTime nextMinute = localDateTime.plusMinutes(1);
        LocalDateTime endOfNextMinute = nextMinute.withSecond(59).withNano(0);
        return Date.from(endOfNextMinute.atZone(ZoneId.systemDefault()).toInstant());
    }

    // 获取下一小时的起始时间（分钟、秒和纳秒为0）
    public static Date getStartOfNextHour() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextHour = now.plusHours(1);
        LocalDateTime startOfNextHour = nextHour.withMinute(0).withSecond(0).withNano(0);
        return Date.from(startOfNextHour.atZone(ZoneId.systemDefault()).toInstant());
    }

    // 获取下一小时的结束时间（59分59秒纳秒）
    public static Date getEndOfNextHour() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextHour = now.plusHours(1);
        LocalDateTime endOfNextHour = nextHour.withMinute(59).withSecond(59).withNano(0);
        return Date.from(endOfNextHour.atZone(ZoneId.systemDefault()).toInstant());
    }

    // 获取下一小时的起始时间（分钟、秒和纳秒为0）
    public static Date getStartOfNextHour(Date time) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(time.getTime()),
                ZoneId.systemDefault()
        );
        LocalDateTime nextHour = localDateTime.plusHours(1);
        LocalDateTime startOfNextHour = nextHour.withMinute(0).withSecond(0).withNano(0);
        return Date.from(startOfNextHour.atZone(ZoneId.systemDefault()).toInstant());
    }

    // 获取下一小时的结束时间（59分59秒纳秒）
    public static Date getEndOfNextHour(Date time) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(time.getTime()),
                ZoneId.systemDefault()
        );
        LocalDateTime nextHour = localDateTime.plusHours(1);
        LocalDateTime endOfNextHour = nextHour.withMinute(59).withSecond(59).withNano(0);
        return Date.from(endOfNextHour.atZone(ZoneId.systemDefault()).toInstant());
    }

}
