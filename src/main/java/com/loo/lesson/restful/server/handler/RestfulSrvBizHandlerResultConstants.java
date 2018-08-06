package com.loo.lesson.restful.server.handler;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 17:36
 **/
public interface RestfulSrvBizHandlerResultConstants {

    String RST_CODE_SUCCESS = "000000";
    String RST_MSG_SUCCESS = "操作成功";

    String RST_CODE_ERR_UNEXPECTED = "999999";
    String RST_MSG_ERR_UNEXPECTED = "服务器未知异常";

    String RST_CODE_ERR_PARAM = "999998";
    String RST_MSG_ERR_PARAM = "参数错误";

    String RST_CODE_ERR_VPARAM = "999997";
    String RST_MSG_ERR_VPARAM = "参数检验失败";

    String RST_CODE_ERR_CPARAM = "999996";
    String RST_MSG_ERR_CPARAM = "参数转换失败";

    String RST_CODE_ERR_BIZCODE = "999995";
    String RST_MSG_ERR_BIZCODE = "未找到业务处理逻辑";

    String RST_CODE_ERR_DB = "999994";
    String RST_MSG_ERR_DB = "数据库异常";

    String RST_CODE_WHITE_USER_LIST = "8000000-001-002";
    String RST_MSG_WHITE_USER_LIST = "您的账号不允许登录，请联系管理员，谢谢";
}
