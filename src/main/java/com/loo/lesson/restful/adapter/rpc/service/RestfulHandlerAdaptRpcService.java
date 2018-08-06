package com.loo.lesson.restful.adapter.rpc.service;

import com.loo.lesson.restful.exception.ServiceException;
import com.loo.lesson.restful.vo.request.RestfulRequest;
import com.loo.lesson.restful.vo.response.RestfulRespMessage;

import java.io.Serializable;

/**
 * @program: base-core
 * @description: Restful 适配PRC调用服务
 * @author: Tomax
 * @create: 2018-08-02 16:33
 **/
public interface RestfulHandlerAdaptRpcService extends Serializable {
    /**
     * 调用远程Rpc服务
     * @param requestParam
     * @return
     * @throws ServiceException
     */
    RestfulRespMessage invokeRmtRestfulHandler(RestfulRequest requestParam) throws ServiceException;
}
