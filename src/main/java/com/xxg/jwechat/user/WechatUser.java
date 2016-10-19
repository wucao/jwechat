package com.xxg.jwechat.user;

import java.io.Serializable;

/**
 * Created by wucao on 16/10/19.
 */
public class WechatUser implements Serializable {

    /**
     * 是否关注(未关注用户获取不到其他信息)
     */
    private boolean subscribe;

    /**
     * 微信用户昵称
     */
    private String nickname;

    /**
     * 微信用户性别
     */
    private WechatUserSex sex;

    /**
     * 用户所在城市
     */
    private String city;

    /**
     * 用户所在国家
     */
    private String country;

    /**
     * 用户所在省份
     */
    private String province;

    /**
     * 用户的语言，简体中文为zh_CN
     */
    private String language;

    /**
     * 用户头像地址
     */
    private String headImgUrl;

    /**
     * 用户关注时间(时间戳)
     */
    private long subscribeTime;

    /**
     * 用户备注(这一项是在公众平台后台用户管理中设置)
     */
    private String remark;

    public boolean getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(boolean subscribe) {
        this.subscribe = subscribe;
    }

    public boolean isSubscribe() {
        return subscribe;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public WechatUserSex getSex() {
        return sex;
    }

    public void setSex(WechatUserSex sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public long getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(long subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
