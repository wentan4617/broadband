package com.tm.broadband.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BroadbandCapability {

	// Retrieves compatible broadband types
	public String getCapabilityResultByAddress(String address) throws Exception {
		
		// BEGIN GeocodeGenerator
		String geoResult = getHttpResult("http://maps.googleapis.com/maps/api/geocode/xml?address="+address.replace(" ", "%20")+"&sensor=true");
		String lat = geoResult.substring(geoResult.indexOf("<lat>")+5, geoResult.indexOf("</lat>"));
		String lng = geoResult.substring(geoResult.indexOf("<lng>")+5, geoResult.indexOf("</lng>"));
		// END GeocodeGenerator
		
		// BEGIN CapabilityGenerator
		String capResult = getHttpResult("http://chorus-viewer.ufbmaps.co.nz/jsonp/point-details?lat="+lat+"&lng="+lng+"&zoom=16&maplayers=1;3&search_type=S&callback=jQuery1110047775714145973325_1399286171375&_=1399286171411");
		String[] broadbandTypes = { "Broadband > 10 Mbps", "Broadband > 20 Mbps", "Business fibre available" };
		// END CapabilityGenerator

		// BEGIN necessary variables
		String[] broadbandTypes2 = null;
		String finalResult = "";
		Integer counter = 0;
		// END necessary variables

		// Counts final broadband amount
		for (int i = 0; i < broadbandTypes.length; i++) {
			if (capResult.contains(broadbandTypes[i])) {
				counter++;
			}
		}
		
		// Defines array length
		broadbandTypes2 = new String[counter];
		
		// Appends final broadband types
		for (int i = 0; i < broadbandTypes2.length; i++) {
			finalResult += broadbandTypes[i];
			if(i+1 < broadbandTypes2.length){
				finalResult += ",";
			}
		}
		
		return finalResult;
	}
	
	// Retrieves HTTP responded content
	private String getHttpResult(String urlPath) throws Exception {
		URL url = new URL(urlPath);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.connect();
		InputStream inputStream = connection.getInputStream();
		
		// Corresponds character encoding transformation
		Reader reader = new InputStreamReader(inputStream, "UTF-8");
		BufferedReader bufferedReader = new BufferedReader(reader);
		String str = null;
		StringBuffer sb = new StringBuffer();
		
		while ((str = bufferedReader.readLine()) != null) {
			sb.append(str);
		}
		
		reader.close();
		connection.disconnect();
		return sb.toString();
	}
}
