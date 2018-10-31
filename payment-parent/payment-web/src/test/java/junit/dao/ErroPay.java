package junit.dao;

public class ErroPay {
	private String queryId;
	private String appid;
	private String tranNo;
	private String result;
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getTranNo() {
		return tranNo;
	}
	public void setTranNo(String tranNo) {
		this.tranNo = tranNo;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "ErroPay [queryId=" + queryId + ", appid=" + appid + ", tranNo=" + tranNo + ", result=" + result + "]";
	}
	
}
