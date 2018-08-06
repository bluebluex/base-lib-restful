package com.loo.lesson.restful.server.handler.service;

import com.loo.lesson.restful.server.handler.model.RestfulSrvHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tomax.loo.lesson.cache.service.local.LocalCacheService;

import javax.annotation.Resource;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 17:34
 **/
public class RestfulSrvHandlerCacheServiceImpl implements RestfulSrvHandlerCacheService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource(name = "businessSettingCache")
    private LocalCacheService businessSettingCache;

    @Override
    public RestfulSrvHandler getRestfulSrvHandler(String bizCode, String version) {
        return (RestfulSrvHandler)businessSettingCache.get(String.format(CACHE_NAME, bizCode, version));
    }

    @Override
    public void putRestfulSrvHandler(String bizCode, String version, RestfulSrvHandler value) {
        businessSettingCache.put(String.format(CACHE_NAME, bizCode, version), value);
    }
}
