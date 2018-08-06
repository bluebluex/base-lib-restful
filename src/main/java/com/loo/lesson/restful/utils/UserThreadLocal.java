package com.loo.lesson.restful.utils;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-03 14:54
 **/
public class UserThreadLocal {

    private static ThreadLocal<Long> userThreadLocal = new ThreadLocal<>();

    public static Long getUid() {
        return userThreadLocal.get();
    }

    public static void setUid(Long uid) {
        userThreadLocal.set(uid);
    }
}
