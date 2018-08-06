package com.loo.lesson.restful.server.handler.dao;

import com.loo.lesson.restful.server.handler.model.RestfulSrvHandler;
import org.springframework.stereotype.Repository;

/**
 * @program: base-core
 * @description: 业务处理 handler
 * @author: Tomax
 * @create: 2018-08-02 16:52
 **/
@Repository
public class RestfulSrvHandlerDao {
    /**
     * 获取业务处理handler
     * @param bizCode
     * @param version
     * @return
     */
    RestfulSrvHandler getRestfulSrvHandler(String bizCode, String version) {
        String hql = "from RestfulSrvHandler t where t.bizcode = ? and t.version = ?";
        return new RestfulSrvHandler();
    }
}
