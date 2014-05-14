package com.tm.broadband.util;

import java.io.IOException;
import java.util.Calendar;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

import com.tm.broadband.model.Customer;


public class Test {

	public static void main(String[] args) throws Exception {

		// String[] pwds = new String[] { "1", "5", "9", "8", "7", "6", "4",
		// "3", "2", "q",
		// "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g",
		// "h", "j",
		// "k", "l", "z", "x", "c", "v", "b", "n", "m", "0"};
		// Random random = new Random();
		// System.out.println(pwds[random.nextInt(36)]);
//		String finalResult = BroadbandCapability.getCapabilityResultByAddress("奥克兰New North Road945");
//		System.out.println(finalResult.substring(finalResult.lastIndexOf(",") + 1));
//		
//		System.out.println(ITextFont.arial_normal_6);
//		Customer c = new Customer();
//		c.setFirst_name("<script>asdasdasd");
//		c.setLast_name("<script>asdasdasd");
//		
//		System.out.println(CheckScriptInjection.isScriptInjection(c));;
//		testGzip();
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
	}

//    public static void testGzip() {
//        HttpClient httpClient = new HttpClient();
//        GetMethod getMethod = new GetMethod("http://localhost:8080/broadband/home");
//        try {
//                getMethod.addRequestHeader("accept-encoding", "gzip,deflate");
//                getMethod.addRequestHeader("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; Alexa Toolbar; Maxthon 2.0)");
//                int result = httpClient.executeMethod(getMethod);
//                if (result == 200) {
//                        System.out.println(getMethod.getResponseContentLength());
//                        String html = getMethod.getResponseBodyAsString();
//                        System.out.println(html);
////                        System.out.println(html.getBytes().length);
//                }
//        } catch (HttpException e) {
//                e.printStackTrace();
//        } catch (IOException e) {
//                e.printStackTrace();
//        } finally {
//                getMethod.releaseConnection();
//        }
//    }

}
