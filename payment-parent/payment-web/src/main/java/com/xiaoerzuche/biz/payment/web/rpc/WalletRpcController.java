package com.xiaoerzuche.biz.payment.web.rpc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/rpc")
public class WalletRpcController {
	@Value("${wallet_service}")
	private String walletService;
	
	@RequestMapping(value = "/v1.0/charge/single", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView singleCharge(HttpServletRequest request){
		System.out.println("=================================================");
//		return "redirect:"+"walletService"+"/rpc/wallet/v1.0/charge/single";
		return new ModelAndView("redirect:"+walletService+"/rpc/wallet/v1.0/charge/single");
	}
	@RequestMapping(value = "/v1.0/query/{orderNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView query(@PathVariable(value = "orderNo") String orderNo, String account,HttpServletRequest request){
//		return "redirect:"+"walletService"+"/rpc/wallet/v1.0/query/"+orderNo+"?account="+account;
		return new ModelAndView("redirect:"+walletService+"/rpc/wallet/v1.0/query/"+orderNo+"?account="+account);
	}
	
}
