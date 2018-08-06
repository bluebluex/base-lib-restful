package com.loo.lesson.restful.adapter.rpc.service.impl;

import com.loo.lesson.restful.adapter.rpc.service.RestfulHandlerAdaptRpcService;
import com.loo.lesson.restful.client.utils.RestfulRequestInfo;
import com.loo.lesson.restful.client.utils.RestfulThreadLoacl;
import com.loo.lesson.restful.exception.ServiceException;
import com.loo.lesson.restful.server.handler.RestfulSrvBizHandler;
import com.loo.lesson.restful.server.handler.RestfulSrvBizHandlerContext;
import com.loo.lesson.restful.server.handler.RestfulSrvBizHandlerResultConstants;
import com.loo.lesson.restful.server.handler.model.RestfulSrvBizReqSupport;
import com.loo.lesson.restful.server.handler.model.RestfulSrvHandler;
import com.loo.lesson.restful.server.handler.service.RestfulSrvHandlerService;
import com.loo.lesson.restful.utils.JacksonUtils;
import com.loo.lesson.restful.utils.UserThreadLocal;
import com.loo.lesson.restful.vo.request.RestfulRequest;
import com.loo.lesson.restful.vo.response.RestfulRespMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @program: base-core
 * @description: Restful 适配RPC调用服务
 * @author: Tomax
 * @create: 2018-08-02 16:34
 **/
public abstract class AbstractRestfulHandlerAdaptRpcServiceImpl implements RestfulHandlerAdaptRpcService, BeanFactoryAware {

    private static final long serialVersionUID = 5354897918424100654L;

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RestfulSrvHandlerService businessHandlerService;
    private BeanFactory beanFactory;

    @Override
    public RestfulRespMessage invokeRmtRestfulHandler(RestfulRequest requestParam) throws ServiceException {
        RestfulSrvBizHandlerContext context = this.buildHandlerContest(requestParam);
        RestfulSrvBizReqSupport support = context.getReqSupport();
        if (support.isParamParseRst()) {
            support.setEndTime(new Date());
            return support.getRespData();
        }
        return this.excuteBusiness(context);
    }

    private RestfulRespMessage excuteBusiness(RestfulSrvBizHandlerContext context) throws ServiceException {
        if (context == null) {
            throw new ServiceException("context为空");
        }
        RestfulSrvBizReqSupport support = context.getReqSupport();
        try {
            RestfulSrvBizHandler srvBizHandler = support.getSrvBizHandler();
            if (context.getHead() != null && NumberUtils.isDigits(context.getHead().getSign())) {
                UserThreadLocal.setUid(NumberUtils.createLong(context.getHead().getSign()));
            }
            setRequestInfoThreadLocal(support);
            RestfulRespMessage result = srvBizHandler.excute(context);
            support.setRespData(result);
            support.setEndTime(new Date());
            return result;
        } catch (Exception e) {
            logger.error("执行业务逻辑失败");
            support = new RestfulSrvBizReqSupport();
            support.setParamParseRst(false);
            RestfulRespMessage resultMsg = new RestfulRespMessage(RestfulSrvBizHandlerResultConstants.RST_CODE_ERR_PARAM, "执行业务逻辑错误： " + e.getMessage() + " param： " + support.getReqParams());
            support.setRespData(resultMsg);
            support.setException(e);
            support.setEndTime(new Date());
            return resultMsg;
        } finally {
            clearRequestInfoToThreadLocal();
        }
    }

    private void clearRequestInfoToThreadLocal() {
        RestfulThreadLoacl.removeRestfulRequestInfo();
    }

    private void setRequestInfoThreadLocal(RestfulSrvBizReqSupport support) {
        RestfulRequestInfo restfulRequestInfo = new RestfulRequestInfo();
        // 从请求头参数里面获取IP
        String clientIp = support.getReqHead().getClientIp();
        if (StringUtils.isBlank(clientIp)) {
            clientIp = "127.0.0.1";
        }
        restfulRequestInfo.setClientIp(clientIp);
        RestfulThreadLoacl.setRestfulRequestInfo(restfulRequestInfo);
    }

    /**
     * 构造RestfulSrvBizHandlerContext
     * @param requestParam
     * @return
     */
    private RestfulSrvBizHandlerContext buildHandlerContest(RestfulRequest requestParam) {
        RestfulSrvBizHandlerContext context = new RestfulSrvBizHandlerContext();
        context.setBody(requestParam.getBody());
        context.setHead(requestParam.getHead());
        context.setReqJson(JacksonUtils.jsonObjSerializer(requestParam));

        RestfulSrvBizReqSupport support = this.buildReqSupport(requestParam, context);
        context.setReqSupport(support);
        context.setRestfulRequest(support.getReqData());
        return context;
    }

    /**
     * 构造RestfulSrvBizReqSupport
     * @param requestParam
     * @param context
     * @return
     */
    private RestfulSrvBizReqSupport buildReqSupport(RestfulRequest requestParam, RestfulSrvBizHandlerContext context) {
        RestfulSrvBizReqSupport support = new RestfulSrvBizReqSupport();
        support.setStartTime(new Date());
        support.setReqParams(context.getReqJson());
        support.setReqHead(context.getHead());
        support.setReqData(requestParam);
        support.setReqBody(context.getBody());
        support.setParamParseRst(true);
        support.setSrvBizHandler(getRestFulSrvBizHandler(support, requestParam));
        RestfulRequest reqData = new RestfulRequest();
        reqData.setBody(support.getReqBody());
        reqData.setHead(support.getReqHead());
        support.setReqData(reqData);
        return null;
    }

    /**
     * 查找业务逻辑处理handler
     */
    private RestfulSrvBizHandler getRestFulSrvBizHandler(RestfulSrvBizReqSupport support, RestfulRequest requestParam) {
        try {
            // head
            String bizCode = requestParam.getHead().getBizCode();
            String version = requestParam.getHead().getVersion();
            // handler
            RestfulSrvHandler handlerSetting = businessHandlerService.getRestfulSrvHandler(bizCode, version);
            if (handlerSetting == null) {
                logger.error("###############DB未配置业务处理逻辑，bizCode={}，version={}", bizCode, version);
                this.setParamErrorResult(RestfulSrvBizHandlerResultConstants.RST_CODE_ERR_BIZCODE, RestfulSrvBizHandlerResultConstants.RST_MSG_ERR_BIZCODE, support);
                return null;
            }
            // strategy handler
            String strategy = handlerSetting.getStrategy();
            RestfulSrvBizHandler srvBizHandler = (RestfulSrvBizHandler) this.beanFactory.getBean(strategy);
            if (srvBizHandler == null) {
                logger.error("###############未找到业务处理逻辑Handler，bizCode={}，version={}", bizCode, version);
                this.setParamErrorResult(RestfulSrvBizHandlerResultConstants.RST_CODE_ERR_BIZCODE, RestfulSrvBizHandlerResultConstants.RST_MSG_ERR_BIZCODE, support);
                return null;
            }
            return srvBizHandler;
        } catch (Exception e) {
            logger.error("################参数解析失败jsonMsg={}", support.getReqParams(), e);
            support.setException(e);
            this.setParamErrorResult(RestfulSrvBizHandlerResultConstants.RST_CODE_ERR_CPARAM, RestfulSrvBizHandlerResultConstants.RST_MSG_ERR_CPARAM, support);
        }
        return null;
    }

    private void setParamErrorResult(String code, String msg, RestfulSrvBizReqSupport support) {
        support.setRespData(new RestfulRespMessage(RestfulSrvBizHandlerResultConstants.RST_CODE_ERR_PARAM, RestfulSrvBizHandlerResultConstants.RST_MSG_ERR_PARAM));
        support.setParamParseRst(false);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
