package com.loo.lesson.restful.server.handler;

import com.loo.lesson.restful.vo.response.RestfulRespDataBody;
import com.loo.lesson.restful.vo.response.RestfulRespHead;
import com.loo.lesson.restful.vo.response.RestfulRespMessage;
import com.loo.lesson.spring.service.factory.ServiceFactory;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 17:35
 **/
public abstract class AbstractRestfulSrvBizHandler implements RestfulSrvBizHandler {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 同一模板入口
     */
    @Override
    public RestfulRespMessage excute(RestfulSrvBizHandlerContext context) {
        RestfulRespMessage result = null;
        if (!validateParam(context)) {
            result = new RestfulRespMessage(RestfulSrvBizHandlerResultConstants.RST_CODE_ERR_PARAM, RestfulSrvBizHandlerResultConstants.RST_MSG_ERR_PARAM);
            return result;
        }
        try {
            result = doExcute(context);
        } catch (Exception e) {
            logger.error("执行restful业务处理失败", e);
            context.getReqSupport().setException(e);
            result = new RestfulRespMessage(RestfulSrvBizHandlerResultConstants.RST_CODE_ERR_UNEXPECTED, RestfulSrvBizHandlerResultConstants.RST_MSG_ERR_UNEXPECTED);
        }
        result.getHead().setBizCode(getBizCode());
        result.getHead().setVersion(getVersion());
        context.getReqSupport().setRespData(result);
        context.getReqSupport().setEndTime(new Date());
        return null;
    }

    /**
     * 获取业务版本号
     * @return
     */
    protected abstract String getBizCode();

    /**
     * 获取业务响应代码
     * @return
     */
    protected abstract String getVersion();

    /**
     * 执行业务逻辑
     * @param context
     * @return
     */
    protected abstract RestfulRespMessage doExcute(RestfulSrvBizHandlerContext context);

    /**
     * 校验参数
     * @param context
     * @return
     */
    protected abstract boolean validateParam(RestfulSrvBizHandlerContext context);

    protected abstract ServiceFactory getServiceFactory();

    /**
     * 构造响应数据
     * @param rstCode
     * @param rstMsg
     * @return
     */
    public RestfulRespMessage builderRespMessage(String rstCode, String rstMsg) {
        RestfulRespMessage message = new RestfulRespMessage();
        RestfulRespHead head = new RestfulRespHead();
        head.setBizCode(this.getBizCode());
        Date now = new Date();
        head.setSendDate(DateFormatUtils.format(now, "yyyyMMdd"));
        head.setSendTime(DateFormatUtils.format(now, "yyyyMMddHHmmss"));
        head.setVersion(this.getVersion());

        RestfulRespDataBody body = new RestfulRespDataBody();
        body.setRstCode(rstCode);
        body.setRstMsg(rstMsg);
        message.setHead(head);
        message.setBody(body);
        return message;
    }

    public RestfulRespMessage builderRespMessage(String rstCode, String rstMsg, RestfulRespDataBody body) {
        RestfulRespMessage result = this.builderRespMessage(rstCode, rstMsg);
        body.setRstMsg(rstCode);
        body.setRstMsg(rstMsg);
        result.setBody(body);
        return result;
    }

    public RestfulRespMessage builderRespMessage(String rstCode, String rstMsg, Object data) {
        RestfulRespDataBody dataBody = new RestfulRespDataBody();
        dataBody.setData(data);
        return this.builderRespMessage(rstCode, rstMsg, dataBody);
    }
}
