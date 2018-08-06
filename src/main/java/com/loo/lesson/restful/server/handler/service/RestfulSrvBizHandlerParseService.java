package com.loo.lesson.restful.server.handler.service;

import com.loo.lesson.restful.server.handler.model.RestfulSrvBizReqSupport;

import java.io.Serializable;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 17:32
 **/
public interface RestfulSrvBizHandlerParseService extends Serializable {
    /**
     * 解析请求参数
     * @param reqData
     * @return
     */
    RestfulSrvBizReqSupport decodeRestfulSrvBizReqParam(String reqData);
}
