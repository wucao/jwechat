package com.xxg.jwechat.message;

/**
 * Created by wucao on 16/10/17.
 */
public class TemplateMessageItem {

    private String value;
    private String color;

    public TemplateMessageItem(String value, String color) {
        this.value = value;
        this.color = color;
    }

    public TemplateMessageItem(String value) {
        this.value = value;
        this.color = "#173177";
    }

    public String getValue() {
        return value;
    }

    public String getColor() {
        return color;
    }
}
