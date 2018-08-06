package com.loo.lesson.restful.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-03 11:27
 **/
public class DateFormateUtils {

    public static String getFormateYYYYMMDD(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }

    public static String getForMateYYYYMMDDHHMMSSTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }
}
