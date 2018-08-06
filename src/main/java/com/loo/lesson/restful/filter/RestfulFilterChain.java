package com.loo.lesson.restful.filter;

import com.loo.lesson.restful.client.RestfulException;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: base-core
 * @description: 过滤器链接口
 * @author: Tomax
 * @create: 2018-08-02 16:50
 **/
public class RestfulFilterChain implements BeanFactoryAware, ApplicationListener<ContextRefreshedEvent> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private List<RestfulFilter> filterList = new ArrayList<>();

    private ThreadLocal<Integer> positions = new ThreadLocal<>();

    private DefaultListableBeanFactory beanFactory;

    public String doFilter(HttpMethod method) throws RestfulException {
        List<RestfulFilter> filters = filterList;
        int pos = positions.get() == null ? 0 : positions.get();
        if (pos < filters.size()) {
            positions.set(pos + 1);
            return filters.get(pos).doFilter(method, this);
        }
        positions.remove();
        logger.info("Finished all the filtering. Send the http request");
        return doHttpPost(method);
    }

    private String doHttpPost(HttpMethod method) throws RestfulException {
        if (method == null) {
            throw new RestfulException("NULL method");
        }
        String response = "";
        HttpClient client = null;
        try {
            client = new HttpClient();
            // 请求连接服务器未响应请求时间
            client.getHttpConnectionManager().getParams().setConnectionTimeout(20000);
            // 整个数据请求socket连接超时时间
            client.getHttpConnectionManager().getParams().setSoTimeout(60000);
            client.getParams().setParameter("http.protocol.content-charset", "UTF-8");
            client.executeMethod(method);
            response = IOUtils.toString(method.getResponseBodyAsStream(), "utf-8");
        } catch (Exception e) {
            logger.error("使用post 方法获取JSON对象requestParam={}", method.getQueryString(), e);
            throw new RestfulException("使用post 方法获取JSON对象requestParam={}"+method.getQueryString(), e);
        } finally {
            // 避免出现CLOSE_WAIT TCP connections
            if (client != null) {
                client.getHttpConnectionManager().closeIdleConnections(0);
            }
            method.releaseConnection();
        }
        return response;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Map<String, RestfulFilter> filterMap = beanFactory.getBeansOfType(RestfulFilter.class);
        if (MapUtils.isEmpty(filterMap)) {
            logger.error("None of restful filters registered");
            return;
        }
        for (Map.Entry<String, RestfulFilter> filterEntry : filterMap.entrySet()) {
            filterList.add(filterEntry.getValue());
        }

    }
}
