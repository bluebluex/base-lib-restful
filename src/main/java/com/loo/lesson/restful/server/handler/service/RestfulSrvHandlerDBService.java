package com.loo.lesson.restful.server.handler.service;

import com.loo.lesson.restful.exception.ServiceException;
import com.loo.lesson.restful.server.handler.model.RestfulSrvHandler;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 17:34
 **/
public interface RestfulSrvHandlerDBService {

    RestfulSrvHandler getRestfulSrvHandlerByDB(String bizCode, String version) throws ServiceException;
}
