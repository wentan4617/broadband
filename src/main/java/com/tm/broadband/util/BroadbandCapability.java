package com.tm.broadband.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BroadbandCapability {

	// Retrieves compatible broadband types
	public static String getCapabilityResultByAddress(String address) throws Exception {
		
		// BEGIN GeocodeGenerator
		String geoResult = getHttpResult("http://maps.googleapis.com/maps/api/geocode/xml?address="+address.replace(" ", "%20")+"&sensor=true");
		String lat = "";
		String lng = "";
		
		// Check location availability
		if(geoResult.indexOf("<lat>") > 0){
			
			// Get coordinates of latitude and longitude
			lat = geoResult.substring(geoResult.indexOf("<lat>")+5, geoResult.indexOf("</lat>"));
			lng = geoResult.substring(geoResult.indexOf("<lng>")+5, geoResult.indexOf("</lng>"));
		}
		// END GeocodeGenerator
		
		// BEGIN CapabilityGenerator
		String capNote = "";
		String capResult = getHttpResult("http://chorus-viewer.ufbmaps.co.nz/jsonp/point-details?lat="+lat+"&lng="+lng+"&zoom=16&maplayers=1;3&search_type=S&callback=jQuery1110047775714145973325_1399286171375&_=1399286171411");
		if(capResult.indexOf("scheduled:<\\/h4><ul><li>") > 0){
			capNote = capResult.substring(capResult.indexOf("scheduled:<\\/h4><ul><li>"));
			capNote = capNote.substring(capNote.indexOf("<li>")+4, capNote.indexOf("<\\/li>"));
			
			// Retrieves note if contained
			if(!"UFB deployment dates for your area are still being developed".equals(capNote) && !"UFB fibre up to 100 Mbps latest forecast date Apr-2014".equals(capNote)){
				capNote = "";
			}
		}
		String[] broadbandTypes = { "Broadband > 10 Mbps", "Broadband > 20 Mbps", "Business fibre available", "UFB fibre up to 100 Mbps by May-2014" };
		// END CapabilityGenerator

		// BEGIN necessary variables
		List<String> broadbandTypes2 = new ArrayList<String>();
		String finalResult = "";
		// END necessary variables

		// Gets available broadband types
		for (int i = 0; i < broadbandTypes.length; i++) {
			if (capResult.contains(broadbandTypes[i])) {
				broadbandTypes2.add(broadbandTypes[i]);
			}
		}
		
		// Appends final broadband types
		for (String broadbandType : broadbandTypes2) {
			finalResult += broadbandType;
			if(!broadbandType.equals(broadbandTypes2.get(broadbandTypes2.size()-1))){
				finalResult += ",";
			}
		}
		
		if(!"".equals(capNote)){
			finalResult += "," + capNote;
		}
		
		return finalResult;
	}
	
	// Retrieves HTTP responded content
	private static String getHttpResult(String urlPath) throws Exception {
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