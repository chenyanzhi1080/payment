package com.xiaoerzuche.biz.payment.util;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.xiaoerzuche.biz.payment.vo.rest.RestResponeVo;
import com.xiaoerzuche.common.util.JsonUtil;

@Component
public class PaymentRestClien {
	private static final Logger	logger = LoggerFactory.getLogger(PaymentRestClien.class);
	@Autowired
	private RestTemplate restTemplate;
	
	public <T> T restClien( Class<T> clazz, String url, Object bodyObj, MediaType mediaType, HttpMethod httpMethod){
		String body = bodyObj==null? null : JsonUtil.toJson(bodyObj);
		logger.info("[payment.restClien][body]"+body);
		HttpEntity<String> requestEntity = new HttpEntity<String>(body,
				getHeader(mediaType));
		HttpEntity<String> response = restTemplate.exchange(url, httpMethod, requestEntity, String.class);
		logger.info("[payment.restClien][response]"+response);
		String responseBody = response.getBody();
		logger.info("[payment.restClien][responseBody]"+responseBody);
		return JsonUtil.fromJson(responseBody, clazz);
	}
	
	public RestResponeVo restClien(String url, Object bodyObj, MediaType mediaType, HttpMethod httpMethod){
		RestResponeVo dataVo = null;
		try {
			String body = bodyObj==null? null : JsonUtil.toJson(bodyObj);
			logger.info("[payment.restClien][body]"+body);
			HttpEntity<String> requestEntity = new HttpEntity<String>(body,
					getHeader(mediaType));
			HttpEntity<String> response = restTemplate.exchange(url, httpMethod, requestEntity, String.class);
			logger.info("[payment.restClien][response]"+response);
			String responseBody = response.getBody();
			logger.info("[payment.restClien][responseBody]"+responseBody);
			dataVo = JsonUtil.fromJson(responseBody, RestResponeVo.class);
		} catch (RestClientException e) {
			logger.error("[payment.restClien][url={}][erro]", new Object[]{url,e});
		}
		return dataVo;
	}
	
	private HttpHeaders getHeader(MediaType mimeType) {
		MediaType mediaType =
		            new MediaType(mimeType.getType(), mimeType.getSubtype(), Charset.forName("UTF-8"));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);
		return headers;
	}
}
