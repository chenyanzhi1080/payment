package com.xiaoerzuche.biz.zmxy.enu;

import java.util.HashMap;
import java.util.Map;

public enum XezcZmEnu {
	
	AD001("AD001","逾期未支付"),
	AD002("AD001","逾期未还车"),
	AD003("AD001","违章未处理"),
	
	D001("001","超期1-7天"),
	D002("002","超期8-14天"),
	D003("003","超期15-30天"),
	D004("004","超期31-60天"),
	D005("005","超期61-90天"),
	D006("006","超期91-120天"),
	D007("007","超期121-150天"),
	D008("008","超期151-180天"),
	D009("009","超期180天以上"),

	M01("M01","0-500元"),
	M02("M02","500-1000元"),
	M03("M03","1000-2000元"),
	M04("M04","2000-3000元"),
	M05("M05","3000-4000元"),
	M06("M06","4000-6000元"),
	M07("M07","6000-8000元"),
	M08("M08","8000-10000元"),
	M09("M09","10000-15000元"),
	M10("M10","15000-20000元"),
	M11("M11","20000-25000元"),
	M12("M12","25000-30000元"),
	M13("M13","30000-40000元"),
	M14("M14","40000元以上"),
	
	BR01("BR01", "具有征信及租车双重黑名单"),
	BR02("BR01", "具有租车黑名单"),
	BR03("BR01", "具有征信黑名单"),
	
	XE01("XE01", "当前不逾期"),
	XE02("XE02", "当前逾期");
	
	private String code;
	private String name;
	
	public static Map<String, String> map = new HashMap<String, String>();
	
	private XezcZmEnu(String code, String name) {
		this.code = code;
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public static Map<String, String> getMap() {
		if(map.isEmpty()){
			map = XezcZmEnu.getEnuMap();
		}
		return map;
	}
	public static void setMap(Map<String, String> map) {
		map = map;
	}
	
	public static XezcZmEnu getXezcZmEnu(int code) {
		XezcZmEnu[] enus = XezcZmEnu.values();
		for (XezcZmEnu enu : enus) {
			if (enu.getCode().equals(code)) {
				return enu;
			}
		}
		return null;
	}

	public static Map<String, String> getEnuMap(){
		Map<String, String> map = new HashMap<String, String>();
		XezcZmEnu[] enus = XezcZmEnu.values();
		for (XezcZmEnu enu : enus) {
			map.put(String.valueOf(enu.getCode()), enu.getName());
		}
		return map;
	}
	
}
