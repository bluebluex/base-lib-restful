package com.loo.lesson.restful.vo.request;

import java.io.Serializable;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 17:37
 **/
public class RestfulReqHead implements Serializable {

    private static final long serialVersionUID = 5963430795587283294L;

    private String version;

    private String signType;

    private String sign;

    private String bizCode;

    private String clientId;

    private String channelId;

    private String clientNodeId;

    private String serverNo;

    private String sendDate;

    private String sendTime;

    private String clientIp;

    private Long seqId = 1L;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getClientNodeId() {
        return clientNodeId;
    }

    public void setClientNodeId(String clientNodeId) {
        this.clientNodeId = clientNodeId;
    }

    public String getServerNo() {
        return serverNo;
    }

    public void setServerNo(String serverNo) {
        this.serverNo = serverNo;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public Long getSeqId() {
        return seqId;
    }

    public void setSeqId(Long seqId) {
        this.seqId = seqId;
    }
}
