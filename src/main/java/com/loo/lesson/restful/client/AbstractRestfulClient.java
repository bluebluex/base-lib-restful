package com.loo.lesson.restful.client;

import com.loo.lesson.restful.adapter.rpc.service.RestfulHandlerAdaptRpcService;
import com.loo.lesson.restful.filter.RestfulFilterChain;
import com.loo.lesson.restful.vo.request.RestfulRequest;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import tomax.loo.lesson.cache.service.centralize.CentralizeCacheService;

import javax.annotation.Resource;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 17:31
 **/
public abstract class AbstractRestfulClient implements InitializingBean, BeanFactoryAware {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private CentralizeCacheService centralizeCacheService;

    private boolean initCache = false;

    private RestfulHandlerAdaptRpcService restfulHandlerAdaptRpcService;

    private BeanFactory beanFactory;
    @Resource(name = "restfulFilterChain")
    private RestfulFilterChain chain;

    @SuppressWarnings("unchecked")
    protected <T> T postForObject(RestfulRequest requestParam, Class<T> responstType) throws RestfulException {
        try {
            String response = postForJSONObject(requestParam);
            if (StringUtils.isBlank(response)) {
                return null;
            }
            JSONObject obj = JSONObject.fromObject(response);
            return (T) JSONObject.toBean(obj, responstType);
        } catch (Exception e) {
            logger.error("使用post 方法获取指定对象");
            throw new RestfulException("使用post 方法获取指定对象", e);
        }
    }

    private String postForJSONObject(RestfulRequest requestParam) {
        return null;
    }
}
