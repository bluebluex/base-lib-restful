package com.loo.lesson.restful.filter;

import com.loo.lesson.restful.client.RestfulException;
import org.apache.commons.httpclient.HttpMethod;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 16:50
 **/
public interface RestfulFilter {
    /**
     * 调用下一个过滤器过滤
     * @param method
     * @param chain
     * @return
     * @throws RestfulException
     */
    String doFilter(HttpMethod method, RestfulFilterChain chain) throws RestfulException;
}
