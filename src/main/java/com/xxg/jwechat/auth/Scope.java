package com.xxg.jwechat.auth;

/**
 * Created by wucao on 16/12/9.
 */
public enum Scope {

    SNSAPI_BASE("snsapi_base"),
    SNSAPI_USERINFO("snsapi_userinfo");

    private String value;

    Scope(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }

}
