package com.loo.lesson.restful.vo.response;

import com.loo.lesson.restful.utils.JacksonUtils;

import java.io.Serializable;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 17:37
 **/
public class RestfulRespBody implements Serializable {

    private static final long serialVersionUID = -8378068320915045059L;

    protected String rstCode;

    protected String rstMsg;

    public RestfulRespBody() {
    }

    public RestfulRespBody(String rstCode, String rstMsg) {
        this.rstCode = rstCode;
        this.rstMsg = rstMsg;
    }

    public String getRstCode() {
        return rstCode;
    }

    public void setRstCode(String rstCode) {
        this.rstCode = rstCode;
    }

    public String getRstMsg() {
        return rstMsg;
    }

    public void setRstMsg(String rstMsg) {
        this.rstMsg = rstMsg;
    }

    @Override
    public String toString() {
        return JacksonUtils.jsonObjSerializer(this);
    }
}
