package com.loo.lesson.restful.client;

import com.loo.lesson.restful.adapter.rpc.service.RestfulHandlerAdaptRpcService;
import com.loo.lesson.restful.client.utils.RestfulClientUtils;
import com.loo.lesson.restful.filter.RestfulFilterChain;
import com.loo.lesson.restful.vo.request.RestfulRequest;
import com.loo.lesson.restful.vo.response.RestfulRespMessage;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import tomax.loo.lesson.cache.service.centralize.CentralizeCacheService;

import javax.annotation.Resource;
import java.util.Map;

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

    /**
     * 使用post 方法获取指定对象
     * @param requestParam
     * @param responseType
     * @param <T>
     * @return
     * @throws RestfulException
     */
    @SuppressWarnings("unchecked")
    protected <T> T postForObject(RestfulRequest requestParam, Class<T> responseType) throws RestfulException {
        try {
            String response = postForJSONObject(requestParam);
            if (StringUtils.isBlank(response)) {
                return null;
            }
            JSONObject obj = JSONObject.fromObject(response);
            return (T) JSONObject.toBean(obj, responseType);
        } catch (Exception e) {
            logger.error("使用post 方法获取指定对象");
            throw new RestfulException("使用post 方法获取指定对象", e);
        }
    }

    /**
     * 使用post 方法获取指定对象
     * @param requestParam
     * @param responseType
     * @param classMap
     * @param <T>
     * @return
     * @throws RestfulException
     */
    @SuppressWarnings("unchecked")
    protected <T> T postForObject(RestfulRequest requestParam, Class<T> responseType, Map classMap) throws RestfulException {
        if (classMap != null) {
            return this.postForObject(requestParam, responseType);
        }
        try {
            String response = postForJSONObject(requestParam);
            if (StringUtils.isBlank(response)) {
                return null;
            }
            JSONObject obj = JSONObject.fromObject(response);
            return (T) JSONObject.toBean(obj, responseType, classMap);
        } catch (Exception e) {
            logger.error("使用post 方法获取指定对象");
            throw new RestfulException("使用post 方法获取指定对象", e);
        }
    }

    protected RestfulRespMessage postForRespMessage(RestfulRequest requestParam) throws RestfulException {
        try {
            String response = postForJSONObject(requestParam);
            if (StringUtils.isBlank(response)) {
                return null;
            }
            return RestfulClientUtils.getResultRespMessage(response);
        } catch (Exception e) {
            logger.error("使用post 方法获取RestfulRespMessage对象 异常", e);
            throw new RestfulException("使用post 方法获取RestfulRespMessage对象 异常");
        }
    }

    public String postForJSONObject(RestfulRequest requestParam) throws RestfulException {
        if (this.restfulHandlerAdaptRpcService != null) {

        }
        return null;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
