package com.loo.lesson.restful.server.handler.service;

import com.loo.lesson.restful.exception.ServiceException;
import com.loo.lesson.restful.server.handler.model.RestfulSrvHandler;

import java.io.Serializable;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 16:53
 **/
public interface RestfulSrvHandlerService extends Serializable {
    /**
     * 获取业务处理 handler
     * @param bizCode
     * @param version
     * @return
     * @throws ServiceException
     */
    RestfulSrvHandler getRestfulSrvHandler(String bizCode, String version) throws ServiceException;
}
