package com.loo.lesson.restful.client.utils;

import com.loo.lesson.restful.utils.JacksonUtils;
import com.loo.lesson.restful.vo.response.RestfulRespDataBody;
import com.loo.lesson.restful.vo.response.RestfulRespHead;
import com.loo.lesson.restful.vo.response.RestfulRespMessage;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 17:30
 **/
public class RestfulClientUtils {

    protected static final Logger logger = LoggerFactory.getLogger(RestfulClientUtils.class);

    /**
     * 获取json body 内部信息
     */
    public static String getResultDataString(String jsonStr) {
        if (StringUtils.isBlank(jsonStr)) {
            logger.error("获取json data内部信息字符串为空");
            return null;
        }
        try {
            ObjectMapper mapper = JacksonUtils.getObjectMapper();
            JsonNode bodyNode = mapper.readTree(jsonStr).path("body");
            if (bodyNode == null) {
                return null;
            }
            JsonNode dataNode = bodyNode.path("data");
            return dataNode == null ? null : dataNode.toString();
        } catch (IOException e) {
            logger.error("获取json data内部信息异常， jsonStr={}", jsonStr, e);
        }
        return null;
    }
    /**
     * 获取json body 内部信息
     */
    public static JsonNode getResultDataJsonNode(String jsonStr) {
        if (StringUtils.isBlank(jsonStr)) {
            logger.error("获取json data内部信息字符串为空");
            return null;
        }
        try {
            ObjectMapper mapper = JacksonUtils.getObjectMapper();
            JsonNode bodyNode = mapper.readTree(jsonStr).path("body");
            if (bodyNode == null) {
                return null;
            }
            return bodyNode.path("data");
        } catch (IOException e) {
            logger.error("获取json data内部信息异常， jsonStr={}", jsonStr, e);
        }
        return null;
    }

    /**
     * 获取json RestfulRespMessage格式对象
     * @param jsonStr
     * @return
     */
    public static RestfulRespMessage getResultRespMessage(String jsonStr) {
        if (StringUtils.isBlank(jsonStr)) {
            logger.error("获取json RestfulRespMessage格式对象字符串为空， jsonStr={}", jsonStr);
        }
        return JacksonUtils.jsonObjUnSerializer(jsonStr, RestfulRespMessage.class);
    }

    /**
     * 获取json RestfulRespMessage格式(body DataType)对象
     * @param jsonStr
     * @param bodyDataType
     * @param <T>
     * @return
     */
    public static <T> RestfulRespMessage getResultRespMessage(String jsonStr, Class<T> bodyDataType) {
        if (StringUtils.isBlank(jsonStr)) {
            logger.error("获取json RestfulRespMessage格式(body DataType)对象字符串为空");
            return null;
        }
        try {
            ObjectMapper mapper = JacksonUtils.getObjectMapper();
            JsonNode messageNode = mapper.readTree(jsonStr);
            RestfulRespMessage message = parseResultRespMessage(messageNode);
            if (message.getBody() == null) {
                return message;
            }
            JsonNode bodyNode = messageNode.path("body");
            JsonNode dataNode = bodyNode.path("data");
            if (dataNode == null) {
                return message;
            }
            message.getBody().setData(JacksonUtils.jsonObjUnSerializer(dataNode, bodyDataType));
            return message;
        } catch (Exception e) {
            logger.error("获取json RestfulRespMessage格式(body DataType)，jsonStr={}", jsonStr, e);
        }
        return null;
    }

    /**
     * 获取json RestfulRespMessage格式(body DataType)对象
     * @param jsonStr
     * @param bodyDataType
     * @param <T>
     * @return
     */
    public static <T> RestfulRespMessage getResultRespMessage(String jsonStr, JavaType bodyDataType) {
        if (StringUtils.isBlank(jsonStr)) {
            logger.error("获取json RestfulRespMessage格式(body DataType)对象字符串为空");
            return null;
        }
        try {
            ObjectMapper mapper = JacksonUtils.getObjectMapper();
            JsonNode messageNode = mapper.readTree(jsonStr);
            RestfulRespMessage message = parseResultRespMessage(messageNode);
            if (message.getBody() == null) {
                return message;
            }
            JsonNode bodyNode = messageNode.path("body");
            JsonNode dataNode = bodyNode.path("data");
            if (dataNode == null) {
                return message;
            }
            message.getBody().setData(JacksonUtils.jsonObjUnSerializer(dataNode, bodyDataType));
            return message;
        } catch (Exception e) {
            logger.error("获取json RestfulRespMessage格式(body DataType)，jsonStr={}", jsonStr, e);
        }
        return null;
    }

    /**
     * 获取json RestfulRespMessage格式
     * eg:
     * new TypeReference<List<A>>() {}
     * @param jsonStr
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> RestfulRespMessage getResultRespMessage(String jsonStr, TypeReference<T> typeReference) {
        if (StringUtils.isBlank(jsonStr)) {
            logger.error("获取json RestfulRespMessage格式(body DataType)对象字符串为空");
            return null;
        }
        try {
            ObjectMapper mapper = JacksonUtils.getObjectMapper();
            JsonNode messageNode = mapper.readTree(jsonStr);
            RestfulRespMessage message = parseResultRespMessage(messageNode);
            if (message.getBody() == null) {
                return message;
            }
            JsonNode bodyNode = messageNode.path("body");
            JsonNode dataNode = bodyNode.path("data");
            if (dataNode == null) {
                return message;
            }
            message.getBody().setData(JacksonUtils.jsonObjUnSerializer(dataNode, typeReference));
            return message;
        } catch (Exception e) {
            logger.error("获取json RestfulRespMessage格式(body DataType)，jsonStr={}", jsonStr, e);
        }
        return null;
    }
    /**
     * 解析通用响应消息数据
     * @param messageNode
     * @return
     */
    private static RestfulRespMessage parseResultRespMessage(JsonNode messageNode) {
        try {
            JsonNode headNode = messageNode.path("head");
            JsonNode bodyNode = messageNode.path("body");
            if (headNode == null) {
                return null;
            }
            RestfulRespMessage message = new RestfulRespMessage();
            message.setHead(JacksonUtils.jsonObjUnSerializer(headNode, RestfulRespHead.class));
            if (bodyNode == null) {
                return message;
            }
            RestfulRespDataBody body = new RestfulRespDataBody();
            JsonNode rstCodeNode = bodyNode.path("rstCode");
            if (rstCodeNode != null) {
                body.setRstCode(rstCodeNode.getTextValue());
            }
            JsonNode rstMsgNode = bodyNode.path("rstMsg");
            if (rstCodeNode != null) {
                body.setRstMsg(rstMsgNode.getTextValue());
            }
            message.setBody(body);
            JsonNode dataNode = bodyNode.path("data");
            if (dataNode == null) {
                return message;
            }
            message.getBody().setData(dataNode.toString());
            return message;
        } catch (Exception e) {
            logger.error("获取json RestfulRespMessage格式(body DataType)", e);
        }
        return null;
    }

    /**
     * 获取json RestfulRespMessage格式(body data为list对象，list元素为dataListType类型)对象.
     * @param jsonStr
     * @param dataListType
     * @param <T>
     * @return
     */
    public static <T> RestfulRespMessage getRestfulRespMessageDataList(String jsonStr, Class<T> dataListType) {
        if (StringUtils.isBlank(jsonStr)) {
            logger.error("获取json RestfulRespMessage格式(body data为List对象，list元素为dataListType类型)对象字符串为空");
            return null;
        }
        return getResultRespMessage(jsonStr, JacksonUtils.getCollectionType(List.class, dataListType));
    }
}
