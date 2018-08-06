package com.loo.lesson.restful.server.handler.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 16:52
 **/
@Entity
@Table(name = "T_RESTFUL_SRV_HANDLER")
public class RestfulSrvHandler implements Serializable {
    private static final long serialVersionUID = -884199182546813465L;

    @Id
    @Column(name = "ID")
    private Long id;
    @Id
    @Column(name = "BIZCODE")
    private String bizCode;
    @Id
    @Column(name = "VERSION")
    private String version;
    @Id
    @Column(name = "VOCLASS")
    private String voClass;
    @Id
    @Column(name = "STRATEGY")
    private String strategy;
    @Id
    @Column(name = "DESCRIPTION")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVoClass() {
        return voClass;
    }

    public void setVoClass(String voClass) {
        this.voClass = voClass;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
