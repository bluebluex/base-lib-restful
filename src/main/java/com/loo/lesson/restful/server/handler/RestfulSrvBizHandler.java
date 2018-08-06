package com.loo.lesson.restful.server.handler;

import com.loo.lesson.restful.vo.response.RestfulRespMessage;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 16:51
 **/
public interface RestfulSrvBizHandler {

    RestfulRespMessage excute(RestfulSrvBizHandlerContext context);
}
