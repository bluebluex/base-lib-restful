package com.loo.lesson.restful.server.handler.service;

import com.loo.lesson.restful.server.handler.model.RestfulSrvHandler;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 17:33
 **/
public interface RestfulSrvHandlerCacheService {

    String CACHE_NAME = "restful_srv_handler_cache_%s_%s";

    RestfulSrvHandler getRestfulSrvHandler(String bizCode, String version);

    void putRestfulSrvHandler(String bizCode, String version, RestfulSrvHandler vaue);
}
