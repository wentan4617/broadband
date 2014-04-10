package com.tm.broadband.paymentexpress;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class Test {

	public Test() {
		
	}

	public static void main(String[] args) throws ClientProtocolException {
		PxPostRequest ppr = new PxPostRequest();
		ppr.setPostUsername("Cardworld");
		ppr.setPostPassword("yuxm021918247");
		ppr.setCardHolderName("XIUMINYU");
		ppr.setCardNumber("4548601558534208");
		ppr.setAmount("1.00");
		ppr.setDateExpiry("0415");
		ppr.setCvc2("764");
		//ppr.setCvc2Presence("1");
		ppr.setInputCurrency("NZD");
		ppr.setTxnType("Purchase");
		
		String requestXml = ppr.getRequestXml();
		
		
		String url = "https://sec.paymentexpress.com/pxpost.aspx";
		System.out.println(requestXml);
		
		HttpClient client = new DefaultHttpClient();

		// Prepare the POST request
		HttpPost pxpayRequest = new HttpPost(url);
		try {
			pxpayRequest.setEntity(new StringEntity(requestXml));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Execute the request and extract the response
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			String responseBody = client.execute(pxpayRequest, responseHandler);
			System.out.println(responseBody);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
