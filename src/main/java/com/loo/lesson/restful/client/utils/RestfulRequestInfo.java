package com.loo.lesson.restful.client.utils;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 17:30
 **/
public class RestfulRequestInfo {

    private String clientIp;// 客户端ip
    private String udid;// 客户端设备号/用户唯一标示
    private String innerMedia;// 内部推广标识 简称内媒
    private String outerMedia;// 外部推广标示 简称外媒
    private String clientId;// 一级渠道
    private String channel;// 应用包来源
    private String subClientId;// 二级渠道

    public RestfulRequestInfo() {
    }

    public RestfulRequestInfo(String udid, String innerMedia, String outerMedia, String channel, String subClientId) {
        this.udid = udid;
        this.innerMedia = innerMedia;
        this.outerMedia = outerMedia;
        this.channel = channel;
        this.subClientId = subClientId;
    }

    public RestfulRequestInfo(String udid, String innerMedia, String outerMedia) {
        this.udid = udid;
        this.innerMedia = innerMedia;
        this.outerMedia = outerMedia;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getInnerMedia() {
        return innerMedia;
    }

    public void setInnerMedia(String innerMedia) {
        this.innerMedia = innerMedia;
    }

    public String getOuterMedia() {
        return outerMedia;
    }

    public void setOuterMedia(String outerMedia) {
        this.outerMedia = outerMedia;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getSubClientId() {
        return subClientId;
    }

    public void setSubClientId(String subClientId) {
        this.subClientId = subClientId;
    }
}
