package com.loo.lesson.restful.vo.response;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 17:38
 **/
@XmlRootElement
public class RestfulRespDataBody extends RestfulRespBody {

    private static final long serialVersionUID = -8378068320915045059L;

    protected Object data;

    public RestfulRespDataBody() {
    }

    public RestfulRespDataBody(String rstCode, String rstMsg, Object data) {
        super(rstCode, rstMsg);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
