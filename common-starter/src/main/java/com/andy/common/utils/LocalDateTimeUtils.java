package com.andy.common.utils;

import java.time.*;
import java.util.Date;

/**
 * <p>ClassName: 日期计算调整类 </p>
 * <p>Description: </p>
 * <p>@author wuqiong  2018/1/29 13:48 </p>
 */
public class LocalDateTimeUtils {

    public static final ZoneId zoneId = ZoneId.systemDefault();

    /**
     * 方法描述: 将java.time.LocalDate 转化为 java.util.Date
     * @params LocalDate
     * @return Date
     * @author wuqiong  2018/1/29 11:17
     */
    public static Date localDateToDate(final LocalDate localDate){
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(zoneId);//使用系统默认时区
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * 方法描述: 将java.time.LocalDateTime 转化为 java.util.Date
     * @params LocalDateTime
     * @return Date
     * @author wuqiong  2018/1/29 11:21
     */
    public static Date localDateTimeToDate(final LocalDateTime localDateTime){
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);//使用系统默认时区
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * 方法描述: 获取两个时间差
     * @params LocalDateTime beginTime,LocalDateTime endTime
     * @return Duration 返回一个持续时间
     *  <p>toNanos()//纳秒</p>
     *  <p>toMillis()//毫秒</p>
     *  <p>toMinutes()//分钟</p>
     *  <p>toHours()//小时</p>
     *  <p>toDays()//天数</p>
     * @author wuqiong  2018/1/29 13:05
     */
    public static Duration localBetween(final LocalDateTime beginTime, final LocalDateTime endTime){
        return Duration.between(beginTime,endTime);
    }




}
