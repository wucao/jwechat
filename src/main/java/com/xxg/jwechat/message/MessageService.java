package com.xxg.jwechat.message;

import com.xxg.jwechat.WechatException;
import com.xxg.jwechat.accesstoken.AccessTokenService;
import com.xxg.jwechat.util.HttpUtil;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by wucao on 16/10/17.
 */
public class MessageService {

    private AccessTokenService accessTokenService;

    public void setAccessTokenService(AccessTokenService accessTokenService) {
        this.accessTokenService = accessTokenService;
    }

    /**
     * 发送模板消息
     * 相关文档: http://mp.weixin.qq.com/wiki/5/6dde9eaa909f83354e0094dc3ad99e05.html#.E5.8F.91.E9.80.81.E6.A8.A1.E6.9D.BF.E6.B6.88.E6.81.AF
     *
     * @param templateId 模板ID
     * @param url 链接页面
     * @param openId 接收用户openId
     * @param map 模板替换内容
     */
    public void sendTemplateMessage(String templateId, String url, String openId, Map<String, TemplateMessageItem> map) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("touser", openId);
        jsonObject.put("template_id", templateId);
        jsonObject.put("url", url);
        JSONObject dataJson = new JSONObject();
        for(Map.Entry<String, TemplateMessageItem> entry : map.entrySet()) {
            JSONObject itemJson = new JSONObject();
            itemJson.put("value", entry.getValue().getValue());
            itemJson.put("color", entry.getValue().getColor());
            dataJson.put(entry.getKey(), itemJson);
        }
        jsonObject.put("data", dataJson);
        String json = jsonObject.toString();
        String result = HttpUtil.post("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessTokenService.getAccessToken(), json);
        JSONObject resultJson = new JSONObject(result);
        if(resultJson.getInt("errcode") != 0 || !"ok".equals(resultJson.getString("errmsg"))) {
            throw new WechatException("发送微信模板消息异常: " + result);
        }
    }
}
