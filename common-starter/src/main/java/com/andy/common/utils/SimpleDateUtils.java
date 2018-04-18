package com.andy.common.utils;

import org.springframework.util.ObjectUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * <p>Description: 实现java日期格式化 </p>
 * @author wuqiong  2017年8月31日
 */
public final class SimpleDateUtils {

    /** 日期模板集合 */
    private static final ConcurrentMap<Patten,ThreadLocal<SimpleDateFormat>> PATTEN_THREAD_LOCAL_CONCURRENT_MAP;

    /**
     * <pre> SimpleDateUtils.to_DATE_ONLY_FORMAT(new Date())="2017-09-01 12:35:30" </pre>
     */
    public static String to_DATE_TIME_FORMAT(Date params) {
        CheckParamsIsEmpty(params);
        return PATTEN_THREAD_LOCAL_CONCURRENT_MAP.get(Patten.DATE_TIME_FORMAT).get().format(params);
    }
    /**
     *  SimpleDateUtils.parse_DATE_TIME_FORMAT("2017-09-01 12:35:30")=new Date()
     */
    public static Date parse_DATE_TIME_FORMAT(String params) throws ParseException{
        CheckParamsIsEmpty(params);
        return PATTEN_THREAD_LOCAL_CONCURRENT_MAP.get(Patten.DATE_TIME_FORMAT).get().parse(params);
    }


    /**
     * <pre> SimpleDateUtils.to_DATE_ONLY_FORMAT(new Date())="2017-09-01" </pre>
     */
    public static String to_DATE_ONLY_FORMAT(Date params) {
        CheckParamsIsEmpty(params);
        return PATTEN_THREAD_LOCAL_CONCURRENT_MAP.get(Patten.DATE_ONLY_FORMAT).get().format(params);
    }
    /**
     * <pre> SimpleDateUtils.parse_DATE_ONLY_FORMAT("2017-09-01")=new Date() </pre>
     */
    public static Date parse_DATE_ONLY_FORMAT(String params) throws ParseException {
        CheckParamsIsEmpty(params);
        return PATTEN_THREAD_LOCAL_CONCURRENT_MAP.get(Patten.DATE_ONLY_FORMAT).get().parse(params);
    }


    /**
     * <pre> SimpleDateUtils.to_DATE_ONLY_FORMAT(new Date())="15:35:23" </pre>
     */
    public static String to_TIME_FORMAT(Date params) {
        CheckParamsIsEmpty(params);
        return PATTEN_THREAD_LOCAL_CONCURRENT_MAP.get(Patten.TIME_FORMAT).get().format(params);
    }
    /**
     * <pre> SimpleDateUtils.parse_DATE_ONLY_FORMAT("15:35:23")=new Date() </pre>
     */
    public static Date parse_TIME_FORMAT(String params) throws ParseException {
        CheckParamsIsEmpty(params);
        return PATTEN_THREAD_LOCAL_CONCURRENT_MAP.get(Patten.TIME_FORMAT).get().parse(params);
    }



    static{
        PATTEN_THREAD_LOCAL_CONCURRENT_MAP=new ConcurrentHashMap<Patten, ThreadLocal<SimpleDateFormat>>();
        for (final Patten patten : Patten.values()) {
            PATTEN_THREAD_LOCAL_CONCURRENT_MAP.put(patten, new ThreadLocal<SimpleDateFormat>() {
                @Override
                public SimpleDateFormat initialValue() {
                    return new SimpleDateFormat(patten.getPatten());
                }
            });
        }
    }

    /**
     * 方法描述: 检查参数是否非空
     * @param obj
     * @author wuqiong 2017年8月31日 下午1:46:50
     */
    private static void CheckParamsIsEmpty(Object obj) {
        if (ObjectUtils.isEmpty(obj)){
            throw new IllegalArgumentException("The params must not be null");
        }
    }


    /**
     * <p>Description: 日期格式化模式枚举 </p>
     * <p>Company:有限公司 </p>
     * @author wuqiong  2017年8月31日
     */
    private enum Patten{
        /** 格式：年－月－日 小时：分钟：秒 **/
        DATE_TIME_FORMAT("yyyy-MM-dd HH:mm:ss"),

        /** 格式：年－月－日 小时：分钟 **/
        DATE_HOUR_MINUTE_FORMAT("yyyy-MM-dd HH:mm"),

        /** 格式：年－月－日 **/
        DATE_ONLY_FORMAT("yyyy-MM-dd"),

        /** 格式：月－日 **/
        MONTH_DAY_FORMAT("MM-dd"),

        /** 格式：小时：分钟：秒 **/
        TIME_FORMAT("HH:mm:ss");


        String patten;
        Patten(String patten){
            this.patten=patten;
        }
        public String getPatten() {
            return patten;
        }
    }


}
