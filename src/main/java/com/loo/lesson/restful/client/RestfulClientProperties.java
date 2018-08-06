package com.loo.lesson.restful.client;

import java.io.Serializable;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 17:31
 **/
public class RestfulClientProperties implements Serializable {

    private static final long serialVersionUID = -4754020363923477761L;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
