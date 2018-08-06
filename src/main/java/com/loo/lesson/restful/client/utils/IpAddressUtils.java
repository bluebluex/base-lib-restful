package com.loo.lesson.restful.client.utils;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 17:29
 **/
public class IpAddressUtils {
    /**
     * 获取客户端IP<br>
     * 好贴 --> www.cnblogs.com/ITtangtang/p/3927768.html
     * <p>
     * 获取HAProxy(option forward for)转发过来的真实IP，经过多级代理，这里会有多个IP用逗号分隔：
     * <code>String clientIp = request.getHeader("X-Forwarded-For");</code>
     * <p>
     * 获取的是HAProxy的IP(HAProxy --> Nginx(proxy_set_header X-Real-IP $remote_addr;) -->Tomcat)<br>
     * <code>String ipFromNgix = getHeader(request, "X-Real-IP");</code>
     * <p>
     * 这样获取到的是Nginx的IP(Nginx --> Tomcat)<br>
     * <code>clientIp = request.getRemoteAddr();</code>
     */

    public static String getRequestIp(HttpServletRequest request) {
        String ip = StringUtils.trim(request.getHeader("X-Forwarded-For"));
        if (StringUtils.isNotBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个IP值，第一个IP才是真实IP
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real_IP");
        if (StringUtils.isNotBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }

        return request.getRemoteAddr();
    }
}
