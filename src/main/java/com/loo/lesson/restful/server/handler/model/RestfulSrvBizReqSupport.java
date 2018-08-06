package com.loo.lesson.restful.server.handler.model;

import com.loo.lesson.restful.server.handler.RestfulSrvBizHandler;
import com.loo.lesson.restful.vo.request.RestfulReqHead;
import com.loo.lesson.restful.vo.request.RestfulRequest;
import com.loo.lesson.restful.vo.response.RestfulRespMessage;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 17:32
 **/
public class RestfulSrvBizReqSupport implements Serializable {

    private static final long serialVersionUID = 6183375899666926919L;

    private Long logid;

    private String reqParams;

    private RestfulRequest reqData = null;

    private RestfulReqHead reqHead = null;

    private Serializable reqBody = null;

    private RestfulRespMessage respData = null;

    private Exception exception = null;

    private boolean paramParseRst = true;

    private RestfulSrvBizHandler srvBizHandler = null;

    private Date startTime = new Date();

    private Date endTime;

    public Long getLogid() {
        return logid;
    }

    public void setLogid(Long logid) {
        this.logid = logid;
    }

    public String getReqParams() {
        return reqParams;
    }

    public void setReqParams(String reqParams) {
        this.reqParams = reqParams;
    }

    public RestfulRequest getReqData() {
        return reqData;
    }

    public void setReqData(RestfulRequest reqData) {
        this.reqData = reqData;
    }

    public RestfulReqHead getReqHead() {
        return reqHead;
    }

    public void setReqHead(RestfulReqHead reqHead) {
        this.reqHead = reqHead;
    }

    public Serializable getReqBody() {
        return reqBody;
    }

    public void setReqBody(Serializable reqBody) {
        this.reqBody = reqBody;
    }

    public RestfulRespMessage getRespData() {
        return respData;
    }

    public void setRespData(RestfulRespMessage respData) {
        this.respData = respData;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public boolean isParamParseRst() {
        return paramParseRst;
    }

    public void setParamParseRst(boolean paramParseRst) {
        this.paramParseRst = paramParseRst;
    }

    public RestfulSrvBizHandler getSrvBizHandler() {
        return srvBizHandler;
    }

    public void setSrvBizHandler(RestfulSrvBizHandler srvBizHandler) {
        this.srvBizHandler = srvBizHandler;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
