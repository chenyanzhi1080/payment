package com.xiaoerzuche.biz.payment.enu;

public enum TranResult {
	PROCESSFAIL(0,"受理失败"),
	PROCESS(1,"进行中"),
	SUCCESS(5,"交易成功"),
	FAIL(10,"交易失败");
	// code
	private int code;
	// 名称
	private String name;

	private TranResult(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	} 

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static TranResult getTranResult(int code){
		TranResult[] results = TranResult.values();
		for(TranResult result : results){
			if(result.getCode() == code){
				return result;
			}
		}
		return null;
	}
}
