package com.xiaoerzuche.biz.payment.dao.outside;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.xiaoerzuche.biz.payment.dao.MessageDao;
import com.xiaoerzuche.biz.payment.vo.MessageVo;
import com.xiaoerzuche.common.util.HttpUtils;

import net.sf.json.JSONObject;

@Repository
public class MessageDaoOutsideImp implements MessageDao{
	private static final Logger logger = LoggerFactory.getLogger(MessageDaoOutsideImp.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${xiaoer_wechat_server}")
	private String xiaoerWechatServer;
	@Value("${message.baseUrl}")
	private String messageUrl;

	@Override
	public String notifyWechat(String openId, String orderId, String type) {
		String url = xiaoerWechatServer+"/payment/v1.0/template?openId="+openId+"&orderId="+orderId+"&type="+type;
		logger.info("[微信公众号模板通知url={}]", new Object[]{url});
		String result = "";
		try {
	        HttpEntity<String> formEntity = new HttpEntity<String>("", getHeader(MediaType.APPLICATION_JSON));
			result = restTemplate.postForObject(url, formEntity, String.class);
			logger.info("[微信公众号模板通知回执]{}", new Object[]{result});
			JSONObject jsonObject = JSONObject.fromObject(result);
			result = jsonObject.getString("code");
		} catch (RestClientException e) {
			logger.error("[微信公众号模板通知异常{}]{}", new Object[]{e.getMessage(),e});
		}
		return result;
	}
	
	private HttpHeaders getHeader(MediaType mediaType) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		return headers;
	}

	@Override
	public String sendMsg(MessageVo msgVo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("msgPhone", msgVo.getPhone());
		map.put("msgContent", msgVo.getMsg());
		logger.info("[支付网关短信发送]MessageDaoOutsideImp.sendMsg:" + messageUrl + "/web/smsMsg/sendMsg.ihtml");
		String result = "";
		try {
			result = HttpUtils.doPost(messageUrl + "/web/smsMsg/sendMsg.ihtml", map, 4000L, 4000L);
			JSONObject jsonObject = JSONObject.fromObject(result);
			logger.info("[支付网关短信发送回执]{}", new Object[]{result});
			result = jsonObject.getString("code");
		} catch (Exception e) {
			logger.error("[支付网关短信发送异常{}]{}", new Object[]{e.getMessage(),e});
		}
		return result;
	}
}
