package com.loo.lesson.restful.vo.request;

import java.io.Serializable;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 16:39
 **/
public class RestfulRequest implements Serializable {

    private static final long serialVersionUID = 6276217223496560489L;

    RestfulReqHead head;
    Serializable body;

    public RestfulRequest() {
        super();
    }

    public RestfulRequest(RestfulReqHead head, Serializable body) {
        this.head = head;
        this.body = body;
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
}
