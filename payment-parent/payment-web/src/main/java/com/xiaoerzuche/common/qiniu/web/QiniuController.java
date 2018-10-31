package com.xiaoerzuche.common.qiniu.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.xiaoerzuche.common.util.DateTimeUtil;
import com.xiaoerzuche.common.util.JsonUtil;

@Controller
@RequestMapping("/cdn/qiniu/")
public class QiniuController {
	private static final Logger logger = LoggerFactory.getLogger(QiniuController.class);
	private static String ACCESS_KEY = "IpK3TWOw0PLlkEGWm7Ss_f3TLGybzTNQTDgaCGaP";
	private static String SECRET_KEY ="2oMBKpDvo3SRopMVdsnWPlTTzNU-LpnSuyLwU44Z";
	
	@Value("${qiniu.callbackUrl}")
	private String callbackUrl;
	@Value("${qiniu.callbackBody}")
	private String callbackBody;//key=$(key)&hash=$(etag)&fileName=$(fname)&fileSize=$(fsize)
	@Value("${qiniu.bucket}")
	private String bucket;

	@RequestMapping("getToken.do")
	public String getToken(String fileName, ModelMap map){
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		String token = auth.uploadToken(bucket, fileName, DateTimeUtil.getUnixTimestamp(System.currentTimeMillis() + 30000), new StringMap()
        .put("callbackUrl", callbackUrl)
        .put("callbackBody", callbackBody)
        .put("insertOnly", 0));
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("token", token);
		map.put("data", JsonUtil.toJson(resultMap));
		return "/frontJson";
	}
	
	/**
	 * http://blog.csdn.net/guoer9973/article/details/49149987
	 * @param key
	 * @param hash
	 * @param fileName
	 * @param fileSize
	 * @param request
	 * @return
	 */
	@RequestMapping("qiniuCB.do")
	public String qiniuCB(String key, String hash, String fileName, Integer fileSize, HttpServletRequest request, ModelMap map){
		/*Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        //String charset = request.getCharacterEncoding();        
        String authorization = request.getHeader("Authorization");
        String contentType = request.getHeader("Content-Type");             
        String line = "";
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        while ((line = br.readLine()) != null) {
	            sb.append(line);
	        }  
		} catch (IOException e) {
			logger.error("获取Request的IO流异常", e);
			throw new ErrorCodeException(ErrorCode.UNKOWN.getErrorCode(), "获取Request的IO流异常");
		}
        boolean check= auth.isValidCallback(authorization, callbackUrl, StringUtils.utf8Bytes(sb.toString()), contentType);
        CheckUtil.assertTrue(check, "非法的回调请求, Authorization="+authorization);
        */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("key", key);
		resultMap.put("hash", hash);
		resultMap.put("fileName", fileName);
		resultMap.put("fileSize", fileSize);
		map.put("data", JsonUtil.toJson(resultMap));
		logger.info("文件上传成功，key="+key+" hash="+hash+" fileName="+fileName+" fileSize="+fileSize);
		return "/frontJson";
	}
	
	/*private void checkQiniuCallback(HttpServletRequest request){
		String authorization = request.getHeader("Authorization");
		logger.info("authorization="+authorization);
		CheckUtil.assertTrue(authorization.contains("QBox "), "非法的回调请求, Authorization="+authorization);
		authorization = authorization.substring(5);
		String[] authStrs = authorization.split(":");
		CheckUtil.assertTrue(authStrs.length == 2, "非法的回调请求, Authorization="+authorization);
		CheckUtil.assertTrue(ACCESS_KEY.equalsIgnoreCase(authStrs[0]), "非法的回调请求, Authorization="+authorization);
		InputStream is = null;
		String contentStr = null;
		try {
			is = request.getInputStream();
			contentStr = IOUtils.toString(is, "utf-8");  
		} catch (IOException e) {
			logger.error("获取Request的IO流异常", e);
			throw new ErrorCodeException(ErrorCode.UNKOWN.getErrorCode(), "获取Request的IO流异常");
		}
		byte[] x = Base64.encodeBase64URLSafe((EncryptUtil.hmacSHA1(contentStr, SECRET_KEY)).getBytes());
		String secretKey = StringUtils.newStringUtf8(x);
		logger.info("secretKey="+secretKey);
		CheckUtil.assertTrue(secretKey.equalsIgnoreCase(authStrs[1]), "非法的回调请求, Authorization="+authorization);
	}*/
}
