package junit.dao;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiaoerzuche.biz.payment.alipayment.AliPaymentStrategy;
import com.xiaoerzuche.common.core.data.jdbc.Jdbc;

import junit.BaseTest;

public class BalancTest extends BaseTest{
	@Autowired
	private Jdbc jdbc;
	@Autowired
	private AliPaymentStrategy aliPaymentStrategy;
	private static final Logger logger = LoggerFactory.getLogger(BalancTest.class);
	
	@Test
	public void balanc(){
		List<ErroPay> tranNos = this.queryErroPay();
		String result = "";
		for(ErroPay erroPay : tranNos){
			try {
				result = aliPaymentStrategy.query2("",erroPay.getTranNo());
				if(result.indexOf("<is_success>T</is_success>") >0 ){
					result = result.substring(result.indexOf("<trade_status>")+14, result.indexOf("</trade_status>"));
				}else if(result.indexOf("<is_success>F</is_success>") >0 ){
					result = result.substring(result.indexOf("<error>")+8, result.indexOf("</error>"));
				}
				
				erroPay.setResult(result);
				System.out.println(erroPay);
				this.update(erroPay);
				Thread.sleep(200);
			} catch (Exception e) {
				System.out.println(result);
				logger.error(""+e);
			}
		}
		
	}
	
	private List<ErroPay> queryErroPay(){
		String sql = "SELECT * FROM erro_pay where appid = '1548027b5de'";
		return this.jdbc.queryForList(sql, ErroPay.class);
	}
	
	private void update(ErroPay erroPay){
		String sql = "update erro_pay set result = '"+ erroPay.getResult() +"' where query_id="+erroPay.getQueryId();
		this.jdbc.update(sql);
	}
	
//	@Test
	public void queryAli(){
		try {
			String result = aliPaymentStrategy.query2("","2016122621001004000253729068");
			if(result.indexOf("<is_success>T</is_success>") >0 ){
				result = result.substring(result.indexOf("<trade_status>")+14, result.indexOf("</trade_status>"));
			}else if(result.indexOf("<is_success>F</is_success>") >0 ){
				result = result.substring(result.indexOf("<error>")+8, result.indexOf("</error>"));
			}
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
//		String result = "<?xml version=\"1.0\" encoding=\"utf-8\"?><alipay><is_success>T</is_success><request><param name=\"trade_no\">2016092021001004380280045538</param><param name=\"_input_charset\">utf-8</param><param name=\"service\">single_trade_query</param><param name=\"partner\">2088811891419125</param></request><response><trade><buyer_email>15808902843</buyer_email><buyer_id>2088112095076386</buyer_id><discount>0.00</discount><flag_trade_locked>0</flag_trade_locked><gmt_create>2016-09-20 18:27:48</gmt_create><gmt_last_modified_time>2016-12-20 18:29:58</gmt_last_modified_time><gmt_payment>2016-09-20 18:28:03</gmt_payment><is_total_fee_adjust>F</is_total_fee_adjust><operator_role>B</operator_role><out_trade_no>14743672303352567</out_trade_no><payment_type>1</payment_type><price>10000.00</price><quantity>1</quantity><seller_email>xiaoerzuche@hnair.com</seller_email><seller_id>2088811891419125</seller_id><subject>xiaoerzucheOrder</subject><to_buyer_fee>0.00</to_buyer_fee><to_seller_fee>10000.00</to_seller_fee><total_fee>10000.00</total_fee><trade_no>2016092021001004380280045538</trade_no><trade_status>TRADE_FINISHED</trade_status><use_coupon>F</use_coupon></trade></response><sign>3ddee1ae35078ba2a17e8fb99fabfff3</sign><sign_type>MD5</sign_type></alipay>";
		String result = "<?xml version=\"1.0\" encoding=\"utf-8\"?><alipay><is_success>F</is_success><error>TRADE_NOT_EXIST</error></alipay>";
		System.out.println("<error>".length());
		System.out.println(result.substring(result.indexOf("<error>")+8, result.indexOf("</error>")));
//		System.out.println(result.indexOf("<is_success>T</is_success>"));
//		System.out.println(result.indexOf("<is_success>F</is_success>"));
//		System.out.println(result.indexOf("<trade_status>"));
//		System.out.println("<trade_status".length());
//		System.out.println(result.indexOf("</trade_status>"));
//		System.out.println("========="+result.substring(result.indexOf("<trade_status>")+14, result.indexOf("</trade_status>")));
	}
}
