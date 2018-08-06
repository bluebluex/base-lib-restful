package com.loo.lesson.restful.client.utils;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 17:30
 **/
public class RestfulThreadLoacl {

    private static ThreadLocal<RestfulRequestInfo> restfulRequestInfoThreadLocal = new ThreadLocal<>();

    public static RestfulRequestInfo getRestfulRequestInfo() {
        return restfulRequestInfoThreadLocal.get();
    }

    public static void setRestfulRequestInfo(RestfulRequestInfo restfulRequestInfo) {
        restfulRequestInfoThreadLocal.set(restfulRequestInfo);
    }

    public static void removeRestfulRequestInfo() {
        restfulRequestInfoThreadLocal.remove();
    }
}
