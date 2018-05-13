package com.bjtu.julie.Util;

/**
 * Created by carrey on 2018/4/14.
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    public String diffDate(String sd) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse(sd);
            long diff = new Date().getTime() - d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diff = new Date().getTime() - d.getTime();
        long r = 0;
        if (diff > year) {
            r = (diff / year);
            return r+"年前";
        } else if (diff > month) {
            r = (diff / month);
            return r+"个月前";
        } else if (diff > day) {
            r = (diff / day);
            return r+"天前";
        } else if (diff > hour) {
            r = (diff / hour);
            return r+"小时前";
        } else if (diff > minute) {
            r = (diff / minute);
            return r+"分钟前";
        } else
            return "刚刚";
    }
}