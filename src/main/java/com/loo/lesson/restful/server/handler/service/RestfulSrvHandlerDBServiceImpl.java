package com.loo.lesson.restful.server.handler.service;

import com.loo.lesson.restful.exception.ServiceException;
import com.loo.lesson.restful.server.handler.dao.RestfulSrvHandlerDao;
import com.loo.lesson.restful.server.handler.model.RestfulSrvHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 17:34
 **/
@Service
public class RestfulSrvHandlerDBServiceImpl implements RestfulSrvHandlerDBService {

    @Resource
    private RestfulSrvHandlerDao restfulSrvHandlerDao;

    @Override
    public RestfulSrvHandler getRestfulSrvHandlerByDB(String bizCode, String version) throws ServiceException {
        return null;
    }
}
