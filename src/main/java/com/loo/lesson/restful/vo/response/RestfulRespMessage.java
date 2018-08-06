package com.loo.lesson.restful.vo.response;

import com.loo.lesson.restful.server.handler.RestfulSrvBizHandlerResultConstants;
import org.apache.commons.lang.time.DateFormatUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 16:36
 **/
public class RestfulRespMessage implements Serializable {


    private static final long serialVersionUID = -5604191480082736623L;

    private RestfulRespHead head;

    private RestfulRespDataBody body;

    public RestfulRespMessage() {
    }

    public RestfulRespMessage(RestfulRespHead head) {
        this.head = head;
    }

    public RestfulRespMessage(RestfulRespHead head, RestfulRespDataBody body) {
        this.head = head;
        this.body = body;
    }

    public RestfulRespMessage(String rstCode, String rstMsg) {
        RestfulRespHead head = new RestfulRespHead();
        Date now = new Date();
        head.setSendDate(DateFormatUtils.format(now, "yyyyMMdd"));
        head.setSendTime(DateFormatUtils.format(now, "yyyyMMddHHmmss"));

        RestfulRespDataBody body = new RestfulRespDataBody();
        body.setRstCode(rstCode);
        body.setRstMsg(rstMsg);

        this.setHead(head);
        this.setBody(body);
    }

    public RestfulRespMessage(String rstCode, String rstMsg, Object data) {
        RestfulRespHead head = new RestfulRespHead();
        Date now = new Date();
        head.setSendDate(DateFormatUtils.format(now, "yyyyMMdd"));
        head.setSendTime(DateFormatUtils.format(now, "yyyyMMddHHmmss"));

        RestfulRespDataBody body = new RestfulRespDataBody();
        body.setRstCode(rstCode);
        body.setRstMsg(rstMsg);
        body.setData(data);

        this.setHead(head);
        this.setBody(body);
    }

    public RestfulRespHead getHead() {
        return head;
    }

    public void setHead(RestfulRespHead head) {
        this.head = head;
    }

    public RestfulRespDataBody getBody() {
        return body;
    }

    public void setBody(RestfulRespDataBody body) {
        this.body = body;
    }

    public boolean isSuccessed() {
        return RestfulSrvBizHandlerResultConstants.RST_CODE_SUCCESS.equals(body.getRstCode());
    }

    /**
     * 防止出现 json INFO net.sf.json.JSONObejct - Property 'successed' of class
     * com.loo.lesson.restful.vo.response.RestfulRespMessage has no write method. SKIPPED
     * @param successed
     */
    public void setSuccessed(boolean successed) {}
}
