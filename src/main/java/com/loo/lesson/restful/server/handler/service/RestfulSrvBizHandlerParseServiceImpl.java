package com.loo.lesson.restful.server.handler.service;

import com.loo.lesson.restful.server.handler.RestfulSrvBizHandler;
import com.loo.lesson.restful.server.handler.RestfulSrvBizHandlerResultConstants;
import com.loo.lesson.restful.server.handler.model.RestfulSrvBizReqSupport;
import com.loo.lesson.restful.server.handler.model.RestfulSrvHandler;
import com.loo.lesson.restful.utils.JacksonUtils;
import com.loo.lesson.restful.vo.request.RestfulReqHead;
import com.loo.lesson.restful.vo.response.RestfulRespMessage;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 17:33
 **/
@Service(value = "restfulSrvBizHandlerParseService")
public class RestfulSrvBizHandlerParseServiceImpl implements RestfulSrvBizHandlerParseService, InitializingBean, BeanFactoryAware {

    private static final long serialVersionUID = -6466071153300080580L;

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private RestfulSrvHandlerService businessHandlerService;

    private BeanFactory beanFactory;

    private String encryptKey;


    @Override
    public RestfulSrvBizReqSupport decodeRestfulSrvBizReqParam(String reqData) {
        RestfulSrvBizReqSupport support = new RestfulSrvBizReqSupport();
        if (StringUtils.isBlank(reqData)) {
            return this.builderParamErrorResult(support);
        }
        logger.info("===========收到客户端请求信息：{}", reqData);
        support.setReqParams(reqData);
        if (!this.validateParamIntact(support, reqData)) {
            logger.error("===========校验参数完整性失败：{}", reqData);
            return support;
        }
        if (!this.coverParamObject(support, reqData)) {
            logger.error("============转换解析参数失败：{}", reqData);
            return support;
        }
        return support;
    }

    @SuppressWarnings("unchecked")
    private boolean coverParamObject(RestfulSrvBizReqSupport support, String reqData) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
            JsonNode root = mapper.readTree(reqData);
            JsonNode headNode = root.path("head");
            JsonNode bodyNode = root.path("body");
            // head
            RestfulReqHead reqHead = mapper.readValue(headNode, RestfulReqHead.class);
            support.setReqHead(reqHead);
            String bizCode = reqHead.getBizCode();
            String version = reqHead.getVersion();
            // handler
            RestfulSrvHandler handlerSetting = businessHandlerService.getRestfulSrvHandler(bizCode, version);
            if (handlerSetting == null) {
                logger.error("======================未找到DB配置的业务处理逻辑，reqData={}", reqData);
                this.builderParamErrorResult(RestfulSrvBizHandlerResultConstants.RST_CODE_ERR_BIZCODE,
                        RestfulSrvBizHandlerResultConstants.RST_MSG_ERR_BIZCODE, support);
                return false;
            }
            // strategy handler
            String strategy = handlerSetting.getStrategy();
            RestfulSrvBizHandler srvBizHandler = (RestfulSrvBizHandler) this.beanFactory.getBean(strategy);
            if (srvBizHandler == null) {
                logger.error("======================未找到DB配置的业务处理逻辑， bean={}，reqData={}", strategy ,reqData);
                this.builderParamErrorResult(RestfulSrvBizHandlerResultConstants.RST_CODE_ERR_BIZCODE,
                        RestfulSrvBizHandlerResultConstants.RST_MSG_ERR_BIZCODE, support);
                return false;
            }
            support.setSrvBizHandler(srvBizHandler);
            // body
            String voClassPath = handlerSetting.getVoClass();
            this.parseBodyParam(voClassPath, bodyNode, mapper, support);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return false;
    }

    /**
     * 解析body参数
     * @param voClassPath
     * @param bodyNode
     * @param mapper
     * @param support
     * @throws Exception
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void parseBodyParam(String voClassPath, JsonNode bodyNode, ObjectMapper mapper, RestfulSrvBizReqSupport support) throws Exception {
        if (StringUtils.isBlank(voClassPath)) {
            return;
        }
        if ("map".equalsIgnoreCase(voClassPath)) {
            HashMap<String, Object> body = mapper.readValue(bodyNode, HashMap.class);
            support.setReqBody(body);
            return;
        } else if ("list".equalsIgnoreCase(voClassPath)) {
            LinkedList<Map<String, Object>> body = mapper.readValue(bodyNode, LinkedList.class);
            support.setReqBody(body);
            return;
        } else {
            Class bodyClass = Class.forName(voClassPath);
            support.setReqBody((Serializable) mapper.readValue(bodyNode, bodyClass));
        }
    }

    /**
     * 校验参数的完整性
     * @param support
     * @param reqData
     * @return
     */
    private boolean validateParamIntact(RestfulSrvBizReqSupport support, String reqData) {
        try {
            ObjectMapper mapper = JacksonUtils.getObjectMapper();
            mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
            JsonNode root = mapper.readTree(reqData);
            JsonNode headNode = root.path("head");
            if (headNode == null) {
                this.builderParamErrorResult(RestfulSrvBizHandlerResultConstants.RST_CODE_ERR_VPARAM,
                        RestfulSrvBizHandlerResultConstants.RST_MSG_ERR_VPARAM, support);
                return false;
            } else {
                if (headNode.get("version") == null || headNode.get("bizCode") == null) {
                    this.builderParamErrorResult(RestfulSrvBizHandlerResultConstants.RST_CODE_ERR_VPARAM,
                            RestfulSrvBizHandlerResultConstants.RST_MSG_ERR_VPARAM, support);
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            logger.error("=================参数校验失败 reqData={}", reqData, e);
            support.setException(e);
            this.builderParamErrorResult(support);
        }
        return false;
    }

    private RestfulSrvBizReqSupport builderParamErrorResult(RestfulSrvBizReqSupport support) {

        return builderParamErrorResult(RestfulSrvBizHandlerResultConstants.RST_CODE_ERR_PARAM, RestfulSrvBizHandlerResultConstants.RST_MSG_ERR_PARAM, support);
    }

    private RestfulSrvBizReqSupport builderParamErrorResult(String code, String msg, RestfulSrvBizReqSupport support) {
        support.setRespData(new RestfulRespMessage(code, msg));
        support.setParamParseRst(false);
        return support;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
