package com.loo.lesson.restful.server.handler;

import com.loo.lesson.restful.server.handler.model.RestfulSrvBizReqSupport;
import com.loo.lesson.restful.vo.request.RestfulReqHead;
import com.loo.lesson.restful.vo.request.RestfulRequest;

import java.io.Serializable;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 17:36
 **/
public class RestfulSrvBizHandlerContext {

    private RestfulRequest restfulRequest;

    private RestfulReqHead head;

    private Serializable body;

    private String reqJson;

    private RestfulSrvBizReqSupport reqSupport;

    public RestfulRequest getRestfulRequest() {
        return restfulRequest;
    }

    public void setRestfulRequest(RestfulRequest restfulRequest) {
        this.restfulRequest = restfulRequest;
    }

    public RestfulReqHead getHead() {
        return head;
    }

    public void setHead(RestfulReqHead head) {
        this.head = head;
    }

    public Serializable getBody() {
        return body;
    }

    public void setBody(Serializable body) {
        this.body = body;
    }

    public String getReqJson() {
        return reqJson;
    }

    public void setReqJson(String reqJson) {
        this.reqJson = reqJson;
    }

    public RestfulSrvBizReqSupport getReqSupport() {
        return reqSupport;
    }

    public void setReqSupport(RestfulSrvBizReqSupport reqSupport) {
        this.reqSupport = reqSupport;
    }
}
