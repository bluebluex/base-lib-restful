package com.loo.lesson.restful.web.servlet;

import com.loo.lesson.restful.client.utils.IpAddressUtils;
import com.loo.lesson.restful.client.utils.RestfulRequestInfo;
import com.loo.lesson.restful.client.utils.RestfulThreadLoacl;
import com.loo.lesson.restful.server.handler.RestfulSrvBizHandler;
import com.loo.lesson.restful.server.handler.RestfulSrvBizHandlerContext;
import com.loo.lesson.restful.server.handler.RestfulSrvBizHandlerResultConstants;
import com.loo.lesson.restful.server.handler.model.RestfulSrvBizReqSupport;
import com.loo.lesson.restful.server.handler.service.RestfulSrvBizHandlerParseService;
import com.loo.lesson.restful.server.log.service.RestfulSrvLogService;
import com.loo.lesson.restful.utils.JacksonUtils;
import com.loo.lesson.restful.utils.UserThreadLocal;
import com.loo.lesson.restful.vo.response.RestfulRespMessage;
import com.loo.lesson.spring.service.factory.ServiceFactory;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * @program: base-core
 * @description: rest 请求入口处理servlet
 * @author: Tomax
 * @create: 2018-08-02 17:38
 **/
public class RestfulHandlerServlet extends HttpServlet {

    private static final long serialVersionUID = -7224667809779273118L;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 解析参数
        RestfulSrvBizReqSupport support = this.parseRestReqParams(req);
        // 参数校验未通过
        if (!support.isParamParseRst()) {
            support.setEndTime(new Date());
            this.writeResultMessage(resp, support);
            return;
        }
        storeReqInfoThreadLocal(support, req);
        // 执行业务逻辑
        this.excuteBusiness(support);
        clearReqInfoThreadLocal();
        this.writeResultMessage(resp, support);

    }

    @Override
    public void destroy() {
        RestfulThreadLoacl.removeRestfulRequestInfo();
        super.destroy();
    }

    /**
     * 执行业务逻辑
     * @param support
     */
    private RestfulSrvBizReqSupport excuteBusiness(RestfulSrvBizReqSupport support) {
        try {
            RestfulSrvBizHandler srvBizHandler = support.getSrvBizHandler();
            RestfulSrvBizHandlerContext context = this.buildAppSrvBizHandlerContext(support);
            if (context != null && context.getHead() != null && NumberUtils.isDigits(context.getHead().getSign())) {
                UserThreadLocal.setUid(NumberUtils.createLong(context.getHead().getSign()));
            }
            RestfulRespMessage result = srvBizHandler.excute(context);
            support.setRespData(result);
        } catch (Exception e) {
            logger.error("执行业务逻辑失败", e);
            support = new RestfulSrvBizReqSupport();
            support.setParamParseRst(false);
            RestfulRespMessage message = new RestfulRespMessage(RestfulSrvBizHandlerResultConstants.RST_CODE_ERR_PARAM, "执行业务逻辑错误：" + e.getMessage() + " param："
                    + support.getReqParams());
            support.setRespData(message);
            support.setException(e);
        }
        return support;
    }

    /**
     * 构造RestfulSrvBizHandlerContext.
     * @param support
     * @return
     */
    private RestfulSrvBizHandlerContext buildAppSrvBizHandlerContext(RestfulSrvBizReqSupport support) {
        RestfulSrvBizHandlerContext context = new RestfulSrvBizHandlerContext();
        context.setBody(support.getReqBody());
        context.setHead(support.getReqHead());
        context.setReqJson(support.getReqParams());
        context.setRestfulRequest(support.getReqData());
        context.setReqSupport(support);
        return context;
    }

    private void clearReqInfoThreadLocal() {
        RestfulThreadLoacl.removeRestfulRequestInfo();
    }

    private void storeReqInfoThreadLocal(RestfulSrvBizReqSupport support, HttpServletRequest req) {
        RestfulRequestInfo restfulRequestInfo = new RestfulRequestInfo();
        // 先从请求头参数里面获取
        String clientIp = support.getReqHead().getClientIp();
        // 再获取请求头里面的IP
        if (StringUtils.isBlank(clientIp)) {
            clientIp = IpAddressUtils.getRequestIp(req);
        }
        restfulRequestInfo.setClientIp(clientIp);
        RestfulThreadLoacl.setRestfulRequestInfo(restfulRequestInfo);
    }

    /**
     * 构造返回结果
     * @param resp
     * @param support
     * @throws IOException
     */
    private void writeResultMessage(HttpServletResponse resp, RestfulSrvBizReqSupport support) throws IOException {
        if (support.getRespData() == null) {
            support.setRespData(new RestfulRespMessage(RestfulSrvBizHandlerResultConstants.RST_CODE_ERR_UNEXPECTED,
                    RestfulSrvBizHandlerResultConstants.RST_MSG_ERR_UNEXPECTED));
        }
        // save log
        RestfulSrvLogService logService = getService(super.getServletContext(), RestfulSrvLogService.class);
        // logService.saveLog(support);
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print(JacksonUtils.jsonObjSerializer(support.getReqData()));
        out.close();
    }

    /**
     * 解析请求参数
     * @param req
     * @return
     */
    private RestfulSrvBizReqSupport parseRestReqParams(HttpServletRequest req) {
        RestfulSrvBizReqSupport support = null;
        try {
            String jsonParams = req.getParameter("data");
            if (StringUtils.isBlank(jsonParams)) {
                String encoding = "utf-8";
                jsonParams = IOUtils.toString(req.getInputStream(), encoding).trim();
            }
            logger.info("收到用户请求信息：{}", jsonParams);
            RestfulSrvBizHandlerParseService service = getService(super.getServletContext(), RestfulSrvBizHandlerParseService.class);
            support = service.decodeRestfulSrvBizReqParam(jsonParams);
        } catch (Exception e) {
            logger.error("解析请求参数失败", e);
            support = new RestfulSrvBizReqSupport();
            support.setParamParseRst(false);
            RestfulRespMessage message = new RestfulRespMessage(RestfulSrvBizHandlerResultConstants.RST_CODE_ERR_PARAM, "解析参数head错误：" + e.getMessage() + " param: "
                    + support.getReqParams());
            support.setRespData(message);
            support.setException(e);
        }
        return support;
    }

    private <T> T getService(ServletContext servletContext, Class<T> clazz) {
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        ServiceFactory serviceFactory = (ServiceFactory) context.getBean("serviceFactory");
        return serviceFactory.getService(clazz);
    }
}
