package com.loo.lesson.restful.server.handler.service;

import com.loo.lesson.restful.exception.ServiceException;
import com.loo.lesson.restful.server.handler.model.RestfulSrvHandler;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 17:34
 **/
public class RestfulSrvHandlerServiceImpl implements RestfulSrvHandlerService {

    private static final long serialVersionUID = -949950423319175190L;

    private RestfulSrvHandlerCacheService restfulSrvHandlerCacheService;

    private RestfulSrvHandlerDBService restfulSrvHandlerDBService;

    @Override
    public RestfulSrvHandler getRestfulSrvHandler(String bizCode, String version) throws ServiceException {
        RestfulSrvHandler srvHandler = restfulSrvHandlerCacheService.getRestfulSrvHandler(bizCode, version);
        if (srvHandler == null) {
            srvHandler = restfulSrvHandlerDBService.getRestfulSrvHandlerByDB(bizCode, version);
            if (srvHandler != null) {
                restfulSrvHandlerCacheService.putRestfulSrvHandler(bizCode, version, srvHandler);
            }
        }
        return srvHandler;
    }
}
