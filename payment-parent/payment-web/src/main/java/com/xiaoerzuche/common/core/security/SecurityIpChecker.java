package com.xiaoerzuche.common.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SecurityIpChecker {
	@Value("${security.ips}")
	private String whiteIps;
	@Autowired  
	private Environment env;
	
	public boolean isPass(String ip){
		if(env.acceptsProfiles("pro")){
			return whiteIps.indexOf(ip) > -1;
		}
		return true;
	}
}
