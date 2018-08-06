package com.loo.lesson.restful.vo.response;

import java.io.Serializable;
import java.util.List;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 17:38
 **/
public class RestfulRespPageBody extends RestfulRespBody {

    private static final long serialVersionUID = -4154980629973733340L;

    private List<Serializable> dataList;

    public List<Serializable> getDataList() {
        return dataList;
    }

    public void setDataList(List<Serializable> dataList) {
        this.dataList = dataList;
    }
}
